package com.example.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DigiNovaDao {

    // --- SERVICES ---
    @Query("SELECT * FROM services WHERE isEnabled = 1 ORDER BY sortOrder ASC, id ASC")
    fun getActiveServices(): Flow<List<ServiceEntity>>

    @Query("SELECT * FROM services ORDER BY sortOrder ASC, id ASC")
    fun getAllServices(): Flow<List<ServiceEntity>>

    @Query("SELECT * FROM services WHERE id = :id LIMIT 1")
    suspend fun getServiceById(id: String): ServiceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(service: ServiceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServices(services: List<ServiceEntity>)

    @Update
    suspend fun updateService(service: ServiceEntity)

    @Query("DELETE FROM services WHERE id = :id")
    suspend fun deleteService(id: String)

    @Query("SELECT DISTINCT category FROM services")
    fun getAllCategories(): Flow<List<String>>

    // --- ONLINE TOOLS ---
    @Query("SELECT * FROM online_tools WHERE isEnabled = 1 ORDER BY sortOrder ASC, id ASC")
    fun getActiveOnlineTools(): Flow<List<OnlineToolEntity>>

    @Query("SELECT * FROM online_tools ORDER BY sortOrder ASC, id ASC")
    fun getAllOnlineTools(): Flow<List<OnlineToolEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOnlineTool(tool: OnlineToolEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOnlineTools(tools: List<OnlineToolEntity>)

    @Update
    suspend fun updateOnlineTool(tool: OnlineToolEntity)

    @Query("DELETE FROM online_tools WHERE id = :id")
    suspend fun deleteOnlineTool(id: String)

    // --- USEFUL TIPS ---
    @Query("SELECT * FROM tips WHERE isPublished = 1 ORDER BY sortOrder ASC, id ASC")
    fun getPublishedTips(): Flow<List<TipEntity>>

    @Query("SELECT * FROM tips ORDER BY sortOrder ASC, id ASC")
    fun getAllTips(): Flow<List<TipEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTip(tip: TipEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTips(tips: List<TipEntity>)

    @Update
    suspend fun updateTip(tip: TipEntity)

    @Query("DELETE FROM tips WHERE id = :id")
    suspend fun deleteTip(id: String)

    // --- UPDATES ---
    @Query("SELECT * FROM updates WHERE isPublished = 1 ORDER BY timestamp DESC")
    fun getPublishedUpdates(): Flow<List<UpdateEntity>>

    @Query("SELECT * FROM updates ORDER BY timestamp DESC")
    fun getAllUpdates(): Flow<List<UpdateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdate(update: UpdateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdates(updates: List<UpdateEntity>)

    @Update
    suspend fun updateUpdate(update: UpdateEntity)

    @Query("DELETE FROM updates WHERE id = :id")
    suspend fun deleteUpdate(id: String)

    // --- NOTIFICATIONS & ANNOUNCEMENTS ---
    @Query("SELECT * FROM notifications WHERE isPublished = 1 ORDER BY timestamp DESC")
    fun getPublishedNotifications(): Flow<List<NotificationEntity>>

    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<NotificationEntity>)

    @Update
    suspend fun updateNotification(notification: NotificationEntity)

    @Query("DELETE FROM notifications WHERE id = :id")
    suspend fun deleteNotification(id: String)

    // --- ENQUIRIES ---
    @Query("SELECT * FROM enquiries ORDER BY createdAt DESC")
    fun getAllEnquiries(): Flow<List<EnquiryEntity>>

    @Query("SELECT * FROM enquiries WHERE status = :status ORDER BY createdAt DESC")
    fun getEnquiriesByStatus(status: String): Flow<List<EnquiryEntity>>

    @Query("SELECT COUNT(*) FROM enquiries WHERE status = 'PENDING'")
    fun getPendingEnquiriesCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnquiry(enquiry: EnquiryEntity)

    @Query("UPDATE enquiries SET status = :status WHERE id = :id")
    suspend fun updateEnquiryStatus(id: String, status: String)

    @Query("DELETE FROM enquiries WHERE id = :id")
    suspend fun deleteEnquiry(id: String)

    // --- APP SETTINGS ---
    @Query("SELECT * FROM app_settings")
    fun getAllSettings(): Flow<List<AppSettingEntity>>

    @Query("SELECT value FROM app_settings WHERE `key` = :key LIMIT 1")
    suspend fun getSettingValue(key: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(setting: AppSettingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: List<AppSettingEntity>)

    // --- AUDIT LOGS ---
    @Query("SELECT * FROM audit_logs ORDER BY timestamp DESC LIMIT 200")
    fun getAllAuditLogs(): Flow<List<AuditLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuditLog(log: AuditLogEntity)

    // --- ADMIN USERS ---
    @Query("SELECT * FROM admin_users WHERE username = :username OR email = :username LIMIT 1")
    suspend fun getAdminUser(username: String): AdminUserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdminUser(user: AdminUserEntity)

    @Update
    suspend fun updateAdminUser(user: AdminUserEntity)

    @Query("SELECT COUNT(*) FROM admin_users")
    suspend fun getAdminUserCount(): Int

    // --- PUBLIC USERS ---
    @Query("SELECT * FROM public_users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM public_users WHERE phone = :phone LIMIT 1")
    suspend fun getUserByPhone(phone: String): UserEntity?

    @Query("SELECT * FROM public_users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: String): UserEntity?

    @Query("SELECT COUNT(*) FROM public_users")
    fun getTotalUsersCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    // --- USER DOCUMENTS ---
    @Query("SELECT * FROM user_documents WHERE userId = :userId ORDER BY createdAt DESC")
    fun getUserDocuments(userId: String): Flow<List<UserDocumentEntity>>

    @Query("SELECT * FROM user_documents ORDER BY createdAt DESC")
    fun getAllDocuments(): Flow<List<UserDocumentEntity>>

    @Query("SELECT * FROM user_documents WHERE id = :id LIMIT 1")
    suspend fun getDocumentById(id: String): UserDocumentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(document: UserDocumentEntity)

    @Query("DELETE FROM user_documents WHERE id = :id")
    suspend fun deleteDocument(id: String)

    @Query("UPDATE user_documents SET title = :newTitle WHERE id = :id")
    suspend fun renameDocument(id: String, newTitle: String)

    // --- EXAM & GOVERNMENT FORMS ---
    @Query("SELECT * FROM exam_forms WHERE isEnabled = 1 ORDER BY createdAt DESC")
    fun getActiveExamForms(): Flow<List<ExamFormEntity>>

    @Query("SELECT * FROM exam_forms ORDER BY createdAt DESC")
    fun getAllExamForms(): Flow<List<ExamFormEntity>>

    @Query("SELECT * FROM exam_forms WHERE id = :id LIMIT 1")
    suspend fun getExamFormById(id: String): ExamFormEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExamForm(examForm: ExamFormEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExamForms(examForms: List<ExamFormEntity>)

    @Update
    suspend fun updateExamForm(examForm: ExamFormEntity)

    @Query("DELETE FROM exam_forms WHERE id = :id")
    suspend fun deleteExamForm(id: String)

    // --- APP BANNERS ---
    @Query("SELECT * FROM app_banners WHERE isEnabled = 1 ORDER BY sortOrder ASC")
    fun getActiveBanners(): Flow<List<AppBannerEntity>>

    @Query("SELECT * FROM app_banners ORDER BY sortOrder ASC")
    fun getAllBanners(): Flow<List<AppBannerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBanner(banner: AppBannerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBanners(banners: List<AppBannerEntity>)

    @Update
    suspend fun updateBanner(banner: AppBannerEntity)

    @Query("DELETE FROM app_banners WHERE id = :id")
    suspend fun deleteBanner(id: String)
}
