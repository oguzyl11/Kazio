package com.kazio.app.widget

import com.kazio.app.domain.usecase.GetRecommendationsUseCase
import com.kazio.app.domain.usecase.GetSummaryUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetEntryPoint {
    fun getSummaryUseCase(): GetSummaryUseCase
    fun getRecommendationsUseCase(): GetRecommendationsUseCase
}
