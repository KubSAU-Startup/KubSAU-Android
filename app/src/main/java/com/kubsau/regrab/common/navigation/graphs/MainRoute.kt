package com.kubsau.regrab.common.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kubsau.regrab.common.navigation.Graphs
import com.kubsau.regrab.common.navigation.Screens
import com.kubsau.regrab.common.storage.AccountConfig
import com.kubsau.regrab.common.storage.NetworkConfig
import com.kubsau.regrab.network.ErrorDomain
import com.kubsau.regrab.ui.screens.main.MainScreen

fun NavGraphBuilder.mainNavGraph(
    onError: (ErrorDomain) -> Unit,
    openUrlScreen: () -> Unit,
    navController: NavHostController
) {
    navigation(
        startDestination = Screens.Main.route,
        route = Graphs.Main.route
    ) {
        composable(route = Screens.Main.route) {
            MainScreen(
                onError = onError,
                openUrlScreen = openUrlScreen,
                onLogOut = {
                    NetworkConfig.logout()
                    AccountConfig.logout()

                    navController.navigate(Graphs.Auth.route) {
                        popUpTo(Graphs.Auth.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
