package com.example.diploma.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.example.diploma.common.storage.AccountConfig
import com.example.diploma.ui.screens.profile.model.ProfileScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface ProfileViewModel {
    val screenState: StateFlow<ProfileScreenState>
}

class ProfileViewModelImpl : ProfileViewModel, ViewModel() {
    override val screenState = MutableStateFlow(ProfileScreenState.EMPTY)

    init {
        loadAccountInfo()
    }

    private fun loadAccountInfo() {
        val newState = screenState.value.copy(
            fullName = AccountConfig.fullName,
            department = AccountConfig.departmentName
        )
        screenState.update { newState }
    }
}
