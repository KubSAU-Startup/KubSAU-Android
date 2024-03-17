package com.example.diploma.ui.screens.camera

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diploma.common.AppGlobal

class CameraVM : ViewModel() {
    var result by mutableStateOf<Bitmap?>(null)
        private set

    fun getQrContent(activityResult: Bitmap?) {
        if (activityResult == null) {
            Toast.makeText(AppGlobal.Instance, "result is null", Toast.LENGTH_SHORT).show()
            return
        }
        result = activityResult
    }
}