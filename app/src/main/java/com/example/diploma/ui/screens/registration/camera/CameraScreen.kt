package com.example.diploma.ui.screens.registration.camera

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diploma.R
import com.example.diploma.ui.screens.registration.camera.components.CameraPreview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(onQrContentObtained: (String) -> Unit) {
    val context = LocalContext.current

    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )

    val isCameraPermissionGranted by remember {
        derivedStateOf { cameraPermissionState.status.isGranted }
    }

    Scaffold(
        topBar = {
            if (!isCameraPermissionGranted) {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.title_camera_permission)) }
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        if (isCameraPermissionGranted) {
            CameraPreview(
                onResult = onQrContentObtained,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                    // If the user has denied the permission but the rationale can be shown,
                    // then gently explain why the app requires this permission
                    stringResource(id = R.string.camera_permission_require)
                } else {
                    // If it's the first time the user lands on this feature, or the user
                    // doesn't want to be asked again for this permission, explain that the
                    // permission is required
                    stringResource(id = R.string.permission_need)
                }
                Text(
                    text = textToShow,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                Spacer(modifier = Modifier.height(36.dp))
                Button(
                    onClick = {
                        if (cameraPermissionState.status.shouldShowRationale) {
                            context.startActivity(
                                Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", context.packageName, null)
                                )
                            )
                        } else {
                            cameraPermissionState.launchPermissionRequest()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .height(56.dp)
                ) {
                    Text(
                        text = stringResource(
                            id = if (cameraPermissionState.status.shouldShowRationale) {
                                R.string.action_open_app_permissions
                            } else {
                                R.string.action_request_permission
                            }
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CameraScreenPreview() {
    CameraScreen {

    }
}
