package com.example.diploma.ui.screens.registration.camera.components

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.diploma.R
import com.example.diploma.common.QR_CONTENT_FORMAT
import com.example.diploma.common.storage.AccountConfig
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CameraPreview(
    onResult: (String) -> Unit,
    modifier: Modifier = Modifier,
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

    var showPreview by remember {
        mutableStateOf(true)
    }

    val coroutineScope = rememberCoroutineScope()

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
        ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED,
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
                val value = barcode.displayValue ?: "null"
                println("Department: " + AccountConfig.departmentId)
                Log.d("Camera", "Camera: result $value")

                if (checkContentTemplate(value)) {
                    val departmentId = value.split(",").first().toInt()
                    if (departmentId != AccountConfig.departmentId) {
                        showDepartmentError = true
                    } else {
                        coroutineScope.launch {
                            cameraProvider?.unbindAll()
                            showPreview = false
                            delay(250)
                            onResult(value)
                        }
                    }
                } else {
                    showContentError = true
                }
            }

            else -> {
                Log.e("Camera", "Camera: result ${barcode.displayValue} $type")
                showFormatError = true
            }
        }

        handleResult = false
    }

    val imageAnalyzer = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build().also { analysis -> analysis.setAnalyzer(cameraExecutor, analyzer) }

    cameraController.setImageAnalysisAnalyzer(cameraExecutor, analyzer)

    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    val addCameraProviderListener: (PreviewView) -> Unit = { previewView ->
        cameraProviderFuture.addListener(
            {
                cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder()
                    .build()
                    .also { it.surfaceProvider = previewView.surfaceProvider }

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
            onDismiss = { showFormatError = false },
            title = stringResource(id = R.string.scan_qr_error_format_title),
            text = stringResource(id = R.string.scan_qr_error_format_text)
        )
    }

    if (showContentError) {
        Alert(
            onDismiss = { showContentError = false },
            title = stringResource(id = R.string.scan_qr_content_format_title),
            text = stringResource(id = R.string.scan_qr_content_format_text)
        )
    }

    if (showDepartmentError) {
        Alert(
            onDismiss = { showDepartmentError = false },
            title = stringResource(id = R.string.scan_qr_content_department_title),
            text = stringResource(id = R.string.scan_qr_content_department_text)
        )
    }

    if (showPreview) {
        AndroidView(
            modifier = modifier.fillMaxSize(),
            factory = { factoryContext ->
                PreviewView(factoryContext).also { preview ->
                    preview.controller = cameraController
                    addCameraProviderListener(preview)
                }
            }
        )
    }
}

//  departmentId, disciplineId, studentId, workTypeId
private fun checkContentTemplate(content: String): Boolean =
    content.matches(Regex(QR_CONTENT_FORMAT))
