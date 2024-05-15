package com.example.diploma.common

import androidx.lifecycle.ViewModel

fun ViewModel.getString(id: Int) = AppGlobal.Instance.getString(id)