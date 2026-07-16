package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.ExpenseEntry
import com.kazio.app.domain.repository.ExpenseRepository
import javax.inject.Inject

class UpdateExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(entry: ExpenseEntry): AddExpenseResult {
        if (entry.amount <= 0) return AddExpenseResult.Error(AddExpenseResult.Reason.AMOUNT_MUST_BE_POSITIVE)
        if (entry.amount > 50_000) return AddExpenseResult.Error(AddExpenseResult.Reason.AMOUNT_TOO_LARGE)

        expenseRepository.updateExpense(entry)
        return AddExpenseResult.Success(entry.id)
    }
}
