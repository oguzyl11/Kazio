package com.kazio.app.domain.usecase

import com.kazio.app.domain.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetActiveShiftIncomeUseCase @Inject constructor(
    private val incomeRepository: IncomeRepository
) {
    operator fun invoke(shiftId: Long): Flow<Double> {
        return incomeRepository.getIncomesForShift(shiftId).map { incomes ->
            incomes.sumOf { it.amount }
        }
    }
}
