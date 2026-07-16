package com.kazio.app.domain.model

data class UserPreferences(
    val isRegistered: Boolean,
    val isLoggedIn: Boolean,
    val userName: String,
    val userEmail: String,
    val userPin: String,
    val isOnboardingSeen: Boolean,
    val vehicleModel: String = "",
    val vehiclePlate: String = "",
    val isPremium: Boolean = false
)
