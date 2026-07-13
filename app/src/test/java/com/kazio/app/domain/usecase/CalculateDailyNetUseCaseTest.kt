package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.ExpenseCategory
import com.kazio.app.domain.model.ExpenseEntry
import com.kazio.app.domain.model.IncomeEntry
import com.kazio.app.domain.repository.ExpenseRepository
import com.kazio.app.domain.repository.IncomeRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Calendar

class CalculateDailyNetUseCaseTest {

    private lateinit var incomeRepository: IncomeRepository
    private lateinit var expenseRepository: ExpenseRepository
    private lateinit var useCase: CalculateDailyNetUseCase

    @Before
    fun setUp() {
        incomeRepository = mockk()
        expenseRepository = mockk()
        useCase = CalculateDailyNetUseCase(incomeRepository, expenseRepository)
    }

    @Test
    fun `daily net calculation subtracts all expense categories from income`() = runTest {
        val timestamp = Calendar.getInstance().timeInMillis
        val incomes = listOf(
            IncomeEntry(1, null, 1, 1000.0, timestamp, null),
            IncomeEntry(2, null, 1, 500.0, timestamp, null)
        )
        val expenses = listOf(
            ExpenseEntry(1, null, ExpenseCategory.FUEL, 200.0, timestamp, null),
            ExpenseEntry(2, null, ExpenseCategory.OTHER, 50.0, timestamp, null)
        )

        every { incomeRepository.getIncomesForDateRange(any(), any()) } returns flowOf(incomes)
        every { expenseRepository.getExpensesForDateRange(any(), any()) } returns flowOf(expenses)

        val result = useCase(timestamp).first()
        assertEquals(1250.0, result, 0.001)
    }

    @Test
    fun `daily net returns negative when expenses exceed income`() = runTest {
        val timestamp = Calendar.getInstance().timeInMillis
        val incomes = listOf(
            IncomeEntry(1, null, 1, 100.0, timestamp, null)
        )
        val expenses = listOf(
            ExpenseEntry(1, null, ExpenseCategory.MAINTENANCE, 500.0, timestamp, null)
        )

        every { incomeRepository.getIncomesForDateRange(any(), any()) } returns flowOf(incomes)
        every { expenseRepository.getExpensesForDateRange(any(), any()) } returns flowOf(expenses)

        val result = useCase(timestamp).first()
        assertEquals(-400.0, result, 0.001)
    }

    @Test
    fun `daily net returns zero when no incomes and expenses`() = runTest {
        val timestamp = Calendar.getInstance().timeInMillis
        
        every { incomeRepository.getIncomesForDateRange(any(), any()) } returns flowOf(emptyList())
        every { expenseRepository.getExpensesForDateRange(any(), any()) } returns flowOf(emptyList())

        val result = useCase(timestamp).first()
        assertEquals(0.0, result, 0.001)
    }
}
