package com.kazio.app.domain.model

data class FrequentIncome(
    val platformId: Long,
    val amount: Double,
    val note: String?
)

data class FrequentExpense(
    val category: ExpenseCategory,
    val amount: Double,
    val note: String?
)
