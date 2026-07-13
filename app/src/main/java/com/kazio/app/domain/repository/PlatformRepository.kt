package com.kazio.app.domain.repository

import com.kazio.app.domain.model.Platform
import kotlinx.coroutines.flow.Flow

interface PlatformRepository {
    fun getAllPlatforms(): Flow<List<Platform>>
    suspend fun addCustomPlatform(name: String, colorTag: String): Long
}
