package com.kubsau.regrab.common.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kubsau.regrab.common.navigation.Graphs
import com.kubsau.regrab.common.navigation.Screens
import com.kubsau.regrab.network.ErrorDomain
import com.kubsau.regrab.ui.screens.profile.ProfileScreen

@Composable
fun ProfileRoute(
    onError: (ErrorDomain) -> Unit,
    onLogOut: () -> Unit,
    openUrlScreen: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Profile.route,
        route = Graphs.Profile.route
    ) {
        composable(Screens.Profile.route) {
            ProfileScreen(
                onLogOut = onLogOut,
                openChangeUrl = openUrlScreen,
                onError = onError,
            )
        }
    }
}
