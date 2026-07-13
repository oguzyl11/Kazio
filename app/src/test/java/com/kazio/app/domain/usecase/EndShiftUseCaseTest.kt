package com.kazio.app.domain.usecase

import com.kazio.app.domain.model.Shift
import com.kazio.app.domain.repository.ShiftRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class EndShiftUseCaseTest {

    private lateinit var shiftRepository: ShiftRepository
    private lateinit var getActiveShiftUseCase: GetActiveShiftUseCase
    private lateinit var useCase: EndShiftUseCase

    @Before
    fun setUp() {
        shiftRepository = mockk()
        getActiveShiftUseCase = mockk()
        useCase = EndShiftUseCase(shiftRepository, getActiveShiftUseCase)
    }

    @Test
    fun `fails if no active shift exists`() = runTest {
        every { getActiveShiftUseCase() } returns flowOf(null)

        val result = useCase()
        assertTrue(result.isFailure)
    }

    @Test
    fun `ends shift successfully`() = runTest {
        val activeShift = Shift(1, 1000L, null, null)
        every { getActiveShiftUseCase() } returns flowOf(activeShift)
        coEvery { shiftRepository.endShift(any()) } returns Unit

        val result = useCase()
        assertTrue(result.isSuccess)
        
        coVerify { 
            shiftRepository.endShift(match { it.id == 1L && it.endedAt != null }) 
        }
    }
}
