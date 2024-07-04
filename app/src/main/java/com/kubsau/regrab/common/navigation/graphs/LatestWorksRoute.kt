package com.kubsau.regrab.common.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kubsau.regrab.common.navigation.Graphs
import com.kubsau.regrab.common.navigation.Screens
import com.kubsau.regrab.network.ErrorDomain
import com.kubsau.regrab.ui.screens.latestworks.LatestWorksScreen

@Composable
fun LatestWorksRoute(
    onError: (ErrorDomain) -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screens.LatestWorks.route,
        route = Graphs.LatestWorks.route
    ) {
        composable(Screens.LatestWorks.route) {
            LatestWorksScreen(onError = onError)
        }
    }
}
