package com.kazio.app.domain.model

enum class VehicleType {
    CAR, MOTORCYCLE, BICYCLE
}

data class Vehicle(
    val id: Long = 0,
    val name: String,
    val type: VehicleType
)
