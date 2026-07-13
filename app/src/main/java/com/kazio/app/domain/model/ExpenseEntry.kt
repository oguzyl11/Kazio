package com.kazio.app.domain.model

enum class ExpenseCategory {
    FUEL, MAINTENANCE, FINE, PARKING, WASHING, OTHER
}

data class ExpenseEntry(
    val id: Long = 0,
    val shiftId: Long?,
    val category: ExpenseCategory,
    val amount: Double,
    val occurredAt: Long,
    val note: String?
)
