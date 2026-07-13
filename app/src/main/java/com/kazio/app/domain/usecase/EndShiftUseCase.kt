package com.kazio.app.domain.usecase

import com.kazio.app.domain.repository.ShiftRepository
import kotlinx.coroutines.flow.firstOrNull
import java.util.Calendar
import javax.inject.Inject

class EndShiftUseCase @Inject constructor(
    private val shiftRepository: ShiftRepository,
    private val getActiveShiftUseCase: GetActiveShiftUseCase
) {
    suspend operator fun invoke(): Result<Unit> {
        val activeShift = getActiveShiftUseCase().firstOrNull()
            ?: return Result.failure(IllegalStateException("Aktif bir vardiya bulunamadı."))

        shiftRepository.endShift(
            shiftId = activeShift.id,
            endAt = Calendar.getInstance().timeInMillis
        )
        return Result.success(Unit)
    }
}
