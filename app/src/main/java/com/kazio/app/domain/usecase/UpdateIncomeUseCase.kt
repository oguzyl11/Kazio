package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.IncomeEntry
import com.kazio.app.domain.repository.IncomeRepository
import javax.inject.Inject

class UpdateIncomeUseCase @Inject constructor(
    private val incomeRepository: IncomeRepository
) {
    suspend operator fun invoke(entry: IncomeEntry): AddIncomeResult {
        if (entry.amount <= 0) return AddIncomeResult.Error(AddIncomeResult.Reason.AMOUNT_MUST_BE_POSITIVE)
        if (entry.amount > 50_000) return AddIncomeResult.Error(AddIncomeResult.Reason.AMOUNT_TOO_LARGE)
        if (entry.platformId <= 0) return AddIncomeResult.Error(AddIncomeResult.Reason.NO_PLATFORM_SELECTED)

        incomeRepository.updateIncome(entry)
        return AddIncomeResult.Success(entry.id)
    }
}
