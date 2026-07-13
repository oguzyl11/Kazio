package com.kazio.app.data.repository

import com.kazio.app.data.local.room.ShiftDao
import com.kazio.app.data.local.room.ShiftEntity
import com.kazio.app.data.mapper.toDomain
import com.kazio.app.domain.model.Shift
import com.kazio.app.domain.repository.ShiftRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShiftRepositoryImpl @Inject constructor(
    private val shiftDao: ShiftDao
) : ShiftRepository {
    override fun getActiveShift(): Flow<Shift?> {
        return shiftDao.getActiveShift().map { it?.toDomain() }
    }

    override suspend fun startShift(vehicleId: Long, startAt: Long, note: String?): Long {
        return shiftDao.insertShift(
            ShiftEntity(
                vehicleId = vehicleId,
                startAt = startAt,
                endAt = null,
                note = note
            )
        )
    }

    override suspend fun endShift(shiftId: Long, endAt: Long) {
        shiftDao.updateShiftEnd(shiftId, endAt)
    }
}
