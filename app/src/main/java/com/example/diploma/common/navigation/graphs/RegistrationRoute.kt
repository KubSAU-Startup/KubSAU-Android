package com.example.diploma.common.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diploma.common.navigation.CAMERA_ROUTE
import com.example.diploma.common.navigation.Screens
import com.example.diploma.ui.screens.registration.RegistrationScreen
import com.example.diploma.ui.screens.registration.camera.CameraScreen

@Composable
fun RegistrationRoute(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController, startDestination = Screens.Camera.route,
        route = CAMERA_ROUTE
    ) {
        composable(Screens.Camera.route) {
            CameraScreen {
                navController.navigate(Screens.Registration.route + "/$it")
            }
        }

        composable(Screens.Registration.route + "/{$QR_KEY}") { backStack ->
            RegistrationScreen(qrResult = backStack.arguments?.getString(QR_KEY).toString()) {
                navController.navigate(CAMERA_ROUTE) {
                    popUpTo(CAMERA_ROUTE) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

private const val QR_KEY = "qr_key"