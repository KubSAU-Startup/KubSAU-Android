package com.example.diploma.common.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diploma.common.navigation.Screens
import com.example.diploma.common.navigation.TEST_ROUTE
import com.example.diploma.ui.screens.test.one.TestScreen
import com.example.diploma.ui.screens.test.TestScreenThree

@Composable
fun TestRoute(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screens.Test.route,
        route = TEST_ROUTE
    ) {
        composable(route = Screens.Test.route) { entry ->
            TestScreen()
        }
        composable(route = Screens.TestThree.route) {
            TestScreenThree { result ->
                navController.previousBackStackEntry?.savedStateHandle?.set(key = "test", result)
                navController.popBackStack()
            }
        }
    }
}