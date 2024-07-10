package com.kubsau.regrab.common.navigation.graphs

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kubsau.regrab.BuildConfig
import com.kubsau.regrab.R
import com.kubsau.regrab.common.navigation.Graphs
import com.kubsau.regrab.common.navigation.Screens
import com.kubsau.regrab.common.storage.NetworkConfig
import com.kubsau.regrab.network.ErrorDomain
import com.kubsau.regrab.ui.screens.auth.url.UrlScreen
import com.kubsau.regrab.ui.screens.registration.camera.components.Alert
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@SuppressLint("InlinedApi")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RootGraph(
    navController: NavHostController = rememberNavController()
) {
    val notificationsPermissionState =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    LaunchedEffect(true) {
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationsPermissionState.launchPermissionRequest()
        }
    }

    val context = LocalContext.current

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

    var errorText: String? by remember {
        mutableStateOf(null)
    }
    var showError: Boolean by remember {
        mutableStateOf(false)
    }

    if (showError) {
        Alert(
            onDismiss = {
                showError = false
                errorText = null
            },
            title = stringResource(id = R.string.error_occurred),
            text = errorText.orEmpty()
        )
    }

    val onError = { error: ErrorDomain ->
        when (error) {
            ErrorDomain.WrongUrlError -> {
                needToChangeUrl = true
                NetworkConfig.clearUrl()
                Toast.makeText(context, R.string.wrong_url_error, Toast.LENGTH_LONG).show()
            }

            ErrorDomain.WrongTokenFormat -> {
                navController.navigate(Graphs.Auth.route) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
                Toast.makeText(context, R.string.wrong_token_format_error, Toast.LENGTH_LONG).show()
            }

            else -> {
                errorText = error.description
                showError = true
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
}
