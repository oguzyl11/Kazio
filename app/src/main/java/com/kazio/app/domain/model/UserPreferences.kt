package com.kazio.app.domain.model

data class UserPreferences(
    val isRegistered: Boolean,
    val userName: String,
    val userEmail: String,
    val userPin: String,
    val isOnboardingSeen: Boolean
)
