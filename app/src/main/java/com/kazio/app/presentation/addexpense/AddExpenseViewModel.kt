package com.kazio.app.presentation.addexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazio.app.domain.model.ExpenseCategory
import com.kazio.app.domain.usecase.AddExpenseResult
import com.kazio.app.domain.usecase.AddExpenseUseCase
import com.kazio.app.domain.usecase.GetActiveShiftUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.kazio.app.domain.usecase.UpdateExpenseUseCase
import com.kazio.app.domain.model.ExpenseEntry

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val getActiveShiftUseCase: GetActiveShiftUseCase,
    private val addExpenseUseCase: AddExpenseUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase
) : ViewModel() {

    private var editingExpenseId: Long? = null
    private var editingOccurredAt: Long? = null
    private var editingShiftId: Long? = null

    private val _uiState = MutableStateFlow(AddExpenseUiState())
    val uiState: StateFlow<AddExpenseUiState> = _uiState.asStateFlow()

    fun setEditingExpense(expense: ExpenseEntry?) {
        if (expense == null) {
            editingExpenseId = null
            editingOccurredAt = null
            editingShiftId = null
            _uiState.update { it.copy(amount = "", selectedCategory = null) }
        } else {
            editingExpenseId = expense.id
            editingOccurredAt = expense.occurredAt
            editingShiftId = expense.shiftId
            
            var amountStr = expense.amount.toString()
            if (amountStr.endsWith(".0")) {
                amountStr = amountStr.substring(0, amountStr.length - 2)
            }
            _uiState.update { it.copy(amount = amountStr, selectedCategory = expense.category) }
        }
    }

    fun onAmountChange(amount: String) {
        _uiState.update { it.copy(amount = amount, error = null) }
    }

    fun onCategorySelect(category: ExpenseCategory) {
        _uiState.update { it.copy(selectedCategory = category, error = null) }
    }

    fun saveExpense() {
        val amountStr = _uiState.value.amount
        val category = _uiState.value.selectedCategory
        
        val amount = amountStr.toDoubleOrNull() ?: 0.0

        if (category == null) {
            _uiState.update { it.copy(error = "Lütfen kategori seçin") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            val result = if (editingExpenseId != null) {
                updateExpenseUseCase(
                    ExpenseEntry(
                        id = editingExpenseId!!,
                        shiftId = editingShiftId,
                        category = category,
                        amount = amount,
                        occurredAt = editingOccurredAt ?: System.currentTimeMillis(),
                        note = null
                    )
                )
            } else {
                val activeShift = getActiveShiftUseCase().firstOrNull()
                val shiftId = activeShift?.id
                addExpenseUseCase(
                    amount = amount,
                    category = category,
                    shiftId = shiftId,
                    note = null
                )
            }

            when (result) {
                is AddExpenseResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }
                is AddExpenseResult.Error -> {
                    val errorMessage = when(result.reason) {
                        AddExpenseResult.Reason.AMOUNT_MUST_BE_POSITIVE -> "Tutar 0'dan büyük olmalıdır"
                        AddExpenseResult.Reason.AMOUNT_TOO_LARGE -> "Tutar çok yüksek"
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
