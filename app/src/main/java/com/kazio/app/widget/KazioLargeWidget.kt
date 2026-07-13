package com.kazio.app.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.kazio.app.MainActivity
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.first
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale

class KazioLargeWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val entryPoint = EntryPointAccessors.fromApplication(context, WidgetEntryPoint::class.java)
        val getSummaryUseCase = entryPoint.getSummaryUseCase()
        val getRecommendationsUseCase = entryPoint.getRecommendationsUseCase()
        
        val calendar = Calendar.getInstance()
        val endAt = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        val startAt = calendar.timeInMillis
        
        val summary = getSummaryUseCase(startAt, endAt).first()
        val recommendations = getRecommendationsUseCase().first()
        val firstRec = recommendations.firstOrNull()
        
        val formatter = NumberFormat.getCurrencyInstance(Locale("tr", "TR"))

        provideContent {
            LargeWidgetContent(
                context = context,
                netProfit = formatter.format(summary.netProfit),
                totalIncome = formatter.format(summary.totalIncome),
                totalExpense = formatter.format(summary.totalExpense),
                recommendation = firstRec?.title ?: "Kayıt Yok"
            )
        }
    }

    @Composable
    private fun LargeWidgetContent(
        context: Context,
        netProfit: String,
        totalIncome: String,
        totalExpense: String,
        recommendation: String
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(ColorProvider(Color(0xFF191C20)))
                .padding(16.dp)
                .clickable(actionStartActivity(intent)),
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            Text(
                text = "KAZIO - Günlük Özet",
                style = TextStyle(color = ColorProvider(Color.LightGray), fontSize = 14.sp)
            )
            Spacer(modifier = GlanceModifier.height(16.dp))
            
            // Net Profit
            Text(text = "Net Kar", style = TextStyle(color = ColorProvider(Color.Gray), fontSize = 12.sp))
            Text(
                text = netProfit,
                style = TextStyle(color = ColorProvider(Color(0xFF4ADE80)), fontWeight = FontWeight.Bold, fontSize = 28.sp)
            )
            Spacer(modifier = GlanceModifier.height(16.dp))
            
            // Income & Expense
            Text(text = "Toplam Gelir", style = TextStyle(color = ColorProvider(Color.Gray), fontSize = 12.sp))
            Text(text = totalIncome, style = TextStyle(color = ColorProvider(Color(0xFF60A5FA)), fontWeight = FontWeight.Bold, fontSize = 20.sp))
            
            Spacer(modifier = GlanceModifier.height(8.dp))
            
            Text(text = "Toplam Gider", style = TextStyle(color = ColorProvider(Color.Gray), fontSize = 12.sp))
            Text(text = totalExpense, style = TextStyle(color = ColorProvider(Color(0xFFF87171)), fontWeight = FontWeight.Bold, fontSize = 20.sp))
            
            Spacer(modifier = GlanceModifier.defaultWeight())
            
            // Recommendation
            Text(
                text = "💡 $recommendation",
                style = TextStyle(color = ColorProvider(Color(0xFFFFC107)), fontSize = 13.sp)
            )
        }
    }
}
