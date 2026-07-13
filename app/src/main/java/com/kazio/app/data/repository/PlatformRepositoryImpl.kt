package com.kazio.app.data.repository

import com.kazio.app.data.local.room.PlatformDao
import com.kazio.app.data.local.room.PlatformEntity
import com.kazio.app.data.mapper.toDomain
import com.kazio.app.domain.model.Platform
import com.kazio.app.domain.repository.PlatformRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlatformRepositoryImpl @Inject constructor(
    private val platformDao: PlatformDao
) : PlatformRepository {
    override fun getAllPlatforms(): Flow<List<Platform>> {
        return platformDao.getAllPlatforms().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun addCustomPlatform(name: String, colorTag: String): Long {
        val entity = PlatformEntity(name = name, colorTag = colorTag, isCustom = true)
        return platformDao.insertPlatform(entity)
    }
}
