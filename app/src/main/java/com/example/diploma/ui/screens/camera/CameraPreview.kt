package com.example.diploma.ui.screens.camera

import android.util.Size
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.diploma.common.AppGlobal
import com.example.diploma.ui.screens.camera.analyzer.QrAnalyzer

@Composable
fun CameraPreview(
    modifier: Modifier,
    resultCallback: (String) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    AndroidView(factory = { factoryContext ->
        val previewView = PreviewView(factoryContext)
        val preview = Preview.Builder().build()
        val selector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        preview.setSurfaceProvider(previewView.surfaceProvider)
        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(previewView.width / 6, previewView.height / 6))
            .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST).build()
        imageAnalysis.setAnalyzer(
            ContextCompat.getMainExecutor(factoryContext),
            QrAnalyzer { result ->
                resultCallback(result)
                Toast.makeText(AppGlobal.Instance, result, Toast.LENGTH_SHORT).show()
            })
        try {
            cameraProviderFuture.get().bindToLifecycle(
                lifecycleOwner, selector,
                preview, imageAnalysis
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        previewView
    }, modifier = modifier)
}