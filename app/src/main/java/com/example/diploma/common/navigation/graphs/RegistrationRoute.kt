package com.example.diploma.common.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diploma.common.navigation.Graphs
import com.example.diploma.common.navigation.Screens
import com.example.diploma.ui.screens.registration.RegistrationScreen
import com.example.diploma.ui.screens.registration.camera.CameraScreen

@Composable
fun RegistrationRoute(
    onError: (String) -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Camera.route,
        route = Graphs.Camera.route
    ) {
        composable(Screens.Camera.route) {
            CameraScreen {
                navController.navigate(Screens.Registration.route + "/$it")
            }
        }

        composable(Screens.Registration.route + "/{$QR_KEY}") { backStack ->
            RegistrationScreen(qrResult = backStack.arguments?.getString(QR_KEY).toString()) {
                navController.navigate(Screens.Camera.route) {
                    popUpTo(Screens.Camera.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

private const val QR_KEY = "qr_key"
