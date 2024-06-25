package com.example.diploma.common.navigation

sealed class Screens(val route: String) {
    data object Login : Screens("login")

    data object Main : Screens("main")

    data object Camera : Screens("camera")

    data object LatestWorks : Screens("journal")

    data object Registration : Screens("registration")

    data object Url : Screens("url")

    data object Profile : Screens("profile")
}
