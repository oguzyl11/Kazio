package com.kazio.app.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShiftDao {
    @Query("SELECT * FROM shifts WHERE endAt IS NULL ORDER BY startAt DESC LIMIT 1")
    fun getActiveShift(): Flow<ShiftEntity?>

    @Query("SELECT * FROM shifts WHERE startAt >= :startAt AND startAt <= :endAt")
    fun getShiftsForDateRange(startAt: Long, endAt: Long): Flow<List<ShiftEntity>>

    @Insert
    suspend fun insertShift(shift: ShiftEntity): Long

    @Query("UPDATE shifts SET endAt = :endAt WHERE id = :shiftId")
    suspend fun updateShiftEnd(shiftId: Long, endAt: Long)
}
