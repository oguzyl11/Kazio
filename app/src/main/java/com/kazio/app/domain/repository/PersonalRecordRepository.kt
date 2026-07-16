package com.kazio.app.domain.repository

import com.kazio.app.domain.model.PersonalRecord
import com.kazio.app.domain.model.RecordType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface PersonalRecordRepository {
    val newRecordEvent: SharedFlow<PersonalRecord>
    
    fun getAllRecords(): Flow<List<PersonalRecord>>
    suspend fun getRecordByType(type: RecordType): PersonalRecord?
    suspend fun saveRecord(record: PersonalRecord)
}
