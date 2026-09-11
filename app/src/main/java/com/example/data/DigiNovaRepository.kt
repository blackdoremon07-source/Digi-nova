package com.example.data

import com.example.model.CompanyContact
import com.example.model.DigitalService
import com.example.model.LatestUpdate
import com.example.model.OnlineService
import com.example.model.UsefulTip

/**
 * Central Data Repository for DIGI NOVA
 * Clean, structured, and easy to edit or connect to an API in the future.
 */
object DigiNovaRepository {

    val contactInfo = CompanyContact()

    val digitalServices: List<DigitalService> = listOf(
        DigitalService(
            id = "ds-web-dev",
            title = "Modern Web & App Development",
            tagline = "Blazing fast, responsive web apps and native Android solutions.",
            category = "Development",
            iconType = "code",
            description = "Empower your organization with custom, scalable web architectures and native mobile applications. Built using Kotlin, Compose, React, and modern microservices with 99.9% uptime and enterprise security.",
            features = listOf(
                "Native Android & Cross-Platform UI",
                "Next.js & Cloud-Optimized Backends",
                "Automated CI/CD Pipeline Deployment",
                "High SEO & Core Web Vitals Performance",
                "End-to-End Encryption & Security Audits"
            ),
            benefits = listOf(
                "Up to 3x faster page load speeds",
                "Mobile-first customer experience",
                "Zero downtime deployment architecture"
            ),
            deliveryTime = "2 - 4 Weeks",
            pricing = "Custom Quote",
            isFeatured = true,
            badge = "Popular"
        ),
        DigitalService(
            id = "ds-ui-ux",
            title = "UI/UX & Product Design",
            tagline = "Intuitive user journeys and futuristic design systems.",
            category = "Design",
            iconType = "palette",
            description = "We craft visually captivating user interfaces, wireframes, and high-fidelity interactive prototypes that convert visitors into loyal customers. Designed adhering strictly to modern Material 3 and Apple HIG principles.",
            features = listOf(
                "Interactive Figma Prototypes & Design Tokens",
                "User Research & Journey Mapping",
                "Responsive Mobile & Tablet Viewports",
                "Design Systems & Component Libraries",
                "Micro-Animations & Interaction Specs"
            ),
            benefits = listOf(
                "Higher conversion and retention rates",
                "Consistent brand visual identity",
                "Developer-ready asset exports"
            ),
            deliveryTime = "1 - 2 Weeks",
            pricing = "Starting at $499",
            isFeatured = true,
            badge = "Top Rated"
        ),
        DigitalService(
            id = "ds-branding",
            title = "Brand Identity & Graphic Design",
            tagline = "Memorable logos, vector guidelines, and digital brand presence.",
            category = "Design",
            iconType = "brush",
            description = "Stand out in a crowded market with a distinct visual presence. From iconic logos and color psychology to typography palettes and social media brand kits.",
            features = listOf(
                "Vector Logo System (Dark & Light Variants)",
                "Full Brand Guidelines Document",
                "Typography & Color Matrix Specification",
                "Social Media Launch Kit & Banner Templates",
                "Commercial Usage Rights & Scalable Vectors"
            ),
            benefits = listOf(
                "Instant recognition across all channels",
                "Professional, cohesive look and feel",
                "Ready for print and high-res displays"
            ),
            deliveryTime = "5 - 7 Days",
            pricing = "Starting at $299",
            isFeatured = false
        ),
        DigitalService(
            id = "ds-cloud-it",
            title = "Cloud Infrastructure & Hosting",
            tagline = "Reliable cloud hosting, domain setup, and DevOps support.",
            category = "Cloud & IT",
            iconType = "cloud",
            description = "Seamless cloud architecture setup on Google Cloud, AWS, or Cloudflare. We handle domain configurations, SSL certificates, automated database backups, and serverless hosting.",
            features = listOf(
                "Custom Domain & DNS Records Setup",
                "Free SSL Certificate & Auto-Renewal",
                "Cloudflare CDN & DDoS Mitigation",
                "Daily Automated Database Backups",
                "24/7 Server Health Monitoring"
            ),
            benefits = listOf(
                "Uninterrupted business uptime",
                "Protection against cyber vulnerabilities",
                "Fast global latency for international clients"
            ),
            deliveryTime = "24 - 48 Hours",
            pricing = "Starting at $149/mo",
            isFeatured = true,
            badge = "Essential"
        ),
        DigitalService(
            id = "ds-seo-marketing",
            title = "SEO & Organic Digital Growth",
            tagline = "Reach page #1 on search engines with targeted organic strategies.",
            category = "Marketing",
            iconType = "trending_up",
            description = "Comprehensive on-page, technical, and off-page search engine optimization to dramatically elevate your search rankings and drive high-intent organic traffic.",
            features = listOf(
                "Comprehensive Technical SEO Audit",
                "Competitor & High-ROI Keyword Research",
                "Google Business Profile Optimization",
                "Speed Optimization & Schema Markup",
                "Monthly Performance & Ranking Reports"
            ),
            benefits = listOf(
                "Sustainable long-term traffic without ad spend",
                "High buyer intent inbound leads",
                "Increased brand authority"
            ),
            deliveryTime = "Ongoing Monthly",
            pricing = "Starting at $399/mo",
            isFeatured = false
        ),
        DigitalService(
            id = "ds-automation",
            title = "Custom Automation & API Workflows",
            tagline = "Automate tedious operations and connect your favorite software.",
            category = "Development",
            iconType = "auto_awesome",
            description = "Eliminate manual data entry and synchronize your customer inquiries, orders, and emails with smart Zapier, Make, and custom webhook API connections.",
            features = listOf(
                "CRM & WhatsApp Business Integration",
                "Automated Invoice & Email Notifications",
                "Custom REST/Webhook API Connectors",
                "Data Sync between Sheets and Database",
                "Error Alerting & Fallback Channels"
            ),
            benefits = listOf(
                "Save 20+ hours of manual work weekly",
                "Zero lost client leads or tickets",
                "Real-time instant client responses"
            ),
            deliveryTime = "3 - 7 Days",
            pricing = "Starting at $349",
            isFeatured = false,
            badge = "Smart"
        )
    )

