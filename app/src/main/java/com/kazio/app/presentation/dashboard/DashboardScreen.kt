package com.kazio.app.presentation.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    var showMenu by remember { mutableStateOf(false) }
    var showIncomeSheet by remember { mutableStateOf(false) }
    var showExpenseSheet by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
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
                    DashboardContent(state)
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
private fun DashboardContent(state: DashboardUiState.Success) {
    val formatter = NumberFormat.getCurrencyInstance(Locale("tr", "TR"))
    val isPositive = state.dailyNetProfit >= 0
    val color = if (isPositive) Color(0xFF4CAF50) else Color(0xFFE53935)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
        
        Spacer(modifier = Modifier.height(16.dp))
        if (state.activeShift != null) {
            Text(
                text = "Vardiya Aktif",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
