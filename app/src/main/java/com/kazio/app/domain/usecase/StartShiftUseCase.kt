package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.Shift
import com.kazio.app.domain.repository.ShiftRepository
import kotlinx.coroutines.flow.firstOrNull
import java.util.Calendar
import javax.inject.Inject

class StartShiftUseCase @Inject constructor(
    private val shiftRepository: ShiftRepository,
    private val getActiveShiftUseCase: GetActiveShiftUseCase
) {
    suspend operator fun invoke(): Result<Long> {
        val activeShift = getActiveShiftUseCase().firstOrNull()
        if (activeShift != null) {
            return Result.failure(IllegalStateException("Zaten devam eden bir vardiya var."))
        }

        val id = shiftRepository.startShift(
            vehicleId = 1L, // Dummy for MVP
            startAt = Calendar.getInstance().timeInMillis,
            note = null
        )
        return Result.success(id)
    }
}
