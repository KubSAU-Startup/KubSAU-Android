package com.example.diploma.ui.screens.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.common.EMPTY_STRING
import com.example.diploma.network.NetworkRepo
import com.example.diploma.network.models.work.Work
import kotlinx.coroutines.launch

class RegistrationVM(private val repo: NetworkRepo) : ViewModel() {

    private var disciplineId by mutableIntStateOf(0)
    private var studentId by mutableIntStateOf(0)
    private var workTypeId by mutableIntStateOf(0)
    var editable by mutableStateOf(false)
        private set

    var disciplineDisplay by mutableStateOf(EMPTY_STRING)
        private set
    var studentDisplay by mutableStateOf(EMPTY_STRING)
        private set
    var workTypeDisplay by mutableStateOf(EMPTY_STRING)
        private set
    var workTitle by mutableStateOf(EMPTY_STRING)

    fun fetchData(data: String) {

    }

    fun registration() {

    }

}

private fun Int.toBoolean(): Boolean = this == 1