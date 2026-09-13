# طابور (Taaboor) — التسليم الأول

## المحتويات

- **/android** — مشروع Android كامل (Kotlin + Jetpack Compose، MVVM)، RTL بالكامل، عربي/إنجليزي.
  يحتوي على 33 شاشة تقريبًا تغطي رحلة العميل بالكامل (Home → Categories → Search → Place →
  Services → Join Queue → Token → Live Queue → Booking → My Queue/Bookings → Favorites → Profile)
  ورحلة صاحب المكان بالكامل (Dashboard → Create Place Wizard → Services/Queue/Employee Management →
  Add Customer → Employee Mode → Queue Display → QR → Analytics → Settings).
  البيانات حاليًا Mock (محلية) لتشتغل الشاشات وتتنقل بينها فورًا بدون سيرفر.

- **/web** — نموذج أولي لصفحة "بدون تطبيق" (QR → اختيار خدمة → Token → متابعة لحظية)، HTML/CSS/JS بسيط،
  افتح `web/index.html` مباشرة في أي متصفح لتجربتها.

- **/docs/architecture.md** — تصميم الـ Backend الحقيقي (Firebase/Supabase)، نموذج البيانات، آلية
  الـ Realtime، العمل Offline، الصلاحيات، وخطة إطلاق مرحلية.

## كيف تفتح مشروع Android
1. افتح Android Studio (Hedgehog أو أحدث).
2. Open → اختر مجلد `android`.
3. اتركه يعمل Gradle Sync (يحتاج اتصال إنترنت لتحميل المكتبات أول مرة).
4. شغّل على أي محاكي أو جهاز حقيقي (Package name: `com.taaboor.app`).

⚠️ ملاحظة: بيئة العمل هنا بدون اتصال إنترنت فلم أستطع تشغيل Gradle Build فعليًا للتأكد من الـ
compile الكامل، لكن الكود مكتوب ومنظم بالكامل وجاهز للفتح المباشر في Android Studio.

## الخطوات التالية المقترحة
1. تجربة المشروع في Android Studio والتأكد من الشاشات.
2. تحديد مزود الـ Backend (Firebase أو Supabase) لبدء ربط البيانات الحقيقية بدل الـ Mock.
3. تصميم الـ Logo فعليًا (حاليًا نص/إيموجي فقط كمكان مؤقت).
4. البدء بـ MVP حسب خطة الإطلاق في `docs/architecture.md`.

## رفع المشروع على GitHub

المشروع مجهّز كـ monorepo واحد (android + web + docs) مع `.gitignore` بيستبعد ملفات الـ build
والملفات الحساسة تلقائيًا. اتبع الخطوات دي من التيرمنال جوه مجلد المشروع:

```bash
# 1) لو مفيش git متثبت أو مش عامل حساب على GitHub لسه، اعمل ده أول مرة بس
git config --global user.name "اسمك"
git config --global user.email "بريدك الإلكتروني"

# 2) داخل مجلد المشروع (اللي فيه android/ و web/ و docs/)
git init
git add .
git commit -m "Initial commit: Taaboor app (Android + Web prototype + architecture docs)"

# 3) روح على github.com واعمل New Repository باسم taaboor (Private أو Public حسب رغبتك)
#    من غير ما تختار "Initialize with README" عشان متعملش تعارض مع الملفات اللي عندك

# 4) اربط الـ repo المحلي بالـ remote اللي على GitHub (هتلاقي الرابط ده في صفحة الـ repo بعد إنشائه)
git remote add origin https://github.com/USERNAME/taaboor.git
git branch -M main
git push -u origin main
```

بعد كده أي تعديل جديد:
```bash
git add .
git commit -m "وصف التعديل"
git push
```

⚠️ تأكد إن ملف `android/app/google-services.json` (هيظهر لما تربط Firebase لاحقًا) *متتسجلش* على
GitHub — الـ `.gitignore` بيستبعده تلقائيًا، بس لو حسّيت إنه ظهر بالغلط في `git status` وقفه فورًا
قبل الـ commit.

