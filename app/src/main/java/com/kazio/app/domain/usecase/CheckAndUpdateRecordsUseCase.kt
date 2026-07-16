package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.PersonalRecord
import com.kazio.app.domain.model.RecordType
import com.kazio.app.domain.repository.PersonalRecordRepository
import kotlinx.coroutines.flow.first
import java.util.Calendar
import javax.inject.Inject

class CheckAndUpdateRecordsUseCase @Inject constructor(
    private val personalRecordRepository: PersonalRecordRepository,
    private val getSummaryUseCase: GetSummaryUseCase
) {
    suspend fun checkDailyProfit() {
        val calendar = Calendar.getInstance()
        val endOfDay = getEndOfDay(calendar.timeInMillis)
        val startOfDay = getStartOfDay(calendar.timeInMillis)
        
        val summary = getSummaryUseCase(startOfDay, endOfDay).first()
        val currentNetProfit = summary.netProfit
        
        checkAndSaveRecord(RecordType.HIGHEST_DAILY_PROFIT, currentNetProfit)
    }

    suspend fun checkWeeklyProfit() {
        val calendar = Calendar.getInstance()
        val endOfWeek = getEndOfWeek(calendar.timeInMillis)
        val startOfWeek = getStartOfWeek(calendar.timeInMillis)
        
        val summary = getSummaryUseCase(startOfWeek, endOfWeek).first()
        val currentNetProfit = summary.netProfit
        
        checkAndSaveRecord(RecordType.HIGHEST_WEEKLY_PROFIT, currentNetProfit)
    }

    suspend fun checkShiftRecords(durationMs: Long, totalIncome: Double) {
        // 1. Longest Shift
        checkAndSaveRecord(RecordType.LONGEST_SHIFT, durationMs.toDouble())

        // 2. Highest Hourly Profit
        val hours = durationMs.toDouble() / (1000 * 60 * 60)
        if (hours > 0) { // Avoid division by zero or very small numbers
            val hourlyProfit = totalIncome / hours
            checkAndSaveRecord(RecordType.HIGHEST_HOURLY_PROFIT, hourlyProfit)
        }
    }

    private suspend fun checkAndSaveRecord(type: RecordType, currentValue: Double) {
        val existingRecord = personalRecordRepository.getRecordByType(type)
        if (existingRecord == null || currentValue > existingRecord.value) {
            personalRecordRepository.saveRecord(
                PersonalRecord(
                    type = type,
                    value = currentValue,
                    achievedAt = System.currentTimeMillis()
                )
            )
        }
    }

    private fun getStartOfDay(timestamp: Long): Long {
        return Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    private fun getEndOfDay(timestamp: Long): Long {
        return Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis
    }

    private fun getStartOfWeek(timestamp: Long): Long {
        return Calendar.getInstance().apply {
            timeInMillis = timestamp
            firstDayOfWeek = Calendar.MONDAY
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    private fun getEndOfWeek(timestamp: Long): Long {
        return Calendar.getInstance().apply {
            timeInMillis = timestamp
            firstDayOfWeek = Calendar.MONDAY
            set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis
    }
}
