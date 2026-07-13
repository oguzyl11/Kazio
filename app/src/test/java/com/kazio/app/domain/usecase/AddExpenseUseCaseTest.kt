package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.ExpenseCategory
import com.kazio.app.domain.repository.ExpenseRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AddExpenseUseCaseTest {

    private lateinit var expenseRepository: ExpenseRepository
    private lateinit var useCase: AddExpenseUseCase

    @Before
    fun setUp() {
        expenseRepository = mockk()
        useCase = AddExpenseUseCase(expenseRepository)
    }

    @Test
    fun `when amount is negative, returns error`() = runTest {
        val result = useCase(amount = -10.0, category = ExpenseCategory.FUEL, shiftId = null, note = null)
        assertTrue(result is AddExpenseResult.Error)
        assertEquals(AddExpenseResult.Reason.AMOUNT_MUST_BE_POSITIVE, (result as AddExpenseResult.Error).reason)
    }

    @Test
    fun `when valid input, saves and returns success`() = runTest {
        coEvery { expenseRepository.addExpense(any()) } returns 2L

        val result = useCase(amount = 150.0, category = ExpenseCategory.MAINTENANCE, shiftId = null, note = null)
        assertTrue(result is AddExpenseResult.Success)
        assertEquals(2L, (result as AddExpenseResult.Success).id)
    }
}
