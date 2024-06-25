package com.example.diploma.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    onError: (String) -> Unit,
    viewModel: ProfileViewModel = koinViewModel<ProfileViewModelImpl>()
) {
    Column {
        Text(text = "Profile")
    }
}
