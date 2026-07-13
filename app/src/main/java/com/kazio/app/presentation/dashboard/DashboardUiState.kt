package com.kazio.app.presentation.dashboard

import com.kazio.app.domain.model.Shift

sealed interface DashboardUiState {
    data object Loading : DashboardUiState
    data class Success(
        val dailyNetProfit: Double,
        val activeShift: Shift?
    ) : DashboardUiState
    data class Error(val message: String) : DashboardUiState
}
