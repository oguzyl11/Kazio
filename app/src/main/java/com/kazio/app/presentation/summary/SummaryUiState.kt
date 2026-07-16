package com.kazio.app.presentation.summary

import com.kazio.app.domain.model.PersonalRecord
import com.kazio.app.domain.model.SummaryResult

enum class SummaryPeriod {
    WEEKLY, MONTHLY
}

sealed interface SummaryUiState {
    data object Loading : SummaryUiState
    data class Success(
        val period: SummaryPeriod,
        val result: SummaryResult,
        val records: List<PersonalRecord> = emptyList()
    ) : SummaryUiState
    data class Error(val message: String) : SummaryUiState
}
