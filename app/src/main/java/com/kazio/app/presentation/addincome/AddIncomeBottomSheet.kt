package com.kazio.app.presentation.addincome

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kazio.app.presentation.theme.SurfaceMidnight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIncomeBottomSheet(
    existingIncome: com.kazio.app.domain.model.IncomeEntry? = null,
    onDismissRequest: () -> Unit,
    viewModel: AddIncomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = uiState.selectedDateMillis ?: System.currentTimeMillis()
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    viewModel.onDateSelect(datePickerState.selectedDateMillis)
                }) { Text("Tamam") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("İptal") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    LaunchedEffect(existingIncome) {
        viewModel.setEditingIncome(existingIncome)
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            viewModel.resetSuccess()
            onDismissRequest()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = SurfaceMidnight,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (existingIncome != null) "Gelir Düzenle" else "Gelir Ekle", 
                style = MaterialTheme.typography.headlineMedium, 
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.platforms) { platform ->
                    val isSelected = uiState.selectedPlatformId == platform.id
                    val color = try {
                        Color(android.graphics.Color.parseColor(platform.colorTag))
                    } catch (e: Exception) {
                        MaterialTheme.colorScheme.primary
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(32.dp))
                            .background(if (isSelected) color else Color.Transparent)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(32.dp)
                            )
                            .clickable { viewModel.onPlatformSelect(platform.id) }
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = platform.name,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = uiState.amount,
                onValueChange = viewModel::onAmountChange,
                label = { Text("Tutar", style = MaterialTheme.typography.labelLarge) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = uiState.error != null,
                textStyle = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                ),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            if (uiState.error != null) {
                Text(
                    text = uiState.error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { showDatePicker = true }) {
                val dateText = uiState.selectedDateMillis?.let {
                    val format = java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
                    format.format(java.util.Date(it))
                } ?: "Tarih Seç (Opsiyonel)"
                Text(dateText, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = viewModel::saveIncome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !uiState.isLoading,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text(if (existingIncome != null) "Güncelle" else "Kaydet", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}
