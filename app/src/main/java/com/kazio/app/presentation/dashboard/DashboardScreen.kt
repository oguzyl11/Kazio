package com.kazio.app.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { /* Menu */ }, modifier = Modifier.offset(x = (-12).dp)) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                    }
                    Text(
                        text = "KuryePanel",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                ) {
                    Icon(Icons.Default.Person, contentDescription = "Profile", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.align(Alignment.Center))
                }
            }
        },
        floatingActionButton = {
            Box {
                FloatingActionButton(
                    onClick = { showMenu = true },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Ekle", modifier = Modifier.size(32.dp))
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh)
                ) {
                    DropdownMenuItem(
                        text = { Text("Gelir Ekle", color = MaterialTheme.colorScheme.onSurface) },
                        onClick = {
                            showMenu = false
                            showIncomeSheet = true
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Gider Ekle", color = MaterialTheme.colorScheme.onSurface) },
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
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (val state = uiState) {
                is DashboardUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.primary)
                }
                is DashboardUiState.Error -> {
                    Text(text = "Hata: ${state.message}", color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
                }
                is DashboardUiState.Success -> {
                    DashboardContent(
                        state = state,
                        onNavigateToSummary = onNavigateToSummary,
                        onStartShift = viewModel::startShift,
                        onEndShift = viewModel::endShift
                    )
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
    onNavigateToSummary: () -> Unit,
    onStartShift: () -> Unit,
    onEndShift: () -> Unit
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale("tr", "TR"))
    val isOnline = state.activeShift != null

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        // Profit Header Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(com.kazio.app.presentation.theme.SurfaceMidnight.copy(alpha = 0.8f))
                    .border(1.dp, MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(12.dp))
                    .clickable { if (isOnline) onEndShift() else onStartShift() }
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Bugün net kazancın",
                    style = MaterialTheme.typography.bodyMedium,
                    color = com.kazio.app.presentation.theme.TextSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = formatter.format(state.dailyNetProfit),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Schedule, contentDescription = null, tint = com.kazio.app.presentation.theme.TextSecondary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isOnline) "Vardiya: ${state.activeShiftDurationStr} (Bitir)" else "Vardiya Başlat",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isOnline) MaterialTheme.colorScheme.primary else com.kazio.app.presentation.theme.TextSecondary
                    )
                }
            }
        }

        // Income/Expense Summary Grid
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                // Income Card
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(com.kazio.app.presentation.theme.SurfaceMidnight)
                        .border(1.dp, MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ArrowUpward, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Toplam Gelir", style = MaterialTheme.typography.labelLarge, color = com.kazio.app.presentation.theme.TextSecondary)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = formatter.format(state.totalIncome), style = MaterialTheme.typography.headlineMedium, color = com.kazio.app.presentation.theme.TextPrimary)
                }

                // Expense Card
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(com.kazio.app.presentation.theme.SurfaceMidnight)
                        .border(1.dp, MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ArrowDownward, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Toplam Gider", style = MaterialTheme.typography.labelLarge, color = com.kazio.app.presentation.theme.TextSecondary)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = formatter.format(state.totalExpense), style = MaterialTheme.typography.headlineMedium, color = com.kazio.app.presentation.theme.TextPrimary)
                }
            }
        }

        // Recent Transactions List (Mocked using platform profits for MVP)
        if (state.platformProfits.isNotEmpty()) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "En İyi Platformlar", style = MaterialTheme.typography.headlineMedium, color = com.kazio.app.presentation.theme.TextPrimary)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    state.platformProfits.forEach { profit ->
                        PlatformListItem(profit, formatter)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(
                        onClick = onNavigateToSummary,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Tümünü Gör", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

@Composable
private fun PlatformListItem(profit: PlatformProfit, formatter: NumberFormat) {
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
            .border(width = 1.dp, color = MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(8.dp))
            // Left border effect
            .drawBehind {
                drawLine(
                    color = borderColor,
                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
                    end = androidx.compose.ui.geometry.Offset(0f, size.height),
                    strokeWidth = 8.dp.toPx()
                )
            }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainerHigh),
            contentAlignment = Alignment.Center
        ) {
            Text(text = profit.platformName.take(1), style = MaterialTheme.typography.headlineMedium, color = borderColor)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = profit.platformName, style = MaterialTheme.typography.labelLarge, color = com.kazio.app.presentation.theme.TextPrimary)
            Text(text = "Gelir Dağılımı", style = MaterialTheme.typography.labelMedium, color = com.kazio.app.presentation.theme.TextSecondary)
        }
        Text(text = "+${formatter.format(profit.totalIncome)}", style = MaterialTheme.typography.labelLarge, color = borderColor)
    }
}
