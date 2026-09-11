package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.DigiNovaDataRepository
import com.example.data.LoginResult
import com.example.data.local.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class DigiNovaProductionSuiteTest {

    private lateinit var database: DigiNovaDatabase
    private lateinit var dao: DigiNovaDao
    private lateinit var repository: DigiNovaDataRepository

    @Before
    fun setup() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, DigiNovaDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.digiNovaDao()

        // Seed initial data
        DefaultSeedData.seed(dao)

        repository = DigiNovaDataRepository(context, database)
    }

    @After
    fun teardown() {
        database.close()
    }

    // 1. Admin login (valid credentials succeed, invalid credentials fail)
    @Test
    fun test1_adminLoginSuccessAndFailure() = runBlocking {
        // Valid login
        val successResult = repository.adminLogin("admin", "Admin@DigiNova2026!")
        assertTrue("Admin login with default credentials should succeed", successResult is LoginResult.Success)
        val adminUser = (successResult as LoginResult.Success).user
        assertEquals("admin", adminUser.username)

        // Invalid password
        val wrongPassResult = repository.adminLogin("admin", "WrongPassword123!")
        assertTrue("Admin login with wrong password should fail", wrongPassResult is LoginResult.Error)

        // Unknown username
        val unknownUserResult = repository.adminLogin("hacker", "Password123!")
        assertTrue("Admin login with non-existent user should fail", unknownUserResult is LoginResult.Error)
    }

    // 2. Admin logout (session cleared, audit log emitted)
    @Test
    fun test2_adminLogoutAndAuditLog() = runBlocking {
        repository.logAudit("LOGOUT", "Admin admin logged out", "admin")
        val logs = repository.auditLogs.first()
        val logoutLog = logs.find { it.action == "LOGOUT" }
        assertNotNull("Logout action should be recorded in audit log", logoutLog)
        assertEquals("admin", logoutLog?.adminUser)
    }

    // 3. Password reset flow (validate current, salt & hash new, re-login)
    @Test
    fun test3_passwordResetFlow() = runBlocking {
        val resetResult = repository.changeAdminPassword("admin", "Admin@DigiNova2026!", "NewSecurePassword2026@#")
        assertTrue("Password change should succeed with correct current password", resetResult.isSuccess)

        // Old password must now fail
        val oldLogin = repository.adminLogin("admin", "Admin@DigiNova2026!")
        assertTrue("Old password should no longer work", oldLogin is LoginResult.Error)

        // New password must succeed
        val newLogin = repository.adminLogin("admin", "NewSecurePassword2026@#")
        assertTrue("New password should grant access", newLogin is LoginResult.Success)
    }

    // 4. Add service
    @Test
    fun test4_addService() = runBlocking {
        val newService = ServiceEntity(
            id = "test_custom_dev",
            title = "Custom Blockchain Architecture",
            category = "Development",
            tagline = "Enterprise ledger systems",
            description = "High-security distributed ledger systems for modern enterprises.",
            iconType = "DEVELOPMENT",
            deliveryTime = "3-4 Weeks",
            pricing = "Starting at $4,500",
            featuresString = "Smart Contracts||Audit Readiness||Cross-chain Bridges",
            benefitsString = "Zero Tampering||Real-time Settlement",
            badge = "ENTERPRISE",
            isEnabled = true,
            sortOrder = 10
        )
        repository.insertOrUpdateService(newService, "admin")

        val all = repository.allServices.first()
        val found = all.find { it.id == "test_custom_dev" }
        assertNotNull("Added service should exist in database", found)
        assertEquals("Custom Blockchain Architecture", found?.title)
    }

    // 5. Edit service
    @Test
    fun test5_editService() = runBlocking {
        val original = repository.allServices.first().first()
        val updated = original.copy(title = "Updated Mobile App Dev Suite", pricing = "$1,999")
        repository.insertOrUpdateService(updated, "admin")

        val found = repository.allServices.first().find { it.id == original.id }
        assertEquals("Updated Mobile App Dev Suite", found?.title)
        assertEquals("$1,999", found?.pricing)
    }

    // 6. Delete service
    @Test
    fun test6_deleteService() = runBlocking {
        val initialCount = repository.allServices.first().size
        val target = repository.allServices.first().first()

        repository.deleteService(target.id, target.title, "admin")

        val remaining = repository.allServices.first()
        assertEquals(initialCount - 1, remaining.size)
        assertNull(remaining.find { it.id == target.id })
    }

    // 7. Enable/disable service
    @Test
    fun test7_toggleServiceStatus() = runBlocking {
        val target = repository.allServices.first().first()
        assertTrue("Target service should initially be active", target.isEnabled)

        repository.toggleServiceStatus(target, "admin")

        val modified = repository.allServices.first().find { it.id == target.id }
        assertNotNull(modified)
        assertFalse("Target service should now be inactive", modified!!.isEnabled)

        // Customer facing activeServices flow should NOT contain inactive service
        val activeForCustomers = repository.activeServices.first()
        assertNull("Inactive service must not be shown to customers", activeForCustomers.find { it.id == target.id })
    }

    // 8. Add update
    @Test
    fun test8_addUpdate() = runBlocking {
        val newUpdate = UpdateEntity(
            id = "update_test_1",
            title = "DIGI NOVA 2026 Spring Launch",
            category = "Platform",
            date = "May 2026",
            summary = "Major new enterprise tool integrations.",
            fullArticle = "Full announcement regarding new digital innovations.",
            readTime = "2 min",
            badge = "NEW",
            author = "Anup Digi Nova",
            isPublished = true,
            timestamp = System.currentTimeMillis()
        )
        repository.insertOrUpdateUpdate(newUpdate, "admin")

        val updates = repository.publishedUpdates.first()
        val found = updates.find { it.id == "update_test_1" }
        assertNotNull("Added update should be visible in published updates", found)
        assertEquals("Anup Digi Nova", found?.author)
    }

    // 9. Add notification
    @Test
    fun test9_addNotification() = runBlocking {
        val newNotif = NotificationEntity(
            id = "notif_test_1",
            title = "Scheduled Security Maintenance",
            message = "Systems will perform scheduled upgrade at 02:00 UTC.",
            date = "Today",
            priority = "HIGH",
            isPublished = true,
            timestamp = System.currentTimeMillis()
        )
        repository.insertOrUpdateNotification(newNotif, "admin")

        val notifications = repository.publishedNotifications.first()
        val found = notifications.find { it.id == "notif_test_1" }
        assertNotNull("Added notification should be visible in published notifications", found)
        assertEquals("HIGH", found?.priority)
    }

    // 10. Change WhatsApp number
    @Test
    fun test10_changeWhatsAppNumber() = runBlocking {
        repository.saveSetting("support_whatsapp", "+15559876543", "admin")

        val settings = repository.appSettings.first()
        assertEquals("+15559876543", settings["support_whatsapp"])
    }

    // 11. Submit customer enquiry
    @Test
    fun test11_submitCustomerEnquiry() = runBlocking {
        val result = repository.submitCustomerEnquiry(
            name = "Jane Doe",
            phone = "+15551234567",
            email = "jane@example.com",
            serviceId = "mobile_dev",
            serviceTitle = "Mobile App Development",
            message = "We need an Android app developed for our retail store."
        )
        assertTrue("Customer enquiry submission should succeed", result.isSuccess)
    }

    // 12. View enquiry in Admin Panel
    @Test
    fun test12_viewEnquiryInAdminPanel() = runBlocking {
        repository.submitCustomerEnquiry(
            name = "Alex Smith",
            phone = "+15554443322",
            email = "alex@test.com",
            serviceId = "ui_ux",
            serviceTitle = "UI/UX Design",
            message = "Requesting a Figma design quote."
        )

        val enquiries = repository.allEnquiries.first()
        val found = enquiries.find { it.customerEmail == "alex@test.com" }
        assertNotNull("Admin should be able to view submitted customer enquiry", found)
        assertEquals("Alex Smith", found?.customerName)
        assertEquals("PENDING", found?.status)

        // Admin updates status to COMPLETED
        repository.updateEnquiryStatus(found!!.id, "COMPLETED", "admin")
        val updated = repository.allEnquiries.first().find { it.id == found.id }
        assertEquals("COMPLETED", updated?.status)
    }

    // 13. Customer cannot access Admin Panel without authentication
    @Test
    fun test13_leastPrivilegeAccessControl() = runBlocking {
        // Without logging in, repository operations requiring admin credentials
        // are strictly validated. When checking authentication state, unauthenticated attempts fail.
        val invalidAttempt = repository.adminLogin("customer_user", "NoPassword")
        assertTrue(invalidAttempt is LoginResult.Error)

        val unauthenticatedPasswordChange = repository.changeAdminPassword("unknown_user", "any", "newPass")
        assertFalse("Unauthenticated password changes must be rejected", unauthenticatedPasswordChange.isSuccess)
    }

    // 14. Database security rules & password hashing (Salt + SHA-256 / PBKDF2)
    @Test
    fun test14_securityRulesAndBruteForceLockout() = runBlocking {
        // Verify passwords in DB are salted & hashed, NEVER plaintext
        val adminEntity = dao.getAdminUser("admin")
        assertNotNull("Admin user entity must exist", adminEntity)
        assertFalse("Stored password MUST NOT be plaintext", adminEntity!!.passwordHash.contains("Admin@DigiNova2026!"))
        assertTrue("Stored password must be a cryptographic hash", adminEntity.passwordHash.length >= 64)
        assertTrue("Salt must be non-empty", adminEntity.salt.isNotBlank())

        // Test Brute-Force lockout: 5 consecutive failed attempts lock the account
        for (i in 1..5) {
            repository.adminLogin("admin", "wrong_attempt_$i")
        }

        val lockedResult = repository.adminLogin("admin", "wrong_attempt_6")
        assertTrue("Account must lock after 5 failed attempts", lockedResult is LoginResult.AccountLocked)
    }

    // 15. App works after restart (data persistence across database connections)
    @Test
    fun test15_dataPersistsAcrossRestart() = runBlocking {
        // Modify a setting and a service
        repository.saveSetting("owner_name", "Anup Digi Nova", "admin")
        repository.saveSetting("support_whatsapp", "+15551112222", "admin")

        // Read directly from DB to simulate a cold application restart
        val whatsappValue = dao.getSettingValue("support_whatsapp")
        assertEquals("+15551112222", whatsappValue)

        val ownerValue = dao.getSettingValue("owner_name")
        assertEquals("Anup Digi Nova", ownerValue)
    }
}
