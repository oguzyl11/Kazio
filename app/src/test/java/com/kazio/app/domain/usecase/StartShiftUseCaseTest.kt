package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.Shift
import com.kazio.app.domain.repository.ShiftRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class StartShiftUseCaseTest {

    private lateinit var shiftRepository: ShiftRepository
    private lateinit var getActiveShiftUseCase: GetActiveShiftUseCase
    private lateinit var useCase: StartShiftUseCase

    @Before
    fun setUp() {
        shiftRepository = mockk()
        getActiveShiftUseCase = mockk()
        useCase = StartShiftUseCase(shiftRepository, getActiveShiftUseCase)
    }

    @Test
    fun `fails if active shift exists`() = runTest {
        val activeShift = Shift(1, 1000L, null, null)
        every { getActiveShiftUseCase() } returns flowOf(activeShift)

        val result = useCase()
        assertTrue(result.isFailure)
    }

    @Test
    fun `starts shift successfully`() = runTest {
        every { getActiveShiftUseCase() } returns flowOf(null)
        coEvery { shiftRepository.startShift(any()) } returns 1L

        val result = useCase()
        assertTrue(result.isSuccess)
        assertEquals(1L, result.getOrNull())
    }
}
