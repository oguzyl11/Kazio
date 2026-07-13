package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.Platform
import com.kazio.app.domain.repository.PlatformRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlatformsUseCase @Inject constructor(
    private val platformRepository: PlatformRepository
) {
    operator fun invoke(): Flow<List<Platform>> {
        return platformRepository.getAllPlatforms()
    }
}
