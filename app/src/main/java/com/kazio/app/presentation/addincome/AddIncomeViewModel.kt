package com.kazio.app.presentation.addincome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazio.app.domain.usecase.AddIncomeResult
import com.kazio.app.domain.usecase.AddIncomeUseCase
import com.kazio.app.domain.usecase.GetActiveShiftUseCase
import com.kazio.app.domain.usecase.GetPlatformsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddIncomeViewModel @Inject constructor(
    private val getPlatformsUseCase: GetPlatformsUseCase,
    private val getActiveShiftUseCase: GetActiveShiftUseCase,
    private val addIncomeUseCase: AddIncomeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddIncomeUiState())
    val uiState: StateFlow<AddIncomeUiState> = _uiState.asStateFlow()

    init {
        loadPlatforms()
    }

    private fun loadPlatforms() {
        viewModelScope.launch {
            getPlatformsUseCase().collect { platforms ->
                _uiState.update { it.copy(platforms = platforms) }
            }
        }
    }

    fun onAmountChange(amount: String) {
        _uiState.update { it.copy(amount = amount, error = null) }
    }

    fun onPlatformSelect(platformId: Long) {
        _uiState.update { it.copy(selectedPlatformId = platformId, error = null) }
    }

    fun saveIncome() {
        val amountStr = _uiState.value.amount
        val platformId = _uiState.value.selectedPlatformId
        
        val amount = amountStr.toDoubleOrNull() ?: 0.0

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val activeShift = getActiveShiftUseCase().firstOrNull()
            val shiftId = activeShift?.id

            val result = addIncomeUseCase(
                amount = amount,
                platformId = platformId ?: 0,
                shiftId = shiftId,
                note = null
            )

            when (result) {
                is AddIncomeResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }
                is AddIncomeResult.Error -> {
                    val errorMessage = when(result.reason) {
                        AddIncomeResult.Reason.AMOUNT_MUST_BE_POSITIVE -> "Tutar 0'dan büyük olmalıdır"
                        AddIncomeResult.Reason.AMOUNT_TOO_LARGE -> "Tutar çok yüksek"
                        AddIncomeResult.Reason.NO_PLATFORM_SELECTED -> "Lütfen platform seçin"
                    }
                    _uiState.update { it.copy(isLoading = false, error = errorMessage) }
                }
            }
        }
    }
    
    fun resetSuccess() {
        _uiState.update { it.copy(isSuccess = false) }
    }
}
