package com.kazio.app.data.mapper

import com.kazio.app.data.local.room.ExpenseEntity
import com.kazio.app.domain.model.ExpenseCategory
import com.kazio.app.domain.model.ExpenseEntry

fun ExpenseEntity.toDomain() = ExpenseEntry(
    id = id,
    shiftId = shiftId,
    category = ExpenseCategory.valueOf(category),
    amount = amount,
    occurredAt = occurredAt,
    note = note
)

fun ExpenseEntry.toEntity() = ExpenseEntity(
    id = id,
    shiftId = shiftId,
    category = category.name,
    amount = amount,
    occurredAt = occurredAt,
    note = note
)
