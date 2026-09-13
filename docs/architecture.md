# طابور (Taaboor) — Architecture & Backend Design

## 1. نظرة عامة
هذا المستند يوضح كيف يُبنى الـ Backend الحقيقي وراء التطبيق (Android + Web) الموجود في هذا التسليم. الكود الحالي في `/android` يستخدم بيانات وهمية (Mock) محليًا حتى يعمل بدون سيرفر؛ هذا الملف هو خارطة الطريق لربطه ببيانات حقيقية.

## 2. الـ Stack المقترح

| الطبقة | التقنية | السبب |
|---|---|---|
| Mobile | Kotlin + Jetpack Compose (MVVM) | مطبّق فعليًا في الكود المرفق |
| Backend | Firebase (Firestore + Realtime DB + Cloud Functions) أو Supabase (Postgres + Realtime) | الاثنان يدعمان Realtime بدون بنية تحتية معقدة، مناسبين لمرحلة الإطلاق |
| Realtime queue sync | Firebase Realtime Database / Supabase Realtime channels | تحديث لحظي لحالة الطابور لكل المشتركين |
| Local offline cache | Room Database (Android) | يسمح للموظف بإدارة الطابور بدون إنترنت |
| Auth | Firebase Auth / Supabase Auth (Phone, Google, Apple) | يدعم الدخول كزائر + تسجيل اختياري |
| Notifications | Firebase Cloud Messaging | إشعارات "أنت التالي" و"دورك الآن" |
| Web (no-app) | Static HTML/JS (مرفق في `/web`) + نفس الـ API | يعمل بدون تثبيت تطبيق (بند 27 في المواصفات) |

## 3. نموذج البيانات (Core Entities)

```
Business
 ├─ id, name, category, logoUrl, brandColor, welcomeMessage
 ├─ location (lat, lng, address)
 ├─ workingHours[]
 ├─ tokenPrefix (e.g. "A", "T", "C")
 ├─ plan (free | pro | premium)
 └─ settings { allowOnlineBooking, allowQrJoin, maxQueueSize }

Service
 ├─ id, businessId, name, durationMinutes, price, description, iconUrl

Queue
 ├─ id, businessId, name, prefix, status (open|almost_full|full|closed)
 └─ maxSize

QueueEntry (Token)
 ├─ id, queueId, tokenNumber, customerId? (nullable for guest), serviceId
 ├─ status (waiting|called|serving|done|skipped|cancelled)
 ├─ source (app|web|qr|manual)
 ├─ createdAt, calledAt, servedAt

Booking
 ├─ id, businessId, serviceId, customerId?, date, time, status

Employee
 ├─ id, businessId, name, serviceIds[], isAvailable, isWorkingNow

Customer (optional account)
 ├─ id, name?, phone?, favorites[], authProvider
```

## 4. تدفق الطابور اللحظي (Realtime Flow)
1. عميل ينضم للطابور (App / Web / QR / يدويًا من الموظف) → يُنشأ `QueueEntry` جديد بحالة `waiting`.
2. كل تغيير في `QueueEntry` أو ترتيب `Queue` يُبث فورًا لكل المشتركين (Firebase Realtime Database `onValue` أو Supabase `postgres_changes`).
3. عند ضغط الموظف "التالي": يُحدَّث آخر Entry بحالة `done`، ويُستدعى أول Entry بحالة `waiting` → `called`.
4. Cloud Function/Edge Function تراقب التغييرات وترسل Push Notification تلقائيًا عندما يصبح ترتيب العميل ≤ 2.

## 5. العمل بدون إنترنت (Offline Mode)
- شاشة الموظف (Employee Mode) تعمل على Room Database محليًا.
- كل عملية (إضافة عميل، استدعاء، إنهاء) تُسجَّل محليًا مع `pendingSync = true`.
- عند عودة الاتصال: مزامنة تلقائية مع منع Duplicate Tokens عبر مقارنة `localId` + `timestamp` مع السيرفر.

## 6. الصلاحيات (Role-Based Access)
`Customer` → `Employee` → `Manager` → `Owner` → `Admin`، كل دور له مجموعة صلاحيات محددة على مستوى الـ API (Firestore Security Rules / Supabase Row Level Security).

## 7. حساب الوقت المتوقع الذكي (Smart ETA)
```
ETA = Σ(مدة الخدمة لكل عميل أمامك) ÷ عدد الموظفين المتاحين لتلك الخدمة
     معدّلة بمتوسط سرعة الموظف الفعلية (rolling average لآخر 20 عميل)
```
تُعاد الحسبة تلقائيًا مع كل تغيير في الطابور (Cloud Function trigger).

## 8. خطة الإطلاق المقترحة (Milestones)
1. **MVP**: طابور واحد لكل مكان + Token يدوي وأونلاين + Realtime tracking (بدون حجز مسبق).
2. **v1.1**: الحجز المسبق + QR + شاشة انتظار (Queue Display).
3. **v1.2**: تعدد الطوابير + الموظفين + Smart ETA.
4. **v1.3**: Analytics + الاشتراكات (Free/Pro/Premium) + تعدد الفروع.

## 9. ما الذي لم يُبنَ في هذا التسليم
هذا مستند تصميم فقط، وليس كود Backend فعلي — بناء الـ Backend الحقيقي (Firebase project setup, Cloud Functions, Security Rules) يحتاج قرارات منك أولًا: أي مزود (Firebase أو Supabase)، وهل تريد بدء بـ MVP بسيط أولًا. بمجرد ما تحدد، أقدر أبني لك الـ Backend فعليًا خطوة بخطوة.
