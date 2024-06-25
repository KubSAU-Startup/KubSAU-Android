package com.example.diploma.common.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diploma.common.navigation.Graphs
import com.example.diploma.common.navigation.Screens
import com.example.diploma.ui.screens.profile.ProfileScreen

@Composable
fun ProfileRoute(
    onError: (String) -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Profile.route,
        route = Graphs.Profile.route
    ) {
        composable(Screens.Profile.route) {
            ProfileScreen(
                onError = onError,
            )
        }
    }
}