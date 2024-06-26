package com.example.diploma.common.navigation.graphs

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diploma.common.navigation.Graphs
import com.example.diploma.common.navigation.Screens
import com.example.diploma.common.storage.NetworkConfig
import com.example.diploma.ui.screens.auth.url.UrlScreen

@Composable
fun RootGraph(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current

    var showErrorAlert by remember {
        mutableStateOf(false)
    }
    var currentError: String? by remember {
        mutableStateOf(null)
    }

    var needToChangeUrl: Boolean by remember {
        mutableStateOf(false)
    }

    val openUrlScreen: (currentRoute: String?, popBackStack: Boolean) -> Unit = remember {
        { currentRoute, popBackStack ->
            val routePostfix = if (currentRoute == null) "" else "?prevRoute=$currentRoute"
            navController.navigate(route = Screens.Url.route + routePostfix) {
                if (popBackStack) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        }
    }

    if (needToChangeUrl) {
        needToChangeUrl = false
        openUrlScreen(null, true)
    }

    val justOpenUrlScreen = remember {
        {
            val currentRoute = navController.currentDestination?.route
            openUrlScreen(currentRoute, false)
        }
    }

    val onError = { error: String ->
        when (error) {
            "Wrong url" -> {
                needToChangeUrl = true
                NetworkConfig.clearUrl()
                Toast.makeText(context, "Url is wrong. Change it", Toast.LENGTH_LONG).show()
            }

            else -> {
                currentError = error
                showErrorAlert = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = when {
            NetworkConfig.url.isEmpty() -> Screens.Url.route
            NetworkConfig.isTokenEmpty -> Graphs.Auth.route
            else -> Graphs.Main.route
        },
        route = Graphs.Root.route
    ) {
        composable(route = Screens.Url.route + "?prevRoute={prevRoute}") {
            UrlScreen(
                goToLoginScreen = {
                    navController.navigate(route = Screens.Login.route) {
                        popUpTo(Screens.Url.route) {
                            inclusive = true
                        }
                    }
                },
                goToPreviousScreen = navController::navigateUp
            )
        }
        authNavGraph(
            onError = onError,
            openChangeUrl = justOpenUrlScreen,
            navController = navController
        )
        mainNavGraph(
            onError = onError,
            openUrlScreen = justOpenUrlScreen,
            navController = navController
        )
    }

    if (showErrorAlert) {
        AlertDialog(
            onDismissRequest = {
                showErrorAlert = false
                currentError = null
            },
            confirmButton = {
                Button(onClick = {}) {
                    Text(text = "OK")
                }
            },
            title = { Text(text = "Error") },
            text = { Text(text = "Error occurred: $currentError") }
        )
    }
}
