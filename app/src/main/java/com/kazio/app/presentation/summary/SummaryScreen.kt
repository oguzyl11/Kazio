package com.kazio.app.presentation.summary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
                title = { Text("Özet") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is SummaryUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is SummaryUiState.Error -> {
                    Text(text = "Hata: ${state.message}", color = MaterialTheme.colorScheme.error)
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
    val isPositive = state.result.netProfit >= 0
    val netColor = if (isPositive) Color(0xFF4CAF50) else Color(0xFFE53935)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val isWeekly = state.period == SummaryPeriod.WEEKLY
            TextButton(
                onClick = { onPeriodSelect(SummaryPeriod.WEEKLY) },
                modifier = Modifier
                    .weight(1f)
                    .background(if (isWeekly) MaterialTheme.colorScheme.primary else Color.Transparent)
            ) {
                Text("Haftalık", color = if (isWeekly) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface)
            }
            TextButton(
                onClick = { onPeriodSelect(SummaryPeriod.MONTHLY) },
                modifier = Modifier
                    .weight(1f)
                    .background(if (!isWeekly) MaterialTheme.colorScheme.primary else Color.Transparent)
            ) {
                Text("Aylık", color = if (!isWeekly) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface)
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))

        // Total Net Profit
        Text(
            text = "Net Kazanç",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = formatter.format(state.result.netProfit),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = netColor
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Gelir: ${formatter.format(state.result.totalIncome)}  Gider: ${formatter.format(state.result.totalExpense)}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Platforms List
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.result.platformProfits) { platformProfit ->
                PlatformProfitItem(platformProfit, formatter)
            }
        }
    }
}

@Composable
private fun PlatformProfitItem(profit: PlatformProfit, formatter: NumberFormat) {
    val color = try {
        Color(android.graphics.Color.parseColor(profit.colorTag))
    } catch (e: Exception) {
        MaterialTheme.colorScheme.primary
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = profit.platformName, fontWeight = FontWeight.Bold)
            Text(text = formatter.format(profit.totalIncome))
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(profit.percentage)
                    .height(8.dp)
                    .background(color)
            )
        }
    }
}
