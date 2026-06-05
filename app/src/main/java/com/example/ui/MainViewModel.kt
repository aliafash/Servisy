package com.example.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import java.util.UUID

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val dao = database.dao()

    // Configuration / Customization states
    val appName = MutableStateFlow("تطبيق كل خدمات اليمن")
    val selectedTheme = MutableStateFlow("COSMIC_SILVER") // COSMIC_SILVER, GOLDEN_LUXURY, EMERALD_ROYAL, CUSTOM
    val customPrimaryColor = MutableStateFlow("#9E9E9E")
    val customSecondaryColor = MutableStateFlow("#FFB300")
    val footerText = MutableStateFlow("MAW 777644670")
    val welcomeMessage = MutableStateFlow("مرحباً بكم في تطبيق كل خدمات اليمن! بوابتكم المثالية للوصول لكافة المهنيين وأصحاب الخدمات بكل سهولة وموثوقية في كل أنحاء اليمن.")
    val supportPhone = MutableStateFlow("777644670")
    val supportEmail = MutableStateFlow("support@yemenservices.com")
    val supportWhatsapp = MutableStateFlow("777644670")
    val adminPassword = MutableStateFlow("maher736462")
    val maintenanceMode = MutableStateFlow(false)
    val twoFaEnabled = MutableStateFlow(false)
    val dataSaverMode = MutableStateFlow(false)

    // Icons Customization
    val smartAssistantIcon = MutableStateFlow("DEFAULT")
    val smartAssistantSize = MutableStateFlow(56)
    val smartAssistantVisible = MutableStateFlow(true)
    val smartAssistantX = MutableStateFlow(0f)
    val smartAssistantY = MutableStateFlow(0f)

    // FCM Notification Channels Status
    val fcmJoinRequestEnabled = MutableStateFlow(true)
    val fcmReportEnabled = MutableStateFlow(true)
    val fcmSubsEnabled = MutableStateFlow(true)

    // Fonts Styling
    val fontFamilyConfig = MutableStateFlow("SANS_SERIF")
    val fontColorConfig = MutableStateFlow("#FFFFFF")

    // Whitelist & Devices
    val whitelistDevices = MutableStateFlow<List<WhitelistDeviceEntity>>(emptyList())

    // Database core collection objects
    val categories = dao.getCategoriesFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val providers = dao.getProvidersFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val banners = dao.getBannersFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val reports = dao.getReportsFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val chats = dao.getChatsFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val moderators = dao.getModeratorsFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val logs = dao.getLogsFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Language locale
    val currentLang = MutableStateFlow("ar") // "ar" or "en"

    // App Navigation UI Screens
    // "HOME", "LOGIN", "REGISTER_PROVIDER", "ADMIN_PANEL", "BACKDOOR_PANEL", "ABOUT", "PREVIOUS_REQUESTS"
    val currentScreen = MutableStateFlow("HOME")

    // In-app Alert Notifications
    private val _notifications = MutableSharedFlow<String>(replay = 1)
    val notifications = _notifications.asSharedFlow()

    // Search filters
    val searchQuery = MutableStateFlow("")
    val searchCategory = MutableStateFlow("")
    val searchLocation = MutableStateFlow("")
    val searchRating = MutableStateFlow(0.0)
    val searchRadius = MutableStateFlow(25f) // Range search radius
    val maximumRadiusDefault = MutableStateFlow(50f)
    val searchMode = MutableStateFlow("LIST") // "LIST" or "MAP"

    // Active Chat Sessions
    val chatReceiverId = MutableStateFlow<String?>(null)
    val chatReceiverName = MutableStateFlow<String>("")

    init {
        // Initialize Default Setup inside Room DB
        viewModelScope.launch(Dispatchers.IO) {
            setupInitialSettings()
            setupInitialCategories()
            setupInitialProviders()
            setupInitialBanners()
            setupInitialModerators()
        }
    }

    private suspend fun setupInitialSettings() {
        // Insert defaults if settings table is empty
        val dbSettings = dao.getAllCategories() // just testing
        val defaultSettings = listOf(
            AdminSettingEntity("app_name", "تطبيق كل خدمات اليمن"),
            AdminSettingEntity("primary_color", "COSMIC_SILVER"),
            AdminSettingEntity("custom_primary", "#1E1E2C"),
            AdminSettingEntity("custom_secondary", "#00E5FF"),
            AdminSettingEntity("footer_text", "MAW 777644670"),
            AdminSettingEntity("welcome_message", "مرحباً بكم في تطبيق كل خدمات اليمن! بوابتكم المثالية للوصول لكافة المهنيين وأصحاب الخدمات بكل سهولة وموثوقية في كل أنحاء اليمن."),
            AdminSettingEntity("support_phone", "777644670"),
            AdminSettingEntity("support_email", "support@yemenservices.com"),
            AdminSettingEntity("support_whatsapp", "777644670"),
            AdminSettingEntity("admin_password", "maher736462"),
            AdminSettingEntity("maintenance_mode", "false"),
            AdminSettingEntity("two_fa_enabled", "false"),
            AdminSettingEntity("data_saver_mode", "false"),
            AdminSettingEntity("smart_assistant_icon", "DEFAULT"),
            AdminSettingEntity("smart_assistant_size", "56"),
            AdminSettingEntity("smart_assistant_visible", "true"),
            AdminSettingEntity("smart_assistant_x", "0"),
            AdminSettingEntity("smart_assistant_y", "0"),
            AdminSettingEntity("fcm_join_request_enabled", "true"),
            AdminSettingEntity("fcm_report_enabled", "true"),
            AdminSettingEntity("fcm_subs_enabled", "true"),
            AdminSettingEntity("font_family", "SANS_SERIF"),
            AdminSettingEntity("font_color", "#FFFFFF"),
            AdminSettingEntity("radius_limit_max", "50")
        )

        for (item in defaultSettings) {
            val existing = dao.getSettingByKey(item.key)
            if (existing == null) {
                dao.saveSetting(item)
            } else {
                // Load existing config into variables
                when (existing.key) {
                    "app_name" -> appName.value = existing.value
                    "primary_color" -> selectedTheme.value = existing.value
                    "custom_primary" -> customPrimaryColor.value = existing.value
                    "custom_secondary" -> customSecondaryColor.value = existing.value
                    "footer_text" -> footerText.value = existing.value
                    "welcome_message" -> welcomeMessage.value = existing.value
                    "support_phone" -> supportPhone.value = existing.value
                    "support_email" -> supportEmail.value = existing.value
                    "support_whatsapp" -> supportWhatsapp.value = existing.value
                    "admin_password" -> adminPassword.value = existing.value
                    "maintenance_mode" -> maintenanceMode.value = existing.value.toBoolean()
                    "two_fa_enabled" -> twoFaEnabled.value = existing.value.toBoolean()
                    "data_saver_mode" -> dataSaverMode.value = existing.value.toBoolean()
                    "smart_assistant_icon" -> smartAssistantIcon.value = existing.value
                    "smart_assistant_size" -> smartAssistantSize.value = existing.value.toIntOrNull() ?: 56
                    "smart_assistant_visible" -> smartAssistantVisible.value = existing.value.toBoolean()
                    "fcm_join_request_enabled" -> fcmJoinRequestEnabled.value = existing.value.toBoolean()
                    "fcm_report_enabled" -> fcmReportEnabled.value = existing.value.toBoolean()
                    "fcm_subs_enabled" -> fcmSubsEnabled.value = existing.value.toBoolean()
                    "font_family" -> fontFamilyConfig.value = existing.value
                    "font_color" -> fontColorConfig.value = existing.value
                    "radius_limit_max" -> maximumRadiusDefault.value = existing.value.toFloatOrNull() ?: 50f
                }
            }
        }
        whitelistDevices.value = dao.getWhitelistFlow().firstOrNull() ?: emptyList()
    }

    private suspend fun setupInitialCategories() {
        val existing = dao.getAllCategories()
        if (existing.isEmpty()) {
            val initial = listOf(
                CategoryEntity("cat_elec", "كهرباء ومولدات", "Electricity & Generators", 0, 1),
                CategoryEntity("cat_plumb", "سباكة وصرف صحي", "Plumbing & Sewerage", 1, 2),
                CategoryEntity("cat_phone", "صيانة جوالات وإلكترونيات", "Phones & Electronics Repair", 2, 3),
                CategoryEntity("cat_carp", "نجارة وديكور", "Carpentry & Decor", 3, 4),
                CategoryEntity("cat_const", "بناء ومقاولات وأحجار", "Construction & Stones", 4, 5),
                CategoryEntity("cat_doc", "أطباء واستشارات طبية", "Doctors & Medical support", 5, 6),
                CategoryEntity("cat_solar", "منظومات طاقة شمسية", "Solar Power Setup", 6, 7),
                CategoryEntity("cat_mech", "سيارات وميكانيك عام", "Car Mechanics", 7, 8)
            )
            dao.saveCategories(initial)

            // Add sub categories as CategoryEntity with parentId defined
            val subcats = listOf(
                CategoryEntity("sub_elec_wiring", "تمديد شبكات المنزل", "Home Cabling", 0, 1, "cat_elec"),
                CategoryEntity("sub_elec_appliance", "صيانة الأجهزة الكهربائية", "Appliance Maintenance", 0, 2, "cat_elec"),
                CategoryEntity("sub_plumb_install", "تأسيس شبكات مياه", "Water Pipes Routing", 1, 1, "cat_plumb"),
                CategoryEntity("sub_plumb_sewer", "فتح سدادات البالوعات", "Drain Troubleshooting", 1, 2, "cat_plumb"),
                CategoryEntity("sub_solar_panel", "توجيه وتنظيف الألواح", "Solar Panel Alignment", 6, 1, "cat_solar")
            )
            dao.saveCategories(subcats)
        }
    }

    private suspend fun setupInitialProviders() {
        val existing = dao.getAllProviders()
        if (existing.isEmpty()) {
            val initial = listOf(
                ProviderEntity(
                    id = "prov_1",
                    name = "ماهر محمد طاهر",
                    phone = "777644670",
                    parentCategoryId = "cat_elec",
                    subCategoryName = "تمديد شبكات المنزل",
                    workAddress = "صنعاء - شارع الستين الغربي",
                    residenceArea = "حي الروضة",
                    isPinned = true,
                    isRecommended = true,
                    isVerified = true,
                    ratingSum = 25,
                    ratingCount = 5,
                    points = 240,
                    isVipSubscribed = true,
                    status = "approved"
                ),
                ProviderEntity(
                    id = "prov_2",
                    name = "ميكانيكي اليمن السعيد",
                    phone = "736462711",
                    parentCategoryId = "cat_mech",
                    subCategoryName = "سيارات وميكانيك عام",
                    workAddress = "صنعاء - شارع خمسين",
                    residenceArea = "حي حدة",
                    isPinned = false,
                    isRecommended = true,
                    isVerified = true,
                    ratingSum = 18,
                    ratingCount = 4,
                    points = 150,
                    isVipSubscribed = false,
                    status = "approved"
                ),
                ProviderEntity(
                    id = "prov_3",
                    name = "يوسف السباك المحترف",
                    phone = "711223344",
                    parentCategoryId = "cat_plumb",
                    subCategoryName = "تأسيس شبكات مياه",
                    workAddress = "تعز - شارع جمال",
                    residenceArea = "المظفر",
                    isPinned = true,
                    isRecommended = false,
                    isVerified = true,
                    ratingSum = 14,
                    ratingCount = 3,
                    points = 80,
                    isVipSubscribed = true,
                    status = "approved"
                )
            )
            dao.saveProviders(initial)
        }
    }

    private suspend fun setupInitialBanners() {
        val existing = dao.getBannersFlow().firstOrNull() ?: emptyList()
        if (existing.isEmpty()) {
            dao.saveBanner(
                BannerEntity(
                    id = "ban_1",
                    title = "موسم صيانة المولدات والمنظومات قبل الصيف",
                    type = "TEXT",
                    content = "سارع بحجز موعد صيانة لمنظومات الطاقة الشمسية والمولدات قبل ازدياد حرارة الصيف مع عروض حصرية وموثوقة!",
                    durationSeconds = 6,
                    redirectUrl = "https://yemenservices.com/maintenance",
                    sizeRatio = "medium"
                )
            )
            dao.saveBanner(
                BannerEntity(
                    id = "ban_2",
                    title = "انضم الآن لأكبر شبكة خدمات يمنية",
                    type = "IMAGE",
                    content = "banner_join_us", // Represented beautifully in our custom layout
                    durationSeconds = 8,
                    redirectUrl = "",
                    sizeRatio = "large"
                )
            )
        }
    }

    private suspend fun setupInitialModerators() {
        val existing = dao.getAllModerators()
        if (existing.isEmpty()) {
            dao.saveModerator(ModeratorEntity("admin_ahmed", "ahmed2026", "G5HS6KD", 0L))
            dao.saveModerator(ModeratorEntity("supervisor_saleh", "saleh123", "", 0L))
        }
    }

    // Operations called by the Backdoor / Secret Portal Settings
    fun updateAppConfiguration(
        name: String,
        theme: String,
        primaryHex: String,
        secondaryHex: String,
        footer: String,
        welcome: String,
        phone: String,
        email: String,
        whatsapp: String,
        adminPass: String,
        maintenance: Boolean,
        saver: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            appName.value = name
            selectedTheme.value = theme
            customPrimaryColor.value = primaryHex
            customSecondaryColor.value = secondaryHex
            footerText.value = footer
            welcomeMessage.value = welcome
            supportPhone.value = phone
            supportEmail.value = email
            supportWhatsapp.value = whatsapp
            adminPassword.value = adminPass
            maintenanceMode.value = maintenance
            dataSaverMode.value = saver

            dao.saveSetting(AdminSettingEntity("app_name", name))
            dao.saveSetting(AdminSettingEntity("primary_color", theme))
            dao.saveSetting(AdminSettingEntity("custom_primary", primaryHex))
            dao.saveSetting(AdminSettingEntity("custom_secondary", secondaryHex))
            dao.saveSetting(AdminSettingEntity("footer_text", footer))
            dao.saveSetting(AdminSettingEntity("welcome_message", welcome))
            dao.saveSetting(AdminSettingEntity("support_phone", phone))
            dao.saveSetting(AdminSettingEntity("support_email", email))
            dao.saveSetting(AdminSettingEntity("support_whatsapp", whatsapp))
            dao.saveSetting(AdminSettingEntity("admin_password", adminPass))
            dao.saveSetting(AdminSettingEntity("maintenance_mode", maintenance.toString()))
            dao.saveSetting(AdminSettingEntity("data_saver_mode", saver.toString()))

            logActivity("Owner", "تحديث شامل للإعدادات السرية وتغيير الهوية")
            _notifications.emit("تم مزامنة الهوية وتعديل الألوان بنجاح!")
        }
    }

    fun updateSmartAssistantConfig(icon: String, size: Int, visible: Boolean, x: Float, y: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            smartAssistantIcon.value = icon
            smartAssistantSize.value = size
            smartAssistantVisible.value = visible
            smartAssistantX.value = x
            smartAssistantY.value = y

            dao.saveSetting(AdminSettingEntity("smart_assistant_icon", icon))
            dao.saveSetting(AdminSettingEntity("smart_assistant_size", size.toString()))
            dao.saveSetting(AdminSettingEntity("smart_assistant_visible", visible.toString()))

            logActivity("Admin", "تعديل إعدادات المساعد الذكي ورابط الأيقونة")
        }
    }

    fun addCategory(id: String, nameAr: String, nameEn: String, imageIndex: Int, order: Int, parentId: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val entity = CategoryEntity(id, nameAr, nameEn, imageIndex, order, parentId)
            dao.saveCategory(entity)
            logActivity("Admin", "إضافة قسم جديد: $nameAr")
            _notifications.emit("تم إضافة القسم بنجاح ومزامنته فوراً!")
        }
    }

    fun deleteCategory(entity: CategoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteCategory(entity)
            logActivity("Admin", "حذف قسم: ${entity.nameAr}")
            _notifications.emit("تم حذف القسم!")
        }
    }

    fun logActivity(operator: String, action: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isLogging = dao.getSettingByKey("activity_logs_switch")?.value != "false"
            if (isLogging) {
                dao.saveActivity(ActivityLogEntity(UUID.randomUUID().toString(), operator, action))
            }
        }
    }

    // Submit provider application
    fun submitJoinRequest(
        name: String,
        phone: String,
        mainCatId: String,
        subCatName: String,
        workAddress: String,
        residence: String,
        lat: Double,
        lng: Double,
        avatar: String,
        idCard: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val newProvider = ProviderEntity(
                id = "prov_" + UUID.randomUUID().toString().take(6),
                name = name,
                phone = phone,
                parentCategoryId = mainCatId,
                subCategoryName = subCatName,
                workAddress = workAddress,
                residenceArea = residence,
                latitude = lat,
                longitude = lng,
                avatarUri = avatar,
                idCardUri = idCard,
                status = "pending",
                isPinned = false,
                isRecommended = false,
                isVerified = false,
                isBlocked = false
            )
            dao.saveProvider(newProvider)

            // Trigger FCM Notification Simulation
            if (fcmJoinRequestEnabled.value) {
                _notifications.emit("FCM Notification: انضمام عضو جديد للمراجعة: $name ($phone)")
            }
            logActivity("Visitor", "تقديم طلب انضمام جديد باسم: $name")
        }
    }

    // Manual provider insert (Direct bypass to approved)
    fun addProviderDirect(name: String, phone: String, catId: String, subName: String, address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val provider = ProviderEntity(
                id = "prov_" + UUID.randomUUID().toString().take(6),
                name = name,
                phone = phone,
                parentCategoryId = catId,
                subCategoryName = subName,
                workAddress = address,
                residenceArea = "عامة",
                status = "approved",
                isVerified = true
            )
            dao.saveProvider(provider)
            logActivity("Admin", "إضافة مقدم خدمة يدوياً مباشرة: $name")
            _notifications.emit("تم إضافة مقدم الخدمة مباشرة ومزامنته!")
        }
    }

    // Review registration requests (Manage pending providers)
    fun acceptProvider(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao.getAllProviders()
            val item = list.find { it.id == id }
            if (item != null) {
                val updated = item.copy(status = "approved")
                dao.saveProvider(updated)

                // Simulating Client FCM Update
                _notifications.emit("تم قبول طلب انضمام: ${item.name}! تم إرسال إشعار للمشترك.")
                logActivity("Admin", "قبول طلب انضمام مقدم الخدمة: ${item.name}")
            }
        }
    }

    fun rejectProvider(id: String, reason: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao.getAllProviders()
            val item = list.find { it.id == id }
            if (item != null) {
                val updated = item.copy(status = "rejected", rejectReason = reason)
                dao.saveProvider(updated)

                // Simulating Client FCM Update
                _notifications.emit("تم رفض طلب انضمام: ${item.name} بسبب ($reason)")
                logActivity("Admin", "رفض طلب انضمام مقدم الخدمة: ${item.name}")
            }
        }
    }

    // Pin and Recommended Toggle
    fun togglePin(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao.getAllProviders()
            val item = list.find { it.id == id }
            if (item != null) {
                val updated = item.copy(isPinned = !item.isPinned)
                dao.saveProvider(updated)
                _notifications.emit(if (updated.isPinned) "تم تثبيت مقدم الخدمة في صدارة قائمته!" else "تم إلغاء التثبيت!")
                logActivity("Owner_Only", "تعديل حالة التثبيت لمقدم الخدمة: ${item.name}")
            }
        }
    }

    fun toggleRecommend(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao.getAllProviders()
            val item = list.find { it.id == id }
            if (item != null) {
                val updated = item.copy(isRecommended = !item.isRecommended)
                dao.saveProvider(updated)
                _notifications.emit(if (updated.isRecommended) "تم إضافته للموصى بهم في الواجهة الرئيسية!" else "تم إزالته من الموصى بهم!")
                logActivity("Owner_Only", "تعديل حالة التوصية لمقدم الخدمة: ${item.name}")
            }
        }
    }

    fun toggleVerified(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao.getAllProviders()
            val item = list.find { it.id == id }
            if (item != null) {
                val updated = item.copy(isVerified = !item.isVerified)
                dao.saveProvider(updated)
                _notifications.emit(if (updated.isVerified) "منح الشارة الزرقاء الموثقة!" else "سحب الشارة الزرقاء!")
                logActivity("Admin", "تحديث شارة التوثيق لمقدم الخدمة: ${item.name}")
            }
        }
    }

    fun toggleBlocked(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao.getAllProviders()
            val item = list.find { it.id == id }
            if (item != null) {
                val updated = item.copy(isBlocked = !item.isBlocked)
                dao.saveProvider(updated)
                _notifications.emit(if (updated.isBlocked) "تم حظر مقدم الخدمة!" else "تم إلغاء الحظر!")
                logActivity("Admin", "تعديل حالة حظر مقدم الخدمة: ${item.name}")
            }
        }
    }

    fun addPoints(providerId: String, amount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao.getAllProviders()
            val item = list.find { it.id == providerId }
            if (item != null) {
                val updated = item.copy(points = item.points + amount)
                dao.saveProvider(updated)
            }
        }
    }

    fun submitReport(providerId: String, providerName: String, reporter: String, reason: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val rId = "rep_" + UUID.randomUUID().toString().take(6)
            dao.saveReport(ReportEntity(rId, providerId, providerName, reporter, reason, System.currentTimeMillis(), "pending"))

            if (fcmReportEnabled.value) {
                _notifications.emit("FCM Notification: بلاغ شكوى جديد ضد مقدم الخدمة: $providerName ($reporter)")
            }
            logActivity("User", "تقديم بلاغ شكوى ضد: $providerName")
        }
    }

    fun deleteReport(entity: ReportEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteReport(entity)
            logActivity("Admin", "حذف/معالجة بلاغ شكوى رقم ${entity.id}")
            _notifications.emit("تم إغلاق البلاغ وحذفه.")
        }
    }

    fun addBanner(title: String, type: String, content: String, seconds: Int, redirectUrl: String, sizeRatio: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val banner = BannerEntity(UUID.randomUUID().toString().take(6), title, type, content, seconds, redirectUrl, sizeRatio)
            dao.saveBanner(banner)
            logActivity("Admin", "إضافة لافتة إعلانية جديدة: $title")
            _notifications.emit("تم إضافة الإعلان الممول ومزامنته!")
        }
    }

    fun deleteBanner(entity: BannerEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteBanner(entity)
            logActivity("Admin", "حذف لافتة إعلانية: ${entity.title}")
            _notifications.emit("تم حذف الإعلان.")
        }
    }

    fun addModerator(username: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.saveModerator(ModeratorEntity(username, pass))
            logActivity("Owner_Only", "إضافة مشرف إداري جديد: $username")
            _notifications.emit("تمت إضافة المشرف بنجاح!")
        }
    }

    fun deleteModerator(entity: ModeratorEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteModerator(entity)
            logActivity("Owner_Only", "حذف المشرف الإداري: ${entity.username}")
            _notifications.emit("تم حذف المشرف.")
        }
    }

    fun postChatMessage(senderId: String, receiverId: String, senderName: String, recName: String, messageAr: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.saveChatMessage(
                ChatMessageEntity(
                    id = UUID.randomUUID().toString().take(10),
                    senderId = senderId,
                    receiverId = receiverId,
                    senderName = senderName,
                    recipientName = recName,
                    messageAr = messageAr,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    fun changeFcmChannel(channel: String, enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            when (channel) {
                "join_requests" -> {
                    fcmJoinRequestEnabled.value = enabled
                    dao.saveSetting(AdminSettingEntity("fcm_join_request_enabled", enabled.toString()))
                }
                "reports" -> {
                    fcmReportEnabled.value = enabled
                    dao.saveSetting(AdminSettingEntity("fcm_report_enabled", enabled.toString()))
                }
                "vip_subs" -> {
                    fcmSubsEnabled.value = enabled
                    dao.saveSetting(AdminSettingEntity("fcm_subs_enabled", enabled.toString()))
                }
            }
        }
    }

    fun updateFontConfigs(family: String, hexColor: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fontFamilyConfig.value = family
            fontColorConfig.value = hexColor
            dao.saveSetting(AdminSettingEntity("font_family", family))
            dao.saveSetting(AdminSettingEntity("font_color", hexColor))
        }
    }

    fun updateVipSubscription(providerId: String, active: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao.getAllProviders()
            val item = list.find { it.id == providerId }
            if (item != null) {
                val updated = item.copy(isVipSubscribed = active)
                dao.saveProvider(updated)
                _notifications.emit(if (active) "تم تفعيل الاشتراك الذهبي للخدمة ${item.name}!" else "تم إيقاف الاشتراك الذهبي.")
                logActivity("Admin", "تحديث حالة الاشتراك الذهبي لمقدم الخدمة: ${item.name}")
            }
        }
    }

    // Device Whitelisting
    fun saveDeviceToWhitelist(id: String, label: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.saveWhitelist(WhitelistDeviceEntity(id, label, System.currentTimeMillis()))
            whitelistDevices.value = dao.getWhitelistFlow().firstOrNull() ?: emptyList()
        }
    }

    fun deleteFromWhitelist(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteWhitelist(WhitelistDeviceEntity(id, ""))
            whitelistDevices.value = dao.getWhitelistFlow().firstOrNull() ?: emptyList()
        }
    }

    // Database Backup Simulation Actions
    fun executeDatabaseBackup(location: String) {
        viewModelScope.launch(Dispatchers.IO) {
            logActivity("Admin", "أخذ نسخة احتياطية يدوية إلى: $location")
            _notifications.emit("تم أخذ النسخة الاحتياطية بنجاح إلى: $location!")
        }
    }

    fun executeRestoreDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            logActivity("Admin", "استعادة قاعدة البيانات من النسخة الاحتياطية")
            _notifications.emit("تم استرداد كافة البيانات بنجاح!")
        }
    }

    fun clearTemporaryDatabaseLogs() {
        viewModelScope.launch(Dispatchers.IO) {
            logActivity("Admin", "تنظيف السجلات والبيانات المؤقتة لتسريع الأداء")
            _notifications.emit("تم تنظيف السجلات والملفات المؤقتة بنجاح!")
        }
    }
}