    val onlineServices: List<OnlineService> = listOf(
        OnlineService(
            id = "os-speedtest",
            title = "Network Speed & Ping Test",
            category = "Utilities",
            description = "Measure your current download speed, upload bandwidth, and latency with real-time accuracy.",
            url = "https://fast.com",
            iconType = "speed",
            badge = "Speed",
            isPopular = true
        ),
        OnlineService(
            id = "os-qr-generator",
            title = "Universal QR Code Creator",
            category = "Tools",
            description = "Generate free vector QR codes for websites, Wi-Fi credentials, text notes, and contact cards.",
            url = "https://www.qr-code-generator.com",
            iconType = "qr_code",
            badge = "Instant",
            isPopular = true
        ),
        OnlineService(
            id = "os-pdf-toolkit",
            title = "PDF Documents Toolkit",
            category = "Productivity",
            description = "Merge multiple PDFs, compress large documents, convert Word/Images to PDF securely.",
            url = "https://www.ilovepdf.com",
            iconType = "description",
            badge = "Essential",
            isPopular = true
        ),
        OnlineService(
            id = "os-breach-check",
            title = "Security & Data Breach Checker",
            category = "Security",
            description = "Check whether your personal email address or passwords have been exposed in known data compromises.",
            url = "https://haveibeenpwned.com",
            iconType = "security",
            badge = "Security",
            isPopular = true
        ),
        OnlineService(
            id = "os-file-transfer",
            title = "Secure Cloud File Transfer",
            category = "Productivity",
            description = "Send large files up to 2GB to anyone worldwide quickly without requiring account registration.",
            url = "https://wetransfer.com",
            iconType = "cloud_upload",
            badge = "Free"
        ),
        OnlineService(
            id = "os-currency-converter",
            title = "Live Currency & FX Rates",
            category = "Finance",
            description = "Real-time mid-market foreign exchange rates and currency conversion calculator for 130+ currencies.",
            url = "https://www.xe.com/currencyconverter",
            iconType = "currency_exchange",
            badge = "Live"
        ),
        OnlineService(
            id = "os-ip-lookup",
            title = "IP Address & Geo Diagnostics",
            category = "Utilities",
            description = "Discover your public IP address, ISP provider, DNS routing, and location diagnostics.",
            url = "https://whatismyipaddress.com",
            iconType = "router",
            badge = "Diagnostics"
        ),
        OnlineService(
            id = "os-url-shortener",
            title = "Smart URL Shortener",
            category = "Tools",
            description = "Condense lengthy web addresses into clean, memorable links with click tracking analytics.",
            url = "https://tinyurl.com",
            iconType = "link",
            badge = "Tool"
        )
    )

