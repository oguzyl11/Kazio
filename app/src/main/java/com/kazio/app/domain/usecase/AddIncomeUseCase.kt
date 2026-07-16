package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.IncomeEntry
import com.kazio.app.domain.repository.IncomeRepository
import java.util.Calendar
import javax.inject.Inject

sealed interface AddIncomeResult {
    data class Success(val id: Long) : AddIncomeResult
    data class Error(val reason: Reason) : AddIncomeResult

    enum class Reason {
        AMOUNT_MUST_BE_POSITIVE,
        AMOUNT_TOO_LARGE,
        NO_PLATFORM_SELECTED
    }
}

class AddIncomeUseCase @Inject constructor(
    private val incomeRepository: IncomeRepository,
    private val checkAndUpdateRecordsUseCase: CheckAndUpdateRecordsUseCase
) {
    suspend operator fun invoke(
        amount: Double,
        platformId: Long,
        shiftId: Long?,
        note: String?,
        occurredAt: Long? = null
    ): AddIncomeResult {
        if (amount <= 0) return AddIncomeResult.Error(AddIncomeResult.Reason.AMOUNT_MUST_BE_POSITIVE)
        if (amount > 50_000) return AddIncomeResult.Error(AddIncomeResult.Reason.AMOUNT_TOO_LARGE)
        if (platformId <= 0) return AddIncomeResult.Error(AddIncomeResult.Reason.NO_PLATFORM_SELECTED)

        val entry = IncomeEntry(
            shiftId = shiftId,
            platformId = platformId,
            amount = amount,
            occurredAt = occurredAt ?: Calendar.getInstance().timeInMillis,
            note = note
        )
        val id = incomeRepository.addIncome(entry)
        
        checkAndUpdateRecordsUseCase.checkDailyProfit()
        checkAndUpdateRecordsUseCase.checkWeeklyProfit()
        
        return AddIncomeResult.Success(id)
    }
}
