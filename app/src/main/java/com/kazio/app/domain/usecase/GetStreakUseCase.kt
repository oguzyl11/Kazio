package com.kazio.app.domain.usecase

import com.kazio.app.domain.repository.ExpenseRepository
import com.kazio.app.domain.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.Calendar
import javax.inject.Inject

class GetStreakUseCase @Inject constructor(
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(): Flow<Int> {
        return combine(
            incomeRepository.getAllIncomes(),
            expenseRepository.getAllExpenses()
        ) { incomes, expenses ->
            val allDates = (incomes.map { it.occurredAt } + expenses.map { it.occurredAt })
                .map { timestamp -> getStartOfDay(timestamp) }
                .distinct()
                .sortedDescending()

            if (allDates.isEmpty()) return@combine 0

            var streak = 0
            val today = getStartOfDay(Calendar.getInstance().timeInMillis)
            
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = today
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            val yesterday = calendar.timeInMillis

            var expectedDate = allDates.firstOrNull { it == today || it == yesterday } ?: return@combine 0

            for (date in allDates) {
                if (date == expectedDate) {
                    streak++
                    calendar.timeInMillis = expectedDate
                    calendar.add(Calendar.DAY_OF_YEAR, -1)
                    expectedDate = calendar.timeInMillis
                } else if (date < expectedDate) {
                    break
                }
            }

            streak
        }
    }

    private fun getStartOfDay(timestamp: Long): Long {
        return Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
}
