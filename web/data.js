// Mock data layer for the Taaboor web prototype.
// This stands in for the real backend described in /docs/architecture.md.

const CATEGORIES = [
  ["💈","حلاق"], ["💇","صالون"], ["🩺","عيادات"], ["🍽️","مطاعم"], ["☕","كافيهات"],
  ["🚗","غسيل سيارات"], ["🔧","صيانة"], ["🛍️","محلات"], ["✂️","خياطة"], ["🏪","خدمات أخرى"]
];

const PLACES = [
  { id:"p1", name:"Barber House", category:"حلاق", emoji:"💈", rating:4.8, open:true, distance:1.2, waiting:7, eta:25,
    services:[
      {id:"s1", name:"قص شعر", duration:20, price:30},
      {id:"s2", name:"قص + ذقن", duration:35, price:50},
      {id:"s3", name:"ذقن", duration:15, price:20}
    ]},
  { id:"p2", name:"Cedar Barber", category:"حلاق", emoji:"💈", rating:4.6, open:true, distance:2.4, waiting:3, eta:12,
    services:[{id:"s1", name:"قص شعر", duration:20, price:25}]},
  { id:"p3", name:"Cloud Coffee", category:"كافيهات", emoji:"☕", rating:4.7, open:true, distance:0.6, waiting:4, eta:10,
    services:[{id:"s4", name:"قهوة مختصة", duration:8, price:18}, {id:"s5", name:"فطار", duration:15, price:35}]},
  { id:"p4", name:"Dr. Layla Clinic", category:"عيادات", emoji:"🩺", rating:4.9, open:false, distance:3.1, waiting:0, eta:0,
    services:[{id:"s6", name:"كشف عام", duration:20, price:150}]},
  { id:"p5", name:"Shine Car Wash", category:"غسيل سيارات", emoji:"🚗", rating:4.5, open:true, distance:1.8, waiting:9, eta:30,
    services:[{id:"s7", name:"غسيل خارجي", duration:20, price:40}, {id:"s8", name:"غسيل كامل", duration:45, price:90}]}
];

const OWNER_QUEUES = [
  { id:"q1", prefix:"A", name:"قص شعر", waiting:12, status:"open" },
  { id:"q2", prefix:"B", name:"ذقن", waiting:4, status:"open" },
  { id:"q3", prefix:"C", name:"سريع", waiting:2, status:"almost" }
];

const OWNER_EMPLOYEES = [
  { id:"e1", name:"أحمد", services:["قص شعر","قص + ذقن"], working:true, served:14 },
  { id:"e2", name:"محمد", services:["ذقن"], working:true, served:9 },
  { id:"e3", name:"علي", services:["قص شعر"], working:false, served:0 }
];

const OWNER_STATS = { customers:87, turns:73, bookings:14, avgWait:16, activeEmployees:3 };

const ANALYTICS = [
  ["اليوم","87 عميل"], ["هذا الأسبوع","423 عميل"], ["متوسط الانتظار","16 دقيقة"],
  ["أكثر خدمة طلبًا","قص شعر"], ["أكثر وقت ازدحامًا","6:00 PM – 8:00 PM"], ["نسبة الحجوزات","32%"]
];

const SETTINGS_ITEMS = [
  "اسم الطوابير و Prefix الأرقام","الخدمات","ألوان المكان و Logo","رسالة الترحيب","ساعات العمل",
  "الموظفين","عدد الطوابير","السماح بالحجز Online","السماح بالحجز من QR","الحد الأقصى للطابور",
  "رسائل Notifications","شاشة الانتظار","الاشتراك (Free / Pro / Premium)","المساعدة"
];

const TIME_SLOTS = ["09:00","09:30","10:00","10:30","11:00"];

// ---- Mutable app state (in-memory only; resets on page reload) ----
const state = {
  role: null,          // "customer" | "owner"
  screen: "role_select",
  currentPlaceId: null,
  selectedServiceId: null,
  myToken: null,       // {tokenNumber, serviceName, placeName, peopleAhead}
  myBookings: [],
  favorites: new Set(),
  liveInterval: null,
};
