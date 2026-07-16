package com.kazio.app.presentation.addincome

import com.kazio.app.domain.model.Platform
import com.kazio.app.domain.model.FrequentIncome

data class AddIncomeUiState(
    val amount: String = "",
    val selectedPlatformId: Long? = null,
    val note: String = "",
    val platforms: List<Platform> = emptyList(),
    val frequentIncomes: List<FrequentIncome> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val selectedDateMillis: Long? = null
)
