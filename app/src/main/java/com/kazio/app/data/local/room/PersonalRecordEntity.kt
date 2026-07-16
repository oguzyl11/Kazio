package com.kazio.app.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personal_records")
data class PersonalRecordEntity(
    @PrimaryKey val type: String,
    val value: Double,
    val achievedAt: Long
)
