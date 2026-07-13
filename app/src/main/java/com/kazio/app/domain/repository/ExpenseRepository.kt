package com.kazio.app.domain.repository

import com.kazio.app.domain.model.ExpenseEntry
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getExpensesForDateRange(startAt: Long, endAt: Long): Flow<List<ExpenseEntry>>
    suspend fun addExpense(entry: ExpenseEntry): Long
    suspend fun deleteExpense(id: Long)
}
