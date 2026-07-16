package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.PersonalRecord
import com.kazio.app.domain.repository.PersonalRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPersonalRecordsUseCase @Inject constructor(
    private val personalRecordRepository: PersonalRecordRepository
) {
    operator fun invoke(): Flow<List<PersonalRecord>> {
        return personalRecordRepository.getAllRecords()
    }
}
