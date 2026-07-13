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
        val dateFormat = java.text.SimpleDateFormat("dd MMMM\nEEEE", Locale("tr", "TR"))
        val dateText = dateFormat.format(calendar.time)

        provideContent {
            LargeWidgetContent(
                context = context,
                netProfit = formatter.format(summary.netProfit),
                totalIncome = formatter.format(summary.totalIncome),
                totalExpense = formatter.format(summary.totalExpense),
                recommendation = firstRec?.title ?: "Kayıt Yok",
                dateText = dateText
            )
        }
    }

    @Composable
    private fun LargeWidgetContent(
        context: Context,
        netProfit: String,
        totalIncome: String,
        totalExpense: String,
        recommendation: String,
        dateText: String
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(ImageProvider(R.drawable.widget_main_bg))
                .padding(20.dp)
                .clickable(actionStartActivity(intent))
        ) {
            // Top Header: Logo + Date
            Row(modifier = GlanceModifier.fillMaxWidth(), verticalAlignment = Alignment.Vertical.CenterVertically) {
                Image(
                    provider = ImageProvider(R.drawable.kazio_logo_transparent),
                    contentDescription = "Logo",
                    modifier = GlanceModifier.size(36.dp)
                )
                Spacer(modifier = GlanceModifier.width(8.dp))
                Text(
                    text = "K A Z I O",
                    style = TextStyle(color = ColorProvider(Color.White), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = GlanceModifier.defaultWeight())
                Row(
                    modifier = GlanceModifier.background(ImageProvider(R.drawable.widget_card_bg)).padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.Vertical.CenterVertically
                ) {
                    Image(
                        provider = ImageProvider(R.drawable.ic_widget_calendar),
                        contentDescription = "Calendar",
                        modifier = GlanceModifier.size(16.dp)
                    )
                    Spacer(modifier = GlanceModifier.width(8.dp))
                    Text(text = dateText, style = TextStyle(color = ColorProvider(Color.LightGray), fontSize = 10.sp))
                }
            }

            Spacer(modifier = GlanceModifier.height(16.dp))

            // Title
            Row(verticalAlignment = Alignment.Vertical.CenterVertically) {
                Box(modifier = GlanceModifier.size(8.dp).background(ColorProvider(Color(0xFF4ADE80)))) {}
                Spacer(modifier = GlanceModifier.width(8.dp))
                Text(text = "Günlük Özet", style = TextStyle(color = ColorProvider(Color.White), fontSize = 16.sp, fontWeight = FontWeight.Bold))
            }
            Text(text = "Kazancını yaz, geleceğini yönet.", style = TextStyle(color = ColorProvider(Color.Gray), fontSize = 12.sp), modifier = GlanceModifier.padding(start = 16.dp, top = 4.dp))
            
            Spacer(modifier = GlanceModifier.height(16.dp))
            
            // Net Kar Card
            Column(modifier = GlanceModifier.fillMaxWidth().background(ImageProvider(R.drawable.widget_card_bg)).padding(16.dp)) {
                Text(text = "Net Kar", style = TextStyle(color = ColorProvider(Color.Gray), fontSize = 12.sp))
                Spacer(modifier = GlanceModifier.height(4.dp))
                Text(text = netProfit, style = TextStyle(color = ColorProvider(Color(0xFF4ADE80)), fontWeight = FontWeight.Bold, fontSize = 28.sp))
            }
            
            Spacer(modifier = GlanceModifier.height(12.dp))
            
            // Income & Expense Cards
            Row(modifier = GlanceModifier.fillMaxWidth()) {
                // Gelir
                Column(modifier = GlanceModifier.defaultWeight().background(ImageProvider(R.drawable.widget_card_bg)).padding(16.dp)) {
                    Text(text = "Toplam Gelir", style = TextStyle(color = ColorProvider(Color.Gray), fontSize = 12.sp))
                    Spacer(modifier = GlanceModifier.height(4.dp))
                    Row(modifier = GlanceModifier.fillMaxWidth(), verticalAlignment = Alignment.Vertical.CenterVertically) {
                        Text(text = totalIncome, style = TextStyle(color = ColorProvider(Color(0xFF60A5FA)), fontWeight = FontWeight.Bold, fontSize = 18.sp))
                        Spacer(modifier = GlanceModifier.defaultWeight())
                        Image(provider = ImageProvider(R.drawable.ic_widget_arrow_up), contentDescription = "Up", modifier = GlanceModifier.size(20.dp))
                    }
                }
                Spacer(modifier = GlanceModifier.width(12.dp))
                // Gider
                Column(modifier = GlanceModifier.defaultWeight().background(ImageProvider(R.drawable.widget_card_bg)).padding(16.dp)) {
                    Text(text = "Toplam Gider", style = TextStyle(color = ColorProvider(Color.Gray), fontSize = 12.sp))
                    Spacer(modifier = GlanceModifier.height(4.dp))
                    Row(modifier = GlanceModifier.fillMaxWidth(), verticalAlignment = Alignment.Vertical.CenterVertically) {
                        Text(text = totalExpense, style = TextStyle(color = ColorProvider(Color(0xFFF87171)), fontWeight = FontWeight.Bold, fontSize = 18.sp))
                        Spacer(modifier = GlanceModifier.defaultWeight())
                        Image(provider = ImageProvider(R.drawable.ic_widget_arrow_down), contentDescription = "Down", modifier = GlanceModifier.size(20.dp))
                    }
                }
            }
            
            Spacer(modifier = GlanceModifier.height(12.dp))
            
            // Bugünün Karı Card with Wallet
            Row(modifier = GlanceModifier.fillMaxWidth().background(ImageProvider(R.drawable.widget_card_bg)).padding(16.dp), verticalAlignment = Alignment.Vertical.CenterVertically) {
                Column {
                    Text(text = "Bugünün Karı", style = TextStyle(color = ColorProvider(Color.Gray), fontSize = 12.sp))
                    Spacer(modifier = GlanceModifier.height(4.dp))
                    Text(text = netProfit, style = TextStyle(color = ColorProvider(Color(0xFF4ADE80)), fontWeight = FontWeight.Bold, fontSize = 18.sp))
                }
                Spacer(modifier = GlanceModifier.defaultWeight())
                Image(provider = ImageProvider(R.drawable.ic_widget_wallet), contentDescription = "Wallet", modifier = GlanceModifier.size(32.dp))
            }
            
            Spacer(modifier = GlanceModifier.height(12.dp))
            
            // Recommendation Card
            Row(modifier = GlanceModifier.fillMaxWidth().background(ImageProvider(R.drawable.widget_card_bg_green)).padding(16.dp), verticalAlignment = Alignment.Vertical.CenterVertically) {
                Image(provider = ImageProvider(R.drawable.ic_widget_trophy), contentDescription = "Trophy", modifier = GlanceModifier.size(24.dp))
                Spacer(modifier = GlanceModifier.width(16.dp))
                Column {
                    Text(text = "Öne Çıkan Not", style = TextStyle(color = ColorProvider(Color.LightGray), fontSize = 10.sp))
                    Text(text = recommendation, style = TextStyle(color = ColorProvider(Color(0xFF4ADE80)), fontSize = 14.sp, fontWeight = FontWeight.Bold))
                }
            }
        }
    }
}
