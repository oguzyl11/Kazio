package com.kazio.app.domain.model

enum class RecommendationType {
    POSITIVE,
    NEGATIVE,
    INFO
}

data class Recommendation(
    val title: String,
    val description: String,
    val type: RecommendationType
)
