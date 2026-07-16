package com.kazio.app.presentation.addexpense

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
import com.kazio.app.domain.model.ExpenseCategory
import com.kazio.app.presentation.theme.SurfaceMidnight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseBottomSheet(
    existingExpense: com.kazio.app.domain.model.ExpenseEntry? = null,
    onDismissRequest: () -> Unit,
    viewModel: AddExpenseViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(existingExpense) {
        viewModel.setEditingExpense(existingExpense)
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
                text = if (existingExpense != null) "Gider Düzenle" else "Gider Ekle", 
                style = MaterialTheme.typography.headlineMedium, 
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(24.dp))

            val categories = ExpenseCategory.values()
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    val isSelected = uiState.selectedCategory == category
                    val name = when (category) {
                        ExpenseCategory.FUEL -> "Yakıt"
                        ExpenseCategory.MAINTENANCE -> "Bakım"
                        ExpenseCategory.FINE -> "Ceza"
                        ExpenseCategory.PARKING -> "Park"
                        ExpenseCategory.WASHING -> "Yıkama"
                        ExpenseCategory.OTHER -> "Diğer"
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(32.dp))
                            .background(if (isSelected) MaterialTheme.colorScheme.error else Color.Transparent)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(32.dp)
                            )
                            .clickable { viewModel.onCategorySelect(category) }
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = name,
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
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                ),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.error,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedLabelColor = MaterialTheme.colorScheme.error,
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

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = viewModel::saveExpense,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !uiState.isLoading,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text(if (existingExpense != null) "Güncelle" else "Kaydet", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}
