package com.kazio.app.domain.model

data class IncomeEntry(
    val id: Long = 0,
    val shiftId: Long?,
    val platformId: Long,
    val amount: Double,
    val occurredAt: Long,
    val note: String?
)
