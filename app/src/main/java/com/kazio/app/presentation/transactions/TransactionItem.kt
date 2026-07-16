package com.kazio.app.presentation.transactions

import com.kazio.app.domain.model.ExpenseEntry
import com.kazio.app.domain.model.IncomeEntry

sealed class TransactionItem {
    abstract val id: Long
    abstract val timestamp: Long
    abstract val amount: Double
    abstract val title: String
    abstract val note: String?

    data class Income(val entry: IncomeEntry) : TransactionItem() {
        override val id: Long = entry.id
        override val timestamp: Long = entry.occurredAt
        override val amount: Double = entry.amount
        override val title: String = "Gelir"
        override val note: String? = entry.note
    }

    data class Expense(val entry: ExpenseEntry) : TransactionItem() {
        override val id: Long = entry.id
        override val timestamp: Long = entry.occurredAt
        override val amount: Double = entry.amount
        override val title: String = when(entry.category) {
            com.kazio.app.domain.model.ExpenseCategory.FUEL -> "Yakıt"
            com.kazio.app.domain.model.ExpenseCategory.MAINTENANCE -> "Bakım"
            com.kazio.app.domain.model.ExpenseCategory.FINE -> "Ceza"
            com.kazio.app.domain.model.ExpenseCategory.PARKING -> "Otopark"
            com.kazio.app.domain.model.ExpenseCategory.WASHING -> "Yıkama"
            com.kazio.app.domain.model.ExpenseCategory.OTHER -> "Diğer"
        }
        override val note: String? = entry.note
    }
}
