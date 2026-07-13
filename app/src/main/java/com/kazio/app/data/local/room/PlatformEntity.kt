package com.kazio.app.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "platforms")
data class PlatformEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val colorTag: String,
    val isCustom: Boolean
)
