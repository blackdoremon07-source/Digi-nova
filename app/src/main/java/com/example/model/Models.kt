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
    PROFILE("Profile", "profile")
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
    val badge: String? = null
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
    val isPopular: Boolean = false
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
    val keyTakeaways: List<String>,
    val tags: List<String>
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
    val author: String = "DIGI NOVA Team"
)

/**
 * Company and contact information model
 */
data class CompanyContact(
    val appName: String = "DIGI NOVA",
    val tagline: String = "DIGITAL • SIMPLE • SMART",
    val companyBio: String = "DIGI NOVA is your premier modern digital partner. We deliver high-impact digital services, curated online tools, practical tech tips, and continuous industry updates to power up your business and everyday digital life.",
    val supportWhatsAppNumber: String = "+15550198421",
    val supportEmail: String = "contact@diginova.io",
    val websiteUrl: String = "https://diginova.io",
    val telegramHandle: String = "diginova_official",
    val twitterUrl: String = "https://x.com/diginova",
    val linkedinUrl: String = "https://linkedin.com/company/diginova",
    val address: String = "Nova Innovation Tower, Silicon Valley, CA",
    val workingHours: String = "24/7 Digital Support & Consultation",
    val version: String = "2.4.0 (Build 2026)"
)
