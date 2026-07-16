package com.kazio.app.domain.usecase

import com.kazio.app.domain.repository.IncomeRepository
import javax.inject.Inject

class DeleteIncomeUseCase @Inject constructor(
    private val incomeRepository: IncomeRepository
) {
    suspend operator fun invoke(id: Long) {
        incomeRepository.deleteIncome(id)
    }
}
