package com.kazio.app.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "incomes")
data class IncomeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val shiftId: Long?,
    val platformId: Long,
    val amount: Double,
    val occurredAt: Long,
    val note: String?
)
