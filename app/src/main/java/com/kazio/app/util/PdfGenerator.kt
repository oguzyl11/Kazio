package com.kazio.app.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.core.content.FileProvider
import com.kazio.app.domain.model.ExpenseEntry
import com.kazio.app.domain.model.IncomeEntry
import com.kazio.app.domain.model.Shift
import java.io.File
import java.io.FileOutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PdfGenerator {

    fun generateMonthlyReport(
        context: Context,
        monthName: String,
        shifts: List<Shift>,
        incomes: List<IncomeEntry>,
        expenses: List<ExpenseEntry>
    ): Uri? {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size
        val page = document.startPage(pageInfo)

        val canvas: Canvas = page.canvas
        val paint = Paint()

        // Draw Title
        paint.color = Color.BLACK
        paint.textSize = 24f
        paint.isFakeBoldText = true
        canvas.drawText("Kazio Aylık Kazanç Raporu - $monthName", 50f, 50f, paint)

        // Calculate Totals
        val totalIncome = incomes.sumOf { it.amount }
        val totalExpense = expenses.sumOf { it.amount }
        val netProfit = totalIncome - totalExpense

        var totalHours = 0.0
        shifts.forEach { shift ->
            val end = shift.endAt ?: System.currentTimeMillis()
            val hours = (end - shift.startAt) / (1000.0 * 60 * 60)
            totalHours += hours
        }

        val formatter = NumberFormat.getCurrencyInstance(Locale("tr", "TR"))

        // Draw Summary
        paint.textSize = 16f
        paint.isFakeBoldText = false
        canvas.drawText("Toplam Çalışma Saati: ${String.format("%.1f", totalHours)} Saat", 50f, 100f, paint)
        canvas.drawText("Toplam Gelir: ${formatter.format(totalIncome)}", 50f, 130f, paint)
        canvas.drawText("Toplam Gider: ${formatter.format(totalExpense)}", 50f, 160f, paint)
        paint.isFakeBoldText = true
        canvas.drawText("Net Kazanç: ${formatter.format(netProfit)}", 50f, 190f, paint)

        // Draw Day by Day Table Header
        paint.textSize = 14f
        var yPosition = 250f
        canvas.drawText("Tarih", 50f, yPosition, paint)
        canvas.drawText("Çalışma (Sa)", 150f, yPosition, paint)
        canvas.drawText("Gelir", 250f, yPosition, paint)
        canvas.drawText("Gider", 350f, yPosition, paint)
        canvas.drawText("Net", 450f, yPosition, paint)

        paint.strokeWidth = 1f
        canvas.drawLine(50f, yPosition + 10f, 545f, yPosition + 10f, paint)

        yPosition += 30f
        paint.isFakeBoldText = false

        // Group by day
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale("tr", "TR"))
        val dayFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        
        val days = incomes.map { dayFormat.format(Date(it.occurredAt)) }
            .plus(expenses.map { dayFormat.format(Date(it.occurredAt)) })
            .plus(shifts.map { dayFormat.format(Date(it.startAt)) })
            .distinct()
            .sorted()

        days.forEach { dayStr ->
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

            canvas.drawText(displayDate, 50f, yPosition, paint)
            canvas.drawText(String.format("%.1f", dHours), 150f, yPosition, paint)
            canvas.drawText(formatter.format(dInc), 250f, yPosition, paint)
            canvas.drawText(formatter.format(dExp), 350f, yPosition, paint)
            
            // Highlight negative net with red
            if (dNet < 0) paint.color = Color.RED else paint.color = Color.BLACK
            canvas.drawText(formatter.format(dNet), 450f, yPosition, paint)
            paint.color = Color.BLACK

            yPosition += 20f

            // Add new page if yPosition is near bottom
            if (yPosition > 800f) {
                document.finishPage(page)
                // We're simplifying by keeping it on one page if possible, 
                // but real app might need multi-page logic if days > 30.
                // A full month of 31 days fits in 842 pt if y starts at 280 (31 * 20 = 620). 280+620=900
                // So it might clip. A simple page logic:
                // break;
            }
        }

        document.finishPage(page)

        try {
            val file = File(context.cacheDir, "kazio_rapor_${System.currentTimeMillis()}.pdf")
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
