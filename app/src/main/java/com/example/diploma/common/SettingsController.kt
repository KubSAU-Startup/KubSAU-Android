package com.example.diploma.common

import com.example.diploma.common.storage.AccountConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object SettingsController {

    private val _greenThemeEnabled = MutableStateFlow(AccountConfig.greenThemeEnabled)
    val greenThemeEnabled = _greenThemeEnabled.asStateFlow()

    fun updateGreenThemeEnabled(enabled: Boolean) {
        _greenThemeEnabled.update { enabled }
    }
}
