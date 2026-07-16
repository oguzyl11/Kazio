package com.kazio.app.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.kazio.app.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kazio.app.domain.model.PlatformProfit
import com.kazio.app.domain.model.Recommendation
import com.kazio.app.domain.model.RecommendationType
import com.kazio.app.presentation.components.ShowcaseOverlay
import com.kazio.app.presentation.components.ShowcaseTarget
import com.kazio.app.presentation.components.ReportOptionsBottomSheet
import java.text.NumberFormat
import java.util.Locale
import com.kazio.app.domain.model.RecordType

@Composable
fun DashboardScreen(
    onNavigateToSummary: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    var showIncomeSheet by remember { mutableStateOf(false) }
    var showExpenseSheet by remember { mutableStateOf(false) }
    var showReportOptions by remember { mutableStateOf(false) }
    var showTransactions by remember { mutableStateOf(false) }
    var showShiftManagement by remember { mutableStateOf(false) }
    var editingIncome by remember { mutableStateOf<com.kazio.app.domain.model.IncomeEntry?>(null) }
    var editingExpense by remember { mutableStateOf<com.kazio.app.domain.model.ExpenseEntry?>(null) }

    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Showcase States
    var incomeButtonRect by remember { mutableStateOf<Rect?>(null) }
    var expenseButtonRect by remember { mutableStateOf<Rect?>(null) }
    var shiftCardRect by remember { mutableStateOf<Rect?>(null) }
    var summaryGridRect by remember { mutableStateOf<Rect?>(null) }
    var showcaseStep by remember { mutableIntStateOf(0) }

    val currentShowcaseTarget = remember(showcaseStep, incomeButtonRect, expenseButtonRect, shiftCardRect, summaryGridRect) {
        when (showcaseStep) {
            1 -> incomeButtonRect?.let { ShowcaseTarget(it, "Gelir Ekle", "Buraya dokunarak kazançlarınızı hızlıca kaydedebilirsiniz.") }
            2 -> expenseButtonRect?.let { ShowcaseTarget(it, "Gider Ekle", "Yakıt veya yemek gibi giderlerinizi buradan ekleyebilirsiniz.") }
            3 -> shiftCardRect?.let { ShowcaseTarget(it, "Vardiya Kontrolü", "Çalışmaya başladığınızda vardiyanızı buradan başlatıp bitirebilirsiniz.") }
            4 -> summaryGridRect?.let { ShowcaseTarget(it, "Günlük Durum", "Günlük toplam gelir ve giderinizi anlık olarak buradan takip edebilirsiniz.") }
            else -> null
        }
    }

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

    LaunchedEffect(uiState) {
        if (uiState is DashboardUiState.Success) {
            val state = uiState as DashboardUiState.Success
            if (state.showOnboarding && showcaseStep == 0) {
                showcaseStep = 1
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.newRecordEvent.collect { record ->
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
                val formatter = NumberFormat.getCurrencyInstance(Locale("tr", "TR"))
                formatter.format(record.value)
            }
            snackbarHostState.showSnackbar("🎉 Yeni Rekor! $title: $valueStr")
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                com.kazio.app.presentation.components.KazioTopBar(
                    actions = {
                        if (uiState is DashboardUiState.Success) {
                            val successState = uiState as DashboardUiState.Success
                            if (successState.streak > 0) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(MaterialTheme.colorScheme.primaryContainer)
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text("🔥", fontSize = 16.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${successState.streak} Gün",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                        IconButton(onClick = { showReportOptions = true }) {
                            Icon(
                                imageVector = Icons.Default.PictureAsPdf,
                                contentDescription = "PDF Rapor",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
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
                            onShiftCardClick = { showShiftManagement = true },
                            onIncomeClick = { showIncomeSheet = true },
                            onExpenseClick = { showExpenseSheet = true },
                            onIncomePositioned = { incomeButtonRect = it },
                            onExpensePositioned = { expenseButtonRect = it },
                            onShiftCardPositioned = { shiftCardRect = it },
                            onSummaryGridPositioned = { summaryGridRect = it },
                            onShowTransactions = { showTransactions = true }
                        )
                    }
                }
            }
        }

        // Showcase Overlay
        if (uiState is DashboardUiState.Success && showcaseStep > 0) {
            ShowcaseOverlay(
                isVisible = showcaseStep > 0,
                currentTarget = currentShowcaseTarget,
                onNext = {
                    if (showcaseStep < 4) {
                        showcaseStep++
                    } else {
                        showcaseStep = 0
                        viewModel.completeOnboarding()
                    }
                },
                onSkip = {
                    showcaseStep = 0
                    viewModel.completeOnboarding()
                }
            )
        }
    }

    if (showIncomeSheet) {
        com.kazio.app.presentation.addincome.AddIncomeBottomSheet(
            onDismissRequest = { 
                showIncomeSheet = false
                editingIncome = null
            },
            existingIncome = editingIncome
        )
    }

    if (showExpenseSheet) {
        com.kazio.app.presentation.addexpense.AddExpenseBottomSheet(
            onDismissRequest = { 
                showExpenseSheet = false
                editingExpense = null
            },
            existingExpense = editingExpense
        )
    }

    if (showTransactions) {
        com.kazio.app.presentation.transactions.TransactionsBottomSheet(
            onDismissRequest = { showTransactions = false },
            onEditIncome = { income ->
                editingIncome = income
                showTransactions = false
                showIncomeSheet = true
            },
            onEditExpense = { expense ->
                editingExpense = expense
                showTransactions = false
                showExpenseSheet = true
            }
        )
    }

    if (showReportOptions) {
        ReportOptionsBottomSheet(
            onDismissRequest = { showReportOptions = false },
            onSelectReport = { reportType ->
                viewModel.generateReport(context, reportType)
                showReportOptions = false
            }
        )
    }

    if (showShiftManagement && uiState is DashboardUiState.Success) {
        val successState = uiState as DashboardUiState.Success
        com.kazio.app.presentation.dashboard.ShiftManagementBottomSheet(
            activeShift = successState.activeShift,
            durationStr = successState.activeShiftDurationStr,
            onDismissRequest = { showShiftManagement = false },
            onPauseShift = viewModel::pauseShift,
            onResumeShift = viewModel::resumeShift,
            onEndShift = viewModel::endShift
        )
    }
}

@Composable
private fun DashboardContent(
    state: DashboardUiState.Success,
    onNavigateToSummary: () -> Unit,
    onStartShift: () -> Unit,
    onShiftCardClick: () -> Unit,
    onIncomeClick: () -> Unit,
    onExpenseClick: () -> Unit,
    onIncomePositioned: (Rect) -> Unit,
    onExpensePositioned: (Rect) -> Unit,
    onShiftCardPositioned: (Rect) -> Unit,
    onSummaryGridPositioned: (Rect) -> Unit,
    onShowTransactions: () -> Unit
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale("tr", "TR"))
    val isOnline = state.activeShift != null

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Profit Header Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { onShiftCardPositioned(it.boundsInRoot()) }
                    .clip(RoundedCornerShape(12.dp))
                    .background(com.kazio.app.presentation.theme.SurfaceMidnight.copy(alpha = 0.8f))
                    .border(1.dp, MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(12.dp))
                    .clickable { if (isOnline) onShiftCardClick() else onStartShift() }
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isOnline) {
                    Text(
                        text = "Aktif Vardiya Kazancı",
                        style = MaterialTheme.typography.bodyMedium,
                        color = com.kazio.app.presentation.theme.TextSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = formatter.format(state.activeShiftIncome),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
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
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Schedule, contentDescription = null, tint = com.kazio.app.presentation.theme.TextSecondary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isOnline) {
                            if (state.activeShift?.isPaused == true) "Vardiya: ${state.activeShiftDurationStr} (Molada)" 
                            else "Vardiya: ${state.activeShiftDurationStr} (Yönet)"
                        } else "Vardiya Başlat",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isOnline) MaterialTheme.colorScheme.primary else com.kazio.app.presentation.theme.TextSecondary
                    )
                }

                // Break-Even Indicator
                if (state.totalIncome > 0 || state.totalExpense > 0) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Divider(color = MaterialTheme.colorScheme.surfaceContainerHigh, thickness = 1.dp, modifier = Modifier.fillMaxWidth(0.8f))
                    BreakEvenIndicator(state = state, formatter = formatter)
                }
            }
        }

        // Action Buttons (Income / Expense / Transactions)
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = onIncomeClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(72.dp)
                        .onGloballyPositioned { onIncomePositioned(it.boundsInRoot()) },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Gelir Ekle", style = MaterialTheme.typography.labelMedium)
                    }
                }

                Button(
                    onClick = onExpenseClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(72.dp)
                        .onGloballyPositioned { onExpensePositioned(it.boundsInRoot()) },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer, contentColor = MaterialTheme.colorScheme.onErrorContainer)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Remove, contentDescription = null, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Gider Ekle", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }

        item {
            Button(
                onClick = onShowTransactions,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer)
            ) {
                Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Geçmiş İşlemler & Düzenle", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
        }

        // Income/Expense Summary Grid
        item {
            Row(
                modifier = Modifier.fillMaxWidth().onGloballyPositioned { onSummaryGridPositioned(it.boundsInRoot()) },
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
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

        // Recommendations
        if (state.recommendations.isNotEmpty()) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Size Özel Tavsiyeler", style = MaterialTheme.typography.headlineMedium, color = com.kazio.app.presentation.theme.TextPrimary)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(end = 20.dp)
                    ) {
                        items(state.recommendations) { recommendation ->
                            RecommendationCard(recommendation)
                        }
                    }
                }
            }
        }

        // Recent Transactions List
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

    val animatedProgress by androidx.compose.animation.core.animateFloatAsState(
        targetValue = profit.percentage,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 1000, easing = androidx.compose.animation.core.FastOutSlowInEasing)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(com.kazio.app.presentation.theme.SurfaceMidnight)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(8.dp))
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
            
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(borderColor)
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(horizontalAlignment = Alignment.End) {
            Text(text = "+${formatter.format(profit.totalIncome)}", style = MaterialTheme.typography.labelLarge, color = borderColor, fontWeight = FontWeight.Bold)
            if (profit.hourlyRate > 0) {
                Text(text = "${formatter.format(profit.hourlyRate)}/saat", style = MaterialTheme.typography.labelSmall, color = com.kazio.app.presentation.theme.TextSecondary)
            }
        }
    }
}