    val usefulTips: List<UsefulTip> = listOf(
        UsefulTip(
            id = "tip-mobile-security",
            title = "Top 7 Mobile Security Best Practices in 2026",
            category = "Security",
            summary = "Protect your mobile banking, sensitive apps, and personal conversations from modern cyber threats.",
            fullContent = """
                With smartphones handling our financial transactions and personal identity, staying vigilant is non-negotiable:
                
                1. Enable App-Level Biometric Locks: Lock banking, WhatsApp, and email apps behind fingerprint or facial biometric authentication.
                2. Audit Permissions Regularly: Review camera, microphone, and location permissions under Android Settings > Privacy. Never grant background location unless strictly necessary.
                3. Beware of Sideloading APKs: Only install applications from official and verified sources. Sideloading untrusted packages risks root-level spyware.
                4. Disable Automatic Wi-Fi Connection: Prevent your phone from silently connecting to rogue open public Wi-Fi access points.
                5. Use Passkeys & Hardware 2FA: Migrate away from SMS verification codes which are vulnerable to SIM swapping.
                6. Keep System Security Patches Updated: Install monthly Google Play system updates immediately.
                7. Set Up Remote Wipe: Configure Android 'Find My Device' beforehand so you can wipe credentials if a device is misplaced.
            """.trimIndent(),
            readTimeMinutes = 3,
            isFeatured = true,
            keyTakeaways = listOf(
                "Never share SMS OTPs with anyone claiming to be bank support.",
                "Review permissions quarterly for unused apps.",
                "Always enable Passkeys or Authenticator apps over SMS."
            ),
            tags = listOf("Security", "Privacy", "Android", "Passkeys")
        ),
        UsefulTip(
            id = "tip-battery-life",
            title = "How to Maximize Smartphone Battery Longevity",
            category = "Hardware",
            summary = "Scientific techniques to preserve lithium-ion battery health and prevent rapid battery degradation.",
            fullContent = """
                Modern smartphone batteries degrade through heat and extreme charging cycles. Here is how to keep your battery healthy for 3+ years:
                
                1. The 20%-80% Rule: Lithium batteries experience the highest stress above 80% and below 20%. Where possible, keep your daily charging cycle within this optimal band.
                2. Avoid Intensive Gaming While Charging: Simultaneous fast-charging and GPU heat leads to accelerated thermal degradation.
                3. Turn On Adaptive Battery: Enable Android's adaptive battery settings, which automatically restricts CPU background wake locks for rarely used apps.
                4. Utilize Dark Mode on OLED Screens: AMOLED screens turn off individual pixels for pure black, saving up to 30% display power.
                5. Avoid Direct Sunlight & Heat: Never leave your phone on a car dashboard under direct sun.
            """.trimIndent(),
            readTimeMinutes = 2,
            isFeatured = true,
            keyTakeaways = listOf(
                "Heat is the primary enemy of lithium battery lifespan.",
                "Deep discharges to 0% cause chemical wear.",
                "Dark mode on AMOLED screens provides measurable energy savings."
            ),
            tags = listOf("Battery", "Hardware", "Performance", "Optimization")
        ),
        UsefulTip(
            id = "tip-phishing-prevention",
            title = "How to Spot Scam Links & SMS Phishing (Smishing)",
            category = "Security",
            summary = "Identify subtle red flags in fake delivery, tax refund, and account suspension notifications.",
            fullContent = """
                Scammers frequently impersonate courier delivery services, electric utilities, and banks. Learn to detect malicious links in seconds:
                
                1. Check the Domain Suffix Carefully: Look closely at the URL. Real domains are 'company.com', while scammers use deceptive variations like 'company-account-verify.xyz'.
                2. Notice Artificial Urgency: Scammers use phrases like 'Account suspended within 2 hours' or 'Package will be returned today' to trigger panic.
                3. Legitimate Banks Never Ask for Passwords: Official institutions never ask for your PIN, CVV, or passwords via SMS links.
                4. Verify Directly: Instead of clicking the link in the message, open your browser independently and navigate to the official website.
            """.trimIndent(),
            readTimeMinutes = 3,
            isFeatured = false,
            keyTakeaways = listOf(
                "Inspect the real domain root before clicking.",
                "High urgency and threatening language is the #1 scam signal.",
                "Open apps directly rather than following text links."
            ),
            tags = listOf("Scam Prevention", "Phishing", "Safety", "Online")
        ),
        UsefulTip(
            id = "tip-wifi-speedup",
            title = "5 Quick Ways to Speed Up Slow Wi-Fi at Home",
            category = "Connectivity",
            summary = "Simple network optimizations to eliminate dead zones and latency lag during streaming and video calls.",
            fullContent = """
                Experiencing buffering or dropped calls? Try these proven adjustments before upgrading your internet plan:
                
                1. Switch to the 5GHz or 6GHz Band: 2.4GHz is crowded with microwaves and bluetooth. The 5GHz band offers drastically higher throughput and lower ping.
                2. Elevate Your Router: Place your Wi-Fi router on an open shelf or desk, rather than behind the TV or inside closed cabinets.
                3. Update DNS to Cloudflare or Google: Change your router or phone DNS to 1.1.1.1 (Cloudflare) or 8.8.8.8 (Google) for faster domain resolution.
                4. Restart the Modem Monthly: Clears stale memory caches and forces re-negotiation of the cleanest spectrum channel with your ISP.
                5. Check for Bandwidth Hogs: Verify no background cloud backups or game updates are monopolizing upload bandwidth.
            """.trimIndent(),
            readTimeMinutes = 2,
            isFeatured = false,
            keyTakeaways = listOf(
                "5GHz bands offer faster speeds with less interference.",
                "Router placement in the center of the home avoids signal dead zones.",
                "Fast DNS servers speed up initial page loading times."
            ),
            tags = listOf("Wi-Fi", "Networking", "Speed", "Productivity")
        ),
        UsefulTip(
            id = "tip-cloud-storage",
            title = "Declutter Cloud Storage & Never Pay for Extra Tiers",
            category = "Productivity",
            summary = "Clean up gigabytes of junk emails, duplicate photos, and cached backups in under 10 minutes.",
            fullContent = """
                Cloud storage accounts often fill up unexpectedly. Here is how to reclaim free space easily:
                
                1. Filter Large Attachments in Mail: In Gmail/Outlook, search 'has:attachment larger:10M' and delete outdated PDF receipts or video clips.
                2. Delete WhatsApp Cloud Backups of Videos: WhatsApp videos consume most Google Drive quotas. Disable video backup under WhatsApp Settings > Chats > Chat Backup.
                3. Clean the Trash Bin: Deleted files often stay in the 'Trash' folder consuming your quota for 30 days unless emptied manually.
                4. Check Google Photos Storage Saver: Convert uncompressed raw photos to high-quality storage saver format.
            """.trimIndent(),
            readTimeMinutes = 2,
            isFeatured = false,
            keyTakeaways = listOf(
                "Search for large email attachments to recover quick gigabytes.",
                "Exclude heavy video formats from chat backups.",
                "Remember to permanently empty the cloud trash bin."
            ),
            tags = listOf("Cloud", "Storage", "Productivity", "Tips")
        )
    )

