package com.kubsau.regrab.common.navigation

sealed class Graphs(val route: String) {
    data object Root : Graphs("root_graph")
    data object Auth : Graphs("auth_route")
    data object Main : Graphs("main_route")
    data object Camera : Graphs("camera_route")
    data object LatestWorks : Graphs("journal_route")
    data object Profile : Graphs("profile_graph")
}
