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
        navController = navController, startDestination = Screens.Registration.route,
        route = CAMERA_ROUTE
    ) {
        composable(Screens.Registration.route) { entry ->
            RegistrationScreen(qrResult = entry.savedStateHandle.get<String>(QR_KEY)) {
                navController.navigate(route = Screens.Camera.route)
            }
        }
        composable(route = Screens.Camera.route) {
            CameraScreen { qrContent ->
                navController.previousBackStackEntry?.savedStateHandle?.set(key = QR_KEY, qrContent)
                navController.popBackStack()
            }
        }
    }
}

private const val QR_KEY = "qr_key"