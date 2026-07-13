package com.kazio.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kazio.app.presentation.components.BottomNavigationBar
import com.kazio.app.presentation.dashboard.DashboardScreen
import com.kazio.app.presentation.settings.SettingsScreen
import com.kazio.app.presentation.summary.SummaryScreen
import com.kazio.app.presentation.theme.KazioTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KazioTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: "dashboard"

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(
                            currentRoute = currentRoute,
                            onNavigate = { route ->
                                navController.navigate(route) {
                                    popUpTo("dashboard") { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
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
                            composable("settings") {
                                SettingsScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
