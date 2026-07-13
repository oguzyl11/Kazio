package com.kazio.app.data.mapper

import com.kazio.app.data.local.room.IncomeEntity
import com.kazio.app.domain.model.IncomeEntry

fun IncomeEntity.toDomain() = IncomeEntry(
    id = id,
    shiftId = shiftId,
    platformId = platformId,
    amount = amount,
    occurredAt = occurredAt,
    note = note
)

fun IncomeEntry.toEntity() = IncomeEntity(
    id = id,
    shiftId = shiftId,
    platformId = platformId,
    amount = amount,
    occurredAt = occurredAt,
    note = note
)
