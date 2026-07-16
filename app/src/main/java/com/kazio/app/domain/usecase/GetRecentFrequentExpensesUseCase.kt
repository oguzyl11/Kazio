package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.FrequentExpense
import com.kazio.app.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecentFrequentExpensesUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<FrequentExpense>> {
        return expenseRepository.getAllExpenses().map { expenses ->
            expenses.sortedByDescending { it.occurredAt }
                .take(50)
                .groupBy { FrequentExpense(it.category, it.amount, it.note) }
                .map { (key, list) -> key to list.size }
                .sortedByDescending { it.second }
                .map { it.first }
                .take(3)
        }
    }
}
