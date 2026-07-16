package com.kazio.app.domain.model

data class Shift(
    val id: Long = 0,
    val vehicleId: Long,
    val startAt: Long,
    val endAt: Long?,
    val note: String?,
    val totalPausedDuration: Long = 0L,
    val isPaused: Boolean = false,
    val lastPausedAt: Long? = null
) {
    val isActive: Boolean get() = endAt == null
}
