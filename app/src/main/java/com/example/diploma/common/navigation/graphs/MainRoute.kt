package com.example.diploma.common.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.diploma.common.navigation.AUTH_ROUTE
import com.example.diploma.common.navigation.MAIN_ROUTE
import com.example.diploma.common.navigation.Screens
import com.example.diploma.ui.screens.main.MainScreen

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    navigation(startDestination = Screens.Main.route, route = MAIN_ROUTE) {
        composable(route = Screens.Main.route) {
            MainScreen(
                logout = {
                    navController.navigate(AUTH_ROUTE) {
                        popUpTo(AUTH_ROUTE) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}