package com.kazio.app.data.repository

import com.kazio.app.data.local.room.ExpenseDao
import com.kazio.app.data.mapper.toDomain
import com.kazio.app.data.mapper.toEntity
import com.kazio.app.domain.model.ExpenseEntry
import com.kazio.app.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {
    override fun getExpensesForDateRange(startAt: Long, endAt: Long): Flow<List<ExpenseEntry>> {
        return expenseDao.getExpensesForDateRange(startAt, endAt).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun addExpense(entry: ExpenseEntry): Long {
        return expenseDao.insertExpense(entry.toEntity())
    }

    override suspend fun deleteExpense(id: Long) {
        expenseDao.deleteExpense(id)
    }
}