@Composable
fun RecommendationCard(recommendation: Recommendation) {
    val (icon, color) = when (recommendation.type) {
        RecommendationType.POSITIVE -> Icons.Default.CheckCircle to MaterialTheme.colorScheme.primary
        RecommendationType.NEGATIVE -> Icons.Default.Warning to MaterialTheme.colorScheme.error
        RecommendationType.INFO -> Icons.Default.Info to MaterialTheme.colorScheme.secondary
    }

    Column(
        modifier = Modifier
            .width(280.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(com.kazio.app.presentation.theme.SurfaceMidnight)
            .border(1.dp, MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = recommendation.title,
                style = MaterialTheme.typography.titleMedium,
                color = com.kazio.app.presentation.theme.TextPrimary,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = recommendation.description,
            style = MaterialTheme.typography.bodyMedium,
            color = com.kazio.app.presentation.theme.TextSecondary
        )
    }
}

@Composable
private fun BreakEvenIndicator(state: DashboardUiState.Success, formatter: NumberFormat) {
    if (state.totalIncome <= 0 && state.totalExpense <= 0) return

    val progress: Float
    val message: String
    val color: Color

    if (state.totalExpense > 0 && state.totalIncome < state.totalExpense) {
        val remaining = state.totalExpense - state.totalIncome
        progress = (state.totalIncome / state.totalExpense).toFloat().coerceIn(0f, 1f)
        message = "Giderlerini karşılamana ${formatter.format(remaining)} kaldı"
        color = MaterialTheme.colorScheme.secondary
    } else {
        progress = 1f
        message = "Bugünkü net kârın başladı 🎉"
        color = MaterialTheme.colorScheme.primary
    }

    val animatedProgress by androidx.compose.animation.core.animateFloatAsState(
        targetValue = progress,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 1000, easing = androidx.compose.animation.core.FastOutSlowInEasing)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.labelMedium,
            color = color,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(color)
            )
        }
    }
}
