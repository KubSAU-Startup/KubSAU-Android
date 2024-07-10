package com.kubsau.regrab.common.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kubsau.regrab.common.navigation.Graphs
import com.kubsau.regrab.common.navigation.Screens
import com.kubsau.regrab.network.ErrorDomain
import com.kubsau.regrab.ui.screens.auth.login.LoginScreen

fun NavGraphBuilder.authNavGraph(
    onError: (ErrorDomain) -> Unit,
    openChangeUrl: () -> Unit,
    navController: NavHostController
) {
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
                openUrlScreen = openChangeUrl
            )
        }
    }
}
