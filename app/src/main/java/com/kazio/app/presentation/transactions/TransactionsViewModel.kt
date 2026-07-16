package com.kazio.app.presentation.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazio.app.domain.repository.ExpenseRepository
import com.kazio.app.domain.repository.IncomeRepository
import com.kazio.app.domain.usecase.DeleteExpenseUseCase
import com.kazio.app.domain.usecase.DeleteIncomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class TransactionsUiState(
    val isLoading: Boolean = false,
    val transactions: List<TransactionItem> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository,
    private val deleteIncomeUseCase: DeleteIncomeUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionsUiState(isLoading = true))
    val uiState: StateFlow<TransactionsUiState> = _uiState.asStateFlow()

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        val calendar = Calendar.getInstance()
        val endAt = calendar.timeInMillis
        // Load for the current month
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startAt = calendar.timeInMillis

        viewModelScope.launch {
            combine(
                incomeRepository.getIncomesForDateRange(startAt, endAt),
                expenseRepository.getExpensesForDateRange(startAt, endAt)
            ) { incomes, expenses ->
                val incomeItems = incomes.map { TransactionItem.Income(it) }
                val expenseItems = expenses.map { TransactionItem.Expense(it) }
                val combined = (incomeItems + expenseItems).sortedByDescending { it.timestamp }
                TransactionsUiState(isLoading = false, transactions = combined)
            }.catch { e ->
                _uiState.value = TransactionsUiState(isLoading = false, error = e.message)
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun deleteTransaction(item: TransactionItem) {
        viewModelScope.launch {
            when (item) {
                is TransactionItem.Income -> deleteIncomeUseCase(item.id)
                is TransactionItem.Expense -> deleteExpenseUseCase(item.id)
            }
        }
    }
}
