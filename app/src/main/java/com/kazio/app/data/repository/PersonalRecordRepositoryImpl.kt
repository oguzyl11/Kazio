package com.kazio.app.data.repository

import com.kazio.app.data.local.room.PersonalRecordDao
import com.kazio.app.data.local.room.PersonalRecordEntity
import com.kazio.app.domain.model.PersonalRecord
import com.kazio.app.domain.model.RecordType
import com.kazio.app.domain.repository.PersonalRecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PersonalRecordRepositoryImpl @Inject constructor(
    private val dao: PersonalRecordDao
) : PersonalRecordRepository {

    private val _newRecordEvent = MutableSharedFlow<PersonalRecord>()
    override val newRecordEvent = _newRecordEvent.asSharedFlow()

    override fun getAllRecords(): Flow<List<PersonalRecord>> {
        return dao.getAllRecords().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getRecordByType(type: RecordType): PersonalRecord? {
        return dao.getRecordByType(type.name)?.toDomain()
    }

    override suspend fun saveRecord(record: PersonalRecord) {
        dao.insertRecord(record.toEntity())
        _newRecordEvent.emit(record)
    }
}

// Mappers
fun PersonalRecordEntity.toDomain(): PersonalRecord {
    return PersonalRecord(
        type = RecordType.valueOf(this.type),
        value = this.value,
        achievedAt = this.achievedAt
    )
}

fun PersonalRecord.toEntity(): PersonalRecordEntity {
    return PersonalRecordEntity(
        type = this.type.name,
        value = this.value,
        achievedAt = this.achievedAt
    )
}
