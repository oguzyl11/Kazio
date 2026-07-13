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

class KazioSmallWidget : GlanceAppWidget() {
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
        val profitText = formatter.format(summary.netProfit)

        provideContent {
            SmallWidgetContent(context, profitText, firstRec?.title ?: "Kayıt Eklemeye Devam Edin")
        }
    }

    @Composable
    private fun SmallWidgetContent(context: Context, profit: String, recommendation: String) {
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
                text = "Bugünün Karı",
                style = TextStyle(color = ColorProvider(Color.LightGray), fontSize = 14.sp)
            )
            Spacer(modifier = GlanceModifier.height(4.dp))
            Text(
                text = profit,
                style = TextStyle(
                    color = ColorProvider(Color(0xFF4ADE80)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )
            Spacer(modifier = GlanceModifier.height(8.dp))
            Text(
                text = "💡 $recommendation",
                style = TextStyle(color = ColorProvider(Color(0xFFFFC107)), fontSize = 13.sp)
            )
        }
    }
}
