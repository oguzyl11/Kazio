package com.kazio.app.presentation.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kazio.app.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    nextDestination: String,
    onSplashFinished: (String) -> Unit
) {
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Animate IN (Büyüyerek ve belirek gelme)
        val overshootEasing = CubicBezierEasing(0.175f, 0.885f, 0.32f, 1.275f)
        launch {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 800, easing = overshootEasing)
            )
        }
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 800)
            )
        }
        
        // Ekranda bir süre bekle (Ortada logoyu izlet)
        delay(1000)
        
        // Animate OUT (Küçülerek ve silinerek gitme)
        val anticipateEasing = CubicBezierEasing(0.6f, -0.28f, 0.735f, 0.045f)
        launch {
            scale.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 500, easing = anticipateEasing)
            )
        }
        launch {
            alpha.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 500)
            )
        }
        
        // Animasyon bitimini bekle ve sonraki ekrana geç
        delay(500)
        onSplashFinished(nextDestination)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.kazio_logo_transparent),
            contentDescription = "Kazio Logo",
            modifier = Modifier
                .size(200.dp)
                .scale(scale.value)
                .alpha(alpha.value)
        )
    }
}
