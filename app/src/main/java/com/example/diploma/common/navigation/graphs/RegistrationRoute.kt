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
    onSuccessRegistration: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Camera.route,
        route = Graphs.Camera.route
    ) {
        composable(Screens.Camera.route) {
            CameraScreen(
                onQrContentObtained = { content ->
                    navController.navigate(Screens.Registration.route + "/$content")
                }
            )
        }

        composable(Screens.Registration.route + "/{$QR_KEY}") {
            RegistrationScreen(
                onSuccessRegistration = {
                    navController.navigate(Graphs.Camera.route) {
                        popUpTo(Graphs.Camera.route) {
                            inclusive = true
                        }
                    }
                    onSuccessRegistration()
                }
            )
        }
    }
}

const val QR_KEY = "qr_key"
