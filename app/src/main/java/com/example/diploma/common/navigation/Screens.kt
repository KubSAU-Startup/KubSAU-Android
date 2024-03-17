package com.example.diploma.common.navigation

sealed class Screens(val route: String) {
    data object Login : Screens("login")

    data object Main : Screens("main")

    data object Camera : Screens("camera")

    data object Journal : Screens("journal")

    data object Registration : Screens("registration")

    //================================================

    data object Test : Screens("test")

    data object TestTwo : Screens("test_two")

    data object TestThree : Screens("test_three")

    data object TestFour : Screens("test_four")
}