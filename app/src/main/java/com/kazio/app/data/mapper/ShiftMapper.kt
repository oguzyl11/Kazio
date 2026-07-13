package com.kazio.app.data.mapper

import com.kazio.app.data.local.room.ShiftEntity
import com.kazio.app.domain.model.Shift

fun ShiftEntity.toDomain() = Shift(
    id = id,
    vehicleId = vehicleId,
    startAt = startAt,
    endAt = endAt,
    note = note
)

fun Shift.toEntity() = ShiftEntity(
    id = id,
    vehicleId = vehicleId,
    startAt = startAt,
    endAt = endAt,
    note = note
)
