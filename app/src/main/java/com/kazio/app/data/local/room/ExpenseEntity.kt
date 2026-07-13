package com.kazio.app.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val shiftId: Long?,
    val category: String,
    val amount: Double,
    val occurredAt: Long,
    val note: String?
)
