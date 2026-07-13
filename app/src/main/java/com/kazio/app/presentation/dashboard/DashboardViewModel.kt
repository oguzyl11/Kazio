package com.kazio.app.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazio.app.domain.usecase.CalculateDailyNetUseCase
import com.kazio.app.domain.usecase.GetActiveShiftUseCase
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
    private val calculateDailyNetUseCase: CalculateDailyNetUseCase,
    private val getActiveShiftUseCase: GetActiveShiftUseCase
) : ViewModel() {

    private val currentTimestamp = MutableStateFlow(Calendar.getInstance().timeInMillis)

    val uiState: StateFlow<DashboardUiState> = combine(
        calculateDailyNetUseCase(currentTimestamp.value),
        getActiveShiftUseCase()
    ) { dailyNet, activeShift ->
        DashboardUiState.Success(
            dailyNetProfit = dailyNet,
            activeShift = activeShift
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState.Loading
    )
}
