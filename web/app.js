// Taaboor web prototype — vanilla JS, no build step, no dependencies.
// Open index.html directly in any browser to run it.

function $(id){ return document.getElementById(id); }

function toast(msg){
  const t = document.createElement("div");
  t.className = "toast";
  t.textContent = msg;
  document.body.appendChild(t);
  setTimeout(() => t.remove(), 2200);
}

function findPlace(id){ return PLACES.find(p => p.id === id); }

function navigate(screen, params){
  if (state.liveInterval) { clearInterval(state.liveInterval); state.liveInterval = null; }
  state.screen = screen;
  Object.assign(state, params || {});
  render();
  window.scrollTo(0,0);
}

// ---------- Shared bits ----------

function topBar(title, backScreen, backParams){
  return `
    <div class="top-bar">
      ${backScreen ? `<button class="back-btn" onclick="navigate('${backScreen}', ${JSON.stringify(backParams||{}).replace(/"/g,'&quot;')})">→</button>` : ""}
      <h1>${title}</h1>
    </div>`;
}

function customerBottomNav(active){
  const items = [
    ["home","🏠","الرئيسية"], ["my_queue","🎫","دورك"], ["my_bookings","📅","حجوزاتي"],
    ["favorites","❤️","المفضلة"], ["profile","👤","حسابي"]
  ];
  return `<div class="bottom-nav">${items.map(([r,icon,label]) => `
      <div class="nav-item ${active===r?'active':''}" onclick="navigate('${r}')">
        <span class="icon">${icon}</span>${label}
      </div>`).join("")}</div>`;
}

function ownerBottomNav(active){
  const items = [
    ["owner_dashboard","🏠","الرئيسية"], ["queue_management","🎫","الطوابير"],
    ["employee_management","👥","العملاء"], ["analytics","📊","الإحصائيات"], ["owner_settings","⚙️","الإعدادات"]
  ];
  return `<div class="bottom-nav">${items.map(([r,icon,label]) => `
      <div class="nav-item ${active===r?'active':''}" onclick="navigate('${r}')">
        <span class="icon">${icon}</span>${label}
      </div>`).join("")}</div>`;
}

// ---------- Onboarding / role select ----------

function screenRoleSelect(){
  return `
    <div class="screen center" style="padding-top:80px;">
      <div style="font-size:48px;">🎫</div>
      <h1 style="color:var(--primary); margin-top:8px;">طابور</h1>
      <p class="muted">احجز دورك.. وكمّل يومك</p>
      <div style="height:40px;"></div>
      <button class="btn btn-primary" onclick="state.role='customer'; navigate('home')">أنا عميل</button>
      <div style="height:12px;"></div>
      <button class="btn btn-outline" onclick="state.role='owner'; navigate('owner_dashboard')">أنا صاحب مكان</button>
    </div>`;
}

// ---------- Customer: Home ----------

function screenHome(){
  const cats = CATEGORIES.map(([emoji,name]) => `<div class="chip" onclick="navigate('search',{presetQuery:'${name}'})"><div>${emoji}</div>${name}</div>`).join("");
  const places = PLACES.map(placeCardHtml).join("");
  return `
    <div class="screen">
      <h1>أهلاً بك 👋</h1>
      <p class="muted">عايز تخلص إيه النهارده؟</p>
      <div style="height:16px;"></div>
      <div class="search-bar" onclick="navigate('search')">🔍 ابحث عن مكان أو خدمة...</div>
      <div style="height:20px;"></div>
      <div class="chip-row">${cats}</div>
      <div style="height:20px;"></div>
      <h2>الأماكن القريبة منك</h2>
      ${places}
    </div>
    ${customerBottomNav("home")}`;
}

function placeCardHtml(p){
  const fav = state.favorites.has(p.id) ? "❤️" : "🤍";
  return `
    <div class="card place-card" onclick="navigate('place_details',{currentPlaceId:'${p.id}'})">
      <div class="place-emoji">${p.emoji}</div>
      <div style="flex:1;">
        <div class="row-between">
          <strong>${p.name}</strong>
          <span onclick="event.stopPropagation(); toggleFavorite('${p.id}')">${fav}</span>
        </div>
        <div class="muted" style="margin-top:2px;">
          <span class="dot ${p.open?'dot-open':'dot-closed'}"></span>${p.open?'مفتوح':'مغلق'}
          &nbsp; ⭐ ${p.rating} &nbsp; 📍 ${p.distance} كم
        </div>
        ${p.open ? `<div style="color:var(--primary); font-size:13px; margin-top:4px;">👥 ${p.waiting} منتظرين ⏱️ حوالي ${p.eta} دقيقة</div>` : ""}
      </div>
    </div>`;
}

function toggleFavorite(id){
  if (state.favorites.has(id)) state.favorites.delete(id); else state.favorites.add(id);
  render();
}

// ---------- Customer: Search ----------

function screenSearch(){
  const q = (state.presetQuery || "").trim();
  const results = PLACES.filter(p => !q || p.name.includes(q) || p.category.includes(q));
  return `
    <div class="screen">
      ${topBar("ابحث", "home")}
      <input id="search-input" placeholder="ابحث عن مكان أو خدمة..." value="${q}" oninput="onSearchInput(this.value)">
      <div style="height:16px;"></div>
      <div id="search-results">${results.map(placeCardHtml).join("") || '<p class="muted center">لا نتائج</p>'}</div>
    </div>`;
}

function onSearchInput(val){
  state.presetQuery = val;
  const results = PLACES.filter(p => !val || p.name.includes(val) || p.category.includes(val));
  $("search-results").innerHTML = results.map(placeCardHtml).join("") || '<p class="muted center">لا نتائج</p>';
}

// ---------- Customer: Place details ----------

function screenPlaceDetails(){
  const p = findPlace(state.currentPlaceId);
  if (!p) return `<div class="screen">لم يتم العثور على المكان.</div>`;
  const services = p.services.map(s => `
    <div class="card row-between" style="margin-bottom:8px;">
      <div><strong>${s.name}</strong><div class="muted">${s.duration} دقيقة</div></div>
      <div style="color:var(--success); font-weight:700;">${s.price} AED</div>
    </div>`).join("");
  return `
    <div class="screen">
      ${topBar(p.name, "home")}
      <div class="muted">⭐ ${p.rating} &nbsp; ${p.open?'🟢 مفتوح الآن':'🔴 مغلق'} &nbsp; 📍 ${p.distance} كم</div>
      <div style="height:16px;"></div>
      <h2>الخدمات</h2>
      ${services}
      <div class="card-soft" style="margin:16px 0;">
        <strong>حالة الطابور الآن</strong>
        <div class="muted" style="margin-top:6px;">${p.waiting} أشخاص منتظرين</div>
        <div style="color:var(--primary);">متوسط الانتظار ${p.eta} دقيقة</div>
      </div>
      <button class="btn btn-primary" onclick="navigate('join_queue',{currentPlaceId:'${p.id}'})">🎫 احجز دوري الآن</button>
      <div style="height:10px;"></div>
      <button class="btn btn-outline" onclick="navigate('booking',{currentPlaceId:'${p.id}'})">📅 احجز موعد</button>
    </div>`;
}

// ---------- Customer: Join queue → pick service ----------

function screenJoinQueue(){
  const p = findPlace(state.currentPlaceId);
  if (!state.selectedServiceId && p.services[0]) state.selectedServiceId = p.services[0].id;
  const services = p.services.map(s => `
    <div class="card service-item ${s.id===state.selectedServiceId?'selected':''}" onclick="state.selectedServiceId='${s.id}'; render()">
      <div><strong>${s.name}</strong><div class="muted">${s.duration} دقيقة</div></div>
      <div>${s.price} AED</div>
    </div>`).join("");
  return `
    <div class="screen">
      ${topBar("ماذا تريد؟", "place_details", {currentPlaceId:p.id})}
      <p class="muted">${p.name}</p>
      <div style="height:12px;"></div>
      ${services}
      <div style="height:16px;"></div>
      <button class="btn btn-primary" onclick="issueToken()">🎫 احجز دوري الآن</button>
    </div>`;
}

function issueToken(){
  const p = findPlace(state.currentPlaceId);
  const service = p.services.find(s => s.id === state.selectedServiceId) || p.services[0];
  state.myToken = {
    tokenNumber: "A0" + (27 + Math.floor(Math.random()*5)),
    serviceName: service.name,
    placeName: p.name,
    peopleAhead: 5
  };
  toast("تم حجز دورك بنجاح 🎫");
  navigate("token");
}

// ---------- Customer: Token / live queue ----------

function screenToken(){
  const t = state.myToken;
  if (!t) return `<div class="screen">مفيش دور نشط حاليًا.</div>`;
  return `
    <div class="screen">
      <div class="card-soft center">
        <div class="token-number">${t.tokenNumber}</div>
        <div class="muted">${t.serviceName}</div>
        <div class="row-between" style="margin-top:20px;">
          <div><div class="muted">أمامك</div><strong>${t.peopleAhead} أشخاص</strong></div>
          <div><div class="muted">الرقم الحالي</div><strong>A022</strong></div>
          <div><div class="muted">الوقت المتوقع</div><strong>${t.peopleAhead*4} دقيقة</strong></div>
        </div>
      </div>
      <div style="height:24px;"></div>
      <button class="btn btn-primary" onclick="navigate('live_queue')">تابع دورك مباشرة</button>
      <div style="height:10px;"></div>
      <button class="btn btn-outline" onclick="toast('تم نسخ رابط المشاركة')">مشاركة الدور</button>
    </div>`;
}

function screenLiveQueue(){
  const t = state.myToken;
  if (!t) return `<div class="screen">مفيش دور نشط حاليًا.</div>`;
  setTimeout(startLiveCountdown, 50);
  return `
    <div class="screen">
      <h2 id="live-status" class="center">باقي ${t.peopleAhead} أشخاص قبلك</h2>
      <div class="progress"><div class="progress-fill" id="progress-fill" style="width:0%"></div></div>
      <p class="muted center">A022 → A023 → A024 → A025 → A026 → ${t.tokenNumber}</p>
    </div>`;
}

function startLiveCountdown(){
  if (state.liveInterval) return;
  const total = state.myToken.peopleAhead;
  const tick = () => {
    const statusEl = $("live-status"), fillEl = $("progress-fill");
    if (!statusEl) { clearInterval(state.liveInterval); state.liveInterval = null; return; }
    const ahead = state.myToken.peopleAhead;
    fillEl.style.width = ((total-ahead)/total*100) + "%";
    if (ahead === 0) statusEl.textContent = "دورك الآن 🎉";
    else if (ahead === 1) statusEl.textContent = "أنت التالي 🔔";
    else statusEl.textContent = `باقي ${ahead} أشخاص قبلك`;
  };
  tick();
  state.liveInterval = setInterval(() => {
    if (state.myToken.peopleAhead <= 0) { clearInterval(state.liveInterval); state.liveInterval = null; return; }
    state.myToken.peopleAhead -= 1;
    if (state.myToken.peopleAhead === 1) toast("أنت التالي 🔔");
    if (state.myToken.peopleAhead === 0) toast("دورك الآن 🎉");
    tick();
  }, 2500);
}

// ---------- Customer: Booking ----------

function screenBooking(){
  const p = findPlace(state.currentPlaceId);
  if (!state.selectedTime) state.selectedTime = TIME_SLOTS[3];
  const slots = TIME_SLOTS.map(t => `
    <div class="chip" style="${t===state.selectedTime?'background:var(--primary); color:#fff;':''}" onclick="state.selectedTime='${t}'; render()">${t}</div>`).join("");
  return `
    <div class="screen">
      ${topBar("احجز موعد", "place_details", {currentPlaceId:p.id})}
      <p class="muted">${p.name}</p>
      <div style="height:12px;"></div>
      <h2>اختر الوقت</h2>
      <div class="chip-row">${slots}</div>
      <div style="height:24px;"></div>
      <button class="btn btn-primary" onclick="confirmBooking()">تأكيد الحجز</button>
    </div>`;
}

function confirmBooking(){
  const p = findPlace(state.currentPlaceId);
  const service = p.services[0];
  const booking = { id:"b"+(state.myBookings.length+1), place:p.name, service:service.name, date:"السبت", time:state.selectedTime };
  state.myBookings.push(booking);
  navigate("booking_confirmation", {lastBooking: booking});
}

function screenBookingConfirmation(){
  const b = state.lastBooking;
  return `
    <div class="screen center">
      <div style="font-size:40px; color:var(--success);">✓</div>
      <h1>تم الحجز بنجاح ✓</h1>
      <div style="height:12px;"></div>
      <p class="muted">الخدمة: ${b.service}</p>
      <p class="muted">التاريخ: ${b.date}</p>
      <p class="muted">الوقت: ${b.time}</p>
      <p class="muted">Booking ID: ${b.id}</p>
      <div style="height:24px;"></div>
      <button class="btn btn-primary" onclick="navigate('home')">تم</button>
    </div>`;
}

// ---------- Customer: My Queue / My Bookings / Favorites / Profile ----------

function screenMyQueue(){
  if (state.myToken && state.myToken.peopleAhead >= 0){
    return `<div>${screenLiveQueue()}</div>${customerBottomNav("my_queue")}`;
  }
  return `
    <div class="screen center" style="padding-top:60px;">
      <div style="font-size:36px;">📭</div>
      <p class="muted" style="margin:12px 0;">لسه ما حجزتش دور</p>
      <button class="btn btn-primary" onclick="navigate('home')">ابحث عن مكان</button>
    </div>
    ${customerBottomNav("my_queue")}`;
}

function screenMyBookings(){
  if (state.myBookings.length === 0){
    return `
      <div class="screen center" style="padding-top:60px;">
        <div style="font-size:36px;">📭</div>
        <p class="muted" style="margin:12px 0;">مفيش حجوزات لحد دلوقتي</p>
        <button class="btn btn-primary" onclick="navigate('home')">احجز موعد</button>
      </div>
      ${customerBottomNav("my_bookings")}`;
  }
  const list = state.myBookings.map(b => `
    <div class="card" style="margin-bottom:10px;">
      <strong>${b.place}</strong>
      <div class="muted">${b.service} • ${b.date} ${b.time}</div>
      <div style="color:var(--success);">مؤكد</div>
    </div>`).join("");
  return `<div class="screen"><h1>حجوزاتي</h1><div style="height:12px;"></div>${list}</div>${customerBottomNav("my_bookings")}`;
}

function screenFavorites(){
  const favs = PLACES.filter(p => state.favorites.has(p.id));
  if (favs.length === 0){
    return `
      <div class="screen center" style="padding-top:60px;">
        <div style="font-size:36px;">📭</div>
        <p class="muted" style="margin:12px 0;">لسه معندكش أماكن مفضلة</p>
        <button class="btn btn-primary" onclick="navigate('home')">ابحث عن مكان</button>
      </div>
      ${customerBottomNav("favorites")}`;
  }
  return `<div class="screen"><h1>المفضلة</h1><div style="height:12px;"></div>${favs.map(placeCardHtml).join("")}</div>${customerBottomNav("favorites")}`;
}

function screenProfile(){
  return `
    <div class="screen">
      <h1>حسابي</h1>
      <div style="height:12px;"></div>
      <div class="card-soft row gap-12">
        <div style="width:48px;height:48px;border-radius:50%;background:#fff;"></div>
        <div><strong>زائر</strong><div class="muted">سجّل الدخول لحفظ حجوزاتك ومفضلتك</div></div>
      </div>
      <div style="height:20px;"></div>
      <button class="btn btn-outline" onclick="navigate('owner_dashboard')">لدي مكان عمل؟ انتقل لوضع صاحب المكان</button>
    </div>
    ${customerBottomNav("profile")}`;
}

// ---------- Owner: Dashboard ----------

function screenOwnerDashboard(){
  const s = OWNER_STATS;
  const queues = OWNER_QUEUES.map(q => `
    <div class="card row-between" style="margin-bottom:8px;">
      <strong>${q.prefix} — ${q.name}</strong>
      <span style="color:var(--primary);">${q.waiting} منتظر</span>
    </div>`).join("");
  return `
    <div class="screen">
      <h1>اليوم</h1>
      <div style="height:12px;"></div>
      <div class="row gap-8">
        <div class="stat-box"><strong>${s.customers}</strong><span class="muted">عميل</span></div>
        <div class="stat-box"><strong>${s.turns}</strong><span class="muted">دور</span></div>
        <div class="stat-box"><strong>${s.bookings}</strong><span class="muted">حجز</span></div>
      </div>
      <div style="height:8px;"></div>
      <div class="row gap-8">
        <div class="stat-box"><strong>${s.avgWait} د</strong><span class="muted">متوسط انتظار</span></div>
        <div class="stat-box"><strong>${s.activeEmployees}</strong><span class="muted">موظفين نشطين</span></div>
      </div>
      <div style="height:20px;"></div>
      <h2>الطوابير الحالية</h2>
      ${queues}
      <div style="height:8px;"></div>
      <button class="btn btn-primary" onclick="navigate('queue_management')">إدارة الطابور</button>
      <div style="height:10px;"></div>
      <button class="btn btn-outline" onclick="navigate('create_place')">+ إنشاء مكان جديد</button>
    </div>
    ${ownerBottomNav("owner_dashboard")}`;
}

// ---------- Owner: Create place wizard ----------

const WIZARD_STEPS = ["اسم المكان","نوع النشاط","الموقع","ساعات العمل","الخدمات","طريقة الطابور"];

function screenCreatePlace(){
  const step = state.wizardStep || 0;
  const pct = Math.round(((step+1)/WIZARD_STEPS.length)*100);
  return `
    <div class="screen">
      <div class="wizard-progress"><div class="wizard-progress-fill" style="width:${pct}%"></div></div>
      ${topBar("الخطوة " + (step+1), "owner_dashboard")}
      <p class="muted">${WIZARD_STEPS[step]}</p>
      <input placeholder="${WIZARD_STEPS[step]}">
      <div style="height:20px;"></div>
      <button class="btn btn-primary" onclick="wizardNext()">${step===WIZARD_STEPS.length-1?'ابدأ استقبال العملاء':'التالي'}</button>
    </div>`;
}

function wizardNext(){
  const step = state.wizardStep || 0;
  if (step === WIZARD_STEPS.length-1){
    state.wizardStep = 0;
    toast("تم إنشاء المكان بنجاح");
    navigate("owner_dashboard");
  } else {
    state.wizardStep = step+1;
    render();
  }
}

// ---------- Owner: Services management ----------

function screenServicesManagement(){
  const services = PLACES[0].services.map(s => `
    <div class="card row-between" style="margin-bottom:8px;">
      <div><strong>${s.name}</strong><div class="muted">${s.duration} دقيقة • ${s.price} AED</div></div>
      <div class="row gap-8">
        <button class="btn-sm" onclick="toast('تعديل الخدمة')">✏️</button>
        <button class="btn-sm" onclick="toast('تم حذف الخدمة')">🗑️</button>
      </div>
    </div>`).join("");
  return `
    <div class="screen">
      ${topBar("الخدمات", "owner_dashboard")}
      ${services}
      <button class="btn btn-outline" onclick="toast('إضافة خدمة جديدة')">+ إضافة خدمة</button>
    </div>
    ${ownerBottomNav("owner_dashboard")}`;
}

// ---------- Owner: Queue management ----------

function screenQueueManagement(){
  const statusLabel = { open:["🟢 مفتوح","status-open"], almost:["🟡 قريب من الامتلاء","status-almost"], full:["🔴 ممتلئ","status-full"], closed:["⚫ مغلق","muted"] };
  const queues = OWNER_QUEUES.map(q => {
    const pair = statusLabel[q.status];
    const label = pair[0], cls = pair[1];
    return `
      <div class="card" style="margin-bottom:10px;">
        <div class="row-between">
          <strong>${q.prefix} — ${q.name}</strong>
          <span class="${cls}">${label}</span>
        </div>
        <div class="muted">${q.waiting} منتظر</div>
        <div class="row gap-8" style="margin-top:10px;">
          <button class="btn-sm" onclick="toast('تم استدعاء التالي')">التالي</button>
          <button class="btn-sm" onclick="toast('تم تخطي العميل')">تخطي</button>
          <button class="btn-sm" onclick="toast('تم الاستدعاء مرة أخرى')">استدعاء مرة أخرى</button>
        </div>
      </div>`;
  }).join("");
  return `
    <div class="screen">
      ${topBar("إدارة الطابور", "owner_dashboard")}
      ${queues}
      <button class="btn btn-primary" onclick="navigate('add_customer')">+ إضافة عميل</button>
      <div style="height:10px;"></div>
      <button class="btn btn-outline" onclick="navigate('queue_display')">فتح شاشة الانتظار</button>
      <div style="height:10px;"></div>
      <button class="btn btn-outline" onclick="navigate('qr_screen')">عرض QR المكان</button>
      <div style="height:10px;"></div>
      <button class="btn btn-outline" onclick="navigate('employee_mode')">وضع الموظف</button>
    </div>
    ${ownerBottomNav("queue_management")}`;
}

// ---------- Owner: Employee management ----------

function screenEmployeeManagement(){
  const list = OWNER_EMPLOYEES.map(e => `
    <div class="card row-between" style="margin-bottom:8px;">
      <div>
        <strong>${e.name}</strong>
        <div class="muted">${e.services.join(" • ")}</div>
        <div class="muted">${e.served} عميل اليوم</div>
      </div>
      <span class="${e.working?'status-open':'muted'}">${e.working?'🟢 يعمل الآن':'⚪ غير متاح'}</span>
    </div>`).join("");
  return `
    <div class="screen">
      ${topBar("العملاء / الموظفين", "owner_dashboard")}
      <h2>الموظفين</h2>
      ${list}
      <button class="btn btn-outline" onclick="toast('إضافة موظف جديد')">+ إضافة موظف</button>
    </div>
    ${ownerBottomNav("employee_management")}`;
}

// ---------- Owner: Add customer ----------

function screenAddCustomer(){
  return `
    <div class="screen">
      ${topBar("إضافة عميل", "queue_management")}
      <input id="ac-name" placeholder="اسم العميل (اختياري)">
      <div style="height:10px;"></div>
      <input id="ac-service" placeholder="الخدمة" value="قص شعر">
      <div style="height:20px;"></div>
      <button class="btn btn-primary" onclick="addCustomerConfirm()">إضافة للطابور</button>
    </div>`;
}

function addCustomerConfirm(){
  toast("تمت إضافة العميل — A031");
  navigate("queue_management");
}

// ---------- Owner: Employee mode ----------

function screenEmployeeMode(){
  return `
    <div class="screen center" style="padding-top:60px;">
      <div class="token-number">A027</div>
      <p class="muted">قص شعر</p>
      <div style="height:32px;"></div>
      <div class="row gap-12">
        <button class="employee-btn" onclick="toast('تم الانتهاء ✅')">✅ تم الانتهاء</button>
        <button class="employee-btn" onclick="toast('التالي ⏭️')">⏭️ التالي</button>
      </div>
      <div style="height:12px;"></div>
      <div class="row gap-12">
        <button class="employee-btn" onclick="toast('تم التخطي ⏸️')">⏸️ تخطي</button>
        <button class="employee-btn" onclick="toast('تم الاستدعاء 🔄')">🔄 استدعاء مرة أخرى</button>
      </div>
      <div style="height:24px;"></div>
      <button class="btn btn-outline" onclick="navigate('queue_management')">رجوع</button>
    </div>`;
}

// ---------- Owner: Queue display (TV screen) ----------

function screenQueueDisplay(){
  return `
    <div class="screen center" style="background:#10182B; min-height:100vh; margin:-20px -20px -78px -20px; padding:60px 20px; color:#fff;">
      <div style="font-size:72px; font-weight:800;">A027</div>
      <p style="color:#B7C3E0;">توجه إلى كرسي 2</p>
      <div style="height:32px;"></div>
      <p style="color:#6E7BA6; font-size:13px;">التالي</p>
      <div class="row gap-12 center" style="justify-content:center; font-size:22px;">
        <span>A028</span><span>A029</span><span>A030</span>
      </div>
      <div style="height:32px;"></div>
      <button class="btn btn-outline" style="border-color:#6E7BA6; color:#fff;" onclick="navigate('queue_management')">رجوع</button>
    </div>`;
}

// ---------- Owner: QR screen ----------

function screenQR(){
  return `
    <div class="screen center">
      ${topBar("QR المكان", "queue_management")}
      <p><strong>Barber House</strong></p>
      <div style="height:16px;"></div>
      <div class="qr-box">▦</div>
      <div style="height:16px;"></div>
      <p class="muted">اطبع هذا الكود وضعه عند المدخل — العملاء يمسحونه لأخذ دورهم مباشرة بدون تطبيق</p>
    </div>`;
}

// ---------- Owner: Analytics ----------

function screenAnalytics(){
  const rows = ANALYTICS.map(pair => `
    <div class="card-soft row-between" style="margin-bottom:8px;">
      <span class="muted">${pair[0]}</span><strong>${pair[1]}</strong>
    </div>`).join("");
  return `<div class="screen"><h1>الإحصائيات</h1><div style="height:12px;"></div>${rows}</div>${ownerBottomNav("analytics")}`;
}

// ---------- Owner: Settings ----------

function screenOwnerSettings(){
  const rows = SETTINGS_ITEMS.map(item => {
    const click = item === "الخدمات" ? `onclick="navigate('services_management')" style="cursor:pointer;"` : `onclick="toast('${item}')" style="cursor:pointer;"`;
    return `<div class="settings-item" ${click}>${item}<span class="muted">›</span></div>`;
  }).join("");
  return `<div class="screen"><h1>الإعدادات</h1><div style="height:8px;"></div>${rows}</div>${ownerBottomNav("owner_settings")}`;
}

// ---------- Router ----------

const SCREENS = {
  role_select: screenRoleSelect,
  home: screenHome,
  search: screenSearch,
  place_details: screenPlaceDetails,
  join_queue: screenJoinQueue,
  token: screenToken,
  live_queue: screenLiveQueue,
  booking: screenBooking,
  booking_confirmation: screenBookingConfirmation,
  my_queue: screenMyQueue,
  my_bookings: screenMyBookings,
  favorites: screenFavorites,
  profile: screenProfile,
  owner_dashboard: screenOwnerDashboard,
  create_place: screenCreatePlace,
  services_management: screenServicesManagement,
  queue_management: screenQueueManagement,
  employee_management: screenEmployeeManagement,
  add_customer: screenAddCustomer,
  employee_mode: screenEmployeeMode,
  queue_display: screenQueueDisplay,
  qr_screen: screenQR,
  analytics: screenAnalytics,
  owner_settings: screenOwnerSettings,
};

function render(){
  const fn = SCREENS[state.screen] || screenRoleSelect;
  $("app").innerHTML = fn();
}

render();
