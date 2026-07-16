package com.kazio.app.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shifts")
data class ShiftEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val vehicleId: Long,
    val startAt: Long,
    val endAt: Long?,
    val note: String?,
    val totalPausedDuration: Long = 0L,
    val isPaused: Boolean = false,
    val lastPausedAt: Long? = null
)
