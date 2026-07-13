package com.kazio.app.domain.model

data class PlatformProfit(
    val platformId: Long,
    val platformName: String,
    val colorTag: String,
    val totalIncome: Double,
    val percentage: Float
)

data class SummaryResult(
    val totalIncome: Double,
    val totalExpense: Double,
    val netProfit: Double,
    val platformProfits: List<PlatformProfit>
)
