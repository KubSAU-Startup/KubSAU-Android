package com.example.diploma.ui.screens.latestworks.model

data class LatestWorksScreenState(
    val isUrlWrong: Boolean
) {

    companion object {
        val EMPTY: LatestWorksScreenState = LatestWorksScreenState(
            isUrlWrong = false
        )
    }
}
