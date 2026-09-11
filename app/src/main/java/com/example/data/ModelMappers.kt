package com.example.data

import com.example.data.local.*
import com.example.model.*

fun ServiceEntity.toModel(): DigitalService = DigitalService(
    id = id,
    title = title,
    tagline = tagline,
    category = category,
    description = description,
    iconType = iconType,
    features = getFeatures(),
    benefits = getBenefits(),
    deliveryTime = deliveryTime,
    pricing = pricing,
    isFeatured = isFeatured,
    isPopular = isPopular,
    isEnabled = isEnabled,
    badge = badge,
    sortOrder = sortOrder
)

fun DigitalService.toEntity(): ServiceEntity = ServiceEntity(
    id = id,
    title = title,
    tagline = tagline,
    category = category,
    description = description,
    iconType = iconType,
    featuresString = features.joinToString("||"),
    benefitsString = benefits.joinToString("||"),
    deliveryTime = deliveryTime,
    pricing = pricing,
    isFeatured = isFeatured,
    isPopular = isPopular,
    isEnabled = isEnabled,
    badge = badge,
    sortOrder = sortOrder
)

fun OnlineToolEntity.toModel(): OnlineService = OnlineService(
    id = id,
    title = title,
    category = category,
    description = description,
    url = url,
    iconType = iconType,
    badge = badge,
    isPopular = isPopular,
    isEnabled = isEnabled,
    sortOrder = sortOrder
)

fun OnlineService.toEntity(): OnlineToolEntity = OnlineToolEntity(
    id = id,
    title = title,
    category = category,
    description = description,
    url = url,
    iconType = iconType,
    badge = badge,
    isPopular = isPopular,
    isEnabled = isEnabled,
    sortOrder = sortOrder
)

fun TipEntity.toModel(): UsefulTip = UsefulTip(
    id = id,
    title = title,
    category = category,
    summary = summary,
    fullContent = fullContent,
    readTimeMinutes = readTimeMinutes,
    isFeatured = isFeatured,
    isPublished = isPublished,
    keyTakeaways = getKeyTakeaways(),
    tags = getTags(),
    sortOrder = sortOrder
)

fun UsefulTip.toEntity(): TipEntity = TipEntity(
    id = id,
    title = title,
    category = category,
    summary = summary,
    fullContent = fullContent,
    readTimeMinutes = readTimeMinutes,
    isFeatured = isFeatured,
    isPublished = isPublished,
    keyTakeawaysString = keyTakeaways.joinToString("||"),
    tagsString = tags.joinToString("||"),
    sortOrder = sortOrder
)

fun UpdateEntity.toModel(): LatestUpdate = LatestUpdate(
    id = id,
    title = title,
    category = category,
    date = date,
    summary = summary,
    fullArticle = fullArticle,
    readTime = readTime,
    badge = badge,
    author = author,
    isPublished = isPublished,
    timestamp = timestamp
)

fun LatestUpdate.toEntity(): UpdateEntity = UpdateEntity(
    id = id,
    title = title,
    category = category,
    date = date,
    summary = summary,
    fullArticle = fullArticle,
    readTime = readTime,
    badge = badge,
    author = author,
    isPublished = isPublished,
    timestamp = timestamp
)

fun NotificationEntity.toModel(): AppNotification = AppNotification(
    id = id,
    title = title,
    message = message,
    date = date,
    priority = priority,
    isPublished = isPublished,
    timestamp = timestamp
)

fun AppNotification.toEntity(): NotificationEntity = NotificationEntity(
    id = id,
    title = title,
    message = message,
    date = date,
    priority = priority,
    isPublished = isPublished,
    timestamp = timestamp
)

fun EnquiryEntity.toModel(): CustomerEnquiry = CustomerEnquiry(
    id = id,
    customerName = customerName,
    customerPhone = customerPhone,
    customerEmail = customerEmail,
    serviceId = serviceId,
    serviceTitle = serviceTitle,
    message = message,
    status = status,
    createdAt = createdAt
)

fun Map<String, String>.toCompanyContact(): CompanyContact = CompanyContact(
    appName = get("brand_name") ?: "DIGI NOVA",
    tagline = get("tagline") ?: "DIGITAL • SIMPLE • SMART",
    ownerName = get("owner_name") ?: "Anup Digi Nova",
    developerName = get("developer_name") ?: "Anup Digi Nova",
    copyright = get("copyright") ?: "© 2026 Anup Digi Nova. All Rights Reserved.",
    companyBio = get("about_text") ?: "DIGI NOVA is your premier modern digital partner. Founded and developed by Anup Digi Nova, we deliver high-impact digital services, curated online tools, practical tech tips, and continuous industry updates to power up your business and everyday digital life.",
    supportWhatsAppNumber = get("whatsapp_number") ?: "+15550198421",
    supportPhoneNumber = get("phone_number") ?: "+1 555-019-8421",
    supportEmail = get("support_email") ?: "contact@diginova.io",
    websiteUrl = get("website_url") ?: "https://diginova.io",
    address = get("address") ?: "Nova Innovation Tower, Silicon Valley, CA",
    workingHours = get("working_hours") ?: "24/7 Digital Support & Consultation",
    version = get("app_version") ?: "3.0.0 Enterprise",
    privacyPolicy = get("privacy_policy") ?: "DIGI NOVA is committed to protecting your privacy. All customer data is handled confidentially and never shared with third parties.",
    termsConditions = get("terms_conditions") ?: "By accessing DIGI NOVA services, you agree to our terms and conditions.",
    isMaintenanceMode = (get("maintenance_mode") ?: "false").toBoolean(),
    maintenanceMessage = get("maintenance_message") ?: "DIGI NOVA is undergoing maintenance."
)
