package com.kazio.app.domain.usecase

import android.content.Context
import android.net.Uri
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

class GenerateMonthlyReportUseCase @Inject constructor(
    private val shiftRepository: ShiftRepository,
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(context: Context): Uri? {
        val calendar = Calendar.getInstance()
        val monthName = SimpleDateFormat("MMMM yyyy", Locale("tr", "TR")).format(Date())
        
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfMonth = calendar.timeInMillis
        
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfMonth = calendar.timeInMillis

        val shifts = shiftRepository.getShiftsForDateRange(startOfMonth, endOfMonth).first()
        val incomes = incomeRepository.getIncomesForDateRange(startOfMonth, endOfMonth).first()
        val expenses = expenseRepository.getExpensesForDateRange(startOfMonth, endOfMonth).first()

        return PdfGenerator.generateMonthlyReport(context, monthName, shifts, incomes, expenses)
    }
}
