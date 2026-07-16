package com.kazio.app.domain.repository

import com.kazio.app.domain.model.IncomeEntry
import kotlinx.coroutines.flow.Flow

interface IncomeRepository {
    fun getAllIncomes(): Flow<List<IncomeEntry>>
    fun getIncomesForDateRange(startAt: Long, endAt: Long): Flow<List<IncomeEntry>>
    fun getIncomesForShift(shiftId: Long): Flow<List<IncomeEntry>>
    suspend fun addIncome(entry: IncomeEntry): Long
    suspend fun updateIncome(entry: IncomeEntry)
    suspend fun deleteIncome(id: Long)
}
