package com.kazio.app.domain.usecase

import com.kazio.app.domain.repository.ShiftRepository
import kotlinx.coroutines.flow.firstOrNull
import java.util.Calendar
import javax.inject.Inject

class EndShiftUseCase @Inject constructor(
    private val shiftRepository: ShiftRepository,
    private val getActiveShiftUseCase: GetActiveShiftUseCase,
    private val getActiveShiftIncomeUseCase: GetActiveShiftIncomeUseCase,
    private val checkAndUpdateRecordsUseCase: CheckAndUpdateRecordsUseCase
) {
    suspend operator fun invoke(): Result<Unit> {
        val activeShift = getActiveShiftUseCase().firstOrNull()
            ?: return Result.failure(IllegalStateException("Aktif bir vardiya bulunamadı."))

        val endTime = Calendar.getInstance().timeInMillis
        shiftRepository.endShift(
            shiftId = activeShift.id,
            endAt = endTime
        )
        
        val duration = endTime - activeShift.startAt - activeShift.totalPausedDuration
        val shiftIncome = getActiveShiftIncomeUseCase(activeShift.id).firstOrNull() ?: 0.0
        checkAndUpdateRecordsUseCase.checkShiftRecords(durationMs = duration, totalIncome = shiftIncome)

        return Result.success(Unit)
    }
}
