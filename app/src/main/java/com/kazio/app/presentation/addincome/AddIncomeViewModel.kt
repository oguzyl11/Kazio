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

import com.kazio.app.domain.usecase.UpdateIncomeUseCase
import com.kazio.app.domain.model.IncomeEntry

@HiltViewModel
class AddIncomeViewModel @Inject constructor(
    private val getPlatformsUseCase: GetPlatformsUseCase,
    private val getActiveShiftUseCase: GetActiveShiftUseCase,
    private val addIncomeUseCase: AddIncomeUseCase,
    private val updateIncomeUseCase: UpdateIncomeUseCase
) : ViewModel() {

    private var editingIncomeId: Long? = null
    private var editingOccurredAt: Long? = null
    private var editingShiftId: Long? = null

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

    fun setEditingIncome(income: IncomeEntry?) {
        if (income == null) {
            editingIncomeId = null
            editingOccurredAt = null
            editingShiftId = null
            _uiState.update { it.copy(amount = "", selectedPlatformId = null) }
        } else {
            editingIncomeId = income.id
            editingOccurredAt = income.occurredAt
            editingShiftId = income.shiftId
            
            // Format amount to string without trailing .0
            var amountStr = income.amount.toString()
            if (amountStr.endsWith(".0")) {
                amountStr = amountStr.substring(0, amountStr.length - 2)
            }
            _uiState.update { 
                it.copy(
                    amount = amountStr, 
                    selectedPlatformId = income.platformId,
                    selectedDateMillis = income.occurredAt
                ) 
            }
        }
    }

    fun onDateSelect(dateMillis: Long?) {
        _uiState.update { it.copy(selectedDateMillis = dateMillis) }
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
            
            val result = if (editingIncomeId != null) {
                updateIncomeUseCase(
                    IncomeEntry(
                        id = editingIncomeId!!,
                        shiftId = editingShiftId,
                        platformId = platformId ?: 0,
                        amount = amount,
                        occurredAt = _uiState.value.selectedDateMillis ?: System.currentTimeMillis(),
                        note = null
                    )
                )
            } else {
                val activeShift = getActiveShiftUseCase().firstOrNull()
                val shiftId = activeShift?.id
                addIncomeUseCase(
                    amount = amount,
                    platformId = platformId ?: 0,
                    shiftId = shiftId,
                    note = null,
                    occurredAt = _uiState.value.selectedDateMillis
                )
            }

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
