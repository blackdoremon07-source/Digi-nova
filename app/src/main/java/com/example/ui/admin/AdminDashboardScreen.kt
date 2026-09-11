package com.example.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.local.*
import com.example.model.*
import com.example.ui.components.EmptyStateView
import com.example.ui.components.getServiceIcon
import com.example.ui.theme.*

enum class AdminTab(val label: String, val icon: ImageVector) {
    OVERVIEW("Overview", Icons.Default.Dashboard),
    SERVICES("Services", Icons.Default.Devices),
    ENQUIRIES("Enquiries", Icons.Default.MarkEmailRead),
    TOOLS("Online Tools", Icons.Default.Language),
    TIPS("Tips", Icons.Default.Lightbulb),
    UPDATES("Updates", Icons.Default.Campaign),
    NOTIFICATIONS("Alerts", Icons.Default.Notifications),
    SETTINGS("Settings", Icons.Default.Settings),
    AUDIT("Audit Log", Icons.Default.History),
    PROFILE("Profile", Icons.Default.Person)
}

@Composable
fun AdminDashboardScreen(
    adminUsername: String,
    adminEmail: String,
    services: List<ServiceEntity>,
    onlineTools: List<OnlineToolEntity>,
    tips: List<TipEntity>,
    updates: List<UpdateEntity>,
    notifications: List<NotificationEntity>,
    enquiries: List<EnquiryEntity>,
    pendingEnquiriesCount: Int,
    settings: Map<String, String>,
    auditLogs: List<AuditLogEntity>,
    onSaveService: (ServiceEntity) -> Unit,
    onDeleteService: (ServiceEntity) -> Unit,
    onToggleService: (ServiceEntity) -> Unit,
    onSaveOnlineTool: (OnlineToolEntity) -> Unit,
    onDeleteOnlineTool: (OnlineToolEntity) -> Unit,
    onSaveTip: (TipEntity) -> Unit,
    onDeleteTip: (TipEntity) -> Unit,
    onSaveUpdate: (UpdateEntity) -> Unit,
    onDeleteUpdate: (UpdateEntity) -> Unit,
    onSaveNotification: (NotificationEntity) -> Unit,
    onDeleteNotification: (NotificationEntity) -> Unit,
    onUpdateEnquiryStatus: (id: String, status: String) -> Unit,
    onDeleteEnquiry: (id: String) -> Unit,
    onCallCustomer: (phone: String) -> Unit,
    onWhatsAppCustomer: (phone: String, name: String, service: String) -> Unit,
    onSaveSetting: (key: String, value: String) -> Unit,
    onSaveBatchSettings: (Map<String, String>) -> Unit,
    onChangePassword: (currentPass: String, newPass: String, onResult: (Boolean, String?) -> Unit) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(AdminTab.OVERVIEW) }

    // Dialog States
    var editingService by remember { mutableStateOf<ServiceEntity?>(null) }
    var isAddingService by remember { mutableStateOf(false) }

    var editingTool by remember { mutableStateOf<OnlineToolEntity?>(null) }
    var isAddingTool by remember { mutableStateOf(false) }

    var editingTip by remember { mutableStateOf<TipEntity?>(null) }
    var isAddingTip by remember { mutableStateOf(false) }

    var editingUpdate by remember { mutableStateOf<UpdateEntity?>(null) }
    var isAddingUpdate by remember { mutableStateOf(false) }

    var editingNotification by remember { mutableStateOf<NotificationEntity?>(null) }
    var isAddingNotification by remember { mutableStateOf(false) }

    var selectedEnquiryForDetails by remember { mutableStateOf<EnquiryEntity?>(null) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }

    // Delete confirmation state
    var deleteConfirmationPrompt by remember { mutableStateOf<Pair<String, () -> Unit>?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NovaDarkNavy)
    ) {
        // --- ADMIN TOP BAR ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(NovaNavySurface)
                .border(1.dp, NovaBorder)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF22C55E))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "DIGI NOVA ADMIN",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = NovaCyan
                    )
                }
                Text(
                    text = "Owner: $adminUsername • Live Database Sync",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextDim,
                    fontSize = 11.sp
                )
            }

            IconButton(
                onClick = onLogout,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(NovaNavyCardElevated)
                    .border(1.dp, Color(0x40EF4444), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Logout",
                    tint = Color(0xFFFCA5A5),
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // --- TAB NAVIGATION BAR ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(NovaNavyCard)
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AdminTab.values().forEach { tab ->
                val isSelected = tab == selectedTab
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) NovaCyan else NovaNavyCardElevated)
                        .border(1.dp, if (isSelected) NovaCyan else NovaBorder, RoundedCornerShape(12.dp))
                        .clickable { selectedTab = tab }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .testTag("admin_tab_${tab.name.lowercase()}"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = null,
                            tint = if (isSelected) Color(0xFF040711) else TextWhite,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = tab.label,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color(0xFF040711) else TextWhite
                        )
                        if (tab == AdminTab.ENQUIRIES && pendingEnquiriesCount > 0) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(if (isSelected) Color(0xFF040711) else Color(0xFFEF4444))
                                    .padding(horizontal = 6.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = pendingEnquiriesCount.toString(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSelected) NovaCyan else Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // --- MAIN TAB CONTENT ROUTER ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when (selectedTab) {
                AdminTab.OVERVIEW -> {
                    AdminOverviewTab(
                        servicesCount = services.size,
                        activeServicesCount = services.count { it.isEnabled },
                        toolsCount = onlineTools.size,
                        tipsCount = tips.size,
                        updatesCount = updates.size,
                        enquiriesCount = enquiries.size,
                        pendingEnquiriesCount = pendingEnquiriesCount,
                        notificationsCount = notifications.size,
                        ownerName = settings["owner_name"] ?: "Anup Digi Nova",
                        brandName = settings["brand_name"] ?: "DIGI NOVA",
                        onNavigateTab = { selectedTab = it }
                    )
                }

                AdminTab.SERVICES -> {
                    AdminServicesTab(
                        services = services,
                        onAddClick = { isAddingService = true },
                        onEditClick = { editingService = it },
                        onToggleStatus = onToggleService,
                        onDeleteClick = { service ->
                            deleteConfirmationPrompt = "Delete service '${service.title}'?" to {
                                onDeleteService(service)
                            }
                        }
                    )
                }

                AdminTab.ENQUIRIES -> {
                    AdminEnquiriesTab(
                        enquiries = enquiries,
                        onSelectEnquiry = { selectedEnquiryForDetails = it },
                        onDeleteEnquiry = { id ->
                            deleteConfirmationPrompt = "Delete this customer enquiry record?" to {
                                onDeleteEnquiry(id)
                            }
                        },
                        onCall = onCallCustomer,
                        onWhatsApp = onWhatsAppCustomer
                    )
                }

                AdminTab.TOOLS -> {
                    AdminToolsTab(
                        tools = onlineTools,
                        onAddClick = { isAddingTool = true },
                        onEditClick = { editingTool = it },
                        onDeleteClick = { tool ->
                            deleteConfirmationPrompt = "Delete online tool '${tool.title}'?" to {
                                onDeleteOnlineTool(tool)
                            }
                        }
                    )
                }

                AdminTab.TIPS -> {
                    AdminTipsTab(
                        tips = tips,
                        onAddClick = { isAddingTip = true },
                        onEditClick = { editingTip = it },
                        onDeleteClick = { tip ->
                            deleteConfirmationPrompt = "Delete tip '${tip.title}'?" to {
                                onDeleteTip(tip)
                            }
                        }
                    )
                }

                AdminTab.UPDATES -> {
                    AdminUpdatesTab(
                        updates = updates,
                        onAddClick = { isAddingUpdate = true },
                        onEditClick = { editingUpdate = it },
                        onDeleteClick = { upd ->
                            deleteConfirmationPrompt = "Delete announcement '${upd.title}'?" to {
                                onDeleteUpdate(upd)
                            }
                        }
                    )
                }

                AdminTab.NOTIFICATIONS -> {
                    AdminNotificationsTab(
                        notifications = notifications,
                        onAddClick = { isAddingNotification = true },
                        onEditClick = { editingNotification = it },
                        onDeleteClick = { notif ->
                            deleteConfirmationPrompt = "Delete notification '${notif.title}'?" to {
                                onDeleteNotification(notif)
                            }
                        }
                    )
                }

                AdminTab.SETTINGS -> {
                    AdminSettingsTab(
                        currentSettings = settings,
                        onSaveSetting = onSaveSetting,
                        onSaveBatch = onSaveBatchSettings
                    )
                }

                AdminTab.AUDIT -> {
                    AdminAuditTab(auditLogs = auditLogs)
                }

                AdminTab.PROFILE -> {
                    AdminProfileTab(
                        username = adminUsername,
                        email = adminEmail,
                        ownerName = settings["owner_name"] ?: "Anup Digi Nova",
                        copyright = settings["copyright"] ?: "© 2026 Anup Digi Nova. All Rights Reserved.",
                        onChangePasswordClick = { showChangePasswordDialog = true },
                        onLogout = onLogout
                    )
                }
            }
        }
    }

    // --- SERVICE EDIT / ADD DIALOG ---
    if (isAddingService || editingService != null) {
        val isEditing = editingService != null
        var sTitle by remember { mutableStateOf(editingService?.title ?: "") }
        var sTagline by remember { mutableStateOf(editingService?.tagline ?: "") }
        var sCategory by remember { mutableStateOf(editingService?.category ?: "Development") }
        var sPrice by remember { mutableStateOf(editingService?.pricing ?: "$199") }
        var sDelivery by remember { mutableStateOf(editingService?.deliveryTime ?: "3–5 Business Days") }
        var sDesc by remember { mutableStateOf(editingService?.description ?: "") }
        var sFeatures by remember { mutableStateOf(editingService?.featuresString?.replace("||", "\n") ?: "") }
        var sBenefits by remember { mutableStateOf(editingService?.benefitsString?.replace("||", "\n") ?: "") }
        var sBadge by remember { mutableStateOf(editingService?.badge ?: "") }
        var sIsFeatured by remember { mutableStateOf(editingService?.isFeatured ?: false) }
        var sIsPopular by remember { mutableStateOf(editingService?.isPopular ?: false) }
        var sIconType by remember { mutableStateOf(editingService?.iconType ?: "code") }

        Dialog(onDismissRequest = {
            isAddingService = false
            editingService = null
        }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f),
                colors = CardDefaults.cardColors(containerColor = NovaNavyCardElevated),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = if (isEditing) "Edit Service" else "Add New Service",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = NovaCyan
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = sTitle,
                        onValueChange = { sTitle = it },
                        label = { Text("Service Title *", color = TextMuted) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = sTagline,
                        onValueChange = { sTagline = it },
                        label = { Text("Tagline *", color = TextMuted) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = sCategory,
                            onValueChange = { sCategory = it },
                            label = { Text("Category", color = TextMuted) },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                        )
                        OutlinedTextField(
                            value = sPrice,
                            onValueChange = { sPrice = it },
                            label = { Text("Price", color = TextMuted) },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = sDelivery,
                            onValueChange = { sDelivery = it },
                            label = { Text("Delivery Time", color = TextMuted) },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                        )
                        OutlinedTextField(
                            value = sIconType,
                            onValueChange = { sIconType = it },
                            label = { Text("Icon (code, palette, cloud...)", color = TextMuted) },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = sDesc,
                        onValueChange = { sDesc = it },
                        label = { Text("Full Description", color = TextMuted) },
                        minLines = 3,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = sFeatures,
                        onValueChange = { sFeatures = it },
                        label = { Text("Features (one per line)", color = TextMuted) },
                        minLines = 3,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = sBenefits,
                        onValueChange = { sBenefits = it },
                        label = { Text("Benefits (one per line)", color = TextMuted) },
                        minLines = 2,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = sBadge,
                        onValueChange = { sBadge = it },
                        label = { Text("Badge (e.g. POPULAR, NEW)", color = TextMuted) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Featured Service", color = TextWhite)
                        Switch(checked = sIsFeatured, onCheckedChange = { sIsFeatured = it })
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Popular Service", color = TextWhite)
                        Switch(checked = sIsPopular, onCheckedChange = { sIsPopular = it })
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                isAddingService = false
                                editingService = null
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel", color = TextMuted)
                        }

                        Button(
                            onClick = {
                                if (sTitle.isNotBlank()) {
                                    val featuresFormatted = sFeatures.split("\n").filter { it.isNotBlank() }.joinToString("||")
                                    val benefitsFormatted = sBenefits.split("\n").filter { it.isNotBlank() }.joinToString("||")
                                    val newService = ServiceEntity(
                                        id = editingService?.id ?: ("srv-" + System.currentTimeMillis().toString().takeLast(6)),
                                        title = sTitle.trim(),
                                        tagline = sTagline.trim(),
                                        category = sCategory.trim(),
                                        description = sDesc.trim(),
                                        iconType = sIconType.trim(),
                                        featuresString = featuresFormatted,
                                        benefitsString = benefitsFormatted,
                                        deliveryTime = sDelivery.trim(),
                                        pricing = sPrice.trim(),
                                        isFeatured = sIsFeatured,
                                        isPopular = sIsPopular,
                                        isEnabled = editingService?.isEnabled ?: true,
                                        badge = sBadge.ifBlank { null },
                                        sortOrder = editingService?.sortOrder ?: 0
                                    )
                                    onSaveService(newService)
                                    isAddingService = false
                                    editingService = null
                                }
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = NovaCyan)
                        ) {
                            Text("Save Service", color = Color(0xFF040711), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // --- ONLINE TOOL ADD/EDIT DIALOG ---
    if (isAddingTool || editingTool != null) {
        val isEditing = editingTool != null
        var tTitle by remember { mutableStateOf(editingTool?.title ?: "") }
        var tCategory by remember { mutableStateOf(editingTool?.category ?: "Tools") }
        var tUrl by remember { mutableStateOf(editingTool?.url ?: "https://") }
        var tDesc by remember { mutableStateOf(editingTool?.description ?: "") }
        var tBadge by remember { mutableStateOf(editingTool?.badge ?: "") }
        var tIcon by remember { mutableStateOf(editingTool?.iconType ?: "link") }

        Dialog(onDismissRequest = {
            isAddingTool = false
            editingTool = null
        }) {
            Card(
                colors = CardDefaults.cardColors(containerColor = NovaNavyCardElevated),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (isEditing) "Edit Online Tool" else "Add Online Tool",
                        style = MaterialTheme.typography.titleLarge,
                        color = NovaCyan,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = tTitle,
                        onValueChange = { tTitle = it },
                        label = { Text("Tool Title", color = TextMuted) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tUrl,
                        onValueChange = { tUrl = it },
                        label = { Text("Destination HTTPS URL", color = TextMuted) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = tCategory,
                            onValueChange = { tCategory = it },
                            label = { Text("Category", color = TextMuted) },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = tBadge,
                            onValueChange = { tBadge = it },
                            label = { Text("Badge", color = TextMuted) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tDesc,
                        onValueChange = { tDesc = it },
                        label = { Text("Description", color = TextMuted) },
                        minLines = 2,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { isAddingTool = false; editingTool = null },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel", color = TextMuted)
                        }
                        Button(
                            onClick = {
                                if (tTitle.isNotBlank() && tUrl.isNotBlank()) {
                                    val tool = OnlineToolEntity(
                                        id = editingTool?.id ?: ("tool-" + System.currentTimeMillis().toString().takeLast(6)),
                                        title = tTitle.trim(),
                                        category = tCategory.trim(),
                                        description = tDesc.trim(),
                                        url = tUrl.trim(),
                                        iconType = tIcon.trim(),
                                        badge = tBadge.ifBlank { null },
                                        isEnabled = editingTool?.isEnabled ?: true,
                                        isPopular = editingTool?.isPopular ?: false
                                    )
                                    onSaveOnlineTool(tool)
                                    isAddingTool = false
                                    editingTool = null
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = NovaCyan),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Save Tool", color = Color(0xFF040711), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // --- TIP ADD/EDIT DIALOG ---
    if (isAddingTip || editingTip != null) {
        val isEditing = editingTip != null
        var tipTitle by remember { mutableStateOf(editingTip?.title ?: "") }
        var tipCategory by remember { mutableStateOf(editingTip?.category ?: "Security") }
        var tipSummary by remember { mutableStateOf(editingTip?.summary ?: "") }
        var tipFullContent by remember { mutableStateOf(editingTip?.fullContent ?: "") }
        var tipTakeaways by remember { mutableStateOf(editingTip?.keyTakeawaysString?.replace("||", "\n") ?: "") }
        var tipTags by remember { mutableStateOf(editingTip?.tagsString?.replace("||", ", ") ?: "") }
        var tipReadTime by remember { mutableStateOf(editingTip?.readTimeMinutes?.toString() ?: "3") }
        var tipIsPublished by remember { mutableStateOf(editingTip?.isPublished ?: true) }

        Dialog(onDismissRequest = { isAddingTip = false; editingTip = null }) {
            Card(
                colors = CardDefaults.cardColors(containerColor = NovaNavyCardElevated),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = if (isEditing) "Edit Tech Tip" else "Add Tech Tip",
                        style = MaterialTheme.typography.titleLarge,
                        color = NovaCyan,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = tipTitle,
                        onValueChange = { tipTitle = it },
                        label = { Text("Tip Title", color = TextMuted) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = tipCategory,
                            onValueChange = { tipCategory = it },
                            label = { Text("Category", color = TextMuted) },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = tipReadTime,
                            onValueChange = { tipReadTime = it },
                            label = { Text("Read (mins)", color = TextMuted) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tipSummary,
                        onValueChange = { tipSummary = it },
                        label = { Text("Summary", color = TextMuted) },
                        minLines = 2,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tipFullContent,
                        onValueChange = { tipFullContent = it },
                        label = { Text("Full Instructions / Article", color = TextMuted) },
                        minLines = 4,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tipTakeaways,
                        onValueChange = { tipTakeaways = it },
                        label = { Text("Key Takeaways (one per line)", color = TextMuted) },
                        minLines = 2,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tipTags,
                        onValueChange = { tipTags = it },
                        label = { Text("Tags (comma-separated)", color = TextMuted) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Published Status", color = TextWhite)
                        Switch(checked = tipIsPublished, onCheckedChange = { tipIsPublished = it })
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(
                            onClick = { isAddingTip = false; editingTip = null },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel", color = TextMuted)
                        }
                        Button(
                            onClick = {
                                if (tipTitle.isNotBlank()) {
                                    val takeawaysFormatted = tipTakeaways.split("\n").filter { it.isNotBlank() }.joinToString("||")
                                    val tagsFormatted = tipTags.split(",").map { it.trim() }.filter { it.isNotBlank() }.joinToString("||")
                                    val tip = TipEntity(
                                        id = editingTip?.id ?: ("tip-" + System.currentTimeMillis().toString().takeLast(6)),
                                        title = tipTitle.trim(),
                                        category = tipCategory.trim(),
                                        summary = tipSummary.trim(),
                                        fullContent = tipFullContent.trim(),
                                        readTimeMinutes = tipReadTime.toIntOrNull() ?: 3,
                                        isPublished = tipIsPublished,
                                        keyTakeawaysString = takeawaysFormatted,
                                        tagsString = tagsFormatted
                                    )
                                    onSaveTip(tip)
                                    isAddingTip = false
                                    editingTip = null
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = NovaCyan),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Save Tip", color = Color(0xFF040711), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // --- UPDATE ADD/EDIT DIALOG ---
    if (isAddingUpdate || editingUpdate != null) {
        val isEditing = editingUpdate != null
        var uTitle by remember { mutableStateOf(editingUpdate?.title ?: "") }
        var uCategory by remember { mutableStateOf(editingUpdate?.category ?: "Announcement") }
        var uDate by remember { mutableStateOf(editingUpdate?.date ?: "Today") }
        var uBadge by remember { mutableStateOf(editingUpdate?.badge ?: "NEW") }
        var uAuthor by remember { mutableStateOf(editingUpdate?.author ?: "Anup Digi Nova") }
        var uSummary by remember { mutableStateOf(editingUpdate?.summary ?: "") }
        var uArticle by remember { mutableStateOf(editingUpdate?.fullArticle ?: "") }
        var uIsPublished by remember { mutableStateOf(editingUpdate?.isPublished ?: true) }

        Dialog(onDismissRequest = { isAddingUpdate = false; editingUpdate = null }) {
            Card(
                colors = CardDefaults.cardColors(containerColor = NovaNavyCardElevated),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = if (isEditing) "Edit Announcement" else "Create Announcement",
                        style = MaterialTheme.typography.titleLarge,
                        color = NovaCyan,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = uTitle,
                        onValueChange = { uTitle = it },
                        label = { Text("Title", color = TextMuted) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = uCategory,
                            onValueChange = { uCategory = it },
                            label = { Text("Category", color = TextMuted) },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = uBadge,
                            onValueChange = { uBadge = it },
                            label = { Text("Badge", color = TextMuted) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = uAuthor,
                        onValueChange = { uAuthor = it },
                        label = { Text("Author", color = TextMuted) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = uSummary,
                        onValueChange = { uSummary = it },
                        label = { Text("Summary", color = TextMuted) },
                        minLines = 2,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = uArticle,
                        onValueChange = { uArticle = it },
                        label = { Text("Full Article", color = TextMuted) },
                        minLines = 4,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Published Status", color = TextWhite)
                        Switch(checked = uIsPublished, onCheckedChange = { uIsPublished = it })
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(
                            onClick = { isAddingUpdate = false; editingUpdate = null },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel", color = TextMuted)
                        }
                        Button(
                            onClick = {
                                if (uTitle.isNotBlank()) {
                                    val update = UpdateEntity(
                                        id = editingUpdate?.id ?: ("upd-" + System.currentTimeMillis().toString().takeLast(6)),
                                        title = uTitle.trim(),
                                        category = uCategory.trim(),
                                        date = uDate.trim(),
                                        summary = uSummary.trim(),
                                        fullArticle = uArticle.trim(),
                                        readTime = "2 min read",
                                        badge = uBadge.ifBlank { null },
                                        author = uAuthor.trim(),
                                        isPublished = uIsPublished
                                    )
                                    onSaveUpdate(update)
                                    isAddingUpdate = false
                                    editingUpdate = null
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = NovaCyan),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Save Update", color = Color(0xFF040711), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // --- NOTIFICATION ADD/EDIT DIALOG ---
    if (isAddingNotification || editingNotification != null) {
        val isEditing = editingNotification != null
        var nTitle by remember { mutableStateOf(editingNotification?.title ?: "") }
        var nMessage by remember { mutableStateOf(editingNotification?.message ?: "") }
        var nPriority by remember { mutableStateOf(editingNotification?.priority ?: "NORMAL") }
        var nPublished by remember { mutableStateOf(editingNotification?.isPublished ?: true) }

        Dialog(onDismissRequest = { isAddingNotification = false; editingNotification = null }) {
            Card(
                colors = CardDefaults.cardColors(containerColor = NovaNavyCardElevated),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (isEditing) "Edit Notification Alert" else "Broadcast Notification",
                        style = MaterialTheme.typography.titleLarge,
                        color = NovaCyan,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = nTitle,
                        onValueChange = { nTitle = it },
                        label = { Text("Title", color = TextMuted) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = nMessage,
                        onValueChange = { nMessage = it },
                        label = { Text("Message", color = TextMuted) },
                        minLines = 3,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Priority: $nPriority", color = TextWhite)
                        TextButton(onClick = {
                            nPriority = if (nPriority == "NORMAL") "HIGH" else "NORMAL"
                        }) {
                            Text("Toggle Priority", color = NovaCyan)
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Published Live", color = TextWhite)
                        Switch(checked = nPublished, onCheckedChange = { nPublished = it })
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(
                            onClick = { isAddingNotification = false; editingNotification = null },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel", color = TextMuted)
                        }
                        Button(
                            onClick = {
                                if (nTitle.isNotBlank() && nMessage.isNotBlank()) {
                                    val notif = NotificationEntity(
                                        id = editingNotification?.id ?: ("notif-" + System.currentTimeMillis().toString().takeLast(6)),
                                        title = nTitle.trim(),
                                        message = nMessage.trim(),
                                        date = "Today",
                                        priority = nPriority,
                                        isPublished = nPublished
                                    )
                                    onSaveNotification(notif)
                                    isAddingNotification = false
                                    editingNotification = null
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = NovaCyan),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Broadcast", color = Color(0xFF040711), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // --- ENQUIRY DETAILS MODAL ---
    if (selectedEnquiryForDetails != null) {
        val enq = selectedEnquiryForDetails!!
        Dialog(onDismissRequest = { selectedEnquiryForDetails = null }) {
            Card(
                colors = CardDefaults.cardColors(containerColor = NovaNavyCardElevated),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Customer Enquiry",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = NovaCyan
                        )
                        IconButton(onClick = { selectedEnquiryForDetails = null }) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Customer Name", color = TextDim, style = MaterialTheme.typography.labelSmall)
                    Text(enq.customerName, color = TextWhite, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Requested Service", color = TextDim, style = MaterialTheme.typography.labelSmall)
                    Text(enq.serviceTitle, color = NovaAccentTeal, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Contact Phone / WhatsApp", color = TextDim, style = MaterialTheme.typography.labelSmall)
                    Text(enq.customerPhone.ifBlank { "Not provided" }, color = TextWhite, style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Customer Email", color = TextDim, style = MaterialTheme.typography.labelSmall)
                    Text(enq.customerEmail.ifBlank { "Not provided" }, color = TextWhite, style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("Customer Message", color = TextDim, style = MaterialTheme.typography.labelSmall)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(NovaNavyCard, RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Text(enq.message, color = TextWhite, style = MaterialTheme.typography.bodyMedium)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text("Update Status", color = TextDim, style = MaterialTheme.typography.labelSmall)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("PENDING", "IN_PROGRESS", "COMPLETED").forEach { st ->
                            val isSel = enq.status == st
                            Button(
                                onClick = {
                                    onUpdateEnquiryStatus(enq.id, st)
                                    selectedEnquiryForDetails = enq.copy(status = st)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSel) NovaCyan else NovaNavyCard
                                ),
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = st.replace("_", " "),
                                    color = if (isSel) Color(0xFF040711) else TextWhite,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Call & WhatsApp Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (enq.customerPhone.isNotBlank()) {
                            Button(
                                onClick = {
                                    onWhatsAppCustomer(enq.customerPhone, enq.customerName, enq.serviceTitle)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = NovaWhatsApp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Chat, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("WhatsApp", color = Color.White, fontSize = 12.sp)
                            }

                            OutlinedButton(
                                onClick = { onCallCustomer(enq.customerPhone) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Phone, contentDescription = null, tint = NovaCyan, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Call", color = TextWhite, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }

    // --- CHANGE PASSWORD DIALOG ---
    if (showChangePasswordDialog) {
        var oldPass by remember { mutableStateOf("") }
        var newPass by remember { mutableStateOf("") }
        var confirmPass by remember { mutableStateOf("") }
        var passError by remember { mutableStateOf<String?>(null) }
        var passSuccess by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { showChangePasswordDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        if (newPass != confirmPass) {
                            passError = "New passwords do not match."
                            return@Button
                        }
                        if (newPass.length < 8) {
                            passError = "Password must be at least 8 characters."
                            return@Button
                        }
                        onChangePassword(oldPass, newPass) { success, err ->
                            if (success) {
                                passSuccess = true
                                passError = null
                            } else {
                                passError = err ?: "Failed to change password."
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NovaCyan)
                ) {
                    Text(if (passSuccess) "OK" else "Update Password", color = Color(0xFF040711), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showChangePasswordDialog = false }) {
                    Text("Cancel", color = TextMuted)
                }
            },
            title = { Text("Change Admin Password", color = TextWhite, fontWeight = FontWeight.Bold) },
            text = {
                if (passSuccess) {
                    Text("Password successfully updated. Your new credentials are active immediately.", color = NovaAccentTeal)
                } else {
                    Column {
                        OutlinedTextField(
                            value = oldPass,
                            onValueChange = { oldPass = it; passError = null },
                            label = { Text("Current Password", color = TextMuted) },
                            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = newPass,
                            onValueChange = { newPass = it; passError = null },
                            label = { Text("New Password (min 8 chars)", color = TextMuted) },
                            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = confirmPass,
                            onValueChange = { confirmPass = it; passError = null },
                            label = { Text("Confirm New Password", color = TextMuted) },
                            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (passError != null) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(passError!!, color = Color(0xFFEF4444), style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            },
            containerColor = NovaNavyCardElevated,
            shape = RoundedCornerShape(16.dp)
        )
    }

    // --- DELETE CONFIRMATION DIALOG ---
    if (deleteConfirmationPrompt != null) {
        val (message, action) = deleteConfirmationPrompt!!
        AlertDialog(
            onDismissRequest = { deleteConfirmationPrompt = null },
            confirmButton = {
                Button(
                    onClick = {
                        action()
                        deleteConfirmationPrompt = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
                ) {
                    Text("Confirm Delete", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { deleteConfirmationPrompt = null }) {
                    Text("Cancel", color = TextMuted)
                }
            },
            title = { Text("Confirm Deletion", color = TextWhite, fontWeight = FontWeight.Bold) },
            text = { Text(message, color = TextMuted) },
            containerColor = NovaNavyCardElevated,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

// --- TAB 1: OVERVIEW ---
@Composable
fun AdminOverviewTab(
    servicesCount: Int,
    activeServicesCount: Int,
    toolsCount: Int,
    tipsCount: Int,
    updatesCount: Int,
    enquiriesCount: Int,
    pendingEnquiriesCount: Int,
    notificationsCount: Int,
    ownerName: String,
    brandName: String,
    onNavigateTab: (AdminTab) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        androidx.compose.ui.graphics.Brush.linearGradient(
                            listOf(Color(0xFF003D7A), Color(0xFF0C2448), Color(0xFF050E1C))
                        )
                    )
                    .border(1.dp, Color(0x4000F0FF), RoundedCornerShape(16.dp))
                    .padding(18.dp)
            ) {
                Column {
                    Text(
                        text = "DIGI NOVA PLATFORM STATUS",
                        style = MaterialTheme.typography.labelSmall,
                        color = NovaCyan,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Live Operations Center",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextWhite,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Platform Owner: $ownerName • Brand: $brandName",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFCBD5E1)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Key Business Metrics",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AdminStatCard(
                    title = "Pending Enquiries",
                    value = pendingEnquiriesCount.toString(),
                    subtitle = "Needs Action",
                    accentColor = Color(0xFFEF4444),
                    icon = Icons.Default.MarkEmailUnread,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTab(AdminTab.ENQUIRIES) }
                )
                AdminStatCard(
                    title = "Total Enquiries",
                    value = enquiriesCount.toString(),
                    subtitle = "All Customer Leads",
                    accentColor = NovaCyan,
                    icon = Icons.Default.People,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTab(AdminTab.ENQUIRIES) }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AdminStatCard(
                    title = "Active Services",
                    value = "$activeServicesCount / $servicesCount",
                    subtitle = "Visible to Clients",
                    accentColor = NovaElectricBlue,
                    icon = Icons.Default.Devices,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTab(AdminTab.SERVICES) }
                )
                AdminStatCard(
                    title = "Online Tools",
                    value = toolsCount.toString(),
                    subtitle = "Curated Utilities",
                    accentColor = NovaAccentTeal,
                    icon = Icons.Default.Language,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTab(AdminTab.TOOLS) }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AdminStatCard(
                    title = "Tech Tips",
                    value = tipsCount.toString(),
                    subtitle = "Knowledge Base",
                    accentColor = Color(0xFFFFB703),
                    icon = Icons.Default.Lightbulb,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTab(AdminTab.TIPS) }
                )
                AdminStatCard(
                    title = "Updates & News",
                    value = updatesCount.toString(),
                    subtitle = "Published Releases",
                    accentColor = Color(0xFFA855F7),
                    icon = Icons.Default.Campaign,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateTab(AdminTab.UPDATES) }
                )
            }
        }
    }
}

@Composable
fun AdminStatCard(
    title: String,
    value: String,
    subtitle: String,
    accentColor: Color,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, NovaBorder, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = NovaNavyCard)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, style = MaterialTheme.typography.labelSmall, color = TextMuted)
                Icon(imageVector = icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = TextWhite)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subtitle, style = MaterialTheme.typography.labelSmall, color = accentColor, fontSize = 10.sp)
        }
    }
}

// --- TAB 2: SERVICES MANAGEMENT ---
@Composable
fun AdminServicesTab(
    services: List<ServiceEntity>,
    onAddClick: () -> Unit,
    onEditClick: (ServiceEntity) -> Unit,
    onToggleStatus: (ServiceEntity) -> Unit,
    onDeleteClick: (ServiceEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Services (${services.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
                Button(
                    onClick = onAddClick,
                    colors = ButtonDefaults.buttonColors(containerColor = NovaCyan),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF040711), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Service", color = Color(0xFF040711), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(services) { service ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .border(1.dp, NovaBorder, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = NovaNavyCard)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0x2000F0FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(getServiceIcon(service.iconType), contentDescription = null, tint = NovaCyan, modifier = Modifier.size(22.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = service.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TextWhite)
                        Text(text = "${service.category} • ${service.pricing}", style = MaterialTheme.typography.bodySmall, color = NovaAccentTeal)
                        Text(
                            text = if (service.isEnabled) "Status: Active" else "Status: Disabled",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (service.isEnabled) Color(0xFF22C55E) else Color(0xFFEF4444),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(onClick = { onToggleStatus(service) }) {
                            Icon(
                                imageVector = if (service.isEnabled) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle status",
                                tint = if (service.isEnabled) NovaCyan else TextDim
                            )
                        }
                        IconButton(onClick = { onEditClick(service) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = NovaCyan)
                        }
                        IconButton(onClick = { onDeleteClick(service) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFFCA5A5))
                        }
                    }
                }
            }
        }
    }
}

// --- TAB 3: ENQUIRIES MANAGEMENT ---
@Composable
fun AdminEnquiriesTab(
    enquiries: List<EnquiryEntity>,
    onSelectEnquiry: (EnquiryEntity) -> Unit,
    onDeleteEnquiry: (String) -> Unit,
    onCall: (String) -> Unit,
    onWhatsApp: (phone: String, name: String, service: String) -> Unit
) {
    var statusFilter by remember { mutableStateOf("ALL") }
    val filtered = if (statusFilter == "ALL") enquiries else enquiries.filter { it.status == statusFilter }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Text(
                text = "Customer Enquiries (${enquiries.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("ALL", "PENDING", "IN_PROGRESS", "COMPLETED").forEach { st ->
                    val isSel = statusFilter == st
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSel) NovaCyan else NovaNavyCardElevated)
                            .clickable { statusFilter = st }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = st.replace("_", " "),
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSel) Color(0xFF040711) else TextWhite,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        if (filtered.isEmpty()) {
            item {
                EmptyStateView(message = "No enquiries found under '$statusFilter'.")
            }
        } else {
            items(filtered) { enq ->
                val statusColor = when (enq.status) {
                    "PENDING" -> Color(0xFFEF4444)
                    "IN_PROGRESS" -> Color(0xFFFFB703)
                    else -> Color(0xFF22C55E)
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .border(1.dp, NovaBorder, RoundedCornerShape(12.dp))
                        .clickable { onSelectEnquiry(enq) },
                    colors = CardDefaults.cardColors(containerColor = NovaNavyCard)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = enq.customerName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TextWhite)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(statusColor.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(text = enq.status, style = MaterialTheme.typography.labelSmall, color = statusColor, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Service: ${enq.serviceTitle}", style = MaterialTheme.typography.bodySmall, color = NovaCyan)
                        Text(text = enq.message, style = MaterialTheme.typography.bodySmall, color = TextMuted, maxLines = 2, overflow = TextOverflow.Ellipsis)
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = enq.customerPhone.ifBlank { enq.customerEmail }, style = MaterialTheme.typography.labelSmall, color = TextDim)
                            Row {
                                if (enq.customerPhone.isNotBlank()) {
                                    IconButton(onClick = { onWhatsApp(enq.customerPhone, enq.customerName, enq.serviceTitle) }, modifier = Modifier.size(28.dp)) {
                                        Icon(Icons.Default.Chat, contentDescription = null, tint = NovaWhatsApp, modifier = Modifier.size(16.dp))
                                    }
                                    IconButton(onClick = { onCall(enq.customerPhone) }, modifier = Modifier.size(28.dp)) {
                                        Icon(Icons.Default.Phone, contentDescription = null, tint = NovaCyan, modifier = Modifier.size(16.dp))
                                    }
                                }
                                IconButton(onClick = { onDeleteEnquiry(enq.id) }, modifier = Modifier.size(28.dp)) {
                                    Icon(Icons.Default.Delete, contentDescription = null, tint = Color(0xFFFCA5A5), modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- TAB 4: ONLINE TOOLS MANAGEMENT ---
@Composable
fun AdminToolsTab(
    tools: List<OnlineToolEntity>,
    onAddClick: () -> Unit,
    onEditClick: (OnlineToolEntity) -> Unit,
    onDeleteClick: (OnlineToolEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Online Tools (${tools.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
                Button(onClick = onAddClick, colors = ButtonDefaults.buttonColors(containerColor = NovaCyan)) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF040711), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Tool", color = Color(0xFF040711), fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(tools) { tool ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .border(1.dp, NovaBorder, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = NovaNavyCard)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = tool.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TextWhite)
                        Text(text = tool.url, style = MaterialTheme.typography.bodySmall, color = NovaCyan)
                        Text(text = tool.category, style = MaterialTheme.typography.labelSmall, color = TextDim)
                    }
                    IconButton(onClick = { onEditClick(tool) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = NovaCyan)
                    }
                    IconButton(onClick = { onDeleteClick(tool) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFFCA5A5))
                    }
                }
            }
        }
    }
}

// --- TAB 5: TIPS MANAGEMENT ---
@Composable
fun AdminTipsTab(
    tips: List<TipEntity>,
    onAddClick: () -> Unit,
    onEditClick: (TipEntity) -> Unit,
    onDeleteClick: (TipEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Tech Tips (${tips.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
                Button(onClick = onAddClick, colors = ButtonDefaults.buttonColors(containerColor = NovaCyan)) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF040711), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Tip", color = Color(0xFF040711), fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(tips) { tip ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .border(1.dp, NovaBorder, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = NovaNavyCard)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = tip.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TextWhite)
                        Text(text = "${tip.category} • ${tip.readTimeMinutes} min read", style = MaterialTheme.typography.bodySmall, color = NovaCyan)
                        Text(
                            text = if (tip.isPublished) "Published" else "Draft",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (tip.isPublished) Color(0xFF22C55E) else Color(0xFFEF4444)
                        )
                    }
                    IconButton(onClick = { onEditClick(tip) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = NovaCyan)
                    }
                    IconButton(onClick = { onDeleteClick(tip) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFFCA5A5))
                    }
                }
            }
        }
    }
}

// --- TAB 6: UPDATES MANAGEMENT ---
@Composable
fun AdminUpdatesTab(
    updates: List<UpdateEntity>,
    onAddClick: () -> Unit,
    onEditClick: (UpdateEntity) -> Unit,
    onDeleteClick: (UpdateEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Announcements & Updates (${updates.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
                Button(onClick = onAddClick, colors = ButtonDefaults.buttonColors(containerColor = NovaCyan)) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF040711), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Update", color = Color(0xFF040711), fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(updates) { upd ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .border(1.dp, NovaBorder, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = NovaNavyCard)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = upd.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TextWhite)
                        Text(text = "${upd.category} • ${upd.date} • ${upd.author}", style = MaterialTheme.typography.bodySmall, color = NovaCyan)
                        Text(text = if (upd.isPublished) "Published" else "Draft", style = MaterialTheme.typography.labelSmall, color = if (upd.isPublished) Color(0xFF22C55E) else Color(0xFFEF4444))
                    }
                    IconButton(onClick = { onEditClick(upd) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = NovaCyan)
                    }
                    IconButton(onClick = { onDeleteClick(upd) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFFCA5A5))
                    }
                }
            }
        }
    }
}

// --- TAB 7: NOTIFICATIONS BROADCAST ---
@Composable
fun AdminNotificationsTab(
    notifications: List<NotificationEntity>,
    onAddClick: () -> Unit,
    onEditClick: (NotificationEntity) -> Unit,
    onDeleteClick: (NotificationEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Broadcast Alerts (${notifications.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
                Button(onClick = onAddClick, colors = ButtonDefaults.buttonColors(containerColor = NovaCyan)) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF040711), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Broadcast", color = Color(0xFF040711), fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(notifications) { notif ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .border(1.dp, NovaBorder, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = NovaNavyCard)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = notif.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TextWhite)
                        Text(text = notif.message, style = MaterialTheme.typography.bodySmall, color = TextMuted, maxLines = 2)
                        Text(text = "Priority: ${notif.priority} • ${if (notif.isPublished) "Live" else "Hidden"}", style = MaterialTheme.typography.labelSmall, color = NovaCyan)
                    }
                    IconButton(onClick = { onEditClick(notif) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = NovaCyan)
                    }
                    IconButton(onClick = { onDeleteClick(notif) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFFCA5A5))
                    }
                }
            }
        }
    }
}

// --- TAB 8: BUSINESS & CONTACT SETTINGS ---
@Composable
fun AdminSettingsTab(
    currentSettings: Map<String, String>,
    onSaveSetting: (String, String) -> Unit,
    onSaveBatch: (Map<String, String>) -> Unit
) {
    var brandName by remember(currentSettings) { mutableStateOf(currentSettings["brand_name"] ?: "DIGI NOVA") }
    var ownerName by remember(currentSettings) { mutableStateOf(currentSettings["owner_name"] ?: "Anup Digi Nova") }
    var whatsapp by remember(currentSettings) { mutableStateOf(currentSettings["whatsapp_number"] ?: "+15550198421") }
    var phone by remember(currentSettings) { mutableStateOf(currentSettings["phone_number"] ?: "+1 555-019-8421") }
    var email by remember(currentSettings) { mutableStateOf(currentSettings["support_email"] ?: "contact@diginova.io") }
    var website by remember(currentSettings) { mutableStateOf(currentSettings["website_url"] ?: "https://diginova.io") }
    var address by remember(currentSettings) { mutableStateOf(currentSettings["address"] ?: "Nova Innovation Tower, Silicon Valley, CA") }
    var aboutText by remember(currentSettings) { mutableStateOf(currentSettings["about_text"] ?: "") }
    var privacyPolicy by remember(currentSettings) { mutableStateOf(currentSettings["privacy_policy"] ?: "") }
    var termsConditions by remember(currentSettings) { mutableStateOf(currentSettings["terms_conditions"] ?: "") }
    var maintenanceMode by remember(currentSettings) { mutableStateOf((currentSettings["maintenance_mode"] ?: "false").toBoolean()) }
    var savedSuccess by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Text(
                text = "Business & System Settings",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = NovaCyan
            )
            Text(
                text = "Changes take effect immediately across all customer screens without rebuilding APK.",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            OutlinedTextField(
                value = brandName,
                onValueChange = { brandName = it; savedSuccess = false },
                label = { Text("App / Brand Name", color = TextMuted) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = ownerName,
                onValueChange = { ownerName = it; savedSuccess = false },
                label = { Text("Owner / Developer Name", color = TextMuted) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = whatsapp,
                onValueChange = { whatsapp = it; savedSuccess = false },
                label = { Text("Official WhatsApp Number (E.164 with +)", color = TextMuted) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it; savedSuccess = false },
                label = { Text("Support Phone Hotline", color = TextMuted) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it; savedSuccess = false },
                label = { Text("Support & Inquiry Email", color = TextMuted) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = website,
                onValueChange = { website = it; savedSuccess = false },
                label = { Text("Official Website URL", color = TextMuted) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = address,
                onValueChange = { address = it; savedSuccess = false },
                label = { Text("Headquarters Address", color = TextMuted) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = aboutText,
                onValueChange = { aboutText = it; savedSuccess = false },
                label = { Text("About Company Text", color = TextMuted) },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = privacyPolicy,
                onValueChange = { privacyPolicy = it; savedSuccess = false },
                label = { Text("Privacy Policy", color = TextMuted) },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = termsConditions,
                onValueChange = { termsConditions = it; savedSuccess = false },
                label = { Text("Terms & Conditions", color = TextMuted) },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Maintenance Mode", color = TextWhite, fontWeight = FontWeight.Bold)
                    Text("Temporarily pause public requests", color = TextDim, fontSize = 11.sp)
                }
                Switch(checked = maintenanceMode, onCheckedChange = { maintenanceMode = it; savedSuccess = false })
            }
            Spacer(modifier = Modifier.height(18.dp))

            if (savedSuccess) {
                Text(
                    text = "All settings saved and synchronized live!",
                    color = Color(0xFF22C55E),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    onSaveBatch(
                        mapOf(
                            "brand_name" to brandName.trim(),
                            "owner_name" to ownerName.trim(),
                            "whatsapp_number" to whatsapp.trim(),
                            "phone_number" to phone.trim(),
                            "support_email" to email.trim(),
                            "website_url" to website.trim(),
                            "address" to address.trim(),
                            "about_text" to aboutText.trim(),
                            "privacy_policy" to privacyPolicy.trim(),
                            "terms_conditions" to termsConditions.trim(),
                            "maintenance_mode" to maintenanceMode.toString()
                        )
                    )
                    savedSuccess = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NovaCyan)
            ) {
                Text("Save All Settings", color = Color(0xFF040711), fontWeight = FontWeight.Bold)
            }
        }
    }
}

// --- TAB 9: AUDIT LOG ---
@Composable
fun AdminAuditTab(auditLogs: List<AuditLogEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Text(
                text = "Security Audit Trail (${auditLogs.size} records)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
            Text(
                text = "Immutable records of administrator actions and system security events.",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        if (auditLogs.isEmpty()) {
            item {
                EmptyStateView(message = "No audit log entries recorded yet.")
            }
        } else {
            items(auditLogs) { log ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .border(1.dp, NovaBorder, RoundedCornerShape(10.dp)),
                    colors = CardDefaults.cardColors(containerColor = NovaNavyCard)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = log.action, style = MaterialTheme.typography.labelSmall, color = NovaCyan, fontWeight = FontWeight.Bold)
                            Text(text = log.formattedDate, style = MaterialTheme.typography.labelSmall, color = TextDim, fontSize = 10.sp)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = log.details, style = MaterialTheme.typography.bodySmall, color = TextWhite)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = "User: ${log.adminUser}", style = MaterialTheme.typography.labelSmall, color = NovaAccentTeal, fontSize = 10.sp)
                    }
                }
            }
        }
    }
}

// --- TAB 10: ADMIN PROFILE ---
@Composable
fun AdminProfileTab(
    username: String,
    email: String,
    ownerName: String,
    copyright: String,
    onChangePasswordClick: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Owner Profile & Security",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = TextWhite
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, NovaBorder, RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = NovaNavyCard)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Account Details", style = MaterialTheme.typography.titleSmall, color = NovaCyan, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Username", style = MaterialTheme.typography.labelSmall, color = TextDim)
                Text(text = username, style = MaterialTheme.typography.bodyLarge, color = TextWhite, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Administrator Email", style = MaterialTheme.typography.labelSmall, color = TextDim)
                Text(text = email, style = MaterialTheme.typography.bodyLarge, color = TextWhite)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Official Platform Owner", style = MaterialTheme.typography.labelSmall, color = TextDim)
                Text(text = ownerName, style = MaterialTheme.typography.bodyLarge, color = NovaAccentTeal, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Copyright", style = MaterialTheme.typography.labelSmall, color = TextDim)
                Text(text = copyright, style = MaterialTheme.typography.bodySmall, color = TextMuted)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Security Actions
        Button(
            onClick = onChangePasswordClick,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = NovaElectricBlue),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.LockReset, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Change Admin Password", color = Color.White, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF4444)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Logout, contentDescription = null, tint = Color(0xFFEF4444))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Log Out of Admin Portal", color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
        }
    }
}
