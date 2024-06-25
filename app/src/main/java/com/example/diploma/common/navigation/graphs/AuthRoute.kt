package com.example.diploma.common.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.diploma.common.navigation.Graphs
import com.example.diploma.common.navigation.Screens
import com.example.diploma.ui.screens.auth.login.LoginScreen

fun NavGraphBuilder.authNavGraph(onError: (String) -> Unit, navController: NavHostController) {
    navigation(
        startDestination = Screens.Login.route,
        route = Graphs.Auth.route
    ) {
        composable(route = Screens.Login.route) {
            LoginScreen(
                onError = onError,
                moveToMainRoot = {
                    navController.navigate(Graphs.Main.route) {
                        popUpTo(Graphs.Main.route) {
                            inclusive = true
                        }
                    }
                },
                openUrlScreen = {
                    navController.navigate(Screens.Url.route) {
                        popUpTo(Graphs.Auth.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
