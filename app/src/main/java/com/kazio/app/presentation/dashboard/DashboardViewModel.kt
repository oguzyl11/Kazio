package com.kazio.app.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazio.app.domain.model.PlatformProfit
import com.kazio.app.domain.usecase.GetActiveShiftUseCase
import com.kazio.app.domain.usecase.GetSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getSummaryUseCase: GetSummaryUseCase,
    private val getActiveShiftUseCase: GetActiveShiftUseCase
) : ViewModel() {

    private val currentTimestamp = MutableStateFlow(Calendar.getInstance().timeInMillis)

    val uiState: StateFlow<DashboardUiState> = combine(
        getSummaryUseCase(getStartOfDay(currentTimestamp.value), getEndOfDay(currentTimestamp.value)),
        getActiveShiftUseCase()
    ) { summaryResult, activeShift ->
        DashboardUiState.Success(
            dailyNetProfit = summaryResult.netProfit,
            activeShift = activeShift,
            platformProfits = summaryResult.platformProfits.take(3)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState.Loading
    )

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
