package com.kazio.app.presentation.summary

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kazio.app.domain.model.PlatformProfit
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.kazio.app.domain.model.PersonalRecord
import com.kazio.app.domain.model.RecordType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPremium: () -> Unit,
    viewModel: SummaryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showReportOptions by remember { mutableStateOf(false) }
    
    val context = androidx.compose.ui.platform.LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.pdfUriEvent.collect { uri ->
            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                flags = android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            context.startActivity(android.content.Intent.createChooser(intent, "PDF Raporunu Görüntüle/Paylaş"))
        }
    }

    Scaffold(
        topBar = {
            com.kazio.app.presentation.components.KazioTopBar(
                actions = {
                    IconButton(onClick = { 
                        if (uiState is SummaryUiState.Success) {
                            val state = uiState as SummaryUiState.Success
                            if (state.isPremium) {
                                showReportOptions = true
                            } else {
                                onNavigateToPremium()
                            }
                        }
                    }) {
                        Icon(Icons.Default.PictureAsPdf, contentDescription = "PDF Rapor", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is SummaryUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.primary)
                }
                is SummaryUiState.Error -> {
                    Text(text = "Hata: ${state.message}", color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
                }
                is SummaryUiState.Success -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(modifier = Modifier.weight(1f)) {
                            SummaryContent(state, viewModel::onPeriodSelected)
                        }
                        if (!state.isPremium) {
                            com.kazio.app.presentation.components.AdBanner()
                        }
                    }
                }
            }
        }
        
        if (showReportOptions) {
            com.kazio.app.presentation.components.ReportOptionsBottomSheet(
                onDismissRequest = { showReportOptions = false },
                onSelectReport = { reportType ->
                    viewModel.generateReport(context, reportType)
                    showReportOptions = false
                }
            )
        }
    }
}

@Composable
private fun SummaryContent(
    state: SummaryUiState.Success,
    onPeriodSelect: (SummaryPeriod) -> Unit
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale("tr", "TR"))
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        item {
            // Segmented Control
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(com.kazio.app.presentation.theme.SurfaceMidnight)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val isWeekly = state.period == SummaryPeriod.WEEKLY
                
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isWeekly) MaterialTheme.colorScheme.surfaceContainer else Color.Transparent)
                        .clickable { onPeriodSelect(SummaryPeriod.WEEKLY) }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Haftalık", style = MaterialTheme.typography.labelLarge, color = if (isWeekly) MaterialTheme.colorScheme.primary else com.kazio.app.presentation.theme.TextSecondary)
                }
                
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (!isWeekly) MaterialTheme.colorScheme.surfaceContainer else Color.Transparent)
                        .clickable { onPeriodSelect(SummaryPeriod.MONTHLY) }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Aylık", style = MaterialTheme.typography.labelLarge, color = if (!isWeekly) MaterialTheme.colorScheme.primary else com.kazio.app.presentation.theme.TextSecondary)
                }
            }
        }

        item {
            // Highlight Card: Net Kazanç
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(com.kazio.app.presentation.theme.SurfaceMidnight)
                    .border(1.dp, MaterialTheme.colorScheme.surfaceContainerHigh, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "TOPLAM NET KAZANÇ",
                    style = MaterialTheme.typography.labelMedium,
                    color = com.kazio.app.presentation.theme.TextSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = formatter.format(state.result.netProfit),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.TrendingUp, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Gelir: ${formatter.format(state.result.totalIncome)} | Gider: ${formatter.format(state.result.totalExpense)}", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        item {
            Text(text = "Platform Dağılımı", style = MaterialTheme.typography.labelLarge, color = com.kazio.app.presentation.theme.TextSecondary)
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(state.result.platformProfits) { platformProfit ->
            PlatformProfitItem(platformProfit, formatter)
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (state.records.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                PersonalRecordsCard(records = state.records, formatter = formatter)
            }
        }
    }
}

@Composable
private fun PlatformProfitItem(profit: PlatformProfit, formatter: NumberFormat) {
    val borderColor = try {
        Color(android.graphics.Color.parseColor(profit.colorTag))
    } catch (e: Exception) {
        MaterialTheme.colorScheme.primary
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(com.kazio.app.presentation.theme.SurfaceMidnight)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(6.dp)
                .background(borderColor)
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = profit.platformName, style = MaterialTheme.typography.labelLarge, color = com.kazio.app.presentation.theme.TextPrimary)
                Text(text = formatter.format(profit.totalIncome), style = MaterialTheme.typography.bodyMedium, color = com.kazio.app.presentation.theme.TextPrimary, fontWeight = FontWeight.Bold)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Gelir", style = MaterialTheme.typography.labelMedium, color = com.kazio.app.presentation.theme.TextSecondary)
                    if (profit.hourlyRate > 0) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "•", style = MaterialTheme.typography.labelSmall, color = com.kazio.app.presentation.theme.TextSecondary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "${formatter.format(profit.hourlyRate)}/saat", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    }
                }
                Text(text = "${(profit.percentage * 100).toInt()}%", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
            }
            val animatedProgress by androidx.compose.animation.core.animateFloatAsState(
                targetValue = profit.percentage,
                animationSpec = androidx.compose.animation.core.tween(durationMillis = 1000, easing = androidx.compose.animation.core.FastOutSlowInEasing)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(borderColor)
                )
            }
        }
    }
}

@Composable
private fun PersonalRecordsCard(records: List<PersonalRecord>, formatter: NumberFormat) {
    val dateFormatter = remember { SimpleDateFormat("d MMM yyyy", Locale("tr", "TR")) }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(com.kazio.app.presentation.theme.SurfaceMidnight)
            .border(1.dp, MaterialTheme.colorScheme.surfaceContainerHigh, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "🏆", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Kişisel Rekorların",
                style = MaterialTheme.typography.titleMedium,
                color = com.kazio.app.presentation.theme.TextPrimary,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        records.forEach { record ->
            val title = when(record.type) {
                RecordType.HIGHEST_DAILY_PROFIT -> "En Yüksek Günlük Kâr"
                RecordType.HIGHEST_WEEKLY_PROFIT -> "En Yüksek Haftalık Kâr"
                RecordType.HIGHEST_HOURLY_PROFIT -> "En Yüksek Saatlik Ortalama"
                RecordType.LONGEST_SHIFT -> "En Uzun Vardiya"
            }

            val valueStr = if (record.type == RecordType.LONGEST_SHIFT) {
                val hours = (record.value / (1000 * 60 * 60)).toInt()
                val minutes = ((record.value / (1000 * 60)) % 60).toInt()
                "${hours}s ${minutes}dk"
            } else {
                formatter.format(record.value)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = title, style = MaterialTheme.typography.labelMedium, color = com.kazio.app.presentation.theme.TextSecondary)
                    Text(text = dateFormatter.format(Date(record.achievedAt)), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                }
                Text(text = valueStr, style = MaterialTheme.typography.bodyLarge, color = com.kazio.app.presentation.theme.TextPrimary, fontWeight = FontWeight.Bold)
            }
            Divider(color = MaterialTheme.colorScheme.surfaceContainer, thickness = 1.dp)
        }
    }
}
