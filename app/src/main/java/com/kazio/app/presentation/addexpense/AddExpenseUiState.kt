package com.kazio.app.presentation.addexpense

import com.kazio.app.domain.model.ExpenseCategory
import com.kazio.app.domain.model.FrequentExpense

data class AddExpenseUiState(
    val amount: String = "",
    val selectedCategory: ExpenseCategory? = null,
    val note: String = "",
    val frequentExpenses: List<FrequentExpense> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val selectedDateMillis: Long? = null
)
