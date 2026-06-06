package com.example.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.FirebaseFirestore


class AppDatabase private constructor(context: Context) : AppDao {
    private val appContext = context.applicationContext
    private val dbHelper = DatabaseHelper(appContext)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    // Flow states mimicking Room's continuous data stream securely
    private val _categoriesFlow = MutableStateFlow<List<CategoryEntity>>(emptyList())
    private val _providersFlow = MutableStateFlow<List<ProviderEntity>>(emptyList())
    private val _settingsFlow = MutableStateFlow(AdminSettingsEntity())
    private val _pendingProvidersFlow = MutableStateFlow<List<PendingProviderEntity>>(emptyList())
    private val _bannersFlow = MutableStateFlow<List<BannerEntity>>(emptyList())
    private val _reportsFlow = MutableStateFlow<List<ReportEntity>>(emptyList())
    private val _activityLogsFlow = MutableStateFlow<List<ActivityLogEntity>>(emptyList())
    private val _whitelistDevicesFlow = MutableStateFlow<List<DeviceWhitelistEntity>>(emptyList())
    private val _chatMessagesFlow = MutableStateFlow<List<ChatMessageEntity>>(emptyList())
    private val _citiesFlow = MutableStateFlow<List<CityEntity>>(emptyList())
    private val _supervisorsFlow = MutableStateFlow<List<SupervisorEntity>>(emptyList())

    init {
        // Initialize real-time Cloud Firestore synchronization list listeners
        setupFirestoreSync()

        // Hydrate initially and pre-populate if empty
        coroutineScope.launch {
            try {
                prepopulateIfEmpty()
                refreshAll()
            } catch (e: Exception) {
                Log.e("AppDatabase", "Error initializing database", e)
            }
        }
    }

    private suspend fun syncDatabaseState() = withContext(Dispatchers.IO) {
        refreshAll()
    }

    private fun refreshAll() {
        refreshCategories()
        refreshProviders()
        refreshSettings()
        refreshPendingProviders()
        refreshBanners()
        refreshReports()
        refreshActivityLogs()
        refreshWhitelistDevices()
        refreshChatMessages()
        refreshCities()
        refreshSupervisors()
    }

    private val firestore: FirebaseFirestore by lazy {
        try {
            val app = try {
                FirebaseApp.getInstance()
            } catch (e: Exception) {
                val options = FirebaseOptions.Builder()
                    .setApplicationId("1:658568660162:android:a61a72f574440f54fd275b")
                    .setApiKey("AIzaSyDHSY_vGko5FendFFVqnv5q4MdmnKrLi-g")
                    .setProjectId("wam2026-8d969")
                    .setStorageBucket("wam2026-8d969.firebasestorage.app")
                    .build()
                FirebaseApp.initializeApp(appContext, options)
            }
            FirebaseFirestore.getInstance(app)
        } catch (e: Exception) {
            Log.e("AppDatabase", "Error initializing Firestore safely, falling back directly to default instance", e)
            try {
                FirebaseFirestore.getInstance()
            } catch (ex: Exception) {
                Log.e("AppDatabase", "CRITICAL fallback Firestore initialization failed", ex)
                throw ex
            }
        }
    }

    fun getFirestoreInstance(): FirebaseFirestore = firestore

    private var servicesListener: com.google.firebase.firestore.ListenerRegistration? = null

