package com.example.data

import android.content.Context
import com.example.data.local.*
import com.example.data.security.SecurityHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

sealed class LoginResult {
    data class Success(val user: AdminUserEntity, val sessionToken: String) : LoginResult()
    data class Error(val message: String) : LoginResult()
    object AccountLocked : LoginResult()
}

class DigiNovaDataRepository(
    context: Context,
    private val database: DigiNovaDatabase = DigiNovaDatabase.getDatabase(context)
) {

    private val dao = database.digiNovaDao()

    // Customer Reactive Flows
    val activeServices: Flow<List<ServiceEntity>> = dao.getActiveServices()
    val activeOnlineTools: Flow<List<OnlineToolEntity>> = dao.getActiveOnlineTools()
    val publishedTips: Flow<List<TipEntity>> = dao.getPublishedTips()
    val publishedUpdates: Flow<List<UpdateEntity>> = dao.getPublishedUpdates()
    val publishedNotifications: Flow<List<NotificationEntity>> = dao.getPublishedNotifications()

    // Admin Reactive Flows
    val allServices: Flow<List<ServiceEntity>> = dao.getAllServices()
    val allOnlineTools: Flow<List<OnlineToolEntity>> = dao.getAllOnlineTools()
    val allTips: Flow<List<TipEntity>> = dao.getAllTips()
    val allUpdates: Flow<List<UpdateEntity>> = dao.getAllUpdates()
    val allNotifications: Flow<List<NotificationEntity>> = dao.getAllNotifications()
    val allEnquiries: Flow<List<EnquiryEntity>> = dao.getAllEnquiries()
    val pendingEnquiriesCount: Flow<Int> = dao.getPendingEnquiriesCount()
    val auditLogs: Flow<List<AuditLogEntity>> = dao.getAllAuditLogs()

    // Key-Value App Settings Map
    val appSettings: Flow<Map<String, String>> = dao.getAllSettings().map { list ->
        list.associate { it.key to it.value }
    }

    /**
     * Ensures initial seed data exists (safety fallback if callback hasn't fired yet)
     */
    suspend fun ensureInitialized() = withContext(Dispatchers.IO) {
        val userCount = dao.getAdminUserCount()
        if (userCount == 0) {
            dao.insertServices(DefaultSeedData.getDefaultServices())
            dao.insertOnlineTools(DefaultSeedData.getDefaultOnlineTools())
            dao.insertTips(DefaultSeedData.getDefaultTips())
            dao.insertUpdates(DefaultSeedData.getDefaultUpdates())
            dao.insertNotifications(DefaultSeedData.getDefaultNotifications())
            dao.insertSettings(DefaultSeedData.getDefaultSettings())
            dao.insertAdminUser(DefaultSeedData.getDefaultAdminUser())
            dao.insertAuditLog(DefaultSeedData.getInitialAuditLog())
        }
    }

    // --- CUSTOMER ACTIONS ---
    suspend fun submitCustomerEnquiry(
        name: String,
        phone: String,
        email: String,
        serviceId: String?,
        serviceTitle: String,
        message: String
    ): Result<String> = withContext(Dispatchers.IO) {
        if (name.isBlank() || (phone.isBlank() && email.isBlank()) || message.isBlank()) {
            return@withContext Result.failure(IllegalArgumentException("Name, contact info, and message are required."))
        }
        val enquiryId = "enq-" + UUID.randomUUID().toString().take(8)
        val enquiry = EnquiryEntity(
            id = enquiryId,
            customerName = name.trim(),
            customerPhone = phone.trim(),
            customerEmail = email.trim(),
            serviceId = serviceId,
            serviceTitle = serviceTitle,
            message = message.trim(),
            status = "PENDING",
            createdAt = System.currentTimeMillis()
        )
        dao.insertEnquiry(enquiry)
        logAudit(
            action = "CUSTOMER_ENQUIRY_SUBMITTED",
            details = "Enquiry from $name for service: $serviceTitle (ID: $enquiryId)",
            adminUser = "CUSTOMER"
        )
        Result.success(enquiryId)
    }

    // --- ADMIN AUTHENTICATION ---
    suspend fun adminLogin(usernameOrEmail: String, passwordAttempt: String): LoginResult = withContext(Dispatchers.IO) {
        ensureInitialized()
        val user = dao.getAdminUser(usernameOrEmail.trim())
            ?: return@withContext LoginResult.Error("Invalid admin credentials.")

        val currentTime = System.currentTimeMillis()
        if (user.lockedUntil > currentTime) {
            return@withContext LoginResult.AccountLocked
        }

        val isPasswordValid = SecurityHelper.verifyPassword(passwordAttempt, user.salt, user.passwordHash)
        if (!isPasswordValid) {
            val newFailedAttempts = user.failedAttempts + 1
            val lockUntilTime = if (newFailedAttempts >= 5) currentTime + (15 * 60 * 1000) else 0L // 15 mins lock
            dao.updateAdminUser(user.copy(failedAttempts = newFailedAttempts, lockedUntil = lockUntilTime))

            logAudit(
                action = "LOGIN_FAILED",
                details = "Failed login attempt for user: ${user.username} (Attempt $newFailedAttempts/5)",
                adminUser = user.username
            )

            if (newFailedAttempts >= 5) {
                return@withContext LoginResult.AccountLocked
            }
            return@withContext LoginResult.Error("Incorrect password. Attempts remaining: ${5 - newFailedAttempts}")
        }

        // Reset failed attempts upon successful login
        dao.updateAdminUser(user.copy(failedAttempts = 0, lockedUntil = 0L))
        val sessionToken = UUID.randomUUID().toString()

        logAudit(
            action = "LOGIN_SUCCESS",
            details = "Admin logged in successfully from session: ${sessionToken.take(8)}...",
            adminUser = user.username
        )

        LoginResult.Success(user, sessionToken)
    }

    suspend fun changeAdminPassword(
        username: String,
        currentPassword: String,
        newPassword: String
    ): Result<Unit> = withContext(Dispatchers.IO) {
        val user = dao.getAdminUser(username)
            ?: return@withContext Result.failure(IllegalStateException("User not found."))

        if (!SecurityHelper.verifyPassword(currentPassword, user.salt, user.passwordHash)) {
            return@withContext Result.failure(IllegalArgumentException("Current password is incorrect."))
        }
        if (newPassword.length < 8) {
            return@withContext Result.failure(IllegalArgumentException("New password must be at least 8 characters."))
        }

        val newSalt = SecurityHelper.generateSalt()
        val newHash = SecurityHelper.hashPassword(newPassword, newSalt)
        dao.updateAdminUser(user.copy(passwordHash = newHash, salt = newSalt))

        logAudit(
            action = "PASSWORD_CHANGED",
            details = "Admin password updated successfully",
            adminUser = username
        )
        Result.success(Unit)
    }

    // --- SERVICE MANAGEMENT ---
    suspend fun insertOrUpdateService(service: ServiceEntity, adminUser: String) = withContext(Dispatchers.IO) {
        dao.insertService(service)
        logAudit(
            action = "SERVICE_SAVED",
            details = "Saved service '${service.title}' (Price: ${service.pricing})",
            adminUser = adminUser
        )
    }

    suspend fun deleteService(serviceId: String, serviceTitle: String, adminUser: String) = withContext(Dispatchers.IO) {
        dao.deleteService(serviceId)
        logAudit(
            action = "SERVICE_DELETED",
            details = "Deleted service '$serviceTitle' (ID: $serviceId)",
            adminUser = adminUser
        )
    }

    suspend fun toggleServiceStatus(service: ServiceEntity, adminUser: String) = withContext(Dispatchers.IO) {
        val updated = service.copy(isEnabled = !service.isEnabled)
        dao.updateService(updated)
        logAudit(
            action = if (updated.isEnabled) "SERVICE_ENABLED" else "SERVICE_DISABLED",
            details = "Toggled status of '${service.title}' to ${if (updated.isEnabled) "Active" else "Disabled"}",
            adminUser = adminUser
        )
    }

    // --- ONLINE TOOLS MANAGEMENT ---
    suspend fun insertOrUpdateOnlineTool(tool: OnlineToolEntity, adminUser: String) = withContext(Dispatchers.IO) {
        dao.insertOnlineTool(tool)
        logAudit(
            action = "TOOL_SAVED",
            details = "Saved online tool '${tool.title}' (${tool.url})",
            adminUser = adminUser
        )
    }

    suspend fun deleteOnlineTool(toolId: String, toolTitle: String, adminUser: String) = withContext(Dispatchers.IO) {
        dao.deleteOnlineTool(toolId)
        logAudit(
            action = "TOOL_DELETED",
            details = "Deleted online tool '$toolTitle' (ID: $toolId)",
            adminUser = adminUser
        )
    }

    // --- USEFUL TIPS MANAGEMENT ---
    suspend fun insertOrUpdateTip(tip: TipEntity, adminUser: String) = withContext(Dispatchers.IO) {
        dao.insertTip(tip)
        logAudit(
            action = "TIP_SAVED",
            details = "Saved tip '${tip.title}' (Published: ${tip.isPublished})",
            adminUser = adminUser
        )
    }

    suspend fun deleteTip(tipId: String, tipTitle: String, adminUser: String) = withContext(Dispatchers.IO) {
        dao.deleteTip(tipId)
        logAudit(
            action = "TIP_DELETED",
            details = "Deleted tip '$tipTitle' (ID: $tipId)",
            adminUser = adminUser
        )
    }

    // --- LATEST UPDATES MANAGEMENT ---
    suspend fun insertOrUpdateUpdate(update: UpdateEntity, adminUser: String) = withContext(Dispatchers.IO) {
        dao.insertUpdate(update)
        logAudit(
            action = "UPDATE_SAVED",
            details = "Saved update '${update.title}' (Published: ${update.isPublished})",
            adminUser = adminUser
        )
    }

    suspend fun deleteUpdate(updateId: String, updateTitle: String, adminUser: String) = withContext(Dispatchers.IO) {
        dao.deleteUpdate(updateId)
        logAudit(
            action = "UPDATE_DELETED",
            details = "Deleted announcement '$updateTitle' (ID: $updateId)",
            adminUser = adminUser
        )
    }

    // --- NOTIFICATIONS MANAGEMENT ---
    suspend fun insertOrUpdateNotification(notification: NotificationEntity, adminUser: String) = withContext(Dispatchers.IO) {
        dao.insertNotification(notification)
        logAudit(
            action = "NOTIFICATION_SAVED",
            details = "Broadcast announcement: '${notification.title}' (Published: ${notification.isPublished})",
            adminUser = adminUser
        )
    }

    suspend fun deleteNotification(id: String, title: String, adminUser: String) = withContext(Dispatchers.IO) {
        dao.deleteNotification(id)
        logAudit(
            action = "NOTIFICATION_DELETED",
            details = "Deleted notification '$title' (ID: $id)",
            adminUser = adminUser
        )
    }

    // --- ENQUIRY MANAGEMENT ---
    suspend fun updateEnquiryStatus(enquiryId: String, status: String, adminUser: String) = withContext(Dispatchers.IO) {
        dao.updateEnquiryStatus(enquiryId, status)
        logAudit(
            action = "ENQUIRY_STATUS_UPDATED",
            details = "Enquiry $enquiryId marked as $status",
            adminUser = adminUser
        )
    }

    suspend fun deleteEnquiry(enquiryId: String, adminUser: String) = withContext(Dispatchers.IO) {
        dao.deleteEnquiry(enquiryId)
        logAudit(
            action = "ENQUIRY_DELETED",
            details = "Deleted enquiry ID: $enquiryId",
            adminUser = adminUser
        )
    }

    // --- BUSINESS & APP SETTINGS ---
    suspend fun saveSetting(key: String, value: String, adminUser: String) = withContext(Dispatchers.IO) {
        dao.insertSetting(AppSettingEntity(key, value))
        logAudit(
            action = "SETTING_UPDATED",
            details = "Updated setting '$key' to '$value'",
            adminUser = adminUser
        )
    }

    suspend fun saveBatchSettings(settings: Map<String, String>, adminUser: String) = withContext(Dispatchers.IO) {
        dao.insertSettings(settings.map { AppSettingEntity(it.key, it.value) })
        logAudit(
            action = "SETTINGS_BATCH_UPDATED",
            details = "Updated ${settings.size} business/app settings",
            adminUser = adminUser
        )
    }

    // --- AUDIT LOG HELPER ---
    suspend fun logAudit(action: String, details: String, adminUser: String) = withContext(Dispatchers.IO) {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        val log = AuditLogEntity(
            action = action,
            details = details,
            adminUser = adminUser,
            timestamp = System.currentTimeMillis(),
            formattedDate = dateFormat.format(Date())
        )
        dao.insertAuditLog(log)
    }
}
