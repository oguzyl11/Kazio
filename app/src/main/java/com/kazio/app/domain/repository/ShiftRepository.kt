package com.kazio.app.domain.repository

import com.kazio.app.domain.model.Shift
import kotlinx.coroutines.flow.Flow

interface ShiftRepository {
    fun getActiveShift(): Flow<Shift?>
    fun getShiftsForDateRange(startAt: Long, endAt: Long): Flow<List<Shift>>
    suspend fun startShift(vehicleId: Long, startAt: Long, note: String?): Long
    suspend fun endShift(shiftId: Long, endAt: Long)
    suspend fun updateShiftPauseState(shiftId: Long, isPaused: Boolean, lastPausedAt: Long?, totalPausedDuration: Long)
}
