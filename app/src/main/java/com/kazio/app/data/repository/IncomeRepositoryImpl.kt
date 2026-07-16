package com.kazio.app.data.repository

import com.kazio.app.data.local.room.IncomeDao
import com.kazio.app.data.mapper.toDomain
import com.kazio.app.data.mapper.toEntity
import com.kazio.app.domain.model.IncomeEntry
import com.kazio.app.domain.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IncomeRepositoryImpl @Inject constructor(
    private val incomeDao: IncomeDao
) : IncomeRepository {
    override fun getIncomesForDateRange(startAt: Long, endAt: Long): Flow<List<IncomeEntry>> {
        return incomeDao.getIncomesForDateRange(startAt, endAt).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getIncomesForShift(shiftId: Long): Flow<List<IncomeEntry>> {
        return incomeDao.getIncomesForShift(shiftId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun addIncome(entry: IncomeEntry): Long {
        return incomeDao.insertIncome(entry.toEntity())
    }

    override suspend fun deleteIncome(id: Long) {
        incomeDao.deleteIncome(id)
    }
}
