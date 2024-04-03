package com.example.diploma.common.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.diploma.common.navigation.AUTH_ROUTE
import com.example.diploma.common.navigation.MAIN_ROUTE
import com.example.diploma.common.navigation.Screens
import com.example.diploma.ui.screens.login.LoginScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        startDestination = Screens.Login.route,
        route = AUTH_ROUTE
    ) {
        composable(route = Screens.Login.route) {
            LoginScreen {
                navController.navigate(MAIN_ROUTE)
            }
        }
    }
}