    val latestUpdates: List<LatestUpdate> = listOf(
        LatestUpdate(
            id = "up-diginova-release",
            title = "DIGI NOVA 2.4 Released: Lightning Fast Experience",
            category = "App Update",
            date = "September 2026",
            summary = "Explore the new interface, enhanced online tools portal, instant WhatsApp support, and dark mode aesthetic.",
            fullArticle = """
                We are thrilled to unveil DIGI NOVA 2.4, engineered from the ground up for superior responsiveness and ease of use.
                
                Key Highlights:
                • Unified Digital Services Hub: Browse our full range of web development, design, and automation services with instant WhatsApp quoting.
                • Curated Online Utilities: One-tap access to speed tests, document converters, and cybersecurity diagnostics.
                • Pro Tech Tips: Actionable guides curated by our engineering team to keep your devices secure and operating at peak performance.
                • Modern Android Architecture: Fully compatible with modern Android edge-to-edge screens, ultra-fluid 120Hz scrolling, and low memory footprint.
                
                Thank you for choosing DIGI NOVA as your trusted digital companion!
            """.trimIndent(),
            readTime = "2 min read",
            badge = "Major"
        ),
        LatestUpdate(
            id = "up-ai-services",
            title = "AI-Powered Workflow Automation Added to Services",
            category = "Services",
            date = "September 2026",
            summary = "Businesses can now deploy custom smart agents and automated response engines for customer inquiries.",
            fullArticle = """
                DIGI NOVA is proud to announce our new AI Workflow Automation suite for forward-thinking organizations.
                
                Our specialized services now include:
                - Intelligent customer support routing on WhatsApp and Web
                - Automated document processing and receipt parsing
                - CRM synchronization without requiring complex coding
                
                Reach out to our specialists via the Digital Services tab to request an initial consultation!
            """.trimIndent(),
            readTime = "3 min read",
            badge = "New"
        ),
        LatestUpdate(
            id = "up-security-notice",
            title = "Cybersecurity Alert: New Banking Trojan Variants Detected",
            category = "Security",
            date = "August 2026",
            summary = "Advisory from our security team regarding fake invoice attachments and credential harvesting campaigns.",
            fullArticle = """
                Security researchers have identified new spear-phishing campaigns targeting smartphone users through deceptive parcel delivery and unpaid utility notices.
                
                Our Recommendations:
                • Do not click links received from unknown shortcodes.
                • Verify all payment requests via official banking applications.
                • Keep Google Play Protect turned on at all times.
                
                Read our Useful Tips section for step-by-step guidance on keeping your credentials safe.
            """.trimIndent(),
            readTime = "2 min read",
            badge = "Alert"
        ),
        LatestUpdate(
            id = "up-5g-connectivity",
            title = "Next-Generation High-Speed Mobile Networks Expanding",
            category = "Tech News",
            date = "August 2026",
            summary = "Telecom providers report over 90% population coverage for 5G Standalone networks, bringing gigabit speeds.",
            fullArticle = """
                The transition towards pure 5G Standalone (SA) infrastructure has achieved major milestones, resulting in lower latencies under 15ms and download speeds frequently exceeding 600 Mbps on compatible smartphones.
                
                Use the 'Network Speed & Ping Test' in our Online Services tab to verify your carrier's current latency and bandwidth!
            """.trimIndent(),
            readTime = "1 min read"
        )
    )
}
