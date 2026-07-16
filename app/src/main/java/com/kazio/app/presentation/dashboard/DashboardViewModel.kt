package com.kazio.app.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazio.app.data.local.datastore.DataStoreRepository
import com.kazio.app.domain.usecase.EndShiftUseCase
import com.kazio.app.domain.usecase.GetActiveShiftUseCase
import com.kazio.app.domain.usecase.GetRecommendationsUseCase
import com.kazio.app.domain.usecase.GetSummaryUseCase
import com.kazio.app.domain.usecase.StartShiftUseCase
import com.kazio.app.domain.usecase.PauseShiftUseCase
import com.kazio.app.domain.repository.PersonalRecordRepository
import com.kazio.app.domain.usecase.GetStreakUseCase
import com.kazio.app.domain.usecase.ResumeShiftUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import com.kazio.app.domain.usecase.GetActiveShiftIncomeUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import com.kazio.app.domain.model.ReportType
import com.kazio.app.domain.usecase.GenerateReportUseCase

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getSummaryUseCase: GetSummaryUseCase,
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val getActiveShiftUseCase: GetActiveShiftUseCase,
    private val getActiveShiftIncomeUseCase: GetActiveShiftIncomeUseCase,
    private val getStreakUseCase: GetStreakUseCase,
    private val startShiftUseCase: StartShiftUseCase,
    private val endShiftUseCase: EndShiftUseCase,
    private val pauseShiftUseCase: PauseShiftUseCase,
    private val resumeShiftUseCase: ResumeShiftUseCase,
    private val generateReportUseCase: GenerateReportUseCase,
    private val dataStoreRepository: DataStoreRepository,
    private val personalRecordRepository: PersonalRecordRepository
) : ViewModel() {

    val newRecordEvent = personalRecordRepository.newRecordEvent

    private val _pdfUriEvent = MutableSharedFlow<Uri>()
    val pdfUriEvent = _pdfUriEvent.asSharedFlow()

    private val currentTimestamp = MutableStateFlow(Calendar.getInstance().timeInMillis)

    private val tickerFlow = flow {
        while (true) {
            emit(Calendar.getInstance().timeInMillis)
            delay(1000)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val activeShiftWithIncomeFlow = getActiveShiftUseCase().flatMapLatest { shift ->
        if (shift != null) {
            getActiveShiftIncomeUseCase(shift.id).map { income ->
                Pair(shift, income)
            }
        } else {
            flowOf(Pair(null, 0.0))
        }
    }

    val uiState: StateFlow<DashboardUiState> = combine(
        getSummaryUseCase(getStartOfDay(currentTimestamp.value), getEndOfDay(currentTimestamp.value)),
        activeShiftWithIncomeFlow,
        tickerFlow,
        combine(
            dataStoreRepository.userPreferencesFlow,
            getRecommendationsUseCase(),
            getStreakUseCase()
        ) { prefs, recs, streak -> Triple(prefs, recs, streak) }
    ) { summaryResult, activeShiftData, currentTime, combinedData ->
        val preferences = combinedData.first
        val recommendations = combinedData.second
        val streak = combinedData.third

        val activeShift = activeShiftData.first
        val activeShiftIncome = activeShiftData.second
        
        val durationStr = if (activeShift != null) {
            val diff = if (activeShift.isPaused && activeShift.lastPausedAt != null) {
                // If paused, time is frozen at lastPausedAt
                activeShift.lastPausedAt - activeShift.startAt - activeShift.totalPausedDuration
            } else {
                // If running, time continues to tick
                currentTime - activeShift.startAt - activeShift.totalPausedDuration
            }
            
            val hours = (diff / (1000 * 60 * 60)).coerceAtLeast(0)
            val minutes = ((diff / (1000 * 60)) % 60).coerceAtLeast(0)
            val seconds = ((diff / 1000) % 60).coerceAtLeast(0)
            if (hours > 0) {
                String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
            } else {
                String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
            }
        } else {
            ""
        }

        DashboardUiState.Success(
            dailyNetProfit = summaryResult.netProfit,
            totalIncome = summaryResult.totalIncome,
            totalExpense = summaryResult.totalExpense,
            activeShift = activeShift,
            activeShiftDurationStr = durationStr,
            activeShiftIncome = activeShiftIncome,
            platformProfits = summaryResult.platformProfits.take(3),
            showOnboarding = !preferences.isOnboardingSeen,
            recommendations = recommendations,
            streak = streak,
            isPremium = preferences.isPremium
        ) as DashboardUiState
    }
        .catch { e ->
            emit(DashboardUiState.Error(e.message ?: "Bilinmeyen bir hata oluştu"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DashboardUiState.Loading
        )

    fun completeOnboarding() {
        viewModelScope.launch {
            dataStoreRepository.updateOnboardingSeen(true)
        }
    }

    fun startShift() {
        viewModelScope.launch {
            startShiftUseCase()
        }
    }

    fun endShift() {
        viewModelScope.launch {
            endShiftUseCase()
        }
    }

    fun pauseShift() {
        viewModelScope.launch {
            pauseShiftUseCase()
        }
    }

    fun resumeShift() {
        viewModelScope.launch {
            resumeShiftUseCase()
        }
    }

    fun generateReport(context: Context, type: ReportType) {
        viewModelScope.launch {
            val uri = generateReportUseCase(context, type)
            if (uri != null) {
                _pdfUriEvent.emit(uri)
            }
        }
    }

    private fun getStartOfDay(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    private fun getEndOfDay(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        return calendar.timeInMillis
    }
}
