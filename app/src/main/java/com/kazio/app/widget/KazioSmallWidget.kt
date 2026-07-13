package com.kazio.app.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.kazio.app.MainActivity
import com.kazio.app.R
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

        val incomeText = formatter.format(summary.totalIncome)
        val expenseText = formatter.format(summary.totalExpense)

        provideContent {
            SmallWidgetContent(
                context = context,
                profit = profitText,
                income = incomeText,
                expense = expenseText
            )
        }
    }

    @Composable
    private fun SmallWidgetContent(context: Context, profit: String, income: String, expense: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        Row(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(ImageProvider(R.drawable.widget_main_bg))
                .padding(20.dp)
                .clickable(actionStartActivity(intent)),
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            // Left Side: Logo
            Column(
                horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
                verticalAlignment = Alignment.Vertical.CenterVertically
            ) {
                Image(
                    provider = ImageProvider(R.drawable.kazio_logo_transparent),
                    contentDescription = "Logo",
                    modifier = GlanceModifier.size(48.dp)
                )
                Spacer(modifier = GlanceModifier.height(4.dp))
                Text(
                    text = "K A Z I O",
                    style = TextStyle(color = ColorProvider(Color.White), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                )
            }
            
            Spacer(modifier = GlanceModifier.width(20.dp))
            
            // Right Side: Data
            Column(modifier = GlanceModifier.defaultWeight()) {
                // Top Row of Data
                Row(
                    modifier = GlanceModifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Vertical.CenterVertically
                ) {
                    Column(modifier = GlanceModifier.defaultWeight()) {
                        Text(text = "Bugünün Karı", style = TextStyle(color = ColorProvider(Color.Gray), fontSize = 12.sp))
                        Spacer(modifier = GlanceModifier.height(2.dp))
                        Text(
                            text = profit,
                            style = TextStyle(color = ColorProvider(Color(0xFF4ADE80)), fontWeight = FontWeight.Bold, fontSize = 22.sp)
                        )
                    }
                    
                    Box(
                        modifier = GlanceModifier
                            .size(40.dp)
                            .background(ImageProvider(R.drawable.widget_card_bg)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            provider = ImageProvider(R.drawable.ic_widget_wallet),
                            contentDescription = "Wallet",
                            modifier = GlanceModifier.size(24.dp)
                        )
                    }
                }
                
                Spacer(modifier = GlanceModifier.height(12.dp))
                
                // Bottom Row of Data (Gelir, Gider, Net)
                Row(modifier = GlanceModifier.fillMaxWidth()) {
                    Column(modifier = GlanceModifier.defaultWeight()) {
                        Text(text = "Gelir", style = TextStyle(color = ColorProvider(Color.Gray), fontSize = 10.sp))
                        Text(text = income, style = TextStyle(color = ColorProvider(Color(0xFF3B82F6)), fontSize = 12.sp, fontWeight = FontWeight.Bold))
                    }
                    Column(modifier = GlanceModifier.defaultWeight()) {
                        Text(text = "Gider", style = TextStyle(color = ColorProvider(Color.Gray), fontSize = 10.sp))
                        Text(text = expense, style = TextStyle(color = ColorProvider(Color(0xFFEF4444)), fontSize = 12.sp, fontWeight = FontWeight.Bold))
                    }
                    Column(modifier = GlanceModifier.defaultWeight()) {
                        Text(text = "Net Kar", style = TextStyle(color = ColorProvider(Color.Gray), fontSize = 10.sp))
                        Text(text = profit, style = TextStyle(color = ColorProvider(Color(0xFF4ADE80)), fontSize = 12.sp, fontWeight = FontWeight.Bold))
                    }
                }
            }
        }
    }
}
