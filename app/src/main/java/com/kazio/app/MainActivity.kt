package com.kazio.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kazio.app.presentation.dashboard.DashboardScreen
import com.kazio.app.presentation.summary.SummaryScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "dashboard") {
                        composable("dashboard") {
                            DashboardScreen(
                                onNavigateToSummary = { navController.navigate("summary") }
                            )
                        }
                        composable("summary") {
                            SummaryScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
