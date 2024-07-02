package com.example.diploma.common.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diploma.common.navigation.Graphs
import com.example.diploma.common.navigation.Screens
import com.example.diploma.network.ErrorDomain
import com.example.diploma.ui.screens.latestworks.LatestWorksScreen

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
