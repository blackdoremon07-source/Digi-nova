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
        AppSettingEntity("owner_email", "anupcpr86@gmail.com"),
        AppSettingEntity("copyright", "© 2026 Anup Digi Nova. All Rights Reserved."),
        AppSettingEntity("whatsapp_number", "+919876543210"),
        AppSettingEntity("phone_number", "+91 98765 43210"),
        AppSettingEntity("support_email", "anupcpr86@gmail.com"),
        AppSettingEntity("website_url", "https://diginova.io"),
        AppSettingEntity("address", "DIGI NOVA Digital Hub, Technology Complex"),
        AppSettingEntity("working_hours", "24/7 Digital Support & Service Center"),
        AppSettingEntity("app_version", "3.2.0 Enterprise"),
        AppSettingEntity("maintenance_mode", "false"),
        AppSettingEntity("maintenance_message", "DIGI NOVA is currently undergoing scheduled platform upgrades. We will be right back!"),
        AppSettingEntity("about_text", "DIGI NOVA is your complete modern digital service center. Owned and developed by Anup Digi Nova, we deliver high-impact digital services, all-in-one photo studio & signature tools, government form tracking, calculators, and verified information to simplify your digital life."),
        AppSettingEntity("privacy_policy", "DIGI NOVA (\"we\", \"our\", or \"us\") respects your privacy. Photos, signatures, and personal documents processed in our Photo Studio and tools are stored securely on your local device and are never uploaded to external servers without explicit consent. All administrative channels are protected by cryptographic authentication."),
        AppSettingEntity("terms_conditions", "By accessing DIGI NOVA, you agree to these terms. All software and features are the intellectual property of Anup Digi Nova. Official government notifications are linked directly to verified portals.")
    )

    fun getDefaultAdminUser(): AdminUserEntity {
        val salt = SecurityHelper.generateSalt()
        // Admin user identifier: anupcpr86@gmail.com
        val passwordHash = SecurityHelper.hashPassword("DigiNova@Owner2026", salt)
        return AdminUserEntity(
            username = "anupcpr86@gmail.com",
            email = "anupcpr86@gmail.com",
            passwordHash = passwordHash,
            salt = salt,
            role = "OWNER_ADMIN",
            twoFactorEnabled = false
        )
    }

    fun getDefaultPublicUser(): UserEntity {
        val salt = SecurityHelper.generateSalt()
        val passwordHash = SecurityHelper.hashPassword("User@12345", salt)
        return UserEntity(
            id = "usr-demo-01",
            fullName = "Rahul Sharma",
            email = "rahul@diginova.io",
            phone = "+91 98765 12345",
            passwordHash = passwordHash,
            salt = salt,
            isEmailVerified = true,
            isPhoneVerified = true
        )
    }

    fun getDefaultExamForms(): List<ExamFormEntity> = listOf(
        ExamFormEntity(
            id = "form-ssc-cgl",
            title = "SSC Combined Graduate Level (CGL) 2026",
            organization = "Staff Selection Commission (SSC)",
            category = "SSC",
            startDate = "June 2026",
            lastDate = "July 2026",
            examDate = "September 2026",
            admitCardDate = "10 Days Before Exam",
            resultDate = "November 2026",
            eligibility = "Bachelor's Degree in any discipline from a recognized University",
            ageLimit = "18 - 32 Years (Age relaxation applicable as per rules)",
            minAge = 18,
            maxAge = 32,
            applicationFee = "Gen/OBC/EWS: ₹100 | SC/ST/PwD/Women: Exempted",
            requiredDocuments = "Passport Size Photo (20-50 KB, 3.5x4.5 cm)||Signature (10-20 KB, White Background)||Graduation Degree/Marksheet||ID Proof (Aadhaar / Voter ID)||Category Certificate",
            officialNotificationUrl = "https://ssc.gov.in",
            officialApplyUrl = "https://ssc.gov.in",
            status = "OPEN",
            isFeatured = true,
            isEnabled = true
        ),
        ExamFormEntity(
            id = "form-rrb-ntpc",
            title = "Railway RRB NTPC Recruitment 2026",
            organization = "Railway Recruitment Board (RRB)",
            category = "Railway",
            startDate = "May 2026",
            lastDate = "June 2026",
            examDate = "October 2026",
            admitCardDate = "4 Days Before Exam",
            resultDate = "December 2026",
            eligibility = "12th Pass or Any Graduate depending on post",
            ageLimit = "18 - 33 Years (3 years OBC, 5 years SC/ST relaxation)",
            minAge = 18,
            maxAge = 33,
            applicationFee = "Gen/OBC: ₹500 (Refundable on CBT)||SC/ST/Female: ₹250",
            requiredDocuments = "Color Passport Photo (JPEG 30-70 KB)||Scanned Signature (JPEG 15-30 KB)||Matriculation Certificate||Community Certificate for fee concession",
            officialNotificationUrl = "https://rrbapply.gov.in",
            officialApplyUrl = "https://rrbapply.gov.in",
            status = "OPEN",
            isFeatured = true,
            isEnabled = true
        ),
        ExamFormEntity(
            id = "form-ibps-po",
            title = "IBPS PO / Management Trainee XIV",
            organization = "Institute of Banking Personnel Selection",
            category = "Banking",
            startDate = "August 2026",
            lastDate = "August 2026",
            examDate = "October 2026",
            admitCardDate = "First Week of October",
            resultDate = "November 2026",
            eligibility = "Graduation Degree in any stream from recognized University",
            ageLimit = "20 - 30 Years",
            minAge = 20,
            maxAge = 30,
            applicationFee = "Gen/OBC/EWS: ₹850 | SC/ST/PwD: ₹175",
            requiredDocuments = "Passport Photo (4.5x3.5 cm, 20-50 KB)||Signature (Black Ink, 10-20 KB)||Left Thumb Impression (20-50 KB)||Handwritten Declaration (50-100 KB)",
            officialNotificationUrl = "https://ibps.in",
            officialApplyUrl = "https://ibps.in",
            status = "OPEN",
            isFeatured = true,
            isEnabled = true
        ),
        ExamFormEntity(
            id = "form-state-police",
            title = "State Police Constable & Sub-Inspector",
            organization = "State Police Recruitment Board",
            category = "Police",
            startDate = "July 2026",
            lastDate = "August 2026",
            examDate = "December 2026",
            admitCardDate = "15 Days Before Exam",
            resultDate = "January 2027",
            eligibility = "12th Standard for Constable, Graduate for SI",
            ageLimit = "18 - 25 Years (Relaxation as per State Gov rules)",
            minAge = 18,
            maxAge = 25,
            applicationFee = "All Candidates: ₹400",
            requiredDocuments = "Passport Photo with Date of Photo||Signature||10th & 12th Marksheets||Domicile Certificate||Caste Certificate",
            officialNotificationUrl = "https://uppbpb.gov.in",
            officialApplyUrl = "https://uppbpb.gov.in",
            status = "OPEN",
            isFeatured = false,
            isEnabled = true
        ),
        ExamFormEntity(
            id = "form-upsc-cse",
            title = "UPSC Civil Services Examination (Prelims)",
            organization = "Union Public Service Commission",
            category = "Central Government",
            startDate = "February 2026",
            lastDate = "March 2026",
            examDate = "May 2026",
            admitCardDate = "3 Weeks Prior to Exam",
            resultDate = "July 2026",
            eligibility = "Degree from a recognized Indian University",
            ageLimit = "21 - 32 Years (6 Attempts for General)",
            minAge = 21,
            maxAge = 32,
            applicationFee = "Gen/OBC/EWS: ₹100 | Female/SC/ST/PwBD: Nil",
            requiredDocuments = "OTR Profile Photo (White background, taken within 10 days)||Signature in black ink||Valid Photo ID Card (Aadhaar / Passport / PAN)",
            officialNotificationUrl = "https://upsconline.nic.in",
            officialApplyUrl = "https://upsconline.nic.in",
            status = "ADMIT_CARD_OUT",
            isFeatured = true,
            isEnabled = true
        ),
        ExamFormEntity(
            id = "form-nsp-scholarship",
            title = "National Scholarship Portal (NSP) Post-Matric",
            organization = "Ministry of Electronics & IT, Government of India",
            category = "Scholarship",
            startDate = "July 2026",
            lastDate = "October 2026",
            examDate = "Merit Based Verification",
            admitCardDate = "N/A",
            resultDate = "December 2026",
            eligibility = "Students enrolled in recognized secondary / higher education",
            ageLimit = "As per educational admission standards",
            minAge = 15,
            maxAge = 35,
            applicationFee = "Free of Cost (Zero Fee)",
            requiredDocuments = "Aadhaar Card / Enrolment ID||Bank Account Passbook (Linked to Aadhaar)||Income Certificate||Previous Year Marksheet||Bonafide Student Certificate",
            officialNotificationUrl = "https://scholarships.gov.in",
            officialApplyUrl = "https://scholarships.gov.in",
            status = "OPEN",
            isFeatured = true,
            isEnabled = true
        )
    )

    fun getDefaultBanners(): List<AppBannerEntity> = listOf(
        AppBannerEntity(
            id = "ban-photo-studio",
            title = "DIGI NOVA Photo Studio",
            subtitle = "Passport photos, A4 multi-photo sheets & clean signature crops - 100% offline inside the app",
            actionType = "NAVIGATE",
            actionTarget = "photo_studio",
            tag = "PHOTO TOOLS",
            isEnabled = true,
            sortOrder = 1
        ),
        AppBannerEntity(
            id = "ban-forms-exams",
            title = "Exam & Government Forms Hub",
            subtitle = "SSC, Railway, Banking, Police & UPSC dates, document guidelines and official portals",
            actionType = "NAVIGATE",
            actionTarget = "forms_and_exams",
            tag = "GOV FORMS",
            isEnabled = true,
            sortOrder = 2
        ),
        AppBannerEntity(
            id = "ban-tools",
            title = "Everyday Tools & Calculators",
            subtitle = "Age Calculator, EMI, GST, Unit Converters, QR Generator and more - Instant & Local",
            actionType = "NAVIGATE",
            actionTarget = "everyday_tools",
            tag = "UTILITIES",
            isEnabled = true,
            sortOrder = 3
        )
    )

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
        dao.insertUser(getDefaultPublicUser())
        dao.insertExamForms(getDefaultExamForms())
        dao.insertBanners(getDefaultBanners())
        dao.insertAuditLog(getInitialAuditLog())
    }
}
