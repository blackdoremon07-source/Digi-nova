package com.example.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.DigiNovaRepository
import com.example.model.DigitalService
import com.example.model.LatestUpdate
import com.example.model.NavScreen
import com.example.model.OnlineService
import com.example.model.UsefulTip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
    val isSearching: Boolean = false
)

class DigiNovaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DigiNovaUiState())
    val uiState: StateFlow<DigiNovaUiState> = _uiState.asStateFlow()

    val digitalServices: List<DigitalService> = DigiNovaRepository.digitalServices
    val onlineServices: List<OnlineService> = DigiNovaRepository.onlineServices
    val usefulTips: List<UsefulTip> = DigiNovaRepository.usefulTips
    val latestUpdates: List<LatestUpdate> = DigiNovaRepository.latestUpdates

    fun dismissSplashScreen() {
        _uiState.update { it.copy(isSplashScreenVisible = false) }
    }

    fun navigateTo(screen: NavScreen) {
        _uiState.update {
            it.copy(
                currentScreen = screen,
                selectedDigitalService = null,
                selectedLatestUpdate = null,
                selectedTip = null
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

    // Handles Android Back Navigation cleanly
    fun handleBackPress(): Boolean {
        val state = _uiState.value
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
        if (state.currentScreen != NavScreen.HOME) {
            navigateTo(NavScreen.HOME)
            return true
        }
        return false
    }

    // Safe URL Opener
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

    // Open WhatsApp directly with inquiry message
    fun openWhatsApp(context: Context, prefilledMessage: String = "Hello DIGI NOVA! I would like to inquire about your services.") {
        try {
            val encodedMsg = URLEncoder.encode(prefilledMessage, "UTF-8")
            val phone = DigiNovaRepository.contactInfo.supportWhatsAppNumber.replace("+", "").replace(" ", "").replace("-", "")
            val uri = Uri.parse("https://api.whatsapp.com/send?phone=$phone&text=$encodedMsg")
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            // Fallback to web WhatsApp or toast
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

    // Send Email
    fun sendEmail(context: Context, subject: String = "DIGI NOVA Inquiry") {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${DigiNovaRepository.contactInfo.supportEmail}")
                putExtra(Intent.EXTRA_SUBJECT, subject)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "No email client found", Toast.LENGTH_SHORT).show()
        }
    }

    // Share Text
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
