package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "services")
data class ServiceEntity(
    @PrimaryKey val id: String,
    val title: String,
    val tagline: String,
    val category: String,
    val description: String,
    val iconType: String,
    val featuresString: String, // Delimited by "||"
    val benefitsString: String, // Delimited by "||"
    val deliveryTime: String,
    val pricing: String,
    val isFeatured: Boolean = false,
    val isPopular: Boolean = false,
    val isEnabled: Boolean = true,
    val badge: String? = null,
    val sortOrder: Int = 0
) {
    fun getFeatures(): List<String> = if (featuresString.isBlank()) emptyList() else featuresString.split("||")
    fun getBenefits(): List<String> = if (benefitsString.isBlank()) emptyList() else benefitsString.split("||")
}

@Entity(tableName = "online_tools")
data class OnlineToolEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val description: String,
    val url: String,
    val iconType: String,
    val badge: String? = null,
    val isPopular: Boolean = false,
    val isEnabled: Boolean = true,
    val sortOrder: Int = 0
)

@Entity(tableName = "tips")
data class TipEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val summary: String,
    val fullContent: String,
    val readTimeMinutes: Int,
    val isFeatured: Boolean = false,
    val isPublished: Boolean = true,
    val keyTakeawaysString: String, // Delimited by "||"
    val tagsString: String,         // Delimited by "||"
    val sortOrder: Int = 0
) {
    fun getKeyTakeaways(): List<String> = if (keyTakeawaysString.isBlank()) emptyList() else keyTakeawaysString.split("||")
    fun getTags(): List<String> = if (tagsString.isBlank()) emptyList() else tagsString.split("||")
}

@Entity(tableName = "updates")
data class UpdateEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val date: String,
    val summary: String,
    val fullArticle: String,
    val readTime: String,
    val badge: String? = null,
    val author: String = "Anup Digi Nova",
    val isPublished: Boolean = true,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: String,
    val title: String,
    val message: String,
    val date: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isPublished: Boolean = true,
    val priority: String = "NORMAL" // NORMAL, HIGH, URGENT
)

@Entity(tableName = "enquiries")
data class EnquiryEntity(
    @PrimaryKey val id: String,
    val customerName: String,
    val customerPhone: String,
    val customerEmail: String,
    val serviceId: String? = null,
    val serviceTitle: String = "General Consultation",
    val message: String,
    val status: String = "PENDING", // PENDING, IN_PROGRESS, COMPLETED
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "app_settings")
data class AppSettingEntity(
    @PrimaryKey val key: String,
    val value: String
)

@Entity(tableName = "audit_logs")
data class AuditLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val action: String,
    val details: String,
    val adminUser: String,
    val timestamp: Long = System.currentTimeMillis(),
    val formattedDate: String
)

@Entity(tableName = "admin_users")
data class AdminUserEntity(
    @PrimaryKey val username: String,
    val email: String,
    val passwordHash: String,
    val salt: String,
    val role: String = "SUPER_ADMIN",
    val twoFactorEnabled: Boolean = false,
    val failedAttempts: Int = 0,
    val lockedUntil: Long = 0L,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "public_users")
data class UserEntity(
    @PrimaryKey val id: String,
    val fullName: String,
    val email: String,
    val phone: String,
    val passwordHash: String,
    val salt: String,
    val isEmailVerified: Boolean = false,
    val isPhoneVerified: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_documents")
data class UserDocumentEntity(
    @PrimaryKey val id: String,
    val userId: String = "guest",
    val title: String,
    val type: String, // "PHOTO", "SIGNATURE", "RESIZED_IMAGE", "PASSPORT_SHEET", "RECEIPT", "QUOTATION"
    val filePath: String,
    val fileSizeBytes: Long,
    val dimensions: String,
    val mimeType: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "exam_forms")
data class ExamFormEntity(
    @PrimaryKey val id: String,
    val title: String,
    val organization: String,
    val category: String, // SSC, Railway, Banking, Police, Defence, Teaching, State Gov, Central Gov, Scholarship, Entrance
    val startDate: String,
    val lastDate: String,
    val examDate: String,
    val admitCardDate: String,
    val resultDate: String,
    val eligibility: String,
    val ageLimit: String,
    val minAge: Int = 18,
    val maxAge: Int = 30,
    val applicationFee: String,
    val requiredDocuments: String, // Delimited by "||"
    val officialNotificationUrl: String,
    val officialApplyUrl: String,
    val status: String = "OPEN", // OPEN, CLOSING_SOON, CLOSED, ADMIT_CARD_OUT, RESULT_OUT
    val isFeatured: Boolean = false,
    val isEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun getRequiredDocsList(): List<String> = if (requiredDocuments.isBlank()) emptyList() else requiredDocuments.split("||")
}

@Entity(tableName = "app_banners")
data class AppBannerEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subtitle: String,
    val actionType: String = "NAVIGATE", // "NAVIGATE", "URL"
    val actionTarget: String, // Route or URL
    val tag: String = "FEATURED",
    val isEnabled: Boolean = true,
    val sortOrder: Int = 0
)

