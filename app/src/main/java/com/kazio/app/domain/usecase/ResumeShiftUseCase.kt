package com.kazio.app.domain.usecase

import com.kazio.app.domain.repository.ShiftRepository
import kotlinx.coroutines.flow.firstOrNull
import java.util.Calendar
import javax.inject.Inject

class ResumeShiftUseCase @Inject constructor(
    private val shiftRepository: ShiftRepository,
    private val getActiveShiftUseCase: GetActiveShiftUseCase
) {
    suspend operator fun invoke(): Result<Unit> {
        val activeShift = getActiveShiftUseCase().firstOrNull()
            ?: return Result.failure(IllegalStateException("Aktif bir vardiya bulunamadı."))

        if (!activeShift.isPaused || activeShift.lastPausedAt == null) {
            return Result.failure(IllegalStateException("Vardiya molada değil."))
        }

        val currentTime = Calendar.getInstance().timeInMillis
        val pausedDuration = currentTime - activeShift.lastPausedAt
        val newTotalPausedDuration = activeShift.totalPausedDuration + pausedDuration

        shiftRepository.updateShiftPauseState(
            shiftId = activeShift.id,
            isPaused = false,
            lastPausedAt = null,
            totalPausedDuration = newTotalPausedDuration
        )
        return Result.success(Unit)
    }
}
