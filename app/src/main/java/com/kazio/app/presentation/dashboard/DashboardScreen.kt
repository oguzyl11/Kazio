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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    viewModel: DashboardViewModel = hiltViewModel(),
    onAddIncomeClick: () -> Unit,
    onAddExpenseClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddIncomeClick) {
                Icon(Icons.Default.Add, contentDescription = "Ekle")
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
