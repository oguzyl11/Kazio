package com.kazio.app.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import java.util.Locale

@Composable
fun DashboardScreen(
    onNavigateToSummary: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    var showMenu by remember { mutableStateOf(false) }
    var showIncomeSheet by remember { mutableStateOf(false) }
    var showExpenseSheet by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            if (uiState is DashboardUiState.Success) {
                val successState = uiState as DashboardUiState.Success
                ShiftBar(
                    activeShift = successState.activeShift,
                    durationStr = successState.activeShiftDurationStr,
                    onStartShift = viewModel::startShift,
                    onEndShift = viewModel::endShift
                )
            }
        },
        floatingActionButton = {
            Box {
                FloatingActionButton(onClick = { showMenu = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Ekle")
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Gelir Ekle") },
                        onClick = {
                            showMenu = false
                            showIncomeSheet = true
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Gider Ekle") },
                        onClick = {
                            showMenu = false
                            showExpenseSheet = true
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is DashboardUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is DashboardUiState.Error -> {
                    Text(text = "Hata: ${state.message}", color = MaterialTheme.colorScheme.error)
                }
                is DashboardUiState.Success -> {
                    DashboardContent(state, onNavigateToSummary)
                }
            }
        }
    }

    if (showIncomeSheet) {
        com.kazio.app.presentation.addincome.AddIncomeBottomSheet(
            onDismissRequest = { showIncomeSheet = false }
        )
    }

    if (showExpenseSheet) {
        com.kazio.app.presentation.addexpense.AddExpenseBottomSheet(
            onDismissRequest = { showExpenseSheet = false }
        )
    }
}

@Composable
private fun DashboardContent(
    state: DashboardUiState.Success,
    onNavigateToSummary: () -> Unit
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale("tr", "TR"))
    val isPositive = state.dailyNetProfit >= 0
    val color = if (isPositive) Color(0xFF4CAF50) else Color(0xFFE53935)

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Bugün net kazancın",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = formatter.format(state.dailyNetProfit),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )

        Spacer(modifier = Modifier.weight(1f))

        if (state.platformProfits.isNotEmpty()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                state.platformProfits.forEach { profit ->
                    MiniPlatformProfitItem(profit, formatter)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                
                TextButton(
                    onClick = onNavigateToSummary,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Tümünü gör")
                }
            }
        }
        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
private fun MiniPlatformProfitItem(profit: PlatformProfit, formatter: NumberFormat) {
    val color = try {
        Color(android.graphics.Color.parseColor(profit.colorTag))
    } catch (e: Exception) {
        MaterialTheme.colorScheme.primary
    }

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(color)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = profit.platformName, style = MaterialTheme.typography.bodyMedium)
        }
        Text(text = formatter.format(profit.totalIncome), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}
