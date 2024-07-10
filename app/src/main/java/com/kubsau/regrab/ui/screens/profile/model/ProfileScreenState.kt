package com.kubsau.regrab.ui.screens.profile.model

data class ProfileScreenState(
    val fullName: String,
    val department: String
) {
    companion object {

        val EMPTY: ProfileScreenState = ProfileScreenState(
            fullName = "",
            department = ""
        )
    }
}
