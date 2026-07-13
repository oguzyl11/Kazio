package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.ExpenseCategory
import com.kazio.app.domain.model.ExpenseEntry
import com.kazio.app.domain.model.IncomeEntry
import com.kazio.app.domain.model.Platform
import com.kazio.app.domain.repository.ExpenseRepository
import com.kazio.app.domain.repository.IncomeRepository
import com.kazio.app.domain.repository.PlatformRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetSummaryUseCaseTest {

    private lateinit var incomeRepository: IncomeRepository
    private lateinit var expenseRepository: ExpenseRepository
    private lateinit var platformRepository: PlatformRepository
    private lateinit var useCase: GetSummaryUseCase

    @Before
    fun setUp() {
        incomeRepository = mockk()
        expenseRepository = mockk()
        platformRepository = mockk()
        useCase = GetSummaryUseCase(incomeRepository, expenseRepository, platformRepository)
    }

    @Test
    fun `summary calculates correct totals and platform percentages`() = runTest {
        val platforms = listOf(
            Platform(1, "Uber", "#111", false),
            Platform(2, "Bolt", "#222", false)
        )
        val incomes = listOf(
            IncomeEntry(1, null, 1, 600.0, 100, null),
            IncomeEntry(2, null, 2, 400.0, 100, null)
        )
        val expenses = listOf(
            ExpenseEntry(1, null, ExpenseCategory.FUEL, 200.0, 100, null)
        )

        every { platformRepository.getAllPlatforms() } returns flowOf(platforms)
        every { incomeRepository.getIncomesForDateRange(any(), any()) } returns flowOf(incomes)
        every { expenseRepository.getExpensesForDateRange(any(), any()) } returns flowOf(expenses)

        val result = useCase(0L, 200L).first()

        assertEquals(1000.0, result.totalIncome, 0.01)
        assertEquals(200.0, result.totalExpense, 0.01)
        assertEquals(800.0, result.netProfit, 0.01)
        
        assertEquals(2, result.platformProfits.size)
        assertEquals(1L, result.platformProfits[0].platformId)
        assertEquals(600.0, result.platformProfits[0].totalIncome, 0.01)
        assertEquals(0.6f, result.platformProfits[0].percentage, 0.01f)
        
        assertEquals(2L, result.platformProfits[1].platformId)
        assertEquals(400.0, result.platformProfits[1].totalIncome, 0.01)
        assertEquals(0.4f, result.platformProfits[1].percentage, 0.01f)
    }
}
