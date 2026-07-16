package com.kazio.app.domain.usecase

import android.content.Context
import android.net.Uri
import com.kazio.app.domain.model.ReportType
import com.kazio.app.domain.repository.ExpenseRepository
import com.kazio.app.domain.repository.IncomeRepository
import com.kazio.app.domain.repository.ShiftRepository
import com.kazio.app.util.PdfGenerator
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GenerateReportUseCase @Inject constructor(
    private val shiftRepository: ShiftRepository,
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(context: Context, type: ReportType): Uri? {
        val calendar = Calendar.getInstance()
        val titleFormat: String
        
        when (type) {
            ReportType.DAILY -> {
                titleFormat = SimpleDateFormat("dd MMMM yyyy", Locale("tr", "TR")).format(Date())
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
            ReportType.WEEKLY -> {
                titleFormat = "Bu Hafta (${SimpleDateFormat("ww. 'Hafta -' yyyy", Locale("tr", "TR")).format(Date())})"
                calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
            ReportType.MONTHLY -> {
                titleFormat = SimpleDateFormat("MMMM yyyy", Locale("tr", "TR")).format(Date())
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
        }
        
        val startOfRange = calendar.timeInMillis
        
        when (type) {
            ReportType.DAILY -> {
                calendar.set(Calendar.HOUR_OF_DAY, 23)
                calendar.set(Calendar.MINUTE, 59)
                calendar.set(Calendar.SECOND, 59)
                calendar.set(Calendar.MILLISECOND, 999)
            }
            ReportType.WEEKLY -> {
                calendar.add(Calendar.DAY_OF_WEEK, 6)
                calendar.set(Calendar.HOUR_OF_DAY, 23)
                calendar.set(Calendar.MINUTE, 59)
                calendar.set(Calendar.SECOND, 59)
                calendar.set(Calendar.MILLISECOND, 999)
            }
            ReportType.MONTHLY -> {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                calendar.set(Calendar.HOUR_OF_DAY, 23)
                calendar.set(Calendar.MINUTE, 59)
                calendar.set(Calendar.SECOND, 59)
                calendar.set(Calendar.MILLISECOND, 999)
            }
        }
        val endOfRange = calendar.timeInMillis

        val shifts = shiftRepository.getShiftsForDateRange(startOfRange, endOfRange).first()
        val incomes = incomeRepository.getIncomesForDateRange(startOfRange, endOfRange).first()
        val expenses = expenseRepository.getExpensesForDateRange(startOfRange, endOfRange).first()

        return PdfGenerator.generateReport(context, type, titleFormat, shifts, incomes, expenses)
    }
}
