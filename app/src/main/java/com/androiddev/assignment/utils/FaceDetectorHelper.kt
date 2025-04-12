package com.androiddev.assignment.utils

import android.annotation.SuppressLint
import android.graphics.Rect
import android.media.Image
import android.util.Log
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions

class FaceDetectorHelper {

    private val detector: FaceDetector by lazy {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST) // fast mode
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
            .build()

        FaceDetection.getClient(options)
    }

    @SuppressLint("UnsafeOptInUsageError")
    fun detectFace(
        imageProxy: ImageProxy,
        onFaceDetected: (Rect?) -> Unit
    ) {
        val mediaImage: Image = imageProxy.image ?: run {
            imageProxy.close()
            return
        }

        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        detector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isNotEmpty()) {
                    val faceBoundingBox = faces[0].boundingBox
                    onFaceDetected(faceBoundingBox)
                } else {
                    onFaceDetected(null)
                }
                imageProxy.close()
            }
            .addOnFailureListener { e ->
                Log.e("FaceDetection", "Detection Failed", e)
                onFaceDetected(null)
                imageProxy.close()
            }
    }
}
