package com.example.diploma.common.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diploma.common.navigation.AUTH_ROUTE
import com.example.diploma.common.navigation.MAIN_ROUTE
import com.example.diploma.common.navigation.ROOT_GRAPH
import com.example.diploma.common.navigation.Screens
import com.example.diploma.common.storage.NetworkConfig
import com.example.diploma.ui.screens.auth.url.UrlScreen

@Composable
fun RootGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = when {
            NetworkConfig.url.isEmpty() -> Screens.Url.route
            NetworkConfig.isTokenEmpty -> AUTH_ROUTE
            else -> MAIN_ROUTE
        },
        route = ROOT_GRAPH
    ) {
        composable(route = Screens.Url.route) {
            UrlScreen {
                navController.navigate(route = Screens.Login.route) {
                    popUpTo(Screens.Url.route) {
                        inclusive = true
                    }
                }
            }
        }
        authNavGraph(navController = navController)
        mainNavGraph(navController = navController)
    }
}
