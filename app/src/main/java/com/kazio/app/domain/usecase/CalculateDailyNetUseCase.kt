package com.kazio.app.domain.usecase

import com.kazio.app.domain.repository.ExpenseRepository
import com.kazio.app.domain.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.Calendar
import javax.inject.Inject

class CalculateDailyNetUseCase @Inject constructor(
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(timestamp: Long): Flow<Double> {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfDay = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val endOfDay = calendar.timeInMillis - 1

        val incomesFlow = incomeRepository.getIncomesForDateRange(startOfDay, endOfDay)
        val expensesFlow = expenseRepository.getExpensesForDateRange(startOfDay, endOfDay)

        return combine(incomesFlow, expensesFlow) { incomes, expenses ->
            val totalIncome = incomes.sumOf { it.amount }
            val totalExpense = expenses.sumOf { it.amount }
            totalIncome - totalExpense
        }
    }
}
