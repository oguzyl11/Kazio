package com.kazio.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.kazio.app.data.local.datastore.DataStoreRepository
import com.kazio.app.presentation.auth.LoginScreen
import com.kazio.app.presentation.auth.RegisterScreen
import com.kazio.app.presentation.components.BottomNavigationBar
import com.kazio.app.presentation.dashboard.DashboardScreen
import com.kazio.app.presentation.settings.SettingsScreen
import com.kazio.app.presentation.summary.SummaryScreen
import com.kazio.app.presentation.theme.KazioTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val preferences by dataStoreRepository.userPreferencesFlow.collectAsState(initial = null)
            
            KazioTheme {
                if (preferences == null) {
                    // Loading State
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                    return@KazioTheme
                }

                val navController = rememberNavController()
                
                // Determine Start Destination
                val startDestination = if (preferences?.isRegistered == true) {
                    "login"
                } else {
                    "register"
                }

                NavHost(navController = navController, startDestination = startDestination) {
                    composable("register") {
                        RegisterScreen(onRegisterSuccess = {
                            navController.navigate("main") {
                                popUpTo("register") { inclusive = true }
                            }
                        })
                    }
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = {
                                navController.navigate("main") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onResetAccount = {
                                navController.navigate("register") {
                                    popUpTo(0)
                                }
                            }
                        )
                    }
                    navigation(startDestination = "dashboard", route = "main") {
                        composable("dashboard") {
                            Scaffold(
                                bottomBar = {
                                    BottomNavigationBar(
                                        currentRoute = "dashboard",
                                        onNavigate = { route ->
                                            if (route != "dashboard") {
                                                navController.navigate(route)
                                            }
                                        }
                                    )
                                }
                            ) { paddingValues ->
                                Box(modifier = Modifier.padding(paddingValues)) {
                                    DashboardScreen(
                                        onNavigateToSummary = { navController.navigate("summary") }
                                    )
                                }
                            }
                        }
                        composable("summary") {
                            Scaffold(
                                bottomBar = {
                                    BottomNavigationBar(
                                        currentRoute = "summary",
                                        onNavigate = { route ->
                                            if (route != "summary") {
                                                navController.navigate(route) {
                                                    popUpTo("dashboard")
                                                }
                                            }
                                        }
                                    )
                                }
                            ) { paddingValues ->
                                Box(modifier = Modifier.padding(paddingValues)) {
                                    SummaryScreen(
                                        onNavigateBack = { navController.popBackStack() }
                                    )
                                }
                            }
                        }
                        composable("settings") {
                            Scaffold(
                                bottomBar = {
                                    BottomNavigationBar(
                                        currentRoute = "settings",
                                        onNavigate = { route ->
                                            if (route != "settings") {
                                                navController.navigate(route) {
                                                    popUpTo("dashboard")
                                                }
                                            }
                                        }
                                    )
                                }
                            ) { paddingValues ->
                                Box(modifier = Modifier.padding(paddingValues)) {
                                    SettingsScreen(
                                        onLogout = {
                                            navController.navigate("login") {
                                                popUpTo("main") { inclusive = true }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
