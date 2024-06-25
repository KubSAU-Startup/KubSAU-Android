package com.example.diploma.common.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.diploma.common.navigation.Graphs
import com.example.diploma.common.navigation.Screens
import com.example.diploma.ui.screens.main.MainScreen

fun NavGraphBuilder.mainNavGraph(
    onError: (String) -> Unit,
    navController: NavHostController
) {
    navigation(
        startDestination = Screens.Main.route,
        route = Graphs.Main.route
    ) {
        composable(route = Screens.Main.route) {
            MainScreen(
                onError = onError,
                onLogOut = {}
            )
        }
    }
}
