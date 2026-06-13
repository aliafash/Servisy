package com.maw

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.serialization.json.*
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class MainViewModel : ViewModel() {

    private val firestore: FirebaseFirestore? by lazy {
        try {
            FirebaseFirestore.getInstance()
        } catch (e: Exception) {
            Log.e("MainViewModel", "Firestore failed to initialize: ${e.message}")
            null
        }
    }

    private val defaultCategories = listOf(
        Category("electricity", "كهرباء وتمديدات", "Electrical Works", "⚡", 1),
        Category("plumbing", "سباكة وصيانة صحية", "Plumbing Services", "🔧", 2),
        Category("maintenance", "صيانة عامة وأعطال", "General Maintenance", "🛠️", 3),
        Category("carpentry", "نجارة وأثاث", "Carpentry & Decor", "🪚", 4),
        Category("conditioning", "تكييف وتبريد", "AC & Refrigeration", "❄️", 5),
        Category("construction", "مقاولات وبناء", "Construction & Paints", "🧱", 6),
        Category("computers", "برمجة وصيانة هواتف", "Mobile & PC Maintenance", "💻", 7),
        Category("medicine", "الطب والرعاية الصحية", "Medicine & Healthcare", "🩺", 8),
        Category("education", "التعليم والتدريس", "Education & Teaching", "🎓", 9),
        Category("law", "المحاماة والاستشارات القانونية", "Law & Legal Services", "⚖️", 10),
        Category("engineering", "الهندسة والاستشارات الفنية", "Engineering & Consulting", "📐", 11),
        Category("transport", "النقل وشحن البضائع", "Transport & Shipping", "🚚", 12),
        Category("dentistry", "طب وجراحة الأسنان", "Dentistry Services", "🦷", 13, "medicine"),
        Category("pharmacy", "الصيدلة والأدوية", "Pharmacy & Medicine", "💊", 14, "medicine"),
        Category("languages_edu", "تعليم لغات أجنبية", "Foreign Languages", "🗣️", 15, "education"),
        Category("school_tutoring", "مدرسين وتقوية خصوصي", "Tutoring", "📖", 16, "education"),
        Category("architect_eng", "هندسة معمارية وتصميم", "Architecture", "🏗️", 17, "engineering"),
        Category("software_eng", "هندسة برمجيات وتقنية", "Software Engineering", "💻", 18, "engineering")
    )

    private val defaultCities = listOf(
        City("sanaa", "صنعاء", "Sanaa"),
        City("aden", "عدن", "Aden"),
        City("taiz", "تعز", "Taiz"),
        City("hodeidah", "الحديدة", "Hodeidah"),
        City("hadramout", "حضرموت", "Hadramout"),
        City("ibb", "إب", "Ibb")
    )

    private val defaultProviders = listOf(
        Provider("1", "المهندس أحمد صالح", "electricity", "sanaa", "777654321", "خبير تمديدات وتأسيس لوحات ذكية وصيانة أعطال منزلية", "شارع حدة مقابل الرشيد", 4.9, true, true, false, true, imageUrl = ""),
        Provider("2", "المقاول يحيى مسعد", "construction", "aden", "733987654", "مقاول تشطيبات داخلية وخارجية وأعمال ديكور ودهانات حديثة", "المنصورة الشارع العام", 4.7, true, true, false, false, imageUrl = ""),
        Provider("3", "الفني محمد الحاشدي", "conditioning", "taiz", "711234567", "صيانة تكييف مركزي ومكيفات اسبليت وشحن فريون أصلي", "شارع جمال بجانب بنك اليمن", 4.8, true, false, false, true, imageUrl = ""),
        Provider("4", "الأستاذ خالد الوصابي", "computers", "sanaa", "771223344", "برمجة وتخطيط شبكات وصيانة هواتف ذكية وأجهزة كمبيوتر", "شارع الدائري بجوار الجامعة", 4.9, true, false, true, false, imageUrl = ""),
        Provider("5", "الدكتور أمين الصبري", "medicine", "sanaa", "771122333", "استشاري طب وجراحة العيون وجراحات الليزك الدقيقة وتصحيح النظر", "شارع الزبيري أمام المستشفى الجمهوري", 4.9, true, true, false, true, imageUrl = ""),
        Provider("6", "الأستاذ كمال الشرعبي", "education", "taiz", "735566777", "مدرس أول مادة الرياضيات والفيزياء ومراجعات شاملة لطلاب الثانوية العامة", "حي جمال بجوار معهد اللغات الدولي", 4.8, true, false, false, true, imageUrl = ""),
        Provider("7", "المحامي عادل الجلال", "law", "sanaa", "770099887", "متخصص في صياغة العقود وتأسيس الشركات وقضايا الأراضي والنزاعات المدنية", "شارع حدة عمارة الأمل الدور الثالث", 4.9, true, true, false, false, imageUrl = ""),
        Provider("8", "المهندسة غيداء العريقي", "engineering", "aden", "734455661", "تصميم معماري وتخطيط داخلي وإعداد المخططات والرسومات الهندسية", "خور مكسر الشارع الخلفي أمام النيابة", 4.7, true, false, false, false, imageUrl = "")
    )

    private val defaultReviews = listOf(
        Review("r1", "1", "أبوجلال", 5, "شغل نظيف وسرعة في الحضور أنصح بالتعامل معه", System.currentTimeMillis()),
        Review("r2", "1", "أمجد حميد", 4, "ممتاز جداً وفاهم شغله صح", System.currentTimeMillis()),
        Review("r3", "2", "عدنان علي", 5, "بناء على أعلى كفاءة وأمانة وإخلاص جزاك الله خير", System.currentTimeMillis()),
        Review("r4", "3", "رأفت وجدي", 5, "دقة في الموعد وأسعار مناسبة وصيانة احترافية", System.currentTimeMillis())
    )

    // State Flows
    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    private val _categoriesState = MutableStateFlow<List<Category>>(defaultCategories)
    val categoriesState: StateFlow<List<Category>> = _categoriesState.asStateFlow()

    private val _citiesState = MutableStateFlow<List<City>>(defaultCities)
    val citiesState: StateFlow<List<City>> = _citiesState.asStateFlow()

    private val _providers = MutableStateFlow<List<Provider>>(defaultProviders)
    val providers: StateFlow<List<Provider>> = _providers.asStateFlow()

    private val _pendingRequests = MutableStateFlow<List<PendingProvider>>(emptyList())
    val pendingRequests: StateFlow<List<PendingProvider>> = _pendingRequests.asStateFlow()

    private val _reviewsState = MutableStateFlow<List<Review>>(defaultReviews)
    val reviewsState: StateFlow<List<Review>> = _reviewsState.asStateFlow()

    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()

    private val _reports = MutableStateFlow<List<Report>>(emptyList())
    val reports: StateFlow<List<Report>> = _reports.asStateFlow()

    private val _notifications = MutableStateFlow<List<UserNotification>>(emptyList())
    val notifications: StateFlow<List<UserNotification>> = _notifications.asStateFlow()

    private val _adminAccounts = MutableStateFlow<List<AdminAccount>>(emptyList())
    val adminAccounts: StateFlow<List<AdminAccount>> = _adminAccounts.asStateFlow()

    private val _auditLogs = MutableStateFlow<List<AuditLog>>(emptyList())
    val auditLogs: StateFlow<List<AuditLog>> = _auditLogs.asStateFlow()

    private val _banners = MutableStateFlow<List<Banner>>(emptyList())
    val banners: StateFlow<List<Banner>> = _banners.asStateFlow()

    private val _relations = MutableStateFlow<List<ProviderCategoryRelation>>(emptyList())
    val relations: StateFlow<List<ProviderCategoryRelation>> = _relations.asStateFlow()

    private val _geminiMessages = MutableStateFlow<List<Pair<String, Boolean>>>(emptyList())
    val geminiMessages: StateFlow<List<Pair<String, Boolean>>> = _geminiMessages.asStateFlow()

    private val _isGeminiThinking = MutableStateFlow(false)
    val isGeminiThinking: StateFlow<Boolean> = _isGeminiThinking.asStateFlow()

    private val _isAdminLoggedIn = MutableStateFlow(false)
    val isAdminLoggedIn: StateFlow<Boolean> = _isAdminLoggedIn.asStateFlow()

    private val _loggedInUsername = MutableStateFlow("")
    val loggedInUsername: StateFlow<String> = _loggedInUsername.asStateFlow()

    private val _currentChatRoomId = MutableStateFlow<String?>(null)
    val currentChatRoomId: MutableStateFlow<String?> = _currentChatRoomId

    private val _navigationTargetTab = MutableStateFlow<String?>(null)
    val navigationTargetTab: StateFlow<String?> = _navigationTargetTab.asStateFlow()

    private val listeners = mutableListOf<ListenerRegistration>()

    init {
        setupFirebaseRealtimeListener()
    }

    private fun setupFirebaseRealtimeListener() {
        val db = firestore ?: return

        try {
            listeners.add(db.collection("settings").document("global").addSnapshotListener { snapshot, _ ->
                if (snapshot != null && snapshot.exists()) {
                    try {
                        val sett = snapshot.toObject(AppSettings::class.java)
                        if (sett != null) _settings.value = sett
                    } catch (e: Exception) {
                        Log.e("Firebase", "Failed parse settings", e)
                    }
                }
            })

            listeners.add(db.collection("categories").addSnapshotListener { snapshot, _ ->
                if (snapshot != null && !snapshot.isEmpty) {
                    val list = snapshot.toObjects(Category::class.java)
                    _categoriesState.value = list.sortedBy { it.order }
                } else {
                    _categoriesState.value = defaultCategories
                }
            })

            listeners.add(db.collection("cities").addSnapshotListener { snapshot, _ ->
                if (snapshot != null && !snapshot.isEmpty) {
                    val list = snapshot.toObjects(City::class.java)
                    _citiesState.value = list
                } else {
                    _citiesState.value = defaultCities
                }
            })

            listeners.add(db.collection("providers").addSnapshotListener { snapshot, _ ->
                if (snapshot != null && !snapshot.isEmpty) {
                    val list = snapshot.toObjects(Provider::class.java)
                    _providers.value = list
                } else {
                    _providers.value = defaultProviders
                }
            })

            listeners.add(db.collection("pending_requests").addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val list = snapshot.toObjects(PendingProvider::class.java)
                    _pendingRequests.value = list
                }
            })

            listeners.add(db.collection("reviews").addSnapshotListener { snapshot, _ ->
                if (snapshot != null && !snapshot.isEmpty) {
                    val list = snapshot.toObjects(Review::class.java)
                    _reviewsState.value = list
                } else {
                    _reviewsState.value = defaultReviews
                }
            })

            listeners.add(db.collection("chats").addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val list = snapshot.toObjects(Chat::class.java)
                    _chats.value = list.sortedByDescending { it.timestamp }
                }
            })

            listeners.add(db.collection("messages").addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val list = snapshot.toObjects(ChatMessage::class.java)
                    _chatMessages.value = list.sortedBy { it.timestamp }
                }
            })

            listeners.add(db.collection("bookings").addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val list = snapshot.toObjects(Booking::class.java)
                    _bookings.value = list.sortedByDescending { it.timestamp }
                }
            })

            listeners.add(db.collection("reports").addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val list = snapshot.toObjects(Report::class.java)
                    _reports.value = list.sortedByDescending { it.timestamp }
                }
            })

            listeners.add(db.collection("notifications").addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val list = snapshot.toObjects(UserNotification::class.java)
                    _notifications.value = list.sortedByDescending { it.timestamp }
                }
            })

            listeners.add(db.collection("admins").addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val list = snapshot.toObjects(AdminAccount::class.java)
                    _adminAccounts.value = list
                }
            })

            listeners.add(db.collection("audit_logs").addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val list = snapshot.toObjects(AuditLog::class.java)
                    _auditLogs.value = list.sortedByDescending { it.timestamp }
                }
            })

            listeners.add(db.collection("banners").addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val list = snapshot.toObjects(Banner::class.java)
                    _banners.value = list
                }
            })

            listeners.add(db.collection("provider_category_relations").addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val list = snapshot.toObjects(ProviderCategoryRelation::class.java)
                    _relations.value = list
                }
            })

        } catch (e: Exception) {
            Log.e("MainViewModel", "Failed setting up snap listeners: ${e.message}")
        }
    }

    override fun onCleared() {
        super.onCleared()
        listeners.forEach { it.remove() }
        listeners.clear()
    }

    // Business Methods
    fun updateAppSettings(newSettings: AppSettings, admin: String) {
        _settings.value = newSettings
        firestore?.collection("settings")?.document("global")?.set(newSettings)
        addAuditLog(admin, "تحديث إعدادات التطبيق العامة")
    }

    fun addAuditLog(admin: String, action: String) {
        val log = AuditLog(action, admin, UUID.randomUUID().toString(), System.currentTimeMillis())
        firestore?.collection("audit_logs")?.document(log.id)?.set(log)
    }

    fun deleteChatMessage(msgId: String, admin: String) {
        firestore?.collection("messages")?.document(msgId)?.delete()
        addAuditLog(admin, "حذف رسالة دردشة: $msgId")
    }

    fun updateChatMessage(msgId: String, newContent: String, admin: String) {
        firestore?.collection("messages")?.document(msgId)?.update("message", newContent)
        addAuditLog(admin, "تعديل محتوى رسالة دردشة: $msgId")
    }

    fun deleteChatRoom(roomId: String, admin: String) {
        firestore?.collection("chats")?.document(roomId)?.delete()
        firestore?.collection("messages")?.whereEqualTo("chatId", roomId)?.get()?.addOnSuccessListener { snap ->
            snap.forEach { doc -> doc.reference.delete() }
        }
        addAuditLog(admin, "حذف غرفة دردشة بالكامل: $roomId")
    }

    fun registerPendingProvider(p: PendingProvider) {
        firestore?.collection("pending_requests")?.document(p.id)?.set(p)
        val notif = UserNotification(
            body = "طلب انضمام جديد من مقدم الخدمة: ${p.name}",
            category = "join",
            id = UUID.randomUUID().toString(),
            recipientId = "admin",
            statusType = "pending",
            time = "الآن",
            timestamp = System.currentTimeMillis(),
            title = "طلب انضمام جديد"
        )
        addNotificationWithCategoryAndRecipient(notif)
    }

    fun approveProviderRequest(pp: PendingProvider, admin: String) {
        val newP = Provider(
            allowedImageCount = 5,
            area = pp.area,
            category = pp.category,
            city = pp.city,
            description = pp.description,
            deviceId = pp.deviceId,
            id = pp.id,
            imageUrl = "",
            isPinned = false,
            isPortfolioEnabled = true,
            isPortfolioUploadEnabled = true,
            isRecommended = false,
            isSubscribed = true,
            isVerified = true,
            name = pp.name,
            nationalIdImageBase64 = pp.nationalIdImageBase64,
            orderPriority = pp.orderPriority,
            phone = pp.phone,
            portfolioImages = pp.portfolioImages,
            rating = 5.0,
            skills = pp.skills
        )
        firestore?.collection("providers")?.document(newP.id)?.set(newP)
        firestore?.collection("pending_requests")?.document(pp.id)?.delete()

        val rel = ProviderCategoryRelation(pp.category, UUID.randomUUID().toString(), newP.id)
        firestore?.collection("provider_category_relations")?.document(rel.id)?.set(rel)

        val notif = UserNotification(
            body = "تمت الموافقة على طلب انضمامك كـ ${pp.name}. يمكنك الآن استقبال طلبات العملاء.",
            category = "all",
            id = UUID.randomUUID().toString(),
            recipientId = pp.deviceId,
            statusType = "approved",
            time = "الآن",
            timestamp = System.currentTimeMillis(),
            title = "مبروك! تم تفعيل حسابك"
        )
        addNotificationWithCategoryAndRecipient(notif)
        addAuditLog(admin, "الموافقة على انضمام مزود الخدمة: ${pp.name}")
    }

    fun rejectProviderRequest(id: String, reason: String, admin: String) {
        firestore?.collection("pending_requests")?.document(id)?.get()?.addOnSuccessListener { doc ->
            if (doc.exists()) {
                val name = doc.getString("name") ?: ""
                val devId = doc.getString("deviceId") ?: ""
                firestore?.collection("pending_requests")?.document(id)?.delete()

                val notif = UserNotification(
                    body = "عذراً، لم تتم الموافقة على طلب انضمامك لسبب: $reason",
                    category = "all",
                    id = UUID.randomUUID().toString(),
                    recipientId = devId,
                    statusType = "rejected",
                    time = "الآن",
                    timestamp = System.currentTimeMillis(),
                    title = "تم رفض طلب الانضمام"
                )
                addNotificationWithCategoryAndRecipient(notif)
                addAuditLog(admin, "رفض طلب انضمام $name للسبب: $reason")
            }
        }
    }

    fun addProviderManual(p: Provider, admin: String) {
        firestore?.collection("providers")?.document(p.id)?.set(p)
        val rel = ProviderCategoryRelation(p.category, UUID.randomUUID().toString(), p.id)
        firestore?.collection("provider_category_relations")?.document(rel.id)?.set(rel)
        addAuditLog(admin, "إضافة يدوية لمزود الخدمة: ${p.name}")
    }

    fun updateProviderManual(p: Provider, admin: String) {
        firestore?.collection("providers")?.document(p.id)?.set(p)
        addAuditLog(admin, "تحديث يدوي لمزود الخدمة: ${p.name}")
    }

    fun requestServiceAppointment(providerId: String, providerName: String, serviceDetails: String, preferredTime: String) {
        val b = Booking(
            details = serviceDetails,
            id = UUID.randomUUID().toString(),
            preferredTime = preferredTime,
            providerId = providerId,
            providerName = providerName,
            status = "قيد المراجعة",
            timestamp = System.currentTimeMillis(),
            userId = "client_${System.currentTimeMillis() % 10000}",
            userName = "عميل محلي"
        )
        firestore?.collection("bookings")?.document(b.id)?.set(b)

        val notif = UserNotification(
            body = "طلب موعد جديد للخدمة من العميل: ${b.userName}. الموعد المفضل: $preferredTime",
            category = "booking",
            id = UUID.randomUUID().toString(),
            recipientId = providerId,
            statusType = "new_booking",
            time = "الآن",
            timestamp = System.currentTimeMillis(),
            title = "طلب موعد خدمة جديد"
        )
        addNotificationWithCategoryAndRecipient(notif)
    }

    fun updateBooking(b: Booking, admin: String) {
        firestore?.collection("bookings")?.document(b.id)?.set(b)
        addAuditLog(admin, "تعديل حالة حجز: ${b.id}")
    }

    fun deleteBooking(id: String, admin: String) {
        firestore?.collection("bookings")?.document(id)?.delete()
        addAuditLog(admin, "حذف حجز خدمة: $id")
    }

    fun addNotificationWithCategoryAndRecipient(not: UserNotification) {
        val list = _notifications.value.toMutableList()
        list.add(0, not)
        _notifications.value = list
        firestore?.collection("notifications")?.document(not.id)?.set(not)
    }

    fun deleteNotification(id: String) {
        firestore?.collection("notifications")?.document(id)?.delete()
    }

    fun deleteProvider(id: String, admin: String) {
        firestore?.collection("providers")?.document(id)?.get()?.addOnSuccessListener { doc ->
            val name = doc.getString("name") ?: ""
            firestore?.collection("providers")?.document(id)?.delete()
            firestore?.collection("provider_category_relations")?.whereEqualTo("providerId", id)?.get()?.addOnSuccessListener { snap ->
                snap.forEach { d -> d.reference.delete() }
            }
            addAuditLog(admin, "حذف مزود خدمة نهائياً: $name")
        }
    }

    fun submitReview(review: Review) {
        firestore?.collection("reviews")?.document(review.id)?.set(review)
        // Recalculate provider rating
        firestore?.collection("providers")?.document(review.providerId)?.get()?.addOnSuccessListener { doc ->
            if (doc.exists()) {
                val p = doc.toObject(Provider::class.java) ?: return@addOnSuccessListener
                val reviewsList = _reviewsState.value.filter { it.providerId == p.id }.toMutableList()
                if (reviewsList.none { it.id == review.id }) {
                    reviewsList.add(review)
                }
                val avg = reviewsList.map { it.rating }.average()
                val rounded = if (avg.isNaN()) 5.0 else Math.round(avg * 10) / 10.0
                firestore?.collection("providers")?.document(p.id)?.update("rating", rounded)
            }
        }
    }

    fun toggleProviderStatus(id: String, isPinned: Boolean, isRecommended: Boolean, isVerified: Boolean, isSubscribed: Boolean, adminName: String) {
        firestore?.collection("providers")?.document(id)?.update(
            "isPinned", isPinned,
            "isRecommended", isRecommended,
            "isVerified", isVerified,
            "isSubscribed", isSubscribed
        )
        addAuditLog(adminName, "تعديل شارات وصلاحيات مزود الخدمة: $id")
    }

    fun addCategory(cat: Category, admin: String) {
        firestore?.collection("categories")?.document(cat.id)?.set(cat)
        addAuditLog(admin, "إضافة قسم مهني جديد: ${cat.nameAr}")
    }

    fun deleteCategory(id: String, admin: String) {
        firestore?.collection("categories")?.document(id)?.delete()
        addAuditLog(admin, "حذف قسم مهني: $id")
    }

    fun updateCategory(cat: Category, admin: String) {
        firestore?.collection("categories")?.document(cat.id)?.set(cat)
        addAuditLog(admin, "تعديل قسم مهني: ${cat.nameAr}")
    }

    fun addCity(city: City, admin: String) {
        firestore?.collection("cities")?.document(city.id)?.set(city)
        addAuditLog(admin, "إضافة مدينة يمنية جديدة: ${city.nameAr}")
    }

    fun deleteCity(id: String, admin: String) {
        firestore?.collection("cities")?.document(id)?.delete()
        addAuditLog(admin, "حذف مدينة: $id")
    }

    fun addReport(rep: Report) {
        firestore?.collection("reports")?.document(rep.id)?.set(rep)
    }

    fun approveReport(id: String, admin: String) {
        firestore?.collection("reports")?.document(id)?.delete()
        addAuditLog(admin, "إغلاق ومعالجة بلاغ شكوى: $id")
    }

    fun startChatWithProvider(userId: String, providerId: String, providerName: String) {
        val chatRoomId = "chat_${userId}_${providerId}"
        _currentChatRoomId.value = chatRoomId
        val existing = _chats.value.find { it.id == chatRoomId }
        if (existing == null) {
            val newChat = Chat(
                id = chatRoomId,
                lastMessage = "بدء محادثة جديدة",
                providerId = providerId,
                providerName = providerName,
                timestamp = System.currentTimeMillis(),
                userName = "مستخدم"
            )
            firestore?.collection("chats")?.document(chatRoomId)?.set(newChat)
        }
    }

    fun sendChatMessage(chatId: String, senderName: String, senderType: String, messageText: String) {
        if (messageText.isBlank()) return
        val msg = ChatMessage(
            chatId = chatId,
            id = UUID.randomUUID().toString(),
            message = messageText,
            senderName = senderName,
            senderType = senderType,
            timestamp = System.currentTimeMillis()
        )
        firestore?.collection("messages")?.document(msg.id)?.set(msg)
        firestore?.collection("chats")?.document(chatId)?.update(
            "lastMessage", messageText,
            "timestamp", System.currentTimeMillis()
        )
    }

    fun addAdminAccount(account: AdminAccount, creator: String) {
        firestore?.collection("admins")?.document(account.username)?.set(account)
        addAuditLog(creator, "إنشاء حساب مشرف جديد: ${account.username}")
    }

    fun deleteAdminAccount(username: String, admin: String) {
        firestore?.collection("admins")?.document(username)?.delete()
        addAuditLog(admin, "حذف حساب مشرف: $username")
    }

    fun updateAdminAccount(oldUsername: String, updatedAccount: AdminAccount, admin: String) {
        if (oldUsername != updatedAccount.username) {
            firestore?.collection("admins")?.document(oldUsername)?.delete()
        }
        firestore?.collection("admins")?.document(updatedAccount.username)?.set(updatedAccount)
        addAuditLog(admin, "تحديث بيانات الحساب للمشرف: $oldUsername")
    }

    fun addBanner(b: Banner, admin: String) {
        firestore?.collection("banners")?.document(b.id)?.set(b)
        addAuditLog(admin, "إضافة لافتة إعلانية جديدة")
    }

    fun deleteBanner(id: String, admin: String) {
        firestore?.collection("banners")?.document(id)?.delete()
        addAuditLog(admin, "حذف لافتة إعلانية: $id")
    }

    fun clearAllChatHistory(admin: String) {
        firestore?.collection("chats")?.get()?.addOnSuccessListener { snap ->
            snap.forEach { it.reference.delete() }
        }
        firestore?.collection("messages")?.get()?.addOnSuccessListener { snap ->
            snap.forEach { it.reference.delete() }
        }
        addAuditLog(admin, "تطهير وحذف كامل سجل المحادثات والدردشة")
    }

    fun checkAdminPassword(password: String): Boolean {
        // Simple plain check
        return password == _settings.value.adminPassword
    }

    fun checkAdminThreeLayersLogin(user: String, pass: String): AdminAccount? {
        // Check if there is matching admin list
        val admin = _adminAccounts.value.find { it.username == user && it.passwordHash == pass }
        if (admin != null) {
            _isAdminLoggedIn.value = true
            _loggedInUsername.value = user
        }
        return admin
    }

    fun logoutAdmin() {
        _isAdminLoggedIn.value = false
        _loggedInUsername.value = ""
    }

    fun markAllNotificationsAsRead() {
        _notifications.value = _notifications.value.map { it.copy(isRead = true) }
    }

    // AI Assistant calls
    fun askGemini(prompt: String) {
        if (prompt.isBlank()) return
        val currentList = _geminiMessages.value.toMutableList()
        currentList.add(Pair(prompt, true))
        _geminiMessages.value = currentList
        _isGeminiThinking.value = true

        viewModelScope.launch {
            val key = _settings.value.geminiApiKey
            val replyText = if (key.isBlank() || key == "PLACEHOLDER") {
                getSimulatedYemeniLocalReply(prompt)
            } else {
                callGeminiAPI(key, prompt)
            }
            val afterList = _geminiMessages.value.toMutableList()
            afterList.add(Pair(replyText, false))
            _geminiMessages.value = afterList
            _isGeminiThinking.value = false
        }
    }

    private suspend fun callGeminiAPI(apiKey: String, prompt: String): String = withContext(Dispatchers.IO) {
        try {
            val endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey"
            val url = URL(endpoint)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "json")
            connection.doOutput = true

            val systemInstruction = "أنت مساعد ذكي مخصص لدليل 'كل خدمات اليمن'. تساعد المستخدمين في العثور على أفضل الحرفيين والكهربائيين والمقاولين والأطباء والمحامين والسباكين والمعلمين في جميع المحافظات اليمنية (صنعاء، عدن، تعز، إب، حضرموت، الحديدة وغيرها). تحدث بلهجة ترحيبية يمنية لبقة وقدم إرشادات دقيقة وموثوقة."

            val jsonBody = buildJsonObject {
                putJsonArray("contents") {
                    addJsonObject {
                        put("role", "user")
                        putJsonArray("parts") {
                            addJsonObject {
                                put("text", "$systemInstruction\n\nالسؤال: $prompt")
                            }
                        }
                    }
                }
            }

            val bodyString = jsonBody.toString()
            connection.outputStream.write(bodyString.toByteArray(Charsets.UTF_8))

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream, Charsets.UTF_8))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                reader.close()

                val root = Json.parseToJsonElement(response.toString()).jsonObject
                val textResponse = root["candidates"]
                    ?.jsonArray?.getOrNull(0)
                    ?.jsonObject?.get("content")
                    ?.jsonObject?.get("parts")
                    ?.jsonArray?.getOrNull(0)
                    ?.jsonObject?.get("text")
                    ?.jsonPrimitive?.content ?: "عذراً يا غالي، لم أستطع استيعاب الرد بشكل مناسب. أعد المحاولة!"
                textResponse
            } else {
                getSimulatedYemeniLocalReply(prompt)
            }
        } catch (e: Exception) {
            getSimulatedYemeniLocalReply(prompt)
        }
    }

    private fun getSimulatedYemeniLocalReply(prompt: String): String {
        val pLower = prompt.lowercase()
        return when {
            pLower.contains("السلام") || pLower.contains("مرحبا") || pLower.contains("هلا") || pLower.contains("أهلاً") -> {
                "حياك الله يا غالي! يا ميت أهلاً وسهلاً بك في دليل خدمات اليمن المباشر. كيف أقدر أخدمك اليوم؟ أدور على كهربائي، سباك، طبيب، أو مقاول؟ أنا في الخدمة!"
            }
            pLower.contains("كهربائي") || pLower.contains("كهرباء") -> {
                "أبشر من عيوني! عندنا كهربائيين معتمدين ممتازين جداً في صنعاء وعدن وجميع المدن. مثل 'المهندس أحمد صالح' في صنعاء خبير تمديدات لوحات وتأسيس ذكي (هاتفه: 777654321). تواصل معه وبيدعي لك!"
            }
            pLower.contains("سباك") || pLower.contains("سباكه") -> {
                "يا هلا بك، بالنسبة للسباكة وتأسيس شبكات المياه والصرف، عندنا فنيين ممتازين متوفرين. تصفح قسم 'سباكة وصيانة صحية' لتجد الفنيين الأقرب لعنوانك وحسب المسافة بدقة!"
            }
            pLower.contains("مقاول") || pLower.contains("بناء") || pLower.contains("تشطيب") -> {
                "على الرحب والسعة! بخصوص المقاولات والتشطيب والدهانات، ننصحك بـ 'المقاول يحيى مسعد' في عدن المنصورة (هاتف: 733987654)، شغلهم احترافي وموثق وبتقييم عالي جداً من الزبائن."
            }
            pLower.contains("طبيب") || pLower.contains("دكتور") || pLower.contains("عيون") -> {
                "سلامتك يا غالي ما تشوف شر! إذا كنت في صنعاء، في قسم العيون نرشح لك 'الدكتور أمين الصبري' استشاري طب وجراحة العيون في شارع الزبيري (هاتف: 771122333). دكتور متمكن بفضل الله."
            }
            pLower.contains("رقم") || pLower.contains("هاتف") || pLower.contains("اتصال") -> {
                "بإمكانك بلمسة واحدة الضغط على بطاقة أي مزود خدمة والاتصال به مباشرة عن طريق زر الرقم، أو مراسلته فوراً عبر دردشة التطبيق المباشرة والمجانية!"
            }
            pLower.contains("حجز") || pLower.contains("موعد") -> {
                "حجز المواعيد سهل جداً! اضغط على مزود الخدمة وبتلقى خيار '🗓️ حجز موعد خدمة فوري ومباشر'. حدد تفاصيل طلبك والوقت وبيرسل له مباشرة وبتحصل تنبيه فوري."
            }
            else -> {
                "أهلاً بك يا أصيل! يسعدني جداً مساعدتك في العثور على ما تبحث عنه في دليل 'كل خدمات اليمن'. اسألني عن أي تخصص (كهرباء، سباكة، بناء، صيانة مكيفات، طب، محاماة) أو كيفية التواصل المباشر وبجاوبك فوراً بنكهة يمنية أصيلة!"
            }
        }
    }
}
