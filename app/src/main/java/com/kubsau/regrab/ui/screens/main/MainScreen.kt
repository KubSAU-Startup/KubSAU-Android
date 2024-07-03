package com.kubsau.regrab.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.kubsau.regrab.R
import com.kubsau.regrab.common.navigation.graphs.LatestWorksRoute
import com.kubsau.regrab.common.navigation.graphs.ProfileRoute
import com.kubsau.regrab.common.navigation.graphs.RegistrationRoute
import com.kubsau.regrab.network.ErrorDomain

private sealed class BottomNavItem(
    val index: Int,
    val selectedIcon: ImageVector,
    val defaultIcon: ImageVector,
    val labelResId: Int
) {
    data object Register : BottomNavItem(
        index = 0,
        selectedIcon = Icons.Filled.AccountBox,
        defaultIcon = Icons.Outlined.AccountBox,
        labelResId = R.string.nav_register
    )

    data object Home : BottomNavItem(
        index = 1,
        selectedIcon = Icons.Filled.Home,
        defaultIcon = Icons.Outlined.Home,
        labelResId = R.string.nav_home
    )

    data object Profile : BottomNavItem(
        index = 2,
        selectedIcon = Icons.Filled.AccountCircle,
        defaultIcon = Icons.Outlined.AccountCircle,
        labelResId = R.string.nav_profile
    )

    companion object {
        fun values() = listOf(Register, Home, Profile)
    }
}

@Composable
fun MainScreen(
    onError: (ErrorDomain) -> Unit,
    openUrlScreen: () -> Unit,
    onLogOut: () -> Unit
) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(value = BottomNavItem.Home.index)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                BottomNavItem.values().forEach { item ->
                    val isSelected = selectedItemIndex == item.index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (selectedItemIndex != item.index) {
                                selectedItemIndex = item.index
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon
                                else item.defaultIcon,
                                contentDescription = null
                            )
                        },
                        label = { Text(text = stringResource(id = item.labelResId)) }
                    )
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (selectedItemIndex) {
                BottomNavItem.Register.index -> {
                    RegistrationRoute(
                        onError = onError,
                        onSuccessRegistration = {
                            selectedItemIndex = BottomNavItem.Home.index
                        }
                    )
                }

                BottomNavItem.Home.index -> LatestWorksRoute(onError = onError)

                BottomNavItem.Profile.index -> {
                    ProfileRoute(
                        onError = onError,
                        openUrlScreen = openUrlScreen,
                        onLogOut = onLogOut
                    )
                }

                else -> Unit
            }
        }
    }
}