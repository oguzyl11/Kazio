package com.kazio.app.presentation.dashboard

import com.kazio.app.domain.model.PlatformProfit
import com.kazio.app.domain.model.Shift

sealed interface DashboardUiState {
    data object Loading : DashboardUiState
    data class Success(
        val dailyNetProfit: Double,
        val totalIncome: Double,
        val totalExpense: Double,
        val activeShift: Shift?,
        val activeShiftDurationStr: String = "",
        val platformProfits: List<PlatformProfit> = emptyList(),
        val showOnboarding: Boolean = false
    ) : DashboardUiState
    data class Error(val message: String) : DashboardUiState
}
