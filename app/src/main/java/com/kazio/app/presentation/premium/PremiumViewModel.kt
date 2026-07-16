package com.kazio.app.presentation.premium

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazio.app.data.billing.BillingManager
import com.kazio.app.data.local.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PremiumUiState(
    val isPremium: Boolean = false,
    val isLoading: Boolean = false,
    val priceText: String = "",
    val error: String? = null
)

@HiltViewModel
class PremiumViewModel @Inject constructor(
    private val billingManager: BillingManager,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PremiumUiState())
    val uiState: StateFlow<PremiumUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreRepository.userPreferencesFlow.collect { prefs ->
                _uiState.value = _uiState.value.copy(isPremium = prefs.isPremium)
            }
        }

        viewModelScope.launch {
            billingManager.products.collect { products ->
                val premiumProduct = products.find { it.productId == BillingManager.PREMIUM_MONTHLY_PLAN }
                val offer = premiumProduct?.subscriptionOfferDetails?.firstOrNull()
                val price = offer?.pricingPhases?.pricingPhaseList?.firstOrNull()?.formattedPrice ?: "29,99 ₺/Ay"
                _uiState.value = _uiState.value.copy(priceText = price)
            }
        }
    }

    fun buyPremium(activity: Activity) {
        val premiumProduct = billingManager.products.value.find { it.productId == BillingManager.PREMIUM_MONTHLY_PLAN }
        if (premiumProduct != null) {
            billingManager.launchBillingFlow(activity, premiumProduct)
        } else {
            _uiState.value = _uiState.value.copy(error = "Ürün bilgileri yüklenemedi. Lütfen internet bağlantınızı kontrol edin.")
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
