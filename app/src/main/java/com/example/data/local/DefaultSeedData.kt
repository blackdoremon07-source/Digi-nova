package com.example.data.local

import com.example.data.security.SecurityHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DefaultSeedData {

    fun getDefaultServices(): List<ServiceEntity> = listOf(
        ServiceEntity(
            id = "ds-web-dev",
            title = "Modern Web & App Development",
            tagline = "Blazing fast, responsive web apps and native Android solutions.",
            category = "Development",
            description = "Empower your organization with custom, scalable web architectures and native mobile applications. Built using Kotlin, Compose, React, and modern microservices with 99.9% uptime and enterprise security.",
            iconType = "code",
            featuresString = "Native Android & Cross-Platform UI||Next.js & Cloud-Optimized Backends||Automated CI/CD Pipeline Deployment||API & Payment Gateway Integrations||Enterprise Performance & Caching",
            benefitsString = "Up to 3x Faster Page Loading||Scalable to Millions of Users||Turnkey Maintenance & 24/7 SLA",
            deliveryTime = "7–14 Business Days",
            pricing = "Starting at $499",
            isFeatured = true,
            isPopular = true,
            isEnabled = true,
            badge = "MOST POPULAR",
            sortOrder = 1
        ),
        ServiceEntity(
            id = "ds-ui-ux",
            title = "UI/UX & Product Design",
            tagline = "Human-centered, conversion-driven mobile and web design systems.",
            category = "Design",
            description = "Transform complex user journeys into intuitive, delightful digital interfaces. We craft high-fidelity prototypes, design tokens, responsive layouts, and interactive micro-animations in Figma.",
            iconType = "palette",
            featuresString = "Complete Figma Component Systems||High-Fidelity Interactive Prototypes||Mobile-First Adaptive Wireframing||UX Research & Usability Testing||Material Design 3 & iOS Guidelines",
            benefitsString = "Boost Conversion Rates by 40%+||Flawless Developer Handoff||Consistency Across All Platforms",
            deliveryTime = "5–10 Business Days",
            pricing = "Starting at $299",
            isFeatured = true,
            isPopular = false,
            isEnabled = true,
            badge = "FEATURED",
            sortOrder = 2
        ),
        ServiceEntity(
            id = "ds-brand-identity",
            title = "Brand Identity & Vector Logos",
            tagline = "Memorable vector logos, color typography kits, and brand books.",
            category = "Design",
            description = "Stand out in crowded digital markets with a bespoke visual identity. We craft scalable vector logos, typography pairings, social media kits, business stationery, and comprehensive brand guidelines.",
            iconType = "brush",
            featuresString = "100% Custom Vector Artwork (SVG/AI)||Full Commercial Copyright Ownership||Comprehensive Brand Guidelines PDF||Social Media Profile & Banner Kits||Light & Dark Palette Variations",
            benefitsString = "Distinctive Brand Recognition||Ready for Print & Digital||Instant Brand Authority",
            deliveryTime = "3–5 Business Days",
            pricing = "Starting at $199",
            isFeatured = false,
            isPopular = false,
            isEnabled = true,
            badge = null,
            sortOrder = 3
        ),
        ServiceEntity(
            id = "ds-cloud-it",
            title = "Cloud Infrastructure & DevOps",
            tagline = "Secure server setup, Docker containers, SSL, and database management.",
            category = "Cloud & IT",
            description = "Achieve zero downtime with robust cloud architecture. We design, deploy, and manage AWS, Google Cloud, and VPS environments with automated backups, DDoS shielding, and SSL encryption.",
            iconType = "cloud",
            featuresString = "AWS / GCP / Cloudflare Setup||Docker & Kubernetes Microservices||Automated Daily Encrypted Backups||SSL Certificates & Firewall Defense||Database Replication & Optimization",
            benefitsString = "99.99% Guaranteed Availability||Enterprise Bank-Grade Security||Predictable Low Infrastructure Costs",
            deliveryTime = "3–7 Business Days",
            pricing = "Starting at $349",
            isFeatured = true,
            isPopular = false,
            isEnabled = true,
            badge = "ENTERPRISE",
            sortOrder = 4
        ),
        ServiceEntity(
            id = "ds-seo-marketing",
            title = "SEO & Growth Marketing",
            tagline = "Dominating search rankings with on-page SEO and viral ad funnels.",
            category = "Marketing",
            description = "Attract paying customers organically. Our digital marketing suite combines technical on-page SEO, high-converting copy, targeted Google Ads, and social campaigns.",
            iconType = "trending_up",
            featuresString = "In-Depth Competitor & Keyword Audits||Technical Schema & Meta Optimization||High-Converting Ad Funnel Architecture||Google Search Console & Analytics 4||Monthly Growth & ROI Reporting",
            benefitsString = "Sustainable Inflow of Organic Leads||Lower Cost Per Acquisition||Measurable Growth Metrics",
            deliveryTime = "Ongoing Monthly",
            pricing = "Starting at $399/mo",
            isFeatured = false,
            isPopular = true,
            isEnabled = true,
            badge = "GROWTH",
            sortOrder = 5
        ),
        ServiceEntity(
            id = "ds-ai-automation",
            title = "AI Solutions & Automation",
            tagline = "Intelligent AI workflows, automated CRM sync, and custom chatbots.",
            category = "Development",
            description = "Harness the power of cutting-edge AI. We integrate Gemini, GPT, and custom automation webhooks into your existing workflows, cutting manual work by up to 80%.",
            iconType = "auto_awesome",
            featuresString = "Gemini & LLM API Integrations||Automated Customer Support Bots||Zapier / Make / Webhook Pipelines||Intelligent Document Processing||Real-Time Sentiment Analysis",
            benefitsString = "Save 20+ Hours of Manual Work Weekly||Instant 24/7 Customer Responses||Future-Proof Your Operations",
            deliveryTime = "5–12 Business Days",
            pricing = "Starting at $449",
            isFeatured = true,
            isPopular = true,
            isEnabled = true,
            badge = "NEW AI",
            sortOrder = 6
        )
    )

    fun getDefaultOnlineTools(): List<OnlineToolEntity> = listOf(
        OnlineToolEntity(
            id = "ot-speedtest",
            title = "Internet Speed Benchmark",
            category = "Utilities",
            description = "Accurately measure your download, upload, ping, and jitter latency across worldwide test nodes.",
            url = "https://fast.com",
            iconType = "speed",
            badge = "HOT",
            isPopular = true,
            isEnabled = true,
            sortOrder = 1
        ),
        OnlineToolEntity(
            id = "ot-qr-generator",
            title = "Dynamic QR Code Generator",
            category = "Tools",
            description = "Generate custom high-resolution QR codes for websites, WiFi credentials, vCards, and text instantly.",
            url = "https://qr-code-generator.com",
            iconType = "qr_code",
            badge = "FREE",
            isPopular = true,
            isEnabled = true,
            sortOrder = 2
        ),
        OnlineToolEntity(
            id = "ot-pdf-editor",
            title = "Online PDF Suite & Tools",
            category = "Productivity",
            description = "Merge, compress, convert, split, and digitally sign PDF documents securely in your web browser.",
            url = "https://ilovepdf.com",
            iconType = "description",
            badge = null,
            isPopular = false,
            isEnabled = true,
            sortOrder = 3
        ),
        OnlineToolEntity(
            id = "ot-pass-checker",
            title = "Password Strength & Pwned Check",
            category = "Security",
            description = "Verify whether your credentials have appeared in known data breaches without sending passwords in plain text.",
            url = "https://haveibeenpwned.com",
            iconType = "security",
            badge = "SECURE",
            isPopular = true,
            isEnabled = true,
            sortOrder = 4
        ),
        OnlineToolEntity(
            id = "ot-image-compressor",
            title = "Lossless Image Optimizer",
            category = "Tools",
            description = "Smart WebP, PNG, and JPEG compression tool to reduce image file size by up to 80% without quality loss.",
            url = "https://tinypng.com",
            iconType = "cloud_upload",
            badge = null,
            isPopular = false,
            isEnabled = true,
            sortOrder = 5
        ),
        OnlineToolEntity(
            id = "ot-currency",
            title = "Live Global Currency Exchange",
            category = "Finance",
            description = "Real-time mid-market foreign exchange rates, conversion calculator, and historical market volatility charts.",
            url = "https://xe.com",
            iconType = "currency_exchange",
            badge = null,
            isPopular = false,
            isEnabled = true,
            sortOrder = 6
        )
    )

    fun getDefaultTips(): List<TipEntity> = listOf(
        TipEntity(
            id = "tip-battery-life",
            title = "How to Maximize Android Battery Longevity",
            category = "Hardware",
            summary = "Smart charging habits, dark mode display benefits, and managing background app refresh to extend battery life.",
            fullContent = "Modern lithium-ion smartphone batteries degrade faster when kept at extreme charge levels.\n\n1. Enable Adaptive Charging: Android learns your charging routine and delays charging past 80% until just before you wake up.\n2. Keep Charge Between 20% and 80%: Regular shallow charging cycles produce significantly less chemical stress.\n3. Utilize Dark Mode with OLED: Black pixels on OLED screens consume zero power, saving 15-30% battery.\n4. Audit Background App Refresh: Go to Settings > Apps > Special App Access > Battery Optimization and restrict background activity for social apps.",
            readTimeMinutes = 3,
            isFeatured = true,
            isPublished = true,
            keyTakeawaysString = "Keep charge between 20%–80% for double lifespan||OLED Dark Mode saves up to 30% battery||Turn on Android Adaptive Charging in Settings",
            tagsString = "Battery||Hardware||Optimization||Android",
            sortOrder = 1
        ),
        TipEntity(
            id = "tip-2fa-security",
            title = "Essential Mobile Security: Protect Your Identity",
            category = "Security",
            summary = "Why SMS two-factor authentication is vulnerable and how to switch to hardware security keys or authenticator apps.",
            fullContent = "SIM swapping attacks have made SMS-based two-factor authentication (2FA) increasingly dangerous for securing email, banking, and crypto accounts.\n\n1. Upgrade to Authenticator Apps: Use Google Authenticator, Aegis, or Bitwarden instead of SMS codes.\n2. Enable Passkeys: Android supports biometric passkeys using Fingerprint/Face Unlock, which cannot be phished.\n3. Lock Your SIM Card: Set a 4-digit SIM PIN in Settings > Security > SIM Card Lock to prevent unauthorized SIM porting.\n4. Regularly Review Connected Devices in your Google Account.",
            readTimeMinutes = 4,
            isFeatured = true,
            isPublished = true,
            keyTakeawaysString = "Switch from SMS to Authenticator apps or Passkeys||Set a 4-digit SIM PIN to prevent SIM swapping||Review logged-in devices every month",
            tagsString = "Security||Privacy||2FA||Passkeys",
            sortOrder = 2
        ),
        TipEntity(
            id = "tip-storage-cleanup",
            title = "Clean Up Gigabytes of Junk Without Buying Cloud Storage",
            category = "Productivity",
            summary = "Step-by-step methods to identify hidden app caches, duplicate photos, and oversized chat attachments.",
            fullContent = "Running out of storage slows down system performance and prevents OS updates.\n\n1. Use Google Files App: Open Files by Google and tap 'Clean' to safely purge temporary cached app files.\n2. Clear WhatsApp Media Cache: Go to WhatsApp Settings > Storage and Data > Manage Storage to review media larger than 5MB.\n3. Enable Smart Storage: Android can automatically remove backed-up photos older than 60 days.\n4. Check Download Folder: Remove leftover APK files and old PDF manuals.",
            readTimeMinutes = 3,
            isFeatured = false,
            isPublished = true,
            keyTakeawaysString = "Purge cached app data via Files by Google||Filter WhatsApp media files > 5MB||Delete leftover APKs and installers",
            tagsString = "Storage||CleanUp||Speed||Android",
            sortOrder = 3
        ),
        TipEntity(
            id = "tip-wifi-dns",
            title = "Speed Up Browsing & Block Ads with Private DNS",
            category = "Connectivity",
            summary = "Configure Encrypted Private DNS on Android to eliminate intrusive ads and accelerate domain lookups.",
            fullContent = "Android allows you to set a system-wide encrypted DNS provider without installing third-party VPN apps.\n\n1. Open Settings > Network & Internet > Private DNS.\n2. Select 'Private DNS provider hostname'.\n3. Enter 'dns.adguard-dns.com' to automatically block trackers and banner ads across all apps and browsers.\n4. Alternatively, use '1dot1dot1dot1.cloudflare-dns.com' for the fastest DNS response times worldwide.",
            readTimeMinutes = 2,
            isFeatured = true,
            isPublished = true,
            keyTakeawaysString = "Private DNS blocks ads across all apps without installing VPNs||Cloudflare DNS (1.1.1.1) provides ultra-fast domain resolution||Takes under 30 seconds to configure in Settings",
            tagsString = "Networking||DNS||AdBlock||WiFi",
            sortOrder = 4
        )
    )

    fun getDefaultUpdates(): List<UpdateEntity> = listOf(
        UpdateEntity(
            id = "upd-diginova-platform",
            title = "DIGI NOVA 2026 Enterprise Upgrade Released",
            category = "Platform Update",
            date = "September 2026",
            summary = "Major update bringing real-time administrative controls, client enquiry portals, offline Room database, and expanded digital services.",
            fullArticle = "We are thrilled to unveil the new DIGI NOVA Digital Platform. This comprehensive release introduces a fully managed client ecosystem with reactive database synchronization, direct customer inquiry flows, and an authorized Owner/Admin dashboard.\n\nClients can now seamlessly explore services, trigger instant WhatsApp and email inquiries, access security tools, and receive verified announcements. The platform architecture has been re-engineered for ultra-low latency, full edge-to-edge responsiveness, and enterprise data privacy.",
            readTime = "2 min read",
            badge = "OFFICIAL",
            author = "Anup Digi Nova",
            isPublished = true
        ),
        UpdateEntity(
            id = "upd-ai-launch",
            title = "New AI Workflow & Intelligent Automation Service",
            category = "New Service",
            date = "August 2026",
            summary = "DIGI NOVA now offers custom LLM integration, automated CRM webhooks, and intelligent customer support pipelines.",
            fullArticle = "Organizations of all sizes can now deploy modern AI capabilities without technical overhead. DIGI NOVA's new AI Solutions team specializes in training and integrating custom conversational assistants, document analysis pipelines, and automated multi-channel messaging.\n\nInitial benchmark results demonstrate up to 80% reduction in customer response times. Get in touch with our team today to schedule an exploratory consultation.",
            readTime = "3 min read",
            badge = "NEW",
            author = "Anup Digi Nova",
            isPublished = true
        )
    )

    fun getDefaultNotifications(): List<NotificationEntity> = listOf(
        NotificationEntity(
            id = "notif-welcome",
            title = "Welcome to DIGI NOVA!",
            message = "Explore our digital services, verified online tools, and expert tech tips. Need assistance? Tap WhatsApp in the header!",
            date = "Today",
            priority = "HIGH",
            isPublished = true
        ),
        NotificationEntity(
            id = "notif-support",
            title = "24/7 Digital Consultation Active",
            message = "Submit inquiries for mobile apps, web development, and cloud hosting directly through the app.",
            date = "Active",
            priority = "NORMAL",
            isPublished = true
        )
    )

    fun getDefaultSettings(): List<AppSettingEntity> = listOf(
        AppSettingEntity("brand_name", "DIGI NOVA"),
        AppSettingEntity("tagline", "DIGITAL • SIMPLE • SMART"),
        AppSettingEntity("owner_name", "Anup Digi Nova"),
        AppSettingEntity("developer_name", "Anup Digi Nova"),
        AppSettingEntity("copyright", "© 2026 Anup Digi Nova. All Rights Reserved."),
        AppSettingEntity("whatsapp_number", "+15550198421"),
        AppSettingEntity("phone_number", "+1 555-019-8421"),
        AppSettingEntity("support_email", "contact@diginova.io"),
        AppSettingEntity("website_url", "https://diginova.io"),
        AppSettingEntity("address", "Nova Innovation Tower, Silicon Valley, CA"),
        AppSettingEntity("working_hours", "24/7 Digital Support & Consultation"),
        AppSettingEntity("app_version", "3.0.0 Enterprise"),
        AppSettingEntity("maintenance_mode", "false"),
        AppSettingEntity("maintenance_message", "DIGI NOVA is currently undergoing scheduled platform upgrades. We will be right back!"),
        AppSettingEntity("about_text", "DIGI NOVA is your premier modern digital partner. Founded and developed by Anup Digi Nova, we deliver high-impact digital services, curated online tools, practical tech tips, and continuous industry updates to power up your business and everyday digital life."),
        AppSettingEntity("privacy_policy", "DIGI NOVA (\"we\", \"our\", or \"us\") is committed to protecting your privacy. We collect minimal customer details solely to process inquiries and deliver requested services. We never sell or share personal information with third parties. All sensitive data is protected via encrypted local and cloud architectures."),
        AppSettingEntity("terms_conditions", "By accessing DIGI NOVA services, you agree to these terms. All software, designs, and content remain the intellectual property of Anup Digi Nova until full completion and delivery. We guarantee dedicated SLA response times and client data confidentiality.")
    )

    fun getDefaultAdminUser(): AdminUserEntity {
        val salt = SecurityHelper.generateSalt()
        // Initial admin credentials with secure salted hash
        // Default username: admin (or admin@diginova.io)
        // Default initial password: Admin@DigiNova2026!
        val passwordHash = SecurityHelper.hashPassword("Admin@DigiNova2026!", salt)
        return AdminUserEntity(
            username = "admin",
            email = "admin@diginova.io",
            passwordHash = passwordHash,
            salt = salt,
            role = "SUPER_ADMIN",
            twoFactorEnabled = false
        )
    }

    fun getInitialAuditLog(): AuditLogEntity {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        return AuditLogEntity(
            action = "SYSTEM_INITIALIZED",
            details = "DIGI NOVA database created with default services, settings, and security policies.",
            adminUser = "SYSTEM",
            formattedDate = dateFormat.format(Date())
        )
    }

    suspend fun seed(dao: DigiNovaDao) {
        dao.insertServices(getDefaultServices())
        dao.insertOnlineTools(getDefaultOnlineTools())
        dao.insertTips(getDefaultTips())
        dao.insertUpdates(getDefaultUpdates())
        dao.insertNotifications(getDefaultNotifications())
        dao.insertSettings(getDefaultSettings())
        dao.insertAdminUser(getDefaultAdminUser())
        dao.insertAuditLog(getInitialAuditLog())
    }
}
