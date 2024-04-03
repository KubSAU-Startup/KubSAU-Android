package com.example.diploma.ui.screens.camera.components

import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode

@Composable
fun CameraPreview(
    onResult: (String) -> Unit
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    var cameraProvider: ProcessCameraProvider? = null

    val cameraController = LifecycleCameraController(context)
    cameraController.bindToLifecycle(lifecycleOwner)

    val cameraExecutor = ContextCompat.getMainExecutor(context)

    val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()

    val barcodeScanner = BarcodeScanning.getClient(options)

    var handleResult = false

    val analyzer = MlKitAnalyzer(
        listOf(barcodeScanner),
        CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED,
        cameraExecutor
    ) { result: MlKitAnalyzer.Result? ->

        if (handleResult) return@MlKitAnalyzer

        handleResult = true

        val barcodeResult = result?.getValue(barcodeScanner)
        if (barcodeResult == null || barcodeResult.size == 0 || barcodeResult.first() == null) {
            handleResult = false
            return@MlKitAnalyzer
        }

        val barcode = barcodeResult[0]

        when (val type = barcode.valueType) {

            Barcode.TYPE_TEXT -> {
                val x = barcode.displayValue ?: "null"
                Log.d("Camera", "Camera: result $x")
                Toast.makeText(context, x, Toast.LENGTH_SHORT).show()

                cameraProvider?.unbindAll()
                cameraProvider = null

                onResult(x)
            }

            else -> {
                val x = barcode.displayValue ?: "null"
                Log.e("Camera", "Camera: result $x $type")
                Toast.makeText(context, x, Toast.LENGTH_SHORT).show()

                cameraProvider?.unbindAll()
                cameraProvider = null
            }

        }

        handleResult = false

    }

    val imageAnalyzer = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build().also {
            it.setAnalyzer(cameraExecutor, analyzer)
        }

    cameraController.setImageAnalysisAnalyzer(
        cameraExecutor, analyzer
    )

    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    val addCameraProviderListener: (PreviewView) -> Unit = { previewView ->
        cameraProviderFuture.addListener(
            {
                cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider?.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalyzer
                    )
                } catch (e: Exception) {
                    Log.e("Camera", "Camera error $e")
                }
            }, cameraExecutor
        )
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            PreviewView(it).also {
                it.controller = cameraController
                addCameraProviderListener.invoke(it)
            }
        }
    )

}