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

class AppDatabase private constructor(context: Context) : AppDao {
    private val dbHelper = DatabaseHelper(context.applicationContext)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    // Flow states mimicking Room's continuous data stream securely
    private val _categoriesFlow = MutableStateFlow<List<CategoryEntity>>(emptyList())
    private val _providersFlow = MutableStateFlow<List<ProviderEntity>>(emptyList())
    private val _settingsFlow = MutableStateFlow(AdminSettingsEntity())

    init {
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
    }

    private fun refreshCategories() {
        coroutineScope.launch(Dispatchers.IO) {
            val list = mutableListOf<CategoryEntity>()
            var db: SQLiteDatabase? = null
            var cursor: Cursor? = null
            try {
                db = dbHelper.readableDatabase
                cursor = db.rawQuery("SELECT * FROM categories", null)
                if (cursor != null && cursor.moveToFirst()) {
                    val idCol = cursor.getColumnIndexOrThrow("id")
                    val nameArCol = cursor.getColumnIndexOrThrow("nameAr")
                    val nameEnCol = cursor.getColumnIndexOrThrow("nameEn")
                    val iconCol = cursor.getColumnIndexOrThrow("iconName")
                    val descCol = cursor.getColumnIndexOrThrow("description")
                    do {
                        list.add(
                            CategoryEntity(
                                id = cursor.getString(idCol),
                                nameAr = cursor.getString(nameArCol),
                                nameEn = cursor.getString(nameEnCol),
                                iconName = cursor.getString(iconCol),
                                description = cursor.getString(descCol)
                            )
                        )
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                Log.e("AppDatabase", "Error loading categories", e)
            } finally {
                cursor?.close()
            }
            _categoriesFlow.value = list
        }
    }

    private fun refreshProviders() {
        coroutineScope.launch(Dispatchers.IO) {
            val list = mutableListOf<ProviderEntity>()
            var db: SQLiteDatabase? = null
            var cursor: Cursor? = null
            try {
                db = dbHelper.readableDatabase
                cursor = db.rawQuery("SELECT * FROM providers", null)
                if (cursor != null && cursor.moveToFirst()) {
                    val idCol = cursor.getColumnIndexOrThrow("id")
                    val nameCol = cursor.getColumnIndexOrThrow("name")
                    val phoneCol = cursor.getColumnIndexOrThrow("phone")
                    val categoryCol = cursor.getColumnIndexOrThrow("categoryId")
                    val areaCol = cursor.getColumnIndexOrThrow("area")
                    val availableCol = cursor.getColumnIndexOrThrow("isAvailable")
                    val ratingSumCol = cursor.getColumnIndexOrThrow("ratingSum")
                    val ratingCountCol = cursor.getColumnIndexOrThrow("ratingCount")
                    val vipCol = cursor.getColumnIndexOrThrow("isVip")
                    val basePriceCol = cursor.getColumnIndexOrThrow("basePrice")

                    do {
                        list.add(
                            ProviderEntity(
                                id = cursor.getString(idCol),
                                name = cursor.getString(nameCol),
                                phone = cursor.getString(phoneCol),
                                categoryId = cursor.getString(categoryCol),
                                area = cursor.getString(areaCol),
                                isAvailable = cursor.getInt(availableCol) == 1,
                                ratingSum = cursor.getInt(ratingSumCol),
                                ratingCount = cursor.getInt(ratingCountCol),
                                isVip = cursor.getInt(vipCol) == 1,
                                basePrice = cursor.getDouble(basePriceCol)
                            )
                        )
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                Log.e("AppDatabase", "Error loading providers", e)
            } finally {
                cursor?.close()
            }
            _providersFlow.value = list
        }
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
                    val footerCol = cursor.getColumnIndexOrThrow("footerMessage")
                    val themeCol = cursor.getColumnIndexOrThrow("activeThemeId")
                    val showVipCol = cursor.getColumnIndexOrThrow("showVipOnly")
                    val timeCol = cursor.getColumnIndexOrThrow("syncTimestamp")
                    
                    settings = AdminSettingsEntity(
                        id = "SINGLETON_SETTINGS",
                        appName = cursor.getString(appNameCol),
                        footerMessage = cursor.getString(footerCol),
                        activeThemeId = cursor.getString(themeCol),
                        showVipOnly = cursor.getInt(showVipCol) == 1,
                        syncTimestamp = cursor.getLong(timeCol)
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
                CategoryEntity("plumb", "سباكة وصيانة الأنابيب", "Plumbing & Pipe Repair", "plumbing", "إصلاح تسريبات المياه وتمديد الأنابيب المنزلية"),
                CategoryEntity("elec", "كهرباء وتوصيلات منزلية", "Electrical & Wiring", "electrical", "تركيب وتصليح الأفياش، الإضاءة والتحكم في الشبكة"),
                CategoryEntity("ac", "صيانة المكيفات والتبريد", "AC & Refrigeration", "ac_unit", "صيانة أجهزة التكييف والتهوية وإرسال شحنات الفريون"),
                CategoryEntity("carp", "نجارة وأشغال خشبية", "Carpentry & Woodwork", "carpentry", "صيانة الأبواب ومطابخ الألمنيوم والخشب وتركيب الأثاث"),
                CategoryEntity("paint", "دهانات وديكورات", "Painting & Decorating", "paint", "أعمال الطلاء والجبس بورد بجودة عالية ودقة العمل"),
                CategoryEntity("clean", "خدمات التنظيف والتعقيم", "Cleaning & Sanitization", "cleaning", "تنظيف الفلل والبيوت وخزانات المياه بأفضل المواد")
            )

            for (cat in defaultCategories) {
                val cv = ContentValues().apply {
                    put("id", cat.id)
                    put("nameAr", cat.nameAr)
                    put("nameEn", cat.nameEn)
                    put("iconName", cat.iconName)
                    put("description", cat.description)
                }
                db.insert("categories", null, cv)
            }

            // Default Service Providers
            val defaultProviders = listOf(
                ProviderEntity("p1", "المهندس ماهر الشرعبي", "771234567", "plumb", "صنعاء - شارع حدة", true, 45, 10, true, 2500.0),
                ProviderEntity("p2", "فني السباكة علي عياش", "733445566", "plumb", "عدن - المنصورة", true, 38, 9, false, 2000.0),
                ProviderEntity("p3", "الكهربائي محمد القدسي", "711556677", "elec", "صنعاء - الحصبة", true, 48, 10, true, 3000.0),
                ProviderEntity("p4", "فني توصيل شبكات خالد", "770112233", "elec", "تعز - شارع جمال", true, 20, 5, false, 1500.0),
                ProviderEntity("p5", "أبو رعد لصيانة التكييف", "773738291", "ac", "الحديدة - شارع صنعاء", true, 49, 11, true, 4000.0),
                ProviderEntity("p6", "الفني أحمد النجار", "738291029", "carp", "إب - الدائري", true, 32, 8, false, 3000.0),
                ProviderEntity("p7", "رائد لأعمال الدهان والطلاء", "774920492", "paint", "صنعاء - الأصبحي", false, 41, 9, true, 5000.0),
                ProviderEntity("p8", "مؤسسة النخبة للتنظيف", "772233445", "clean", "صنعاء - حدة", true, 50, 10, true, 10000.0)
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
                }
                db.insert("providers", null, cv)
            }

            // Fill default single settings
            val initialSettings = ContentValues().apply {
                put("id", "SINGLETON_SETTINGS")
                put("appName", "كل خدمات اليمن")
                put("footerMessage", "يربطك بأفضل المهنيين الفنيين واليدويين في اليمن دقة وأمان وسرعة")
                put("activeThemeId", "EMERALD_YEMEN")
                put("showVipOnly", 0)
                put("syncTimestamp", System.currentTimeMillis())
            }
            db.insert("admin_settings", null, initialSettings)
        }
    }

    // --- AppDao implementations ---
    override fun getCategoriesFlow(): Flow<List<CategoryEntity>> = _categoriesFlow.asStateFlow()

    override suspend fun insertCategory(category: CategoryEntity) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id", category.id)
            put("nameAr", category.nameAr)
            put("nameEn", category.nameEn)
            put("iconName", category.iconName)
            put("description", category.description)
        }
        db.insertWithOnConflict("categories", null, values, SQLiteDatabase.CONFLICT_REPLACE)
        refreshCategories()
    }

