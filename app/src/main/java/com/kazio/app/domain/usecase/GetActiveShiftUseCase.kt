package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.Shift
import com.kazio.app.domain.repository.ShiftRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActiveShiftUseCase @Inject constructor(
    private val shiftRepository: ShiftRepository
) {
    operator fun invoke(): Flow<Shift?> {
        return shiftRepository.getActiveShift()
    }
}