    override fun updateCategoryFilter(categoryId: String?) {
        try {
            servicesListener?.remove()
            val query = if (categoryId == null) {
                firestore.collection("services")
            } else {
                firestore.collection("services").whereEqualTo("categoryId", categoryId)
            }
            servicesListener = query.addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.e("AppDatabase", "Firestore services listen failed", e)
                    return@addSnapshotListener
                }
                if (snapshots != null) {
                    if (snapshots.isEmpty && categoryId == null) {
                        try {
                            seedDefaultProvidersToFirestore()
                        } catch (ex: Exception) {
                            Log.e("AppDatabase", "Seeding default providers failed", ex)
                        }
                    } else {
                        val list = snapshots.documents.mapNotNull { doc ->
                            try {
                                doc.data?.toProviderEntity()
                            } catch (ex: Exception) {
                                Log.e("AppDatabase", "Failing to decode provider entity mapping", ex)
                                null
                            }
                        }.sortedByDescending { it.isPinned }
                        _providersFlow.value = list
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("AppDatabase", "Error during updateCategoryFilter execution", e)
        }
    }

    private fun setupFirestoreSync() {
        try {
            // Real-Time Listener for Firestore categories with Snapshot Listener
            firestore.collection("categories")
                .addSnapshotListener { snapshots, e ->
                    if (e != null) {
                        Log.e("AppDatabase", "Firestore categories listen failed", e)
                        return@addSnapshotListener
                    }
                    if (snapshots != null) {
                        if (snapshots.isEmpty) {
                            try {
                                seedDefaultCategoriesToFirestore()
                            } catch (ex: Exception) {
                                Log.e("AppDatabase", "Seeding default categories failed", ex)
                            }
                        } else {
                            val list = snapshots.documents.mapNotNull { doc ->
                                try {
                                    doc.data?.toCategoryEntity()
                                } catch (ex: Exception) {
                                    Log.e("AppDatabase", "Failing to decode category entity mapping", ex)
                                    null
                                }
                            }.sortedBy { it.displayOrder }
                            _categoriesFlow.value = list
                        }
                    }
                }
        } catch (e: Exception) {
            Log.e("AppDatabase", "Error setting up categories real-time sync listeners", e)
        }

        // Initially listen to all services in real time
        try {
            updateCategoryFilter(null)
        } catch (e: Exception) {
            Log.e("AppDatabase", "Error starting initial services collection query sync", e)
        }
    }

    private fun seedDefaultCategoriesToFirestore() {
        Log.d("AppDatabase", "Seeding default categories to Firestore...")
        val defaultCategories = listOf(
            CategoryEntity("plumb", "سباكة وصيانة الأنابيب", "Plumbing & Pipe Repair", "plumbing", "إصلاح تسريبات المياه وتمديد الأنابيب المنزلية", 1),
            CategoryEntity("elec", "كهرباء وتوصيلات منزلية", "Electrical & Wiring", "electrical", "تركيب وتصليح الأفياش، الإضاءة والتحكم في الشبكة", 2),
            CategoryEntity("ac", "صيانة المكيفات والتبريد", "AC & Refrigeration", "ac_unit", "صيانة أجهزة التكييف والتهوية وإرسال شحنات الفريون", 3),
            CategoryEntity("carp", "نجارة وأشغال خشبية", "Carpentry & Woodwork", "carpentry", "صيانة الأبواب ومطابخ الألمنيوم والخشب وتركيب الأثاث", 4),
            CategoryEntity("paint", "دهانات وديكورات", "Painting & Decorating", "paint", "أعمال الطلاء والجبس بورد بجودة عالية ودقة العمل", 5),
            CategoryEntity("clean", "خدمات التنظيف والتعقيم", "Cleaning & Sanitization", "cleaning", "تنظيف الفلل والبيوت وخزانات المياه بأفضل المواد", 6)
        )
        for (cat in defaultCategories) {
            firestore.collection("categories").document(cat.id).set(cat.toMap())
                .addOnFailureListener { Log.e("AppDatabase", "Error seeding category: ${cat.id}", it) }
        }
    }

    private fun seedDefaultProvidersToFirestore() {
        Log.d("AppDatabase", "Seeding default providers to Firestore...")
        val defaultProviders = listOf(
            ProviderEntity("p1", "المهندس ماهر الشرعبي", "771234567", "plumb", "صنعاء - شارع حدة", true, 45, 10, true, 2500.0, true, true, true, "APPROVED", 120, 15.3694, 44.1910),
            ProviderEntity("p2", "فني السباكة علي عياش", "733445566", "plumb", "عدن - المنصورة", true, 38, 9, false, 2000.0, false, false, false, "APPROVED", 45, 12.8000, 45.0333),
            ProviderEntity("p3", "الكهربائي محمد القدسي", "711556677", "elec", "صنعاء - الحصبة", true, 48, 10, true, 3000.0, true, true, true, "APPROVED", 90, 15.3720, 44.2000),
            ProviderEntity("p4", "فني توصيل شبكات خالد", "770112233", "elec", "تعز - شارع جمال", true, 20, 5, false, 1500.0, false, false, false, "APPROVED", 10, 13.5833, 44.0167),
            ProviderEntity("p5", "أبو رعد لصيانة التكييف", "773738291", "ac", "الحديدة - شارع صنعاء", true, 49, 11, true, 4000.0, false, true, true, "APPROVED", 160, 14.8000, 42.9500),
            ProviderEntity("p6", "الفني أحمد النجار", "738291029", "carp", "إب - الدائري", true, 32, 8, false, 3000.0, false, false, false, "APPROVED", 30, 13.9667, 44.1833),
            ProviderEntity("p7", "رائد لأعمال الدهان والطلاء", "774920492", "paint", "صنعاء - الأصبحي", false, 41, 9, true, 5000.0, false, true, true, "APPROVED", 80, 15.3100, 44.2200),
            ProviderEntity("p8", "مؤسسة النخبة للتنظيف", "772233445", "clean", "صنعاء - حدة", true, 50, 10, true, 10000.0, false, true, true, "APPROVED", 220, 15.3500, 44.1800)
        )
        for (prov in defaultProviders) {
            firestore.collection("services").document(prov.id).set(prov.toMap())
                .addOnFailureListener { Log.e("AppDatabase", "Error seeding provider: ${prov.id}", it) }
        }
    }

    private fun Map<String, Any>.toCategoryEntity(): CategoryEntity {
        return CategoryEntity(
            id = this["id"] as? String ?: "",
            nameAr = this["nameAr"] as? String ?: "",
            nameEn = this["nameEn"] as? String ?: "",
            iconName = this["iconName"] as? String ?: "",
            description = this["description"] as? String ?: "",
            displayOrder = (this["displayOrder"] as? Number)?.toInt() ?: 0,
            parentId = this["parentId"] as? String ?: ""
        )
    }

    private fun CategoryEntity.toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "nameAr" to nameAr,
            "nameEn" to nameEn,
            "iconName" to iconName,
            "description" to description,
            "displayOrder" to displayOrder,
            "parentId" to parentId
        )
    }

    private fun Map<String, Any>.toProviderEntity(): ProviderEntity {
        return ProviderEntity(
            id = this["id"] as? String ?: "",
            name = this["name"] as? String ?: "",
            phone = this["phone"] as? String ?: "",
            categoryId = this["categoryId"] as? String ?: "",
            area = this["area"] as? String ?: "",
            isAvailable = this["isAvailable"] as? Boolean ?: true,
            ratingSum = (this["ratingSum"] as? Number)?.toInt() ?: 0,
            ratingCount = (this["ratingCount"] as? Number)?.toInt() ?: 0,
            isVip = this["isVip"] as? Boolean ?: false,
            basePrice = (this["basePrice"] as? Number)?.toDouble() ?: 0.0,
            isPinned = this["isPinned"] as? Boolean ?: false,
            isRecommended = this["isRecommended"] as? Boolean ?: false,
            isVerified = this["isVerified"] as? Boolean ?: false,
            subscriptionStatus = this["subscriptionStatus"] as? String ?: "NONE",
            loyaltyPoints = (this["loyaltyPoints"] as? Number)?.toInt() ?: 0,
            latitude = (this["latitude"] as? Number)?.toDouble() ?: 15.3694,
            longitude = (this["longitude"] as? Number)?.toDouble() ?: 44.1910,
            photoUri = this["photoUri"] as? String ?: "",
            idCardUri = this["idCardUri"] as? String ?: "",
            supportText = this["supportText"] as? String ?: ""
        )
    }

    private fun ProviderEntity.toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "name" to name,
            "phone" to phone,
            "categoryId" to categoryId,
            "area" to area,
            "isAvailable" to isAvailable,
            "ratingSum" to ratingSum,
            "ratingCount" to ratingCount,
            "isVip" to isVip,
            "basePrice" to basePrice,
            "isPinned" to isPinned,
            "isRecommended" to isRecommended,
            "isVerified" to isVerified,
            "subscriptionStatus" to subscriptionStatus,
            "loyaltyPoints" to loyaltyPoints,
            "latitude" to latitude,
            "longitude" to longitude,
            "photoUri" to photoUri,
            "idCardUri" to idCardUri,
            "supportText" to supportText
        )
    }

    private fun refreshCategories() {
        // Handled by Firestore real-time snapshot listeners
    }

    private fun refreshProviders() {
        // Handled by Firestore real-time snapshot listeners
    }

    private fun refreshSettings() {
        coroutineScope.launch(Dispatchers.IO) {
            var db: SQLiteDatabase? = null
            var cursor: Cursor? = null
            var settings = AdminSettingsEntity()
            try {
                db = dbHelper.readableDatabase
                cursor = db.rawQuery("SELECT * FROM admin_settings WHERE id = 'SINGLETON_SETTINGS' LIMIT 1", null)
                if (cursor != null && cursor.moveToFirst()) {
                    val appNameCol = cursor.getColumnIndexOrThrow("appName")
                    val welcomeCol = cursor.getColumnIndexOrThrow("welcomeMessage")
                    val footerCol = cursor.getColumnIndexOrThrow("footerMessage")
                    val themeCol = cursor.getColumnIndexOrThrow("activeThemeId")
                    val showVipCol = cursor.getColumnIndexOrThrow("showVipOnly")
                    val timeCol = cursor.getColumnIndexOrThrow("syncTimestamp")
                    val phoneCol = cursor.getColumnIndexOrThrow("supportPhone")
                    val emailCol = cursor.getColumnIndexOrThrow("supportEmail")
                    val whatsappCol = cursor.getColumnIndexOrThrow("supportWhatsapp")
                    val versionCol = cursor.getColumnIndexOrThrow("appVersion")
                    val passwordCol = cursor.getColumnIndexOrThrow("adminPassword")
                    val hidePromoCol = cursor.getColumnIndexOrThrow("hidePromoFooter")
                    val asHiddenCol = cursor.getColumnIndexOrThrow("assistantHidden")
                    val asSizeCol = cursor.getColumnIndexOrThrow("assistantSize")
                    val asIconCol = cursor.getColumnIndexOrThrow("assistantIcon")
                    val asIconEffectsCol = cursor.getColumnIndexOrThrow("assistantIconEffects")
                    val asXCol = cursor.getColumnIndexOrThrow("assistantXPercent")
                    val asYCol = cursor.getColumnIndexOrThrow("assistantYPercent")
                    val chatHiddenCol = cursor.getColumnIndexOrThrow("chatHidden")
                    val chatSizeCol = cursor.getColumnIndexOrThrow("chatSize")
                    val chatIconCol = cursor.getColumnIndexOrThrow("chatIcon")
                    val chatIconEffectsCol = cursor.getColumnIndexOrThrow("chatIconEffects")
                    val chatXCol = cursor.getColumnIndexOrThrow("chatXPercent")
                    val chatYCol = cursor.getColumnIndexOrThrow("chatYPercent")
                    val fcmCol = cursor.getColumnIndexOrThrow("fcmChannelsJson")
                    val customPrimaryCol = cursor.getColumnIndexOrThrow("customPrimaryHex")
                    val customSecondaryCol = cursor.getColumnIndexOrThrow("customSecondaryHex")
                    val fontNameCol = cursor.getColumnIndexOrThrow("fontName")
                    val fontColorCol = cursor.getColumnIndexOrThrow("fontColorHex")
                    val maintenanceCol = cursor.getColumnIndexOrThrow("isMaintenanceActive")
                    val speechCol = cursor.getColumnIndexOrThrow("isSpeechSearchEnabled")
                    val radiusCol = cursor.getColumnIndexOrThrow("maxSearchRadiusKm")
                    val saverCol = cursor.getColumnIndexOrThrow("isDataSaverActive")
                    val qualityCol = cursor.getColumnIndexOrThrow("imageQualityPercent")
                    val showSubscriptionsCol = cursor.getColumnIndexOrThrow("showSubscriptionsFeature")
                    
                    settings = AdminSettingsEntity(
                        id = "SINGLETON_SETTINGS",
                        appName = cursor.getString(appNameCol),
                        welcomeMessage = cursor.getString(welcomeCol),
                        footerMessage = cursor.getString(footerCol),
                        activeThemeId = cursor.getString(themeCol),
                        showVipOnly = cursor.getInt(showVipCol) == 1,
                        syncTimestamp = cursor.getLong(timeCol),
                        supportPhone = cursor.getString(phoneCol),
                        supportEmail = cursor.getString(emailCol),
                        supportWhatsapp = cursor.getString(whatsappCol),
                        appVersion = cursor.getString(versionCol),
                        adminPassword = cursor.getString(passwordCol),
                        hidePromoFooter = cursor.getInt(hidePromoCol) == 1,
                        assistantHidden = cursor.getInt(asHiddenCol) == 1,
                        assistantSize = cursor.getInt(asSizeCol),
                        assistantIcon = cursor.getString(asIconCol),
                        assistantIconEffects = cursor.getString(asIconEffectsCol),
                        assistantXPercent = cursor.getFloat(asXCol),
                        assistantYPercent = cursor.getFloat(asYCol),
                        chatHidden = cursor.getInt(chatHiddenCol) == 1,
                        chatSize = cursor.getInt(chatSizeCol),
                        chatIcon = cursor.getString(chatIconCol),
                        chatIconEffects = cursor.getString(chatIconEffectsCol),
                        chatXPercent = cursor.getFloat(chatXCol),
                        chatYPercent = cursor.getFloat(chatYCol),
                        fcmChannelsJson = cursor.getString(fcmCol),
                        customPrimaryHex = cursor.getString(customPrimaryCol),
                        customSecondaryHex = cursor.getString(customSecondaryCol),
                        fontName = cursor.getString(fontNameCol),
                        fontColorHex = cursor.getString(fontColorCol),
                        isMaintenanceActive = cursor.getInt(maintenanceCol) == 1,
                        isSpeechSearchEnabled = cursor.getInt(speechCol) == 1,
                        maxSearchRadiusKm = cursor.getInt(radiusCol),
                        isDataSaverActive = cursor.getInt(saverCol) == 1,
                        imageQualityPercent = cursor.getInt(qualityCol),
                        showSubscriptionsFeature = cursor.getInt(showSubscriptionsCol) == 1
                    )
                }
            } catch (e: Exception) {
                Log.e("AppDatabase", "Error loading settings", e)
            } finally {
                cursor?.close()
            }
            _settingsFlow.value = settings
        }
    }

    private fun refreshPendingProviders() {
        coroutineScope.launch(Dispatchers.IO) {
            val list = mutableListOf<PendingProviderEntity>()
            var db: SQLiteDatabase? = null
            var cursor: Cursor? = null
            try {
                db = dbHelper.readableDatabase
                cursor = db.rawQuery("SELECT * FROM pending_providers ORDER BY submitDate DESC", null)
                if (cursor != null && cursor.moveToFirst()) {
                    val idCol = cursor.getColumnIndexOrThrow("id")
                    val nameCol = cursor.getColumnIndexOrThrow("name")
                    val phoneCol = cursor.getColumnIndexOrThrow("phone")
                    val categoryCol = cursor.getColumnIndexOrThrow("categoryId")
                    val areaCol = cursor.getColumnIndexOrThrow("area")
                    val hoodCol = cursor.getColumnIndexOrThrow("localNeighborhood")
                    val coordsCol = cursor.getColumnIndexOrThrow("coords")
                    val photoCol = cursor.getColumnIndexOrThrow("photoUri")
                    val cardCol = cursor.getColumnIndexOrThrow("idCardUri")
                    val dateCol = cursor.getColumnIndexOrThrow("submitDate")
                    val reasonCol = cursor.getColumnIndexOrThrow("rejectionReason")
                    val statusCol = cursor.getColumnIndexOrThrow("status")

                    do {
                        list.add(
                            PendingProviderEntity(
                                id = cursor.getString(idCol),
                                name = cursor.getString(nameCol),
                                phone = cursor.getString(phoneCol),
                                categoryId = cursor.getString(categoryCol),
                                area = cursor.getString(areaCol),
                                localNeighborhood = cursor.getString(hoodCol),
                                coords = cursor.getString(coordsCol),
                                photoUri = cursor.getString(photoCol),
                                idCardUri = cursor.getString(cardCol),
                                submitDate = cursor.getLong(dateCol),
                                rejectionReason = cursor.getString(reasonCol),
                                status = cursor.getString(statusCol)
                            )
                        )
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                Log.e("AppDatabase", "Error loading pending providers", e)
            } finally {
                cursor?.close()
            }
            _pendingProvidersFlow.value = list
        }
    }

    private fun refreshBanners() {
        coroutineScope.launch(Dispatchers.IO) {
            val list = mutableListOf<BannerEntity>()
            var db: SQLiteDatabase? = null
            var cursor: Cursor? = null
            try {
                db = dbHelper.readableDatabase
                cursor = db.rawQuery("SELECT * FROM banners WHERE isActive = 1", null)
                if (cursor != null && cursor.moveToFirst()) {
                    val idCol = cursor.getColumnIndexOrThrow("id")
                    val titleCol = cursor.getColumnIndexOrThrow("title")
                    val imgCol = cursor.getColumnIndexOrThrow("imageUrl")
                    val urlCol = cursor.getColumnIndexOrThrow("redirectUrl")
                    val typeCol = cursor.getColumnIndexOrThrow("displayType")
                    val sizeCol = cursor.getColumnIndexOrThrow("bannerSize")
                    val durCol = cursor.getColumnIndexOrThrow("durationSeconds")
                    val activeCol = cursor.getColumnIndexOrThrow("isActive")

                    do {
                        list.add(
                            BannerEntity(
                                id = cursor.getString(idCol),
                                title = cursor.getString(titleCol),
                                imageUrl = cursor.getString(imgCol),
                                redirectUrl = cursor.getString(urlCol),
                                displayType = cursor.getString(typeCol),
                                bannerSize = cursor.getString(sizeCol),
                                durationSeconds = cursor.getInt(durCol),
                                isActive = cursor.getInt(activeCol) == 1
                            )
                        )
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                Log.e("AppDatabase", "Error loading banners", e)
            } finally {
                cursor?.close()
            }
            _bannersFlow.value = list
        }
    }

    private fun refreshReports() {
        coroutineScope.launch(Dispatchers.IO) {
            val list = mutableListOf<ReportEntity>()
            var db: SQLiteDatabase? = null
            var cursor: Cursor? = null
            try {
                db = dbHelper.readableDatabase
                cursor = db.rawQuery("SELECT * FROM reports ORDER BY timestamp DESC", null)
                if (cursor != null && cursor.moveToFirst()) {
                    val idCol = cursor.getColumnIndexOrThrow("id")
                    val pIdCol = cursor.getColumnIndexOrThrow("providerId")
                    val pNameCol = cursor.getColumnIndexOrThrow("providerName")
                    val repCol = cursor.getColumnIndexOrThrow("reporterName")
                    val contentCol = cursor.getColumnIndexOrThrow("content")
                    val timeCol = cursor.getColumnIndexOrThrow("timestamp")
                    val statusCol = cursor.getColumnIndexOrThrow("status")

                    do {
                        list.add(
                            ReportEntity(
                                id = cursor.getString(idCol),
                                providerId = cursor.getString(pIdCol),
                                providerName = cursor.getString(pNameCol),
                                reporterName = cursor.getString(repCol),
                                content = cursor.getString(contentCol),
                                timestamp = cursor.getLong(timeCol),
                                status = cursor.getString(statusCol)
                            )
                        )
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                Log.e("AppDatabase", "Error loading reports", e)
            } finally {
                cursor?.close()
            }
            _reportsFlow.value = list
        }
    }

    private fun refreshActivityLogs() {
        coroutineScope.launch(Dispatchers.IO) {
            val list = mutableListOf<ActivityLogEntity>()
            var db: SQLiteDatabase? = null
            var cursor: Cursor? = null
            try {
                db = dbHelper.readableDatabase
                cursor = db.rawQuery("SELECT * FROM activity_logs ORDER BY timestamp DESC LIMIT 200", null)
                if (cursor != null && cursor.moveToFirst()) {
                    val idCol = cursor.getColumnIndexOrThrow("id")
                    val adminCol = cursor.getColumnIndexOrThrow("adminName")
                    val descCol = cursor.getColumnIndexOrThrow("actionDesc")
                    val timeCol = cursor.getColumnIndexOrThrow("timestamp")

                    do {
                        list.add(
                            ActivityLogEntity(
                                id = cursor.getString(idCol),
                                adminName = cursor.getString(adminCol),
                                actionDesc = cursor.getString(descCol),
                                timestamp = cursor.getLong(timeCol)
                            )
                        )
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                Log.e("AppDatabase", "Error loading activity logs", e)
            } finally {
                cursor?.close()
            }
            _activityLogsFlow.value = list
        }
    }

    private fun refreshWhitelistDevices() {
        coroutineScope.launch(Dispatchers.IO) {
            val list = mutableListOf<DeviceWhitelistEntity>()
            var db: SQLiteDatabase? = null
            var cursor: Cursor? = null
            try {
                db = dbHelper.readableDatabase
                cursor = db.rawQuery("SELECT * FROM whitelist_devices", null)
                if (cursor != null && cursor.moveToFirst()) {
                    val idCol = cursor.getColumnIndexOrThrow("id")
                    val nameCol = cursor.getColumnIndexOrThrow("deviceName")
                    val allowedCol = cursor.getColumnIndexOrThrow("isAllowed")

                    do {
                        list.add(
                            DeviceWhitelistEntity(
                                id = cursor.getString(idCol),
                                deviceName = cursor.getString(nameCol),
                                isAllowed = cursor.getInt(allowedCol) == 1
                            )
                        )
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                Log.e("AppDatabase", "Error loading whitelisted devices", e)
            } finally {
                cursor?.close()
            }
            _whitelistDevicesFlow.value = list
        }
    }

    private fun refreshChatMessages() {
        coroutineScope.launch(Dispatchers.IO) {
            val list = mutableListOf<ChatMessageEntity>()
            var db: SQLiteDatabase? = null
            var cursor: Cursor? = null
            try {
                db = dbHelper.readableDatabase
                cursor = db.rawQuery("SELECT * FROM chat_messages ORDER BY timestamp ASC", null)
                if (cursor != null && cursor.moveToFirst()) {
                    val idCol = cursor.getColumnIndexOrThrow("id")
                    val sndCol = cursor.getColumnIndexOrThrow("senderId")
                    val rcvCol = cursor.getColumnIndexOrThrow("receiverId")
                    val sndNameCol = cursor.getColumnIndexOrThrow("senderName")
                    val rcvNameCol = cursor.getColumnIndexOrThrow("receiverName")
                    val msgCol = cursor.getColumnIndexOrThrow("messageText")
                    val timeCol = cursor.getColumnIndexOrThrow("timestamp")
                    val readCol = cursor.getColumnIndexOrThrow("isRead")
                    val offlineCol = cursor.getColumnIndexOrThrow("isOfflineSent")

                    do {
                        list.add(
                            ChatMessageEntity(
                                id = cursor.getString(idCol),
                                senderId = cursor.getString(sndCol),
                                receiverId = cursor.getString(rcvCol),
                                senderName = cursor.getString(sndNameCol),
                                receiverName = cursor.getString(rcvNameCol),
                                messageText = cursor.getString(msgCol),
                                timestamp = cursor.getLong(timeCol),
                                isRead = cursor.getInt(readCol) == 1,
                                isOfflineSent = cursor.getInt(offlineCol) == 1
                            )
                        )
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                Log.e("AppDatabase", "Error loading chat messages", e)
            } finally {
                cursor?.close()
            }
            _chatMessagesFlow.value = list
        }
    }

    private fun refreshCities() {
        coroutineScope.launch(Dispatchers.IO) {
            val list = mutableListOf<CityEntity>()
            var db: SQLiteDatabase? = null
            var cursor: Cursor? = null
            try {
                db = dbHelper.readableDatabase
                cursor = db.rawQuery("SELECT * FROM cities ORDER BY nameAr ASC", null)
                if (cursor != null && cursor.moveToFirst()) {
                    val idCol = cursor.getColumnIndexOrThrow("id")
                    val arCol = cursor.getColumnIndexOrThrow("nameAr")
                    val enCol = cursor.getColumnIndexOrThrow("nameEn")
                    do {
                        list.add(
                            CityEntity(
                                id = cursor.getString(idCol),
                                nameAr = cursor.getString(arCol),
                                nameEn = cursor.getString(enCol)
                            )
                        )
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                Log.e("AppDatabase", "Error loading cities", e)
            } finally {
                cursor?.close()
            }
            _citiesFlow.value = list
        }
    }

    private fun refreshSupervisors() {
        coroutineScope.launch(Dispatchers.IO) {
            val list = mutableListOf<SupervisorEntity>()
            var db: SQLiteDatabase? = null
            var cursor: Cursor? = null
            try {
                db = dbHelper.readableDatabase
                cursor = db.rawQuery("SELECT * FROM supervisors ORDER BY username ASC", null)
                if (cursor != null && cursor.moveToFirst()) {
                    val idCol = cursor.getColumnIndexOrThrow("id")
                    val userCol = cursor.getColumnIndexOrThrow("username")
                    val passCol = cursor.getColumnIndexOrThrow("password")
                    val canAccCol = cursor.getColumnIndexOrThrow("canAcceptRejectRequests")
                    val canCatCol = cursor.getColumnIndexOrThrow("canManageCategories")
                    val canBanCol = cursor.getColumnIndexOrThrow("canManageBanners")
                    val canDelCol = cursor.getColumnIndexOrThrow("canDeleteProviders")
                    val canViewCol = cursor.getColumnIndexOrThrow("canViewReports")

                    do {
                        list.add(
                            SupervisorEntity(
                                id = cursor.getString(idCol),
                                username = cursor.getString(userCol),
                                password = cursor.getString(passCol),
                                canAcceptRejectRequests = cursor.getInt(canAccCol) == 1,
                                canManageCategories = cursor.getInt(canCatCol) == 1,
                                canManageBanners = cursor.getInt(canBanCol) == 1,
                                canDeleteProviders = cursor.getInt(canDelCol) == 1,
                                canViewReports = cursor.getInt(canViewCol) == 1
                            )
                        )
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                Log.e("AppDatabase", "Error loading supervisors", e)
            } finally {
                cursor?.close()
            }
            _supervisorsFlow.value = list
        }
    }

    private suspend fun prepopulateIfEmpty() = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        var cursor: Cursor? = null
        var count = 0
        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM categories", null)
            if (cursor != null && cursor.moveToFirst()) {
                count = cursor.getInt(0)
            }
        } finally {
            cursor?.close()
        }

        if (count == 0) {
            Log.d("AppDatabase", "Database is empty! Prepopulating default service categories and models.")
            
            // Default Categories
            val defaultCategories = listOf(
                CategoryEntity("plumb", "سباكة وصيانة الأنابيب", "Plumbing & Pipe Repair", "plumbing", "إصلاح تسريبات المياه وتمديد الأنابيب المنزلية", 1),
                CategoryEntity("elec", "كهرباء وتوصيلات منزلية", "Electrical & Wiring", "electrical", "تركيب وتصليح الأفياش، الإضاءة والتحكم في الشبكة", 2),
                CategoryEntity("ac", "صيانة المكيفات والتبريد", "AC & Refrigeration", "ac_unit", "صيانة أجهزة التكييف والتهوية وإرسال شحنات الفريون", 3),
                CategoryEntity("carp", "نجارة وأشغال خشبية", "Carpentry & Woodwork", "carpentry", "صيانة الأبواب ومطابخ الألمنيوم والخشب وتركيب الأثاث", 4),
                CategoryEntity("paint", "دهانات وديكورات", "Painting & Decorating", "paint", "أعمال الطلاء والجبس بورد بجودة عالية ودقة العمل", 5),
                CategoryEntity("clean", "خدمات التنظيف والتعقيم", "Cleaning & Sanitization", "cleaning", "تنظيف الفلل والبيوت وخزانات المياه بأفضل المواد", 6)
            )

            for (cat in defaultCategories) {
                val cv = ContentValues().apply {
                    put("id", cat.id)
                    put("nameAr", cat.nameAr)
                    put("nameEn", cat.nameEn)
                    put("iconName", cat.iconName)
                    put("description", cat.description)
                    put("displayOrder", cat.displayOrder)
                }
                db.insert("categories", null, cv)
            }

            // Default Service Providers
            val defaultProviders = listOf(
                ProviderEntity("p1", "المهندس ماهر الشرعبي", "771234567", "plumb", "صنعاء - شارع حدة", true, 45, 10, true, 2500.0, true, true, true, "APPROVED", 120, 15.3694, 44.1910),
                ProviderEntity("p2", "فني السباكة علي عياش", "733445566", "plumb", "عدن - المنصورة", true, 38, 9, false, 2000.0, false, false, false, "APPROVED", 45, 12.8000, 45.0333),
                ProviderEntity("p3", "الكهربائي محمد القدسي", "711556677", "elec", "صنعاء - الحصبة", true, 48, 10, true, 3000.0, true, true, true, "APPROVED", 90, 15.3720, 44.2000),
                ProviderEntity("p4", "فني توصيل شبكات خالد", "770112233", "elec", "تعز - شارع جمال", true, 20, 5, false, 1500.0, false, false, false, "APPROVED", 10, 13.5833, 44.0167),
                ProviderEntity("p5", "أبو رعد لصيانة التكييف", "773738291", "ac", "الحديدة - شارع صنعاء", true, 49, 11, true, 4000.0, false, true, true, "APPROVED", 160, 14.8000, 42.9500),
                ProviderEntity("p6", "الفني أحمد النجار", "738291029", "carp", "إب - الدائري", true, 32, 8, false, 3000.0, false, false, false, "APPROVED", 30, 13.9667, 44.1833),
                ProviderEntity("p7", "رائد لأعمال الدهان والطلاء", "774920492", "paint", "صنعاء - الأصبحي", false, 41, 9, true, 5000.0, false, true, true, "APPROVED", 80, 15.3100, 44.2200),
                ProviderEntity("p8", "مؤسسة النخبة للتنظيف", "772233445", "clean", "صنعاء - حدة", true, 50, 10, true, 10000.0, false, true, true, "APPROVED", 220, 15.3500, 44.1800)
            )

            for (prov in defaultProviders) {
                val cv = ContentValues().apply {
                    put("id", prov.id)
                    put("name", prov.name)
                    put("phone", prov.phone)
                    put("categoryId", prov.categoryId)
                    put("area", prov.area)
                    put("isAvailable", if (prov.isAvailable) 1 else 0)
                    put("ratingSum", prov.ratingSum)
                    put("ratingCount", prov.ratingCount)
                    put("isVip", if (prov.isVip) 1 else 0)
                    put("basePrice", prov.basePrice)
                    put("isPinned", if (prov.isPinned) 1 else 0)
                    put("isRecommended", if (prov.isRecommended) 1 else 0)
                    put("isVerified", if (prov.isVerified) 1 else 0)
                    put("subscriptionStatus", prov.subscriptionStatus)
                    put("loyaltyPoints", prov.loyaltyPoints)
                    put("latitude", prov.latitude)
                    put("longitude", prov.longitude)
                    put("photoUri", prov.photoUri)
                    put("idCardUri", prov.idCardUri)
                    put("supportText", prov.supportText)
                }
                db.insert("providers", null, cv)
            }

            // Fill default single settings
            val initialSettings = ContentValues().apply {
                put("id", "SINGLETON_SETTINGS")
                put("appName", "كل خدمات اليمن")
                put("welcomeMessage", "أهلاً ومرحباً بكم مع تطبيق كل خدمات اليمن - دقة، أمان وسرعة فائقة")
                put("footerMessage", "MAW 777644670")
                put("activeThemeId", "EMERALD_YEMEN")
                put("showVipOnly", 0)
                put("syncTimestamp", System.currentTimeMillis())
                put("supportPhone", "777644670")
                put("supportEmail", "support@mawservices.com")
                put("supportWhatsapp", "777644670")
                put("appVersion", "V2.6.2026")
                put("adminPassword", "WAM2026:maher736462")
                put("hidePromoFooter", 0)
                put("assistantHidden", 0)
                put("assistantSize", 54)
                put("assistantIcon", "smart_bot")
                put("assistantIconEffects", "none")
                put("assistantXPercent", 0.9f)
                put("assistantYPercent", 0.85f)
                put("chatHidden", 0)
                put("chatSize", 54)
                put("chatIcon", "chat_default")
                put("chatIconEffects", "none")
                put("chatXPercent", 0.9f)
                put("chatYPercent", 0.70f)
                put("fcmChannelsJson", "join_requests:true,reports:true,memberships:true")
                put("customPrimaryHex", "#047857")
                put("customSecondaryHex", "#064E3B")
                put("fontName", "DefaultBold")
                put("fontColorHex", "#FFFFFF")
                put("isMaintenanceActive", 0)
                put("isSpeechSearchEnabled", 1)
                put("maxSearchRadiusKm", 20)
                put("isDataSaverActive", 0)
                put("imageQualityPercent", 75)
                put("showSubscriptionsFeature", 1)
            }
            db.insert("admin_settings", null, initialSettings)

            // Dynamic Banners
            val testBanner = ContentValues().apply {
                put("id", "b1")
                put("title", "تخفيضات صيفية 30% على صيانة المكيفات المركزية")
                put("imageUrl", "https://images.unsplash.com/photo-1621905251189-08b45d6a269e")
                put("redirectUrl", "ac")
                put("displayType", "IMAGE")
                put("bannerSize", "MEDIUM")
                put("durationSeconds", 8)
                put("isActive", 1)
            }
            db.insert("banners", null, testBanner)

            // Dynamic Cities
            val defaultCities = listOf(
                CityEntity("sa", "صنعاء", "Sanaa"),
                CityEntity("ad", "عدن", "Aden"),
                CityEntity("ta", "تعز", "Taiz"),
                CityEntity("ho", "الحديدة", "Hodeidah"),
                CityEntity("ib", "إب", "Ibb"),
                CityEntity("ha", "حضرموت", "Hadhramout")
            )
            for (city in defaultCities) {
                val cv = ContentValues().apply {
                    put("id", city.id)
                    put("nameAr", city.nameAr)
                    put("nameEn", city.nameEn)
                }
                db.insert("cities", null, cv)
            }

            // Default Whitelisted devices
            val masterDevice = ContentValues().apply {
                put("id", "d1")
                put("deviceName", "Yemen Owner Android Phone (Emulator)")
                put("isAllowed", 1)
            }
            db.insert("whitelist_devices", null, masterDevice)
        }
    }

    // --- AppDao implementations ---
    override fun getCategoriesFlow(): Flow<List<CategoryEntity>> = _categoriesFlow.asStateFlow()

    override suspend fun insertCategory(category: CategoryEntity) = withContext(Dispatchers.IO) {
        firestore.collection("categories").document(category.id).set(category.toMap())
        Unit
    }

    override suspend fun deleteCategory(id: String) = withContext(Dispatchers.IO) {
        firestore.collection("categories").document(id).delete()
        Unit
    }

    override fun getProvidersFlow(): Flow<List<ProviderEntity>> = _providersFlow.asStateFlow()

    override suspend fun insertProvider(provider: ProviderEntity) = withContext(Dispatchers.IO) {
        firestore.collection("services").document(provider.id).set(provider.toMap())
        Unit
    }

    override suspend fun deleteProvider(id: String) = withContext(Dispatchers.IO) {
        firestore.collection("services").document(id).delete()
        Unit
    }

    override suspend fun rateProvider(id: String, rating: Int) = withContext(Dispatchers.IO) {
        val docRef = firestore.collection("services").document(id)
        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(docRef)
            if (snapshot.exists()) {
                val currentSum = (snapshot.get("ratingSum") as? Number)?.toInt() ?: 0
                val currentCount = (snapshot.get("ratingCount") as? Number)?.toInt() ?: 0
                val currentPoints = (snapshot.get("loyaltyPoints") as? Number)?.toInt() ?: 0
                
                transaction.update(docRef, mapOf(
                    "ratingSum" to currentSum + rating,
                    "ratingCount" to currentCount + 1,
                    "loyaltyPoints" to currentPoints + 20
                ))
            }
        }.addOnFailureListener {
            Log.e("AppDatabase", "Error rating provider in transaction", it)
        }
        Unit
    }

    override fun getSettingsFlow(): Flow<AdminSettingsEntity> = _settingsFlow.asStateFlow()

    override suspend fun getSettings(): AdminSettingsEntity = withContext(Dispatchers.IO) {
        var db: SQLiteDatabase? = null
        var cursor: Cursor? = null
        var settings = AdminSettingsEntity()
        try {
            db = dbHelper.readableDatabase
            cursor = db.rawQuery("SELECT * FROM admin_settings WHERE id = 'SINGLETON_SETTINGS' LIMIT 1", null)
            if (cursor != null && cursor.moveToFirst()) {
                val appNameCol = cursor.getColumnIndexOrThrow("appName")
                val welcomeCol = cursor.getColumnIndexOrThrow("welcomeMessage")
                val footerCol = cursor.getColumnIndexOrThrow("footerMessage")
                val themeCol = cursor.getColumnIndexOrThrow("activeThemeId")
                val showVipCol = cursor.getColumnIndexOrThrow("showVipOnly")
                val timeCol = cursor.getColumnIndexOrThrow("syncTimestamp")
                val phoneCol = cursor.getColumnIndexOrThrow("supportPhone")
                val emailCol = cursor.getColumnIndexOrThrow("supportEmail")
                val whatsappCol = cursor.getColumnIndexOrThrow("supportWhatsapp")
                val versionCol = cursor.getColumnIndexOrThrow("appVersion")
                val passwordCol = cursor.getColumnIndexOrThrow("adminPassword")
                val hidePromoCol = cursor.getColumnIndexOrThrow("hidePromoFooter")
                val asHiddenCol = cursor.getColumnIndexOrThrow("assistantHidden")
                val asSizeCol = cursor.getColumnIndexOrThrow("assistantSize")
                val asIconCol = cursor.getColumnIndexOrThrow("assistantIcon")
                val asIconEffectsCol = cursor.getColumnIndexOrThrow("assistantIconEffects")
                val asXCol = cursor.getColumnIndexOrThrow("assistantXPercent")
                val asYCol = cursor.getColumnIndexOrThrow("assistantYPercent")
                val chatHiddenCol = cursor.getColumnIndexOrThrow("chatHidden")
                val chatSizeCol = cursor.getColumnIndexOrThrow("chatSize")
                val chatIconCol = cursor.getColumnIndexOrThrow("chatIcon")
                val chatIconEffectsCol = cursor.getColumnIndexOrThrow("chatIconEffects")
                val chatXCol = cursor.getColumnIndexOrThrow("chatXPercent")
                val chatYCol = cursor.getColumnIndexOrThrow("chatYPercent")
                val fcmCol = cursor.getColumnIndexOrThrow("fcmChannelsJson")
                val customPrimaryCol = cursor.getColumnIndexOrThrow("customPrimaryHex")
                val customSecondaryCol = cursor.getColumnIndexOrThrow("customSecondaryHex")
                val fontNameCol = cursor.getColumnIndexOrThrow("fontName")
                val fontColorCol = cursor.getColumnIndexOrThrow("fontColorHex")
                val maintenanceCol = cursor.getColumnIndexOrThrow("isMaintenanceActive")
                val speechCol = cursor.getColumnIndexOrThrow("isSpeechSearchEnabled")
                val radiusCol = cursor.getColumnIndexOrThrow("maxSearchRadiusKm")
                val saverCol = cursor.getColumnIndexOrThrow("isDataSaverActive")
                val qualityCol = cursor.getColumnIndexOrThrow("imageQualityPercent")
                val showSubscriptionsCol = cursor.getColumnIndexOrThrow("showSubscriptionsFeature")
                
                settings = AdminSettingsEntity(
                    id = "SINGLETON_SETTINGS",
                    appName = cursor.getString(appNameCol),
                    welcomeMessage = cursor.getString(welcomeCol),
                    footerMessage = cursor.getString(footerCol),
                    activeThemeId = cursor.getString(themeCol),
                    showVipOnly = cursor.getInt(showVipCol) == 1,
                    syncTimestamp = cursor.getLong(timeCol),
                    supportPhone = cursor.getString(phoneCol),
                    supportEmail = cursor.getString(emailCol),
                    supportWhatsapp = cursor.getString(whatsappCol),
                    appVersion = cursor.getString(versionCol),
                    adminPassword = cursor.getString(passwordCol),
                    hidePromoFooter = cursor.getInt(hidePromoCol) == 1,
                    assistantHidden = cursor.getInt(asHiddenCol) == 1,
                    assistantSize = cursor.getInt(asSizeCol),
                    assistantIcon = cursor.getString(asIconCol),
                    assistantIconEffects = cursor.getString(asIconEffectsCol),
                    assistantXPercent = cursor.getFloat(asXCol),
                    assistantYPercent = cursor.getFloat(asYCol),
                    chatHidden = cursor.getInt(chatHiddenCol) == 1,
                    chatSize = cursor.getInt(chatSizeCol),
                    chatIcon = cursor.getString(chatIconCol),
                    chatIconEffects = cursor.getString(chatIconEffectsCol),
                    chatXPercent = cursor.getFloat(chatXCol),
                    chatYPercent = cursor.getFloat(chatYCol),
                    fcmChannelsJson = cursor.getString(fcmCol),
                    customPrimaryHex = cursor.getString(customPrimaryCol),
                    customSecondaryHex = cursor.getString(customSecondaryCol),
                    fontName = cursor.getString(fontNameCol),
                    fontColorHex = cursor.getString(fontColorCol),
                    isMaintenanceActive = cursor.getInt(maintenanceCol) == 1,
                    isSpeechSearchEnabled = cursor.getInt(speechCol) == 1,
                    maxSearchRadiusKm = cursor.getInt(radiusCol),
                    isDataSaverActive = cursor.getInt(saverCol) == 1,
                    imageQualityPercent = cursor.getInt(qualityCol),
                    showSubscriptionsFeature = cursor.getInt(showSubscriptionsCol) == 1
                )
            }
        } catch (e: Exception) {
            Log.e("AppDatabase", "Error fetching settings directly", e)
        } finally {
            cursor?.close()
        }
        _settingsFlow.value = settings
        settings
    }

    override suspend fun saveSettings(settings: AdminSettingsEntity) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id", "SINGLETON_SETTINGS")
            put("appName", settings.appName)
            put("welcomeMessage", settings.welcomeMessage)
            put("footerMessage", settings.footerMessage)
            put("activeThemeId", settings.activeThemeId)
            put("showVipOnly", if (settings.showVipOnly) 1 else 0)
            put("syncTimestamp", System.currentTimeMillis())
            put("supportPhone", settings.supportPhone)
            put("supportEmail", settings.supportEmail)
            put("supportWhatsapp", settings.supportWhatsapp)
            put("appVersion", settings.appVersion)
            put("adminPassword", settings.adminPassword)
            put("hidePromoFooter", if (settings.hidePromoFooter) 1 else 0)
            put("assistantHidden", if (settings.assistantHidden) 1 else 0)
            put("assistantSize", settings.assistantSize)
            put("assistantIcon", settings.assistantIcon)
            put("assistantIconEffects", settings.assistantIconEffects)
            put("assistantXPercent", settings.assistantXPercent)
            put("assistantYPercent", settings.assistantYPercent)
            put("chatHidden", if (settings.chatHidden) 1 else 0)
            put("chatSize", settings.chatSize)
            put("chatIcon", settings.chatIcon)
            put("chatIconEffects", settings.chatIconEffects)
            put("chatXPercent", settings.chatXPercent)
            put("chatYPercent", settings.chatYPercent)
            put("fcmChannelsJson", settings.fcmChannelsJson)
            put("customPrimaryHex", settings.customPrimaryHex)
            put("customSecondaryHex", settings.customSecondaryHex)
            put("fontName", settings.fontName)
            put("fontColorHex", settings.fontColorHex)
            put("isMaintenanceActive", if (settings.isMaintenanceActive) 1 else 0)
            put("isSpeechSearchEnabled", if (settings.isSpeechSearchEnabled) 1 else 0)
            put("maxSearchRadiusKm", settings.maxSearchRadiusKm)
            put("isDataSaverActive", if (settings.isDataSaverActive) 1 else 0)
            put("imageQualityPercent", settings.imageQualityPercent)
            put("showSubscriptionsFeature", if (settings.showSubscriptionsFeature) 1 else 0)
        }
        db.insertWithOnConflict("admin_settings", null, values, SQLiteDatabase.CONFLICT_REPLACE)
        _settingsFlow.value = settings
        refreshSettings()
    }

    // Pending Requests
    override fun getPendingProvidersFlow(): Flow<List<PendingProviderEntity>> = _pendingProvidersFlow.asStateFlow()

    override suspend fun insertPendingProvider(pending: PendingProviderEntity) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id", pending.id)
            put("name", pending.name)
            put("phone", pending.phone)
            put("categoryId", pending.categoryId)
            put("area", pending.area)
            put("localNeighborhood", pending.localNeighborhood)
            put("coords", pending.coords)
            put("photoUri", pending.photoUri)
            put("idCardUri", pending.idCardUri)
            put("submitDate", pending.submitDate)
            put("rejectionReason", pending.rejectionReason)
            put("status", pending.status)
        }
        db.insertWithOnConflict("pending_providers", null, values, SQLiteDatabase.CONFLICT_REPLACE)
        refreshPendingProviders()
    }

    override suspend fun deletePendingProvider(id: String) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        db.delete("pending_providers", "id = ?", arrayOf(id))
        refreshPendingProviders()
    }

    override suspend fun updatePendingStatus(id: String, status: String, rejectionReason: String) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        db.execSQL(
            "UPDATE pending_providers SET status = ?, rejectionReason = ? WHERE id = ?",
            arrayOf(status, rejectionReason, id)
        )
        refreshPendingProviders()
    }

    // Banners
    override fun getBannersFlow(): Flow<List<BannerEntity>> = _bannersFlow.asStateFlow()

    override suspend fun insertBanner(banner: BannerEntity) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id", banner.id)
            put("title", banner.title)
            put("imageUrl", banner.imageUrl)
            put("redirectUrl", banner.redirectUrl)
            put("displayType", banner.displayType)
            put("bannerSize", banner.bannerSize)
            put("durationSeconds", banner.durationSeconds)
            put("isActive", if (banner.isActive) 1 else 0)
        }
        db.insertWithOnConflict("banners", null, values, SQLiteDatabase.CONFLICT_REPLACE)
        refreshBanners()
    }

    override suspend fun deleteBanner(id: String) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        db.delete("banners", "id = ?", arrayOf(id))
        refreshBanners()
    }

    // Reports
    override fun getReportsFlow(): Flow<List<ReportEntity>> = _reportsFlow.asStateFlow()

    override suspend fun insertReport(report: ReportEntity) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id", report.id)
            put("providerId", report.providerId)
            put("providerName", report.providerName)
            put("reporterName", report.reporterName)
            put("content", report.content)
            put("timestamp", report.timestamp)
            put("status", report.status)
        }
        db.insertWithOnConflict("reports", null, values, SQLiteDatabase.CONFLICT_REPLACE)
        refreshReports()
    }

    override suspend fun deleteReport(id: String) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        db.delete("reports", "id = ?", arrayOf(id))
        refreshReports()
    }

    // Logs
    override fun getActivityLogsFlow(): Flow<List<ActivityLogEntity>> = _activityLogsFlow.asStateFlow()

    override suspend fun insertActivityLog(log: ActivityLogEntity) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id", log.id)
            put("adminName", log.adminName)
            put("actionDesc", log.actionDesc)
            put("timestamp", log.timestamp)
        }
        db.insertWithOnConflict("activity_logs", null, values, SQLiteDatabase.CONFLICT_REPLACE)
        refreshActivityLogs()
    }

    // Devices
    override fun getWhitelistDevicesFlow(): Flow<List<DeviceWhitelistEntity>> = _whitelistDevicesFlow.asStateFlow()

    override suspend fun insertDevice(device: DeviceWhitelistEntity) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id", device.id)
            put("deviceName", device.deviceName)
            put("isAllowed", if (device.isAllowed) 1 else 0)
        }
        db.insertWithOnConflict("whitelist_devices", null, values, SQLiteDatabase.CONFLICT_REPLACE)
        refreshWhitelistDevices()
    }

    override suspend fun deleteDevice(id: String) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        db.delete("whitelist_devices", "id = ?", arrayOf(id))
        refreshWhitelistDevices()
    }

    // Chat messages
    override fun getAllChatMessagesFlow(): Flow<List<ChatMessageEntity>> = _chatMessagesFlow.asStateFlow()

    override fun getChatMessagesFlow(userId1: String, userId2: String): Flow<List<ChatMessageEntity>> = _chatMessagesFlow.asStateFlow()

    override suspend fun insertChatMessage(msg: ChatMessageEntity) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id", msg.id)
            put("senderId", msg.senderId)
            put("receiverId", msg.receiverId)
            put("senderName", msg.senderName)
            put("receiverName", msg.receiverName)
            put("messageText", msg.messageText)
            put("timestamp", msg.timestamp)
            put("isRead", if (msg.isRead) 1 else 0)
            put("isOfflineSent", if (msg.isOfflineSent) 1 else 0)
        }
        db.insertWithOnConflict("chat_messages", null, values, SQLiteDatabase.CONFLICT_REPLACE)
        refreshChatMessages()
    }

    override suspend fun clearAllChatMessages() = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        db.delete("chat_messages", null, null)
        refreshChatMessages()
    }

    // Cities
    override fun getCitiesFlow(): Flow<List<CityEntity>> = _citiesFlow.asStateFlow()

    override suspend fun insertCity(city: CityEntity) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id", city.id)
            put("nameAr", city.nameAr)
            put("nameEn", city.nameEn)
        }
        db.insertWithOnConflict("cities", null, values, SQLiteDatabase.CONFLICT_REPLACE)
        refreshCities()
    }

    override suspend fun deleteCity(id: String) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        db.delete("cities", "id = ?", arrayOf(id))
        refreshCities()
    }

    // Supervisors
    override fun getSupervisorsFlow(): Flow<List<SupervisorEntity>> = _supervisorsFlow.asStateFlow()

    override suspend fun insertSupervisor(supervisor: SupervisorEntity) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id", supervisor.id)
            put("username", supervisor.username)
            put("password", supervisor.password)
            put("canAcceptRejectRequests", if (supervisor.canAcceptRejectRequests) 1 else 0)
            put("canManageCategories", if (supervisor.canManageCategories) 1 else 0)
            put("canManageBanners", if (supervisor.canManageBanners) 1 else 0)
            put("canDeleteProviders", if (supervisor.canDeleteProviders) 1 else 0)
            put("canViewReports", if (supervisor.canViewReports) 1 else 0)
        }
        db.insertWithOnConflict("supervisors", null, values, SQLiteDatabase.CONFLICT_REPLACE)
        refreshSupervisors()
    }

    override suspend fun deleteSupervisor(id: String) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        db.delete("supervisors", "id = ?", arrayOf(id))
        refreshSupervisors()
    }

    // --- Private SQLite Database Helper ---
    private class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "yemen_services_final_optimized.db", null, 3) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE categories (
                    id TEXT PRIMARY KEY,
                    nameAr TEXT NOT NULL,
                    nameEn TEXT NOT NULL,
                    iconName TEXT NOT NULL,
                    description TEXT NOT NULL,
                    displayOrder INTEGER NOT NULL DEFAULT 0,
                    parentId TEXT NOT NULL DEFAULT ''
                )
                """.trimIndent()
            )

            db.execSQL(
                """
                CREATE TABLE providers (
                    id TEXT PRIMARY KEY,
                    name TEXT NOT NULL,
                    phone TEXT NOT NULL,
                    categoryId TEXT NOT NULL,
                    area TEXT NOT NULL,
                    isAvailable INTEGER NOT NULL DEFAULT 1,
                    ratingSum INTEGER NOT NULL DEFAULT 0,
                    ratingCount INTEGER NOT NULL DEFAULT 0,
                    isVip INTEGER NOT NULL DEFAULT 0,
                    basePrice REAL NOT NULL DEFAULT 0.0,
                    isPinned INTEGER NOT NULL DEFAULT 0,
                    isRecommended INTEGER NOT NULL DEFAULT 0,
                    isVerified INTEGER NOT NULL DEFAULT 0,
                    subscriptionStatus TEXT NOT NULL DEFAULT 'NONE',
                    loyaltyPoints INTEGER NOT NULL DEFAULT 0,
                    latitude REAL NOT NULL DEFAULT 15.3694,
                    longitude REAL NOT NULL DEFAULT 44.1910,
                    photoUri TEXT NOT NULL DEFAULT '',
                    idCardUri TEXT NOT NULL DEFAULT '',
                    supportText TEXT NOT NULL DEFAULT '',
                    FOREIGN KEY(categoryId) REFERENCES categories(id) ON DELETE CASCADE
                )
                """.trimIndent()
            )

            db.execSQL(
                """
                CREATE TABLE admin_settings (
                    id TEXT PRIMARY KEY,
                    appName TEXT NOT NULL,
                    welcomeMessage TEXT NOT NULL,
                    footerMessage TEXT NOT NULL,
                    activeThemeId TEXT NOT NULL,
                    showVipOnly INTEGER NOT NULL DEFAULT 0,
                    syncTimestamp INTEGER NOT NULL,
                    supportPhone TEXT NOT NULL,
                    supportEmail TEXT NOT NULL,
                    supportWhatsapp TEXT NOT NULL,
                    appVersion TEXT NOT NULL,
                    adminPassword TEXT NOT NULL,
                    hidePromoFooter INTEGER NOT NULL DEFAULT 0,
                    assistantHidden INTEGER NOT NULL DEFAULT 0,
                    assistantSize INTEGER NOT NULL DEFAULT 54,
                    assistantIcon TEXT NOT NULL,
                    assistantIconEffects TEXT NOT NULL DEFAULT 'none',
                    assistantXPercent REAL NOT NULL,
                    assistantYPercent REAL NOT NULL,
                    chatHidden INTEGER NOT NULL DEFAULT 0,
                    chatSize INTEGER NOT NULL DEFAULT 54,
                    chatIcon TEXT NOT NULL DEFAULT 'chat_default',
                    chatIconEffects TEXT NOT NULL DEFAULT 'none',
                    chatXPercent REAL NOT NULL,
                    chatYPercent REAL NOT NULL,
                    fcmChannelsJson TEXT NOT NULL,
                    customPrimaryHex TEXT NOT NULL,
                    customSecondaryHex TEXT NOT NULL,
                    fontName TEXT NOT NULL,
                    fontColorHex TEXT NOT NULL,
                    isMaintenanceActive INTEGER NOT NULL DEFAULT 0,
                    isSpeechSearchEnabled INTEGER NOT NULL DEFAULT 1,
                    maxSearchRadiusKm INTEGER NOT NULL DEFAULT 20,
                    isDataSaverActive INTEGER NOT NULL DEFAULT 0,
                    imageQualityPercent INTEGER NOT NULL DEFAULT 75,
                    showSubscriptionsFeature INTEGER NOT NULL DEFAULT 1
                )
                """.trimIndent()
            )

            db.execSQL(
                """
                CREATE TABLE pending_providers (
                    id TEXT PRIMARY KEY,
                    name TEXT NOT NULL,
                    phone TEXT NOT NULL,
                    categoryId TEXT NOT NULL,
                    area TEXT NOT NULL,
                    localNeighborhood TEXT NOT NULL DEFAULT '',
                    coords TEXT NOT NULL DEFAULT '',
                    photoUri TEXT NOT NULL DEFAULT '',
                    idCardUri TEXT NOT NULL DEFAULT '',
                    submitDate INTEGER NOT NULL,
                    rejectionReason TEXT NOT NULL DEFAULT '',
                    status TEXT NOT NULL DEFAULT 'PENDING'
                )
                """.trimIndent()
            )

            db.execSQL(
                """
                CREATE TABLE banners (
                    id TEXT PRIMARY KEY,
                    title TEXT NOT NULL,
                    imageUrl TEXT NOT NULL DEFAULT '',
                    redirectUrl TEXT NOT NULL DEFAULT '',
                    displayType TEXT NOT NULL DEFAULT 'IMAGE',
                    bannerSize TEXT NOT NULL DEFAULT 'MEDIUM',
                    durationSeconds INTEGER NOT NULL DEFAULT 10,
                    isActive INTEGER NOT NULL DEFAULT 1
                )
                """.trimIndent()
            )

            db.execSQL(
                """
                CREATE TABLE reports (
                    id TEXT PRIMARY KEY,
                    providerId TEXT NOT NULL,
                    providerName TEXT NOT NULL DEFAULT '',
                    reporterName TEXT NOT NULL DEFAULT '',
                    content TEXT NOT NULL,
                    timestamp INTEGER NOT NULL,
                    status TEXT NOT NULL DEFAULT 'PENDING'
                )
                """.trimIndent()
            )

            db.execSQL(
                """
                CREATE TABLE activity_logs (
                    id TEXT PRIMARY KEY,
                    adminName TEXT NOT NULL,
                    actionDesc TEXT NOT NULL,
                    timestamp INTEGER NOT NULL
                )
                """.trimIndent()
            )

            db.execSQL(
                """
                CREATE TABLE whitelist_devices (
                    id TEXT PRIMARY KEY,
                    deviceName TEXT NOT NULL,
                    isAllowed INTEGER NOT NULL DEFAULT 1
                )
                """.trimIndent()
            )

            db.execSQL(
                """
                CREATE TABLE chat_messages (
                    id TEXT PRIMARY KEY,
                    senderId TEXT NOT NULL,
                    receiverId TEXT NOT NULL,
                    senderName TEXT NOT NULL,
                    receiverName TEXT NOT NULL,
                    messageText TEXT NOT NULL,
                    timestamp INTEGER NOT NULL,
                    isRead INTEGER NOT NULL DEFAULT 0,
                    isOfflineSent INTEGER NOT NULL DEFAULT 0
                )
                """.trimIndent()
            )

            db.execSQL(
                """
                CREATE TABLE cities (
                    id TEXT PRIMARY KEY,
                    nameAr TEXT NOT NULL,
                    nameEn TEXT NOT NULL
                )
                """.trimIndent()
            )

            db.execSQL(
                """
                CREATE TABLE supervisors (
                    id TEXT PRIMARY KEY,
                    username TEXT UNIQUE,
                    password TEXT,
                    canAcceptRejectRequests INTEGER DEFAULT 1,
                    canManageCategories INTEGER DEFAULT 0,
                    canManageBanners INTEGER DEFAULT 0,
                    canDeleteProviders INTEGER DEFAULT 0,
                    canViewReports INTEGER DEFAULT 1
                )
                """.trimIndent()
            )
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS categories")
            db.execSQL("DROP TABLE IF EXISTS providers")
            db.execSQL("DROP TABLE IF EXISTS admin_settings")
            db.execSQL("DROP TABLE IF EXISTS pending_providers")
            db.execSQL("DROP TABLE IF EXISTS banners")
            db.execSQL("DROP TABLE IF EXISTS reports")
            db.execSQL("DROP TABLE IF EXISTS activity_logs")
            db.execSQL("DROP TABLE IF EXISTS whitelist_devices")
            db.execSQL("DROP TABLE IF EXISTS chat_messages")
            db.execSQL("DROP TABLE IF EXISTS cities")
            db.execSQL("DROP TABLE IF EXISTS supervisors")
            onCreate(db)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = AppDatabase(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
}
