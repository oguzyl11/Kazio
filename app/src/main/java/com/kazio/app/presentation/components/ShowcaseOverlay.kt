package com.kazio.app.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

data class ShowcaseTarget(
    val rect: Rect,
    val title: String,
    val description: String
)

@Composable
fun ShowcaseOverlay(
    isVisible: Boolean,
    currentTarget: ShowcaseTarget?,
    onNext: () -> Unit,
    onSkip: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible && currentTarget != null,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        if (currentTarget == null) return@AnimatedVisibility

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { onNext() },
                        onLongPress = { onSkip() }
                    )
                }
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Draw dark background
                drawRect(color = Color.Black.copy(alpha = 0.8f))

                // Cut out the target area
                val radius = (maxOf(currentTarget.rect.width, currentTarget.rect.height) / 2) + 24.dp.toPx()
                
                drawCircle(
                    color = Color.Transparent,
                    radius = radius,
                    center = currentTarget.rect.center,
                    blendMode = BlendMode.Clear
                )
            }

            // Draw Text Box
            val isTargetAtTop = currentTarget.rect.center.y < 1000f // Rough estimate to place text below or above
            val textOffsetY = if (isTargetAtTop) {
                currentTarget.rect.bottom + 48f
            } else {
                currentTarget.rect.top - 300f
            }

            Box(
                modifier = Modifier
                    .offset { IntOffset(x = 64, y = textOffsetY.roundToInt()) }
                    .width(280.dp)
                    .background(Color.Black.copy(alpha = 0.8f), androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                androidx.compose.foundation.layout.Column {
                    Text(
                        text = currentTarget.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = currentTarget.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        lineHeight = 22.sp
                    )
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        text = "(Devam etmek için dokunun)",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}
