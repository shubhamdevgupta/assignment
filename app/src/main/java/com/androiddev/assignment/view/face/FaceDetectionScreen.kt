package com.androiddev.assignment.view.face

import android.Manifest
import android.content.pm.PackageManager
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageAnalysis
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.androiddev.assignment.utils.CameraHelper
import com.androiddev.assignment.viewmodel.FaceDetectionViewModel

@Composable
fun FaceDetectionScreen() {
    val context = LocalContext.current
    val viewModel: FaceDetectionViewModel = viewModel()

    val isFaceInsideCircle by viewModel.isFaceInsideBox.collectAsState()

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    if (hasCameraPermission) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            var previewView: PreviewView? by remember { mutableStateOf(null) }

            // Get screen size dynamically
            val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels.toFloat()
            val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels.toFloat()

            AndroidView(
                factory = {
                    PreviewView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        previewView = this
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            previewView?.let { pv ->
                val cameraHelper = remember {
                    CameraHelper(
                        context = context,
                        previewView = pv,
                        analyzer = ImageAnalysis.Analyzer { imageProxy ->
                            viewModel.processImage(
                                imageProxy,
                                center = Offset(screenWidth / 2, screenHeight / 2),
                                radius = 350f,
                                screenWidth = screenWidth,
                                screenHeight = screenHeight
                            )
                        }
                    )
                }
                LaunchedEffect(Unit) {
                    cameraHelper.startCamera()
                }
            }

            // Drawing
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = 350f

                // Circle
                drawCircle(
                    color = if (isFaceInsideCircle) Color.Green else Color.Red,
                    radius = radius,
                    center = center,
                    style = Stroke(width = 8f)
                )
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Camera permission required.")
        }
    }
}

