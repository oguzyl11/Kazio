package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.PlatformProfit
import com.kazio.app.domain.model.SummaryResult
import com.kazio.app.domain.repository.ExpenseRepository
import com.kazio.app.domain.repository.IncomeRepository
import com.kazio.app.domain.repository.PlatformRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetSummaryUseCase @Inject constructor(
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository,
    private val platformRepository: PlatformRepository
) {
    operator fun invoke(startAt: Long, endAt: Long): Flow<SummaryResult> {
        val incomesFlow = incomeRepository.getIncomesForDateRange(startAt, endAt)
        val expensesFlow = expenseRepository.getExpensesForDateRange(startAt, endAt)
        val platformsFlow = platformRepository.getAllPlatforms()

        return combine(incomesFlow, expensesFlow, platformsFlow) { incomes, expenses, platforms ->
            val totalIncome = incomes.sumOf { it.amount }
            val totalExpense = expenses.sumOf { it.amount }
            val netProfit = totalIncome - totalExpense

            val platformMap = platforms.associateBy { it.id }
            
            val groupedIncomes = incomes.groupBy { it.platformId }
            
            val platformProfits = groupedIncomes.mapNotNull { (platformId, platformIncomes) ->
                val platform = platformMap[platformId] ?: return@mapNotNull null
                val platformTotal = platformIncomes.sumOf { it.amount }
                val percentage = if (totalIncome > 0) (platformTotal / totalIncome).toFloat() else 0f
                
                PlatformProfit(
                    platformId = platform.id,
                    platformName = platform.name,
                    colorTag = platform.colorTag,
                    totalIncome = platformTotal,
                    percentage = percentage
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
