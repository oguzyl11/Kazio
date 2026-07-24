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
        
        Box(modifier = Modifier.fillMaxSize().graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)) {
            val configuration = androidx.compose.ui.platform.LocalConfiguration.current
            val screenHeightPx = with(androidx.compose.ui.platform.LocalDensity.current) { configuration.screenHeightDp.dp.toPx() }
            val isTargetAtTop = currentTarget.rect.center.y < (screenHeightPx / 2)
            
            val textOffsetY = if (isTargetAtTop) {
                screenHeightPx * 0.65f
            } else {
                screenHeightPx * 0.15f
            }

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                onNext()
                            },
                            onLongPress = { onSkip() }
                        )
                    }
            ) {
                drawRect(
                    color = Color.Black.copy(alpha = 0.7f),
                    size = size
                )
                
                if (currentTarget.rect.width > 0 && currentTarget.rect.height > 0) {
                    val radius = (maxOf(currentTarget.rect.width, currentTarget.rect.height) / 2) + 24.dp.toPx()
                    drawCircle(
                        color = Color.Transparent,
                        radius = radius,
                        center = currentTarget.rect.center,
                        blendMode = BlendMode.Clear
                    )
                }
            }

            Box(
                modifier = Modifier
                    .offset { IntOffset(x = 64, y = textOffsetY.toInt()) }
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
