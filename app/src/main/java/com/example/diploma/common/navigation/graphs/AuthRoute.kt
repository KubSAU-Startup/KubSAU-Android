package com.example.diploma.common.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.diploma.common.navigation.AUTH_ROUTE
import com.example.diploma.common.navigation.MAIN_ROUTE
import com.example.diploma.common.navigation.Screens
import com.example.diploma.common.storage.NetworkConfig
import com.example.diploma.ui.screens.auth.login.LoginScreen
import com.example.diploma.ui.screens.auth.url.UrlScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        startDestination = if (NetworkConfig.isUrlEmpty) Screens.Url.route else Screens.Login.route,
        route = AUTH_ROUTE
    ) {
        composable(route = Screens.Url.route) {
            UrlScreen {
                navController.navigate(route = Screens.Login.route)
            }
        }

        composable(route = Screens.Login.route) {
            LoginScreen {
                navController.navigate(MAIN_ROUTE) {
                    popUpTo(MAIN_ROUTE) {
                        inclusive = true
                    }
                }
            }
        }
    }
}