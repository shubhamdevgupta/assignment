package com.androiddev.assignment.utils

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.androiddev.assignment.R
import kotlinx.coroutines.delay

@Composable
fun KissAnimation(isFaceInsideCircle: Boolean) {
    var showKiss by remember { mutableStateOf(false) }

    LaunchedEffect(isFaceInsideCircle) {
        if (isFaceInsideCircle) {
            showKiss = true
            delay(1500) // Kiss stays for 1.5 sec
            showKiss = false
        }
    }

    AnimatedVisibility(
        visible = showKiss,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight }, // Start from bottom
            animationSpec = tween(durationMillis = 500)
        ) + fadeIn(animationSpec = tween(500)),
        exit = fadeOut(animationSpec = tween(500))
    ) {
        Image(
            painter = painterResource(id = R.drawable.kiss),
            contentDescription = "Flying Kiss",
            modifier = Modifier
                .size(100.dp)
        )
    }
}
