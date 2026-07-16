package com.kazio.app.domain.usecase

import com.kazio.app.domain.repository.ShiftRepository
import kotlinx.coroutines.flow.firstOrNull
import java.util.Calendar
import javax.inject.Inject

class PauseShiftUseCase @Inject constructor(
    private val shiftRepository: ShiftRepository,
    private val getActiveShiftUseCase: GetActiveShiftUseCase
) {
    suspend operator fun invoke(): Result<Unit> {
        val activeShift = getActiveShiftUseCase().firstOrNull()
            ?: return Result.failure(IllegalStateException("Aktif bir vardiya bulunamadı."))

        if (activeShift.isPaused) {
            return Result.failure(IllegalStateException("Vardiya zaten molada."))
        }

        shiftRepository.updateShiftPauseState(
            shiftId = activeShift.id,
            isPaused = true,
            lastPausedAt = Calendar.getInstance().timeInMillis,
            totalPausedDuration = activeShift.totalPausedDuration
        )
        return Result.success(Unit)
    }
}
