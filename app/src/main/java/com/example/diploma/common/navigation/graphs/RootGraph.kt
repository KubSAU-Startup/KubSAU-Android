package com.example.diploma.common.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.diploma.common.NetworkConfig
import com.example.diploma.common.navigation.AUTH_ROUTE
import com.example.diploma.common.navigation.MAIN_ROUTE
import com.example.diploma.common.navigation.ROOT_GRAPH

@Composable
fun RootGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = if (NetworkConfig.isEmpty) AUTH_ROUTE else MAIN_ROUTE,
        route = ROOT_GRAPH
    ) {
        authNavGraph(navController = navController)
        mainNavGraph(navController = navController)
    }
}