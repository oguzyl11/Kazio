package com.kazio.app.domain.usecase

import com.kazio.app.domain.repository.IncomeRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AddIncomeUseCaseTest {

    private lateinit var incomeRepository: IncomeRepository
    private lateinit var useCase: AddIncomeUseCase

    @Before
    fun setUp() {
        incomeRepository = mockk()
        useCase = AddIncomeUseCase(incomeRepository)
    }

    @Test
    fun `when amount is negative, returns error`() = runTest {
        val result = useCase(amount = -10.0, platformId = 1, shiftId = null, note = null)
        assertTrue(result is AddIncomeResult.Error)
        assertEquals(AddIncomeResult.Reason.AMOUNT_MUST_BE_POSITIVE, (result as AddIncomeResult.Error).reason)
    }

    @Test
    fun `when amount is zero, returns error`() = runTest {
        val result = useCase(amount = 0.0, platformId = 1, shiftId = null, note = null)
        assertTrue(result is AddIncomeResult.Error)
        assertEquals(AddIncomeResult.Reason.AMOUNT_MUST_BE_POSITIVE, (result as AddIncomeResult.Error).reason)
    }

    @Test
    fun `when amount is too large, returns error`() = runTest {
        val result = useCase(amount = 100_000.0, platformId = 1, shiftId = null, note = null)
        assertTrue(result is AddIncomeResult.Error)
        assertEquals(AddIncomeResult.Reason.AMOUNT_TOO_LARGE, (result as AddIncomeResult.Error).reason)
    }

    @Test
    fun `when platform id is invalid, returns error`() = runTest {
        val result = useCase(amount = 100.0, platformId = 0, shiftId = null, note = null)
        assertTrue(result is AddIncomeResult.Error)
        assertEquals(AddIncomeResult.Reason.NO_PLATFORM_SELECTED, (result as AddIncomeResult.Error).reason)
    }

    @Test
    fun `when valid input, saves and returns success`() = runTest {
        coEvery { incomeRepository.addIncome(any()) } returns 1L

        val result = useCase(amount = 150.0, platformId = 1, shiftId = null, note = null)
        assertTrue(result is AddIncomeResult.Success)
        assertEquals(1L, (result as AddIncomeResult.Success).id)
    }
}
