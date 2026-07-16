package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.PlatformProfit
import com.kazio.app.domain.model.SummaryResult
import com.kazio.app.domain.repository.ExpenseRepository
import com.kazio.app.domain.repository.IncomeRepository
import com.kazio.app.domain.repository.PlatformRepository
import com.kazio.app.domain.repository.ShiftRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetSummaryUseCase @Inject constructor(
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository,
    private val platformRepository: PlatformRepository,
    private val shiftRepository: ShiftRepository
) {
    operator fun invoke(startAt: Long, endAt: Long): Flow<SummaryResult> {
        val incomesFlow = incomeRepository.getIncomesForDateRange(startAt, endAt)
        val expensesFlow = expenseRepository.getExpensesForDateRange(startAt, endAt)
        val platformsFlow = platformRepository.getAllPlatforms()
        val shiftsFlow = shiftRepository.getShiftsForDateRange(startAt, endAt)

        return combine(incomesFlow, expensesFlow, platformsFlow, shiftsFlow) { incomes, expenses, platforms, shifts ->
            val totalIncome = incomes.sumOf { it.amount }
            val totalExpense = expenses.sumOf { it.amount }
            val netProfit = totalIncome - totalExpense

            val platformMap = platforms.associateBy { it.id }
            
            val groupedIncomes = incomes.groupBy { it.platformId }
            
            val platformProfits = groupedIncomes.mapNotNull { (platformId, platformIncomes) ->
                val platform = platformMap[platformId] ?: return@mapNotNull null
                val platformTotal = platformIncomes.sumOf { it.amount }
                val percentage = if (totalIncome > 0) (platformTotal / totalIncome).toFloat() else 0f
                
                // Calculate total duration for this platform
                // Find all shiftIds from incomes for this platform
                val shiftIdsForPlatform = platformIncomes.mapNotNull { it.shiftId }.toSet()
                var totalDurationMillis = 0L
                for (shiftId in shiftIdsForPlatform) {
                    val shift = shifts.find { it.id == shiftId }
                    if (shift != null) {
                        val end = shift.endAt ?: System.currentTimeMillis()
                        totalDurationMillis += (end - shift.startAt - shift.totalPausedDuration)
                    }
                }
                
                val hourlyRate = if (totalDurationMillis > 0) {
                    val hours = totalDurationMillis / (1000.0 * 60 * 60)
                    platformTotal / hours
                } else {
                    0.0
                }
                
                PlatformProfit(
                    platformId = platform.id,
                    platformName = platform.name,
                    colorTag = platform.colorTag,
                    totalIncome = platformTotal,
                    percentage = percentage,
                    hourlyRate = hourlyRate
                )
            }.sortedByDescending { it.totalIncome }

            SummaryResult(
                totalIncome = totalIncome,
                totalExpense = totalExpense,
                netProfit = netProfit,
                platformProfits = platformProfits
            )
        }
    }
}
