package com.example.ui

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import com.example.data.local.*
import com.example.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.URLEncoder

data class DigiNovaUiState(
    val currentScreen: NavScreen = NavScreen.HOME,
    val isSplashScreenVisible: Boolean = true,
    val searchQuery: String = "",
    val selectedServiceCategory: String = "All",
    val selectedTipCategory: String = "All",
    val selectedOnlineCategory: String = "All",
    val selectedDigitalService: DigitalService? = null,
    val selectedLatestUpdate: LatestUpdate? = null,
    val selectedTip: UsefulTip? = null,
    val bookmarkedTipIds: Set<String> = emptySet(),
    val isSearching: Boolean = false,
    // Customer Enquiry Dialog
    val isEnquiryDialogOpen: Boolean = false,
    val targetEnquiryService: DigitalService? = null,
    // Admin Auth State
    val isAdminLoggedIn: Boolean = false,
    val adminUsername: String = "",
    val adminEmail: String = "",
    val adminLoginError: String? = null,
    val isAdminLoading: Boolean = false
)

class DigiNovaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DigiNovaDataRepository(application)

    private val _uiState = MutableStateFlow(DigiNovaUiState())
    val uiState: StateFlow<DigiNovaUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.ensureInitialized()
        }
    }

    // --- REACTIVE DATA STREAMS FOR CUSTOMER APP ---
    val digitalServices: StateFlow<List<DigitalService>> = repository.activeServices
        .map { list -> list.map { it.toModel() } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val onlineServices: StateFlow<List<OnlineService>> = repository.activeOnlineTools
        .map { list -> list.map { it.toModel() } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val usefulTips: StateFlow<List<UsefulTip>> = repository.publishedTips
        .map { list -> list.map { it.toModel() } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val latestUpdates: StateFlow<List<LatestUpdate>> = repository.publishedUpdates
        .map { list -> list.map { it.toModel() } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val notifications: StateFlow<List<AppNotification>> = repository.publishedNotifications
        .map { list -> list.map { it.toModel() } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val companyContact: StateFlow<CompanyContact> = repository.appSettings
        .map { it.toCompanyContact() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, CompanyContact())

    // --- REACTIVE DATA STREAMS FOR ADMIN DASHBOARD ---
    val allServicesForAdmin: StateFlow<List<ServiceEntity>> = repository.allServices
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allOnlineToolsForAdmin: StateFlow<List<OnlineToolEntity>> = repository.allOnlineTools
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allTipsForAdmin: StateFlow<List<TipEntity>> = repository.allTips
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allUpdatesForAdmin: StateFlow<List<UpdateEntity>> = repository.allUpdates
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allNotificationsForAdmin: StateFlow<List<NotificationEntity>> = repository.allNotifications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allEnquiriesForAdmin: StateFlow<List<EnquiryEntity>> = repository.allEnquiries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val pendingEnquiriesCount: StateFlow<Int> = repository.pendingEnquiriesCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val auditLogs: StateFlow<List<AuditLogEntity>> = repository.auditLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val rawSettings: StateFlow<Map<String, String>> = repository.appSettings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    // --- NAVIGATION & CUSTOMER UI METHODS ---
    fun dismissSplashScreen() {
        _uiState.update { it.copy(isSplashScreenVisible = false) }
    }

    fun navigateTo(screen: NavScreen) {
        _uiState.update {
            it.copy(
                currentScreen = screen,
                selectedDigitalService = null,
                selectedLatestUpdate = null,
                selectedTip = null,
                adminLoginError = null
            )
        }
    }

    fun openServiceDetail(service: DigitalService) {
        _uiState.update { it.copy(selectedDigitalService = service) }
    }

    fun closeServiceDetail() {
        _uiState.update { it.copy(selectedDigitalService = null) }
    }

    fun openUpdateDetail(update: LatestUpdate) {
        _uiState.update { it.copy(selectedLatestUpdate = update) }
    }

    fun closeUpdateDetail() {
        _uiState.update { it.copy(selectedLatestUpdate = null) }
    }

    fun openTipDetail(tip: UsefulTip) {
        _uiState.update { it.copy(selectedTip = tip) }
    }

    fun closeTipDetail() {
        _uiState.update { it.copy(selectedTip = null) }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query, isSearching = query.isNotBlank()) }
    }

    fun clearSearchQuery() {
        _uiState.update { it.copy(searchQuery = "", isSearching = false) }
    }

    fun setServiceCategory(category: String) {
        _uiState.update { it.copy(selectedServiceCategory = category) }
    }

    fun setTipCategory(category: String) {
        _uiState.update { it.copy(selectedTipCategory = category) }
    }

    fun setOnlineCategory(category: String) {
        _uiState.update { it.copy(selectedOnlineCategory = category) }
    }

    fun toggleBookmarkTip(tipId: String) {
        _uiState.update { state ->
            val updated = if (state.bookmarkedTipIds.contains(tipId)) {
                state.bookmarkedTipIds - tipId
            } else {
                state.bookmarkedTipIds + tipId
            }
            state.copy(bookmarkedTipIds = updated)
        }
    }

    fun openCustomerEnquiryDialog(service: DigitalService? = null) {
        _uiState.update { it.copy(isEnquiryDialogOpen = true, targetEnquiryService = service) }
    }

    fun closeCustomerEnquiryDialog() {
        _uiState.update { it.copy(isEnquiryDialogOpen = false, targetEnquiryService = null) }
    }

    fun submitCustomerEnquiry(
        name: String,
        phone: String,
        email: String,
        serviceId: String?,
        serviceTitle: String,
        message: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val result = repository.submitCustomerEnquiry(name, phone, email, serviceId, serviceTitle, message)
            if (result.isSuccess) {
                closeCustomerEnquiryDialog()
                onSuccess()
            } else {
                onError(result.exceptionOrNull()?.message ?: "Failed to submit enquiry.")
            }
        }
    }

    // Handles Android Back Navigation cleanly
    fun handleBackPress(): Boolean {
        val state = _uiState.value
        if (state.isEnquiryDialogOpen) {
            closeCustomerEnquiryDialog()
            return true
        }
        if (state.selectedDigitalService != null) {
            closeServiceDetail()
            return true
        }
        if (state.selectedLatestUpdate != null) {
            closeUpdateDetail()
            return true
        }
        if (state.selectedTip != null) {
            closeTipDetail()
            return true
        }
        if (state.searchQuery.isNotBlank()) {
            clearSearchQuery()
            return true
        }
        if (state.currentScreen == NavScreen.ADMIN_DASHBOARD) {
            // Stay in dashboard or ask to logout
            return false
        }
        if (state.currentScreen != NavScreen.HOME) {
            navigateTo(NavScreen.HOME)
            return true
        }
        return false
    }

    // --- ADMIN AUTHENTICATION & SESSION MANAGEMENT ---
    fun loginAdmin(usernameAttempt: String, passwordAttempt: String) {
        _uiState.update { it.copy(isAdminLoading = true, adminLoginError = null) }
        viewModelScope.launch {
            when (val result = repository.adminLogin(usernameAttempt, passwordAttempt)) {
                is LoginResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isAdminLoggedIn = true,
                            adminUsername = result.user.username,
                            adminEmail = result.user.email,
                            isAdminLoading = false,
                            adminLoginError = null,
                            currentScreen = NavScreen.ADMIN_DASHBOARD
                        )
                    }
                }
                is LoginResult.Error -> {
                    _uiState.update { it.copy(isAdminLoading = false, adminLoginError = result.message) }
                }
                is LoginResult.AccountLocked -> {
                    _uiState.update {
                        it.copy(
                            isAdminLoading = false,
                            adminLoginError = "Account locked for 15 minutes due to 5 consecutive failed attempts."
                        )
                    }
                }
            }
        }
    }

    fun logoutAdmin() {
        val adminUser = _uiState.value.adminUsername
        viewModelScope.launch {
            repository.logAudit("LOGOUT", "Admin $adminUser logged out", adminUser)
        }
        _uiState.update {
            it.copy(
                isAdminLoggedIn = false,
                adminUsername = "",
                adminEmail = "",
                adminLoginError = null,
                currentScreen = NavScreen.HOME
            )
        }
    }

    fun changeAdminPassword(currentPass: String, newPass: String, onResult: (Boolean, String?) -> Unit) {
        val username = _uiState.value.adminUsername
        viewModelScope.launch {
            val res = repository.changeAdminPassword(username, currentPass, newPass)
            if (res.isSuccess) {
                onResult(true, null)
            } else {
                onResult(false, res.exceptionOrNull()?.message)
            }
        }
    }

    // --- ADMIN SERVICE MANAGEMENT ---
    fun saveService(service: ServiceEntity) {
        viewModelScope.launch {
            repository.insertOrUpdateService(service, _uiState.value.adminUsername)
        }
    }

    fun deleteService(service: ServiceEntity) {
        viewModelScope.launch {
            repository.deleteService(service.id, service.title, _uiState.value.adminUsername)
        }
    }

    fun toggleServiceStatus(service: ServiceEntity) {
        viewModelScope.launch {
            repository.toggleServiceStatus(service, _uiState.value.adminUsername)
        }
    }

    // --- ADMIN ONLINE TOOLS MANAGEMENT ---
    fun saveOnlineTool(tool: OnlineToolEntity) {
        viewModelScope.launch {
            repository.insertOrUpdateOnlineTool(tool, _uiState.value.adminUsername)
        }
    }

    fun deleteOnlineTool(tool: OnlineToolEntity) {
        viewModelScope.launch {
            repository.deleteOnlineTool(tool.id, tool.title, _uiState.value.adminUsername)
        }
    }

    // --- ADMIN TIPS MANAGEMENT ---
    fun saveTip(tip: TipEntity) {
        viewModelScope.launch {
            repository.insertOrUpdateTip(tip, _uiState.value.adminUsername)
        }
    }

    fun deleteTip(tip: TipEntity) {
        viewModelScope.launch {
            repository.deleteTip(tip.id, tip.title, _uiState.value.adminUsername)
        }
    }

    // --- ADMIN UPDATES MANAGEMENT ---
    fun saveUpdate(update: UpdateEntity) {
        viewModelScope.launch {
            repository.insertOrUpdateUpdate(update, _uiState.value.adminUsername)
        }
    }

    fun deleteUpdate(update: UpdateEntity) {
        viewModelScope.launch {
            repository.deleteUpdate(update.id, update.title, _uiState.value.adminUsername)
        }
    }

    // --- ADMIN NOTIFICATIONS MANAGEMENT ---
    fun saveNotification(notification: NotificationEntity) {
        viewModelScope.launch {
            repository.insertOrUpdateNotification(notification, _uiState.value.adminUsername)
        }
    }

    fun deleteNotification(notification: NotificationEntity) {
        viewModelScope.launch {
            repository.deleteNotification(notification.id, notification.title, _uiState.value.adminUsername)
        }
    }

    // --- ADMIN ENQUIRY MANAGEMENT ---
    fun updateEnquiryStatus(enquiryId: String, status: String) {
        viewModelScope.launch {
            repository.updateEnquiryStatus(enquiryId, status, _uiState.value.adminUsername)
        }
    }

    fun deleteEnquiry(enquiryId: String) {
        viewModelScope.launch {
            repository.deleteEnquiry(enquiryId, _uiState.value.adminUsername)
        }
    }

    // --- ADMIN SETTINGS MANAGEMENT ---
    fun saveSetting(key: String, value: String) {
        viewModelScope.launch {
            repository.saveSetting(key, value, _uiState.value.adminUsername)
        }
    }

    fun saveBatchSettings(settings: Map<String, String>) {
        viewModelScope.launch {
            repository.saveBatchSettings(settings, _uiState.value.adminUsername)
        }
    }

    // --- INTENT ACTIONS ---
    fun openWebUrl(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open link: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsApp(context: Context, prefilledMessage: String = "Hello DIGI NOVA! I would like to inquire about your services.") {
        try {
            val phone = companyContact.value.supportWhatsAppNumber.replace("+", "").replace(" ", "").replace("-", "")
            val encodedMsg = URLEncoder.encode(prefilledMessage, "UTF-8")
            val uri = Uri.parse("https://api.whatsapp.com/send?phone=$phone&text=$encodedMsg")
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/?text=${URLEncoder.encode(prefilledMessage, "UTF-8")}")).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(browserIntent)
            } catch (err: Exception) {
                Toast.makeText(context, "WhatsApp is not installed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun makePhoneCall(context: Context, rawPhone: String? = null) {
        val phoneToCall = rawPhone ?: companyContact.value.supportPhoneNumber
        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneToCall")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Cannot open dialer: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendEmail(context: Context, subject: String = "DIGI NOVA Inquiry") {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${companyContact.value.supportEmail}")
                putExtra(Intent.EXTRA_SUBJECT, subject)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "No email client found", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareContent(context: Context, title: String, content: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, title)
                putExtra(Intent.EXTRA_TEXT, "$title\n\n$content\n\nShared via DIGI NOVA App")
            }
            context.startActivity(Intent.createChooser(intent, "Share via"))
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to share", Toast.LENGTH_SHORT).show()
        }
    }
}
