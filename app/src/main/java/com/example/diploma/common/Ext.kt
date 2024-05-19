package com.example.diploma.common

import androidx.lifecycle.ViewModel

fun ViewModel.getString(id: Int) = AppGlobal.Instance.getString(id)

fun List<Int>.intToString() = this.toString().trim('[', ']')

fun String.toListInt() = this.split(',').map { it.trim().toInt() }