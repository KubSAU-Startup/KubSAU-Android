package com.example.diploma.ui.screens.test.one

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.network.NetworkRepo
import kotlinx.coroutines.launch

class TestVM(private val repo: NetworkRepo) : ViewModel() {
    var responseText by mutableStateOf("empty text")
        private set

    fun login(login: String, password: String) {
        viewModelScope.launch {
        }
    }
/*
    fun getAccountInfo() {
        viewModelScope.launch {
            responseText = repo.getAccountInfo().toString()
        }
    }

    fun getJournal() {
        viewModelScope.launch {
            responseText = repo.getJournal().toString()
        }
    }*/
}