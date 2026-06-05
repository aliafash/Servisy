package com.example.data

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val nameAr: String,
    val nameEn: String,
    val imageIndex: Int,
    val sortOrder: Int,
    val parentId: String? = null
)

@Entity(tableName = "providers")
data class ProviderEntity(
    @PrimaryKey val id: String,
    val name: String,
    val phone: String,
    val parentCategoryId: String,
    val subCategoryName: String,
    val workAddress: String,
    val residenceArea: String,
    val latitude: Double = 15.3694,
    val longitude: Double = 44.1910,
    val avatarUri: String = "",
    val idCardUri: String = "",
    val isPinned: Boolean = false,
    val isRecommended: Boolean = false,
    val isVerified: Boolean = false,
    val isBlocked: Boolean = false,
    val ratingSum: Int = 0,
    val ratingCount: Int = 0,
    val points: Int = 0,
    val isVipSubscribed: Boolean = false,
    val status: String = "approved", // "pending", "approved", "rejected"
    val rejectReason: String = "",
    val activeAds: Boolean = false,
    val adDurationDays: Int = 0,
    val adBudget: Double = 0.0,
    val isSponsored: Boolean = false
)

@Entity(tableName = "banners")
data class BannerEntity(
    @PrimaryKey val id: String,
    val title: String,
    val type: String, // "IMAGE", "VIDEO", "TEXT"
    val content: String,
    val durationSeconds: Int = 5,
    val redirectUrl: String = "",
    val sizeRatio: String = "medium"
)

@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey val id: String,
    val providerId: String,
    val providerName: String,
    val reporterName: String,
    val reasonAr: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "pending"
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey val id: String,
    val senderId: String,
    val receiverId: String,
    val senderName: String,
    val recipientName: String,
    val messageAr: String,
    val timestamp: Long = System.currentTimeMillis(),
    val imageType: Boolean = false
)

@Entity(tableName = "settings")
data class AdminSettingEntity(
    @PrimaryKey val key: String,
    val value: String
)

@Entity(tableName = "moderators")
data class ModeratorEntity(
    @PrimaryKey val username: String,
    val passwordHash: String,
    val twoFactorSecret: String = "",
    val lastLoginTime: Long = 0L
)

@Entity(tableName = "activity_logs")
data class ActivityLogEntity(
    @PrimaryKey val id: String,
    val operatorName: String,
    val actionAr: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "whitelist_devices")
data class WhitelistDeviceEntity(
    @PrimaryKey val deviceId: String,
    val label: String,
    val addedTime: Long = System.currentTimeMillis()
)

@Dao
interface AppDao {
    @Query("SELECT * FROM categories ORDER BY sortOrder ASC")
    fun getCategoriesFlow(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategories(categories: List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    // Providers
    @Query("SELECT * FROM providers")
    fun getProvidersFlow(): Flow<List<ProviderEntity>>

    @Query("SELECT * FROM providers")
    suspend fun getAllProviders(): List<ProviderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProvider(provider: ProviderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProviders(providers: List<ProviderEntity>)

    @Delete
    suspend fun deleteProvider(provider: ProviderEntity)

    // Banners
    @Query("SELECT * FROM banners")
    fun getBannersFlow(): Flow<List<BannerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBanner(banner: BannerEntity)

    @Delete
    suspend fun deleteBanner(banner: BannerEntity)

    // Reports
    @Query("SELECT * FROM reports ORDER BY timestamp DESC")
    fun getReportsFlow(): Flow<List<ReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReport(report: ReportEntity)

    @Delete
    suspend fun deleteReport(report: ReportEntity)

    // Chats
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    fun getChatsFlow(): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveChatMessage(msg: ChatMessageEntity)

    // Settings
    @Query("SELECT * FROM settings")
    fun getSettingsFlow(): Flow<List<AdminSettingEntity>>

    @Query("SELECT * FROM settings WHERE `key` = :key LIMIT 1")
    suspend fun getSettingByKey(key: String): AdminSettingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSetting(setting: AdminSettingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: List<AdminSettingEntity>)

    // Moderators
    @Query("SELECT * FROM moderators")
    fun getModeratorsFlow(): Flow<List<ModeratorEntity>>

    @Query("SELECT * FROM moderators")
    suspend fun getAllModerators(): List<ModeratorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveModerator(mod: ModeratorEntity)

    @Delete
    suspend fun deleteModerator(mod: ModeratorEntity)

    // Activity Logs
    @Query("SELECT * FROM activity_logs ORDER BY timestamp DESC")
    fun getLogsFlow(): Flow<List<ActivityLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveActivity(log: ActivityLogEntity)

    // Whitelist Devices
    @Query("SELECT * FROM whitelist_devices")
    fun getWhitelistFlow(): Flow<List<WhitelistDeviceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWhitelist(device: WhitelistDeviceEntity)

    @Delete
    suspend fun deleteWhitelist(device: WhitelistDeviceEntity)
}

@Database(
    entities = [
        CategoryEntity::class,
        ProviderEntity::class,
        BannerEntity::class,
        ReportEntity::class,
        ChatMessageEntity::class,
        AdminSettingEntity::class,
        ModeratorEntity::class,
        ActivityLogEntity::class,
        WhitelistDeviceEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "yemen_services_db"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