    override suspend fun deleteCategory(id: String) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        db.delete("categories", "id = ?", arrayOf(id))
        refreshCategories()
    }

    override fun getProvidersFlow(): Flow<List<ProviderEntity>> = _providersFlow.asStateFlow()

    override suspend fun insertProvider(provider: ProviderEntity) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id", provider.id)
            put("name", provider.name)
            put("phone", provider.phone)
            put("categoryId", provider.categoryId)
            put("area", provider.area)
            put("isAvailable", if (provider.isAvailable) 1 else 0)
            put("ratingSum", provider.ratingSum)
            put("ratingCount", provider.ratingCount)
            put("isVip", if (provider.isVip) 1 else 0)
            put("basePrice", provider.basePrice)
        }
        db.insertWithOnConflict("providers", null, values, SQLiteDatabase.CONFLICT_REPLACE)
        refreshProviders()
    }

    override suspend fun deleteProvider(id: String) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        db.delete("providers", "id = ?", arrayOf(id))
        refreshProviders()
    }

    override suspend fun rateProvider(id: String, rating: Int) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        db.execSQL(
            "UPDATE providers SET ratingSum = ratingSum + ?, ratingCount = ratingCount + 1 WHERE id = ?",
            arrayOf(rating, id)
        )
        refreshProviders()
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
                val footerCol = cursor.getColumnIndexOrThrow("footerMessage")
                val themeCol = cursor.getColumnIndexOrThrow("activeThemeId")
                val showVipCol = cursor.getColumnIndexOrThrow("showVipOnly")
                val timeCol = cursor.getColumnIndexOrThrow("syncTimestamp")
                
                settings = AdminSettingsEntity(
                    id = "SINGLETON_SETTINGS",
                    appName = cursor.getString(appNameCol),
                    footerMessage = cursor.getString(footerCol),
                    activeThemeId = cursor.getString(themeCol),
                    showVipOnly = cursor.getInt(showVipCol) == 1,
                    syncTimestamp = cursor.getLong(timeCol)
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
            put("footerMessage", settings.footerMessage)
            put("activeThemeId", settings.activeThemeId)
            put("showVipOnly", if (settings.showVipOnly) 1 else 0)
            put("syncTimestamp", System.currentTimeMillis())
        }
        db.insertWithOnConflict("admin_settings", null, values, SQLiteDatabase.CONFLICT_REPLACE)
        _settingsFlow.value = settings
        refreshSettings()
    }

    // --- Private SQLite Database Helper ---
    private class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "yemen_services_optimized.db", null, 1) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE categories (
                    id TEXT PRIMARY KEY,
                    nameAr TEXT NOT NULL,
                    nameEn TEXT NOT NULL,
                    iconName TEXT NOT NULL,
                    description TEXT NOT NULL
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
                    FOREIGN KEY(categoryId) REFERENCES categories(id) ON DELETE CASCADE
                )
                """.trimIndent()
            )

            db.execSQL(
                """
                CREATE TABLE admin_settings (
                    id TEXT PRIMARY KEY,
                    appName TEXT NOT NULL,
                    footerMessage TEXT NOT NULL,
                    activeThemeId TEXT NOT NULL,
                    showVipOnly INTEGER NOT NULL DEFAULT 0,
                    syncTimestamp INTEGER NOT NULL
                )
                """.trimIndent()
            )
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS categories")
            db.execSQL("DROP TABLE IF EXISTS providers")
            db.execSQL("DROP TABLE IF EXISTS admin_settings")
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
