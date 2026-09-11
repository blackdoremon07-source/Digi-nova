package com.example.model

/**
 * Screen destinations for DIGI NOVA
 */
enum class NavScreen(val title: String, val route: String) {
    HOME("Home", "home"),
    DIGITAL_SERVICES("Digital Services", "services"),
    ONLINE_SERVICES("Online Services", "online"),
    USEFUL_TIPS("Useful Tips", "tips"),
    LATEST_UPDATES("Updates", "updates"),
    NOTIFICATIONS("Notifications", "notifications"),
    PROFILE("Profile", "profile"),
    CONTACT_US("Contact Us", "contact_us"),
    PRIVACY_POLICY("Privacy Policy", "privacy_policy"),
    TERMS_CONDITIONS("Terms & Conditions", "terms_conditions"),
    ADMIN_LOGIN("Admin Login", "admin_login"),
    ADMIN_DASHBOARD("Admin Dashboard", "admin_dashboard")
}

/**
 * Digital Service representation
 */
data class DigitalService(
    val id: String,
    val title: String,
    val tagline: String,
    val category: String,
    val description: String,
    val iconType: String,
    val features: List<String>,
    val benefits: List<String>,
    val deliveryTime: String,
    val pricing: String,
    val isFeatured: Boolean = false,
    val isPopular: Boolean = false,
    val isEnabled: Boolean = true,
    val badge: String? = null,
    val sortOrder: Int = 0
)

/**
 * Online Service Link representation
 */
data class OnlineService(
    val id: String,
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

/**
 * Useful Tip representation
 */
data class UsefulTip(
    val id: String,
    val title: String,
    val category: String,
    val summary: String,
    val fullContent: String,
    val readTimeMinutes: Int,
    val isFeatured: Boolean = false,
    val isPublished: Boolean = true,
    val keyTakeaways: List<String>,
    val tags: List<String>,
    val sortOrder: Int = 0
)

/**
 * Latest Update / News representation
 */
data class LatestUpdate(
    val id: String,
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

/**
 * Customer Enquiry representation
 */
data class CustomerEnquiry(
    val id: String,
    val customerName: String,
    val customerPhone: String,
    val customerEmail: String,
    val serviceId: String? = null,
    val serviceTitle: String,
    val message: String,
    val status: String = "PENDING", // PENDING, IN_PROGRESS, COMPLETED
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Announcement / Notification representation
 */
data class AppNotification(
    val id: String,
    val title: String,
    val message: String,
    val date: String,
    val priority: String = "NORMAL",
    val isPublished: Boolean = true,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Business & Contact Settings representation
 */
data class CompanyContact(
    val appName: String = "DIGI NOVA",
    val tagline: String = "DIGITAL • SIMPLE • SMART",
    val ownerName: String = "Anup Digi Nova",
    val developerName: String = "Anup Digi Nova",
    val copyright: String = "© 2026 Anup Digi Nova. All Rights Reserved.",
    val companyBio: String = "DIGI NOVA is your premier modern digital partner. Founded and developed by Anup Digi Nova, we deliver high-impact digital services, curated online tools, practical tech tips, and continuous industry updates to power up your business and everyday digital life.",
    val supportWhatsAppNumber: String = "+15550198421",
    val supportPhoneNumber: String = "+1 555-019-8421",
    val supportEmail: String = "contact@diginova.io",
    val websiteUrl: String = "https://diginova.io",
    val telegramHandle: String = "diginova_official",
    val twitterUrl: String = "https://x.com/diginova",
    val linkedinUrl: String = "https://linkedin.com/company/diginova",
    val address: String = "Nova Innovation Tower, Silicon Valley, CA",
    val workingHours: String = "24/7 Digital Support & Consultation",
    val version: String = "3.0.0 Enterprise",
    val privacyPolicy: String = "DIGI NOVA (\"we\", \"our\", or \"us\") is committed to protecting your privacy. We collect minimal customer details solely to process inquiries and deliver requested services. We never sell or share personal information with third parties. All sensitive data is protected via encrypted local and cloud architectures.",
    val termsConditions: String = "By accessing DIGI NOVA services, you agree to these terms. All software, designs, and content remain the intellectual property of Anup Digi Nova until full completion and delivery. We guarantee dedicated SLA response times and client data confidentiality.",
    val isMaintenanceMode: Boolean = false,
    val maintenanceMessage: String = "DIGI NOVA is currently undergoing scheduled platform upgrades. We will be right back!"
)
