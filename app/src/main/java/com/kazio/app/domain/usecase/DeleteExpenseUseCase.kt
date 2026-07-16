package com.kazio.app.domain.usecase

import com.kazio.app.domain.repository.ExpenseRepository
import javax.inject.Inject

class DeleteExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(id: Long) {
        expenseRepository.deleteExpense(id)
    }
}
