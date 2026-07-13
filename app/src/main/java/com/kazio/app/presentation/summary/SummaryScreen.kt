package com.kazio.app.presentation.summary

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kazio.app.domain.model.PlatformProfit
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(
    onNavigateBack: () -> Unit,
    viewModel: SummaryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Özet", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Geri")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                )
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
                    SummaryContent(state, viewModel::onPeriodSelected)
                }
            }
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
            .padding(start = 6.dp) // Space for the side bar
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(6.dp)
                .background(borderColor)
                .offset(x = (-6).dp)
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 16.dp, top = 12.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = profit.platformName, style = MaterialTheme.typography.labelLarge, color = com.kazio.app.presentation.theme.TextPrimary)
                Text(text = formatter.format(profit.totalIncome), style = MaterialTheme.typography.bodyMedium, color = com.kazio.app.presentation.theme.TextPrimary, fontWeight = FontWeight.Bold)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Gelir", style = MaterialTheme.typography.labelMedium, color = com.kazio.app.presentation.theme.TextSecondary)
                Text(text = "${(profit.percentage * 100).toInt()}%", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(profit.percentage)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(borderColor)
                )
            }
        }
    }
}
