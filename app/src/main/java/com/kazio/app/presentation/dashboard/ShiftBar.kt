package com.kazio.app.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kazio.app.domain.model.Shift

@Composable
fun ShiftBar(
    activeShift: Shift?,
    durationStr: String,
    onStartShift: () -> Unit,
    onEndShift: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isOnline = activeShift != null

    val backgroundColor = if (isOnline) Color(0xFF4CAF50) else Color(0xFFE0E0E0)
    val contentColor = if (isOnline) Color.White else Color.DarkGray

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = if (isOnline) "Çevrimiçi" else "Çevrimdışı",
                    color = contentColor,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                if (isOnline) {
                    Text(
                        text = "Süre: $durationStr",
                        color = contentColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Button(
                onClick = if (isOnline) onEndShift else onStartShift,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isOnline) Color.White else MaterialTheme.colorScheme.primary,
                    contentColor = if (isOnline) Color(0xFF4CAF50) else Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(if (isOnline) "Bitir" else "Başlat")
            }
        }
    }
}
