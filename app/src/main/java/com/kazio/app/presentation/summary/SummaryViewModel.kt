package com.kazio.app.presentation.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazio.app.domain.usecase.GetSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import android.content.Context
import android.net.Uri
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import com.kazio.app.domain.model.ReportType
import com.kazio.app.domain.usecase.GenerateReportUseCase

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val getSummaryUseCase: GetSummaryUseCase,
    private val generateReportUseCase: GenerateReportUseCase
) : ViewModel() {

    private val _pdfUriEvent = MutableSharedFlow<Uri>()
    val pdfUriEvent = _pdfUriEvent.asSharedFlow()

    private val _selectedPeriod = MutableStateFlow(SummaryPeriod.WEEKLY)
    
    val uiState: StateFlow<SummaryUiState> = _selectedPeriod.flatMapLatest { period ->
        val (startAt, endAt) = getTimestampsForPeriod(period)
        getSummaryUseCase(startAt, endAt).map { result ->
            SummaryUiState.Success(period, result) as SummaryUiState
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SummaryUiState.Loading
    )

    fun onPeriodSelected(period: SummaryPeriod) {
        _selectedPeriod.value = period
    }

    fun generateReport(context: Context, type: ReportType) {
        viewModelScope.launch {
            val uri = generateReportUseCase(context, type)
            if (uri != null) {
                _pdfUriEvent.emit(uri)
            }
        }
    }

    private fun getTimestampsForPeriod(period: SummaryPeriod): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        val endAt = calendar.timeInMillis

        when (period) {
            SummaryPeriod.WEEKLY -> {
                calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
            SummaryPeriod.MONTHLY -> {
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
        }
        val startAt = calendar.timeInMillis
        return Pair(startAt, endAt)
    }
}
