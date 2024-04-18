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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.diploma.R
import com.example.diploma.common.QR_CONTENT_FORMAT
import com.example.diploma.common.storage.AccountConfig
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode

@Composable
fun CameraPreview(
    onResult: (String) -> Unit
) {

    var showFormatError by rememberSaveable {
        mutableStateOf(false)
    }

    var showContentError by rememberSaveable {
        mutableStateOf(false)
    }

    var showDepartmentError by rememberSaveable {
        mutableStateOf(false)
    }

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
                val barcodeResult = barcode.displayValue ?: "null"
                Log.d("Camera", "Camera: result $barcodeResult")

                if (checkContentTemplate(barcodeResult)) {

                    val (main, department) = barcodeResult.split(";")

                    if (department.toInt() == AccountConfig.department) {

                        cameraProvider?.unbindAll()
                        cameraProvider = null

                        onResult(main)
                    } else showDepartmentError = true

                } else showContentError = true

            }

            else -> {
                val barcodeResult = barcode.displayValue ?: "null"
                Log.e("Camera", "Camera: result $barcodeResult $type")

                Toast.makeText(context, "text", Toast.LENGTH_SHORT).show()

                showFormatError = true
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

    if (showFormatError) {
        Alert(
            onDismiss = {
                showFormatError = false
            },
            title = stringResource(id = R.string.scan_qr_error_format_title),
            text = stringResource(id = R.string.scan_qr_error_format_title)
        )
    }

    if (showContentError) {
        Alert(
            onDismiss = {
                showContentError = false
            },
            title = stringResource(id = R.string.scan_qr_content_format_title),
            text = stringResource(id = R.string.scan_qr_content_format_text)
        )
    }

    if (showDepartmentError) {
        Alert(
            onDismiss = {
                showDepartmentError = false
            },
            title = stringResource(id = R.string.scan_qr_content_department_title),
            text = stringResource(id = R.string.scan_qr_content_department_text)
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

//    studentId, subjectId, editable (1 | 0); departmentId
private fun checkContentTemplate(content: String): Boolean =
    content.matches(Regex(QR_CONTENT_FORMAT))