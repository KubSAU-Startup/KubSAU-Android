package com.example.diploma.common

import android.widget.Toast
import androidx.lifecycle.ViewModel

fun ViewModel.getString(id: Int) = AppGlobal.Instance.getString(id)

fun List<Int>.intToString() = this.toString().trim('[', ']')

fun String.toListInt() = this.split(',').map { it.trim().toInt() }

fun String.toToast() = Toast.makeText(AppGlobal.Instance, this, Toast.LENGTH_SHORT).show()