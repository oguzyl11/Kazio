package com.kazio.app.presentation.addexpense

import com.kazio.app.domain.model.ExpenseCategory

data class AddExpenseUiState(
    val amount: String = "",
    val selectedCategory: ExpenseCategory? = null,
    val note: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)
