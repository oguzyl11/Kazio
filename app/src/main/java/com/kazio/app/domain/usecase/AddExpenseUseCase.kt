package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.ExpenseCategory
import com.kazio.app.domain.model.ExpenseEntry
import com.kazio.app.domain.repository.ExpenseRepository
import java.util.Calendar
import javax.inject.Inject

sealed interface AddExpenseResult {
    data class Success(val id: Long) : AddExpenseResult
    data class Error(val reason: Reason) : AddExpenseResult

    enum class Reason {
        AMOUNT_MUST_BE_POSITIVE,
        AMOUNT_TOO_LARGE
    }
}

class AddExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(
        amount: Double,
        category: ExpenseCategory,
        shiftId: Long?,
        note: String?
    ): AddExpenseResult {
        if (amount <= 0) return AddExpenseResult.Error(AddExpenseResult.Reason.AMOUNT_MUST_BE_POSITIVE)
        if (amount > 50_000) return AddExpenseResult.Error(AddExpenseResult.Reason.AMOUNT_TOO_LARGE)

        val entry = ExpenseEntry(
            shiftId = shiftId,
            category = category,
            amount = amount,
            occurredAt = Calendar.getInstance().timeInMillis,
            note = note
        )
        val id = expenseRepository.addExpense(entry)
        return AddExpenseResult.Success(id)
    }
}
