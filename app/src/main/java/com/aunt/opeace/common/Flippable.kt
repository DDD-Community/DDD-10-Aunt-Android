package com.aunt.opeace.common

import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.zIndex
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@Composable
fun Flippable(
    modifier: Modifier = Modifier,
    frontSide: @Composable () -> Unit,
    backSide: @Composable () -> Unit,
    flipController: FlipController,
    clockwise: Boolean = true,
) {
    val flipDurationMs = 400
    var flippableState by remember { mutableStateOf(FlippableState.FRONT) }
    val transition: Transition<FlippableState> = updateTransition(
        targetState = flippableState,
        label = "Flip Transition",
    )

    LaunchedEffect(key1 = flipController, block = {
        flipController.flipRequests
            .onEach { flippableState = it }
            .launchIn(this)
    })

    val frontRotation: Float by transition.animateFloat(
        transitionSpec = {
            when {
                FlippableState.FRONT isTransitioningTo FlippableState.BACK -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        0f at 0
                        90f at flipDurationMs / 2
                        90f at flipDurationMs
                    }
                }

                FlippableState.BACK isTransitioningTo FlippableState.FRONT -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        90f at 0
                        90f at flipDurationMs / 2
                        0f at flipDurationMs
                    }
                }

                else -> snap()
            }
        },
        label = "Front Rotation"
    ) { state ->
        when(state) {
            FlippableState.FRONT -> 0f
            FlippableState.BACK -> 180f
        }
    }

    val backRotation: Float by transition.animateFloat(
        transitionSpec = {
            when {
                FlippableState.FRONT isTransitioningTo FlippableState.BACK -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        -90f at 0
                        -90f at flipDurationMs / 2
                        0f at flipDurationMs
                    }
                }

                FlippableState.BACK isTransitioningTo FlippableState.FRONT -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        0f at 0
                        -90f at flipDurationMs / 2
                        -90f at flipDurationMs
                    }
                }

                else -> snap()
            }
        },
        label = "Back Rotation"
    ) { state ->
        when(state) {
            FlippableState.FRONT -> 180f
            FlippableState.BACK -> 0f
        }
    }

    val frontOpacity: Float by transition.animateFloat(
        transitionSpec = {
            when {
                FlippableState.FRONT isTransitioningTo FlippableState.BACK -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        1f at 0
                        1f at (flipDurationMs / 2) - 1
                        0f at flipDurationMs / 2
                        0f at flipDurationMs
                    }
                }

                FlippableState.BACK isTransitioningTo FlippableState.FRONT -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        0f at 0
                        0f at (flipDurationMs / 2) - 1
                        1f at flipDurationMs / 2
                        1f at flipDurationMs
                    }
                }

                else -> snap()
            }
        },
        label = "Front Opacity"
    ) { state ->
        when(state) {
            FlippableState.FRONT -> 1f
            FlippableState.BACK -> 0f
        }
    }

    val backOpacity: Float by transition.animateFloat(
        transitionSpec = {
            when {
                FlippableState.FRONT isTransitioningTo FlippableState.BACK -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        0f at 0
                        0f at (flipDurationMs / 2) - 1
                        1f at flipDurationMs / 2
                        1f at flipDurationMs
                    }
                }

                FlippableState.BACK isTransitioningTo FlippableState.FRONT -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        1f at 0
                        1f at (flipDurationMs / 2) - 1
                        0f at flipDurationMs / 2
                        0f at flipDurationMs
                    }
                }

                else -> snap()
            }
        },
        label = "Back Opacity"
    ) { state ->
        when(state) {
            FlippableState.FRONT -> 0f
            FlippableState.BACK -> 1f
        }
    }

    Box(contentAlignment = Alignment.Center) {

        Box(modifier = Modifier
            .graphicsLayer {
                this.cameraDistance = 100f
                rotationY = if (clockwise) backRotation else -backRotation
            }
            .alpha(backOpacity)
            .zIndex(1F - backOpacity)
        ) {
            backSide()
        }

        Box(modifier = Modifier
            .graphicsLayer {
                this.cameraDistance = 100f
                rotationY = if (clockwise) frontRotation else -frontRotation
            }
            .alpha(frontOpacity)
            .zIndex(1F - frontRotation)
        ) {
            frontSide()
        }
    }
}

enum class FlippableState {
    FRONT,
    BACK
}

class FlipController {

    private val _flipRequests = MutableSharedFlow<FlippableState>(extraBufferCapacity = 1)
    internal val flipRequests = _flipRequests.asSharedFlow()

    private var _currentSide: FlippableState = FlippableState.FRONT

    fun flip() {
        if (_currentSide == FlippableState.FRONT) flipToBack()
        else flipToFront()
    }

    private fun flipToFront() {
        flip(FlippableState.FRONT)
    }

    private fun flipToBack() {
        flip(FlippableState.BACK)
    }

    private fun flip(flippableState: FlippableState) {
        _currentSide = flippableState
        _flipRequests.tryEmit(flippableState)
    }
}
