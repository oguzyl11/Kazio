package com.kazio.app.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDao {
    @Query("SELECT * FROM incomes WHERE occurredAt >= :startAt AND occurredAt <= :endAt")
    fun getIncomesForDateRange(startAt: Long, endAt: Long): Flow<List<IncomeEntity>>

    @Query("SELECT * FROM incomes WHERE shiftId = :shiftId")
    fun getIncomesForShift(shiftId: Long): Flow<List<IncomeEntity>>

    @Insert
    suspend fun insertIncome(income: IncomeEntity): Long

    @Update
    suspend fun updateIncome(income: IncomeEntity)

    @Query("DELETE FROM incomes WHERE id = :id")
    suspend fun deleteIncome(id: Long)
}
