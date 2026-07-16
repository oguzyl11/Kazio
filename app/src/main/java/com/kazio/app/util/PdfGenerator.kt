package com.kazio.app.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.core.content.FileProvider
import com.kazio.app.domain.model.ExpenseEntry
import com.kazio.app.domain.model.IncomeEntry
import com.kazio.app.domain.model.ReportType
import com.kazio.app.domain.model.Shift
import java.io.File
import java.io.FileOutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PdfGenerator {

    private const val PAGE_WIDTH = 595
    private const val PAGE_HEIGHT = 842
    private const val MARGIN = 50f
    
    // Brand Colors
    private val COLOR_PRIMARY = Color.parseColor("#4CAF50") // Green shade for primary
    private val COLOR_BACKGROUND = Color.parseColor("#F9F9F9")
    private val COLOR_TEXT_PRIMARY = Color.parseColor("#212121")
    private val COLOR_TEXT_SECONDARY = Color.parseColor("#757575")
    private val COLOR_DIVIDER = Color.parseColor("#E0E0E0")
    private val COLOR_INCOME = Color.parseColor("#388E3C")
    private val COLOR_EXPENSE = Color.parseColor("#D32F2F")

    fun generateReport(
        context: Context,
        type: ReportType,
        titleStr: String,
        shifts: List<Shift>,
        incomes: List<IncomeEntry>,
        expenses: List<ExpenseEntry>
    ): Uri? {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, 1).create()
        var page = document.startPage(pageInfo)
        var canvas = page.canvas
        val paint = Paint()

        var yPos = MARGIN

        // Helper to handle pagination
        fun checkNewPage(requiredSpace: Float) {
            if (yPos + requiredSpace > PAGE_HEIGHT - MARGIN) {
                document.finishPage(page)
                page = document.startPage(pageInfo)
                canvas = page.canvas
                yPos = MARGIN
            }
        }

        // Draw Header
        paint.color = COLOR_PRIMARY
        canvas.drawRect(0f, 0f, PAGE_WIDTH.toFloat(), 120f, paint)

        paint.color = Color.WHITE
        paint.textSize = 28f
        paint.isFakeBoldText = true
        paint.textAlign = Paint.Align.CENTER
        
        val reportTypeName = when(type) {
            ReportType.DAILY -> "Günlük Kazanç Raporu"
            ReportType.WEEKLY -> "Haftalık Kazanç Raporu"
            ReportType.MONTHLY -> "Aylık Kazanç Raporu"
        }
        
        canvas.drawText("KAZIO", PAGE_WIDTH / 2f, 50f, paint)
        paint.textSize = 18f
        paint.isFakeBoldText = false
        canvas.drawText("$reportTypeName - $titleStr", PAGE_WIDTH / 2f, 85f, paint)
        
        paint.textAlign = Paint.Align.LEFT
        yPos = 150f

        // Calculate Totals
        val totalIncome = incomes.sumOf { it.amount }
        val totalExpense = expenses.sumOf { it.amount }
        val netProfit = totalIncome - totalExpense

        var totalHours = 0.0
        shifts.forEach { shift ->
            val end = shift.endAt ?: System.currentTimeMillis()
            totalHours += (end - shift.startAt) / (1000.0 * 60 * 60)
        }

        val formatter = NumberFormat.getCurrencyInstance(Locale("tr", "TR"))

        // Draw Summary Cards
        val cardWidth = (PAGE_WIDTH - (MARGIN * 2) - 20) / 3f
        
        fun drawSummaryCard(x: Float, y: Float, title: String, value: String, valueColor: Int) {
            val rect = RectF(x, y, x + cardWidth, y + 80f)
            paint.color = COLOR_BACKGROUND
            canvas.drawRoundRect(rect, 8f, 8f, paint)
            
            paint.color = COLOR_DIVIDER
            paint.style = Paint.Style.STROKE
            canvas.drawRoundRect(rect, 8f, 8f, paint)
            paint.style = Paint.Style.FILL

            paint.color = COLOR_TEXT_SECONDARY
            paint.textSize = 12f
            paint.textAlign = Paint.Align.CENTER
            canvas.drawText(title, x + (cardWidth / 2f), y + 30f, paint)
            
            paint.color = valueColor
            paint.textSize = 16f
            paint.isFakeBoldText = true
            canvas.drawText(value, x + (cardWidth / 2f), y + 60f, paint)
            paint.isFakeBoldText = false
            paint.textAlign = Paint.Align.LEFT
        }

        drawSummaryCard(MARGIN, yPos, "Toplam Çalışma", "${String.format("%.1f", totalHours)} Saat", COLOR_TEXT_PRIMARY)
        drawSummaryCard(MARGIN + cardWidth + 10f, yPos, "Toplam Gelir", formatter.format(totalIncome), COLOR_INCOME)
        drawSummaryCard(MARGIN + (cardWidth + 10f) * 2, yPos, "Toplam Gider", formatter.format(totalExpense), COLOR_EXPENSE)
        
        yPos += 100f
        
        // Large Net Profit
        val netRect = RectF(MARGIN, yPos, PAGE_WIDTH - MARGIN, yPos + 60f)
        paint.color = if (netProfit >= 0) Color.parseColor("#E8F5E9") else Color.parseColor("#FFEBEE")
        canvas.drawRoundRect(netRect, 8f, 8f, paint)
        
        paint.color = if (netProfit >= 0) COLOR_INCOME else COLOR_EXPENSE
        paint.textSize = 20f
        paint.isFakeBoldText = true
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("Net Kazanç: ${formatter.format(netProfit)}", PAGE_WIDTH / 2f, yPos + 38f, paint)
        paint.textAlign = Paint.Align.LEFT
        paint.isFakeBoldText = false
        
        yPos += 100f

        if (type == ReportType.DAILY) {
            // Daily Report: Show detailed list of incomes and expenses
            paint.color = COLOR_TEXT_PRIMARY
            paint.textSize = 18f
            paint.isFakeBoldText = true
            canvas.drawText("İşlem Detayları", MARGIN, yPos, paint)
            paint.isFakeBoldText = false
            yPos += 20f
            
            val timeFormat = SimpleDateFormat("HH:mm", Locale("tr", "TR"))
            
            val allTransactions = mutableListOf<Pair<Long, String>>()
            incomes.forEach { allTransactions.add(Pair(it.occurredAt, "Gelir (+${formatter.format(it.amount)}) ${it.note ?: ""}")) }
            expenses.forEach { allTransactions.add(Pair(it.occurredAt, "Gider (-${formatter.format(it.amount)}) - ${it.category} ${it.note ?: ""}")) }
            
            allTransactions.sortByDescending { it.first }
            
            if (allTransactions.isEmpty()) {
                yPos += 20f
                paint.color = COLOR_TEXT_SECONDARY
                paint.textSize = 14f
                canvas.drawText("Kayıtlı işlem bulunmamaktadır.", MARGIN, yPos, paint)
            } else {
                allTransactions.forEach { tx ->
                    checkNewPage(40f)
                    val timeStr = timeFormat.format(Date(tx.first))
                    
                    paint.color = COLOR_BACKGROUND
                    canvas.drawRect(MARGIN, yPos, PAGE_WIDTH - MARGIN, yPos + 30f, paint)
                    
                    paint.color = COLOR_TEXT_SECONDARY
                    paint.textSize = 12f
                    canvas.drawText(timeStr, MARGIN + 10f, yPos + 20f, paint)
                    
                    val isIncome = tx.second.startsWith("Gelir")
                    paint.color = if (isIncome) COLOR_INCOME else COLOR_EXPENSE
                    paint.textSize = 14f
                    canvas.drawText(tx.second, MARGIN + 80f, yPos + 20f, paint)
                    
                    yPos += 35f
                }
            }

        } else {
            // Weekly/Monthly Report: Show Day by Day summary table
            paint.color = COLOR_TEXT_PRIMARY
            paint.textSize = 18f
            paint.isFakeBoldText = true
            canvas.drawText("Günlük Döküm", MARGIN, yPos, paint)
            paint.isFakeBoldText = false
            yPos += 30f

            // Table Header
            paint.color = COLOR_PRIMARY
            canvas.drawRect(MARGIN, yPos - 20f, PAGE_WIDTH - MARGIN, yPos + 10f, paint)
            
            paint.color = Color.WHITE
            paint.textSize = 12f
            paint.isFakeBoldText = true
            canvas.drawText("Tarih", MARGIN + 10f, yPos, paint)
            canvas.drawText("Saat", MARGIN + 100f, yPos, paint)
            canvas.drawText("Gelir", MARGIN + 180f, yPos, paint)
            canvas.drawText("Gider", MARGIN + 280f, yPos, paint)
            canvas.drawText("Net", MARGIN + 380f, yPos, paint)
            paint.isFakeBoldText = false

            yPos += 25f

            val dateFormat = SimpleDateFormat("dd MMM", Locale("tr", "TR"))
            val dayFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            
            val days = incomes.map { dayFormat.format(Date(it.occurredAt)) }
                .plus(expenses.map { dayFormat.format(Date(it.occurredAt)) })
                .plus(shifts.map { dayFormat.format(Date(it.startAt)) })
                .distinct()
                .sorted()

            var alternateBg = false

            days.forEach { dayStr ->
                checkNewPage(30f)
                
                if (alternateBg) {
                    paint.color = COLOR_BACKGROUND
                    canvas.drawRect(MARGIN, yPos - 15f, PAGE_WIDTH - MARGIN, yPos + 10f, paint)
                }
                alternateBg = !alternateBg

                val dailyShifts = shifts.filter { dayFormat.format(Date(it.startAt)) == dayStr }
                val dailyIncomes = incomes.filter { dayFormat.format(Date(it.occurredAt)) == dayStr }
                val dailyExpenses = expenses.filter { dayFormat.format(Date(it.occurredAt)) == dayStr }

                var dHours = 0.0
                dailyShifts.forEach { s ->
                    val end = s.endAt ?: System.currentTimeMillis()
                    dHours += (end - s.startAt) / (1000.0 * 60 * 60)
                }
                val dInc = dailyIncomes.sumOf { it.amount }
                val dExp = dailyExpenses.sumOf { it.amount }
                val dNet = dInc - dExp

                val displayDate = dateFormat.format(dayFormat.parse(dayStr) ?: Date())

                paint.color = COLOR_TEXT_PRIMARY
                paint.textSize = 12f
                canvas.drawText(displayDate, MARGIN + 10f, yPos, paint)
                canvas.drawText(String.format("%.1f", dHours), MARGIN + 100f, yPos, paint)
                
                paint.color = COLOR_INCOME
                canvas.drawText(formatter.format(dInc), MARGIN + 180f, yPos, paint)
                
                paint.color = COLOR_EXPENSE
                canvas.drawText(formatter.format(dExp), MARGIN + 280f, yPos, paint)
                
                paint.color = if (dNet < 0) COLOR_EXPENSE else COLOR_TEXT_PRIMARY
                paint.isFakeBoldText = true
                canvas.drawText(formatter.format(dNet), MARGIN + 380f, yPos, paint)
                paint.isFakeBoldText = false

                yPos += 25f
            }
            
            // Draw table bottom border
            paint.color = COLOR_DIVIDER
            paint.strokeWidth = 1f
            canvas.drawLine(MARGIN, yPos - 15f, PAGE_WIDTH - MARGIN, yPos - 15f, paint)
        }

        document.finishPage(page)

        try {
            val prefix = when(type) {
                ReportType.DAILY -> "gunluk"
                ReportType.WEEKLY -> "haftalik"
                ReportType.MONTHLY -> "aylik"
            }
            val file = File(context.cacheDir, "kazio_${prefix}_rapor_${System.currentTimeMillis()}.pdf")
            document.writeTo(FileOutputStream(file))
            document.close()

            return FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )
        } catch (e: Exception) {
            e.printStackTrace()
            document.close()
            return null
        }
    }
}
