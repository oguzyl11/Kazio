package com.kazio.app.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazio.app.domain.usecase.EndShiftUseCase
import com.kazio.app.domain.usecase.GetActiveShiftUseCase
import com.kazio.app.domain.usecase.GetSummaryUseCase
import com.kazio.app.domain.usecase.StartShiftUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getSummaryUseCase: GetSummaryUseCase,
    private val getActiveShiftUseCase: GetActiveShiftUseCase,
    private val startShiftUseCase: StartShiftUseCase,
    private val endShiftUseCase: EndShiftUseCase
) : ViewModel() {

    private val currentTimestamp = MutableStateFlow(Calendar.getInstance().timeInMillis)

    private val tickerFlow = flow {
        while (true) {
            emit(Calendar.getInstance().timeInMillis)
            delay(1000)
        }
    }

    val uiState: StateFlow<DashboardUiState> = combine(
        getSummaryUseCase(getStartOfDay(currentTimestamp.value), getEndOfDay(currentTimestamp.value)),
        getActiveShiftUseCase(),
        tickerFlow
    ) { summaryResult, activeShift, currentTime ->
        val durationStr = if (activeShift != null) {
            val diff = currentTime - activeShift.startAt
            val hours = (diff / (1000 * 60 * 60))
            val minutes = (diff / (1000 * 60)) % 60
            String.format(Locale.getDefault(), "%02d:%02d", hours, minutes)
        } else {
            ""
        }

        DashboardUiState.Success(
            dailyNetProfit = summaryResult.netProfit,
            totalIncome = summaryResult.totalIncome,
            totalExpense = summaryResult.totalExpense,
            activeShift = activeShift,
            activeShiftDurationStr = durationStr,
            platformProfits = summaryResult.platformProfits.take(3)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState.Loading
    )

    fun startShift() {
        viewModelScope.launch {
            startShiftUseCase()
        }
    }

    fun endShift() {
        viewModelScope.launch {
            endShiftUseCase()
        }
    }

    private fun getStartOfDay(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    private fun getEndOfDay(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        return calendar.timeInMillis
    }
}
