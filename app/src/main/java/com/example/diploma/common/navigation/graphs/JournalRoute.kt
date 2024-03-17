package com.example.diploma.common.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diploma.common.navigation.JOURNAL_ROUTE
import com.example.diploma.common.navigation.Screens
import com.example.diploma.ui.screens.journal.JournalScreen

@Composable
fun JournalRoute(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screens.Journal.route,
        route = JOURNAL_ROUTE
    ) {
        composable(Screens.Journal.route) {
            JournalScreen()
        }
    }
}