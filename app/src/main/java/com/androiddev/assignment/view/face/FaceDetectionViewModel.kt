package com.androiddev.assignment.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FaceDetectionViewModel : ViewModel() {

    private val _isFaceInsideBox = MutableStateFlow(false)
    val isFaceInsideBox: StateFlow<Boolean> = _isFaceInsideBox

    private val detector by lazy {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .build()
        FaceDetection.getClient(options)
    }

    @SuppressLint("UnsafeOptInUsageError")
    fun processImage(
        imageProxy: ImageProxy, center: Offset, radius: Float, screenWidth: Float,
        screenHeight: Float
    ) {
        val mediaImage = imageProxy.image ?: return
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        detector.process(image)
            .addOnSuccessListener { faces ->
                var faceInside = false

                val imageWidth = imageProxy.width.toFloat()
                val imageHeight = imageProxy.height.toFloat()
                val rotation = imageProxy.imageInfo.rotationDegrees

                for (face in faces) {
                    val bounds = face.boundingBox
                    val faceCenterX = bounds.centerX().toFloat()
                    val faceCenterY = bounds.centerY().toFloat()

                    val (scaledX, scaledY) = when (rotation) {
                        0, 180 -> {
                            val scaleX = screenWidth / imageWidth
                            val scaleY = screenHeight / imageHeight
                            faceCenterX * scaleX to faceCenterY * scaleY
                        }

                        90, 270 -> {
                            val scaleX = screenWidth / imageHeight
                            val scaleY = screenHeight / imageWidth
                            faceCenterY * scaleX to (imageWidth - faceCenterX) * scaleY
                        }

                        else -> faceCenterX to faceCenterY
                    }

                    val distance = kotlin.math.sqrt(
                        (scaledX - center.x) * (scaledX - center.x) +
                                (scaledY - center.y) * (scaledY - center.y)
                    )

                    if (distance <= radius) {
                        faceInside = true
                    }
                }

                _isFaceInsideBox.value = faceInside
            }
            .addOnFailureListener { e ->
                Log.e("FaceDetection", "Face detection failed: ${e.message}")
                _isFaceInsideBox.value = false
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }

}
