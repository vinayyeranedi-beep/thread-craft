package com.example.threadcraft.ui.components

import android.graphics.Bitmap
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.threadcraft.ui.theme.Ink
import com.example.threadcraft.ui.theme.InkLine
import com.example.threadcraft.ui.theme.Thread
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun TshirtCanvas(
    shirtColor: Color,
    view: String, // "front" or "back"
    designId: String,
    customBitmap: Bitmap? = null,
    autoRotate: Boolean = false,
    modifier: Modifier = Modifier,
    onViewChanged: ((String) -> Unit)? = null
) {
    var rotationY by remember { mutableFloatStateOf(if (view == "back") 180f else 0f) }
    var tiltX by remember { mutableFloatStateOf(0f) }
    val scope = rememberCoroutineScope()
    val animatedRotation = remember { Animatable(rotationY) }

    // Synchronize rotation with view changes from buttons
    LaunchedEffect(view) {
        val target = if (view == "back") 180f else 0f
        val currentMod = ((rotationY % 360f) + 360f) % 360f
        val isBackCurrently = currentMod in 90f..270f
        if ((view == "back" && !isBackCurrently) || (view == "front" && isBackCurrently)) {
            animatedRotation.animateTo(
                targetValue = target,
                animationSpec = tween(durationMillis = 500)
            ) {
                rotationY = value
            }
        }
    }

    // Auto rotate loop when enabled
    LaunchedEffect(autoRotate) {
        if (autoRotate) {
            val autoAnim = Animatable(rotationY)
            autoAnim.animateTo(
                targetValue = rotationY + 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(12000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            ) {
                rotationY = value
            }
        }
    }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    rotationY = (rotationY + dragAmount.x * 0.7f)
                    tiltX = (tiltX - dragAmount.y * 0.3f).coerceIn(-15f, 15f)

                    val normalized = ((rotationY % 360f) + 360f) % 360f
                    val newView = if (normalized in 90f..270f) "back" else "front"
                    if (newView != view) {
                        onViewChanged?.invoke(newView)
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // Subtle ambient floor shadow
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val shadowY = h * 0.88f
            val shadowWidth = w * 0.55f * (0.85f + 0.15f * abs(cos(rotationY * PI / 180.0)).toFloat())
            drawOval(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x99000000), Color(0x33000000), Color.Transparent),
                    center = Offset(w / 2f, shadowY),
                    radius = shadowWidth / 2f
                ),
                topLeft = Offset(w / 2f - shadowWidth / 2f, shadowY - h * 0.04f),
                size = Size(shadowWidth, h * 0.08f)
            )
        }

        // T-Shirt Garment & Design
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            val radY = (rotationY * PI / 180.0).toFloat()
            val cosRot = cos(radY)
            val isFacingFront = cosRot >= 0f

            // Perspective horizontal compression based on angle
            val scaleX = abs(cosRot) * 0.95f + 0.05f
            val shiftX = sin(radY) * (w * 0.04f)

            // Garment bounds
            val shirtTop = h * 0.14f + tiltX * 2.5f
            val shirtBottom = h * 0.82f + tiltX * 2.5f
            val shirtHeight = shirtBottom - shirtTop
            val shirtCenterY = shirtTop + shirtHeight / 2f
            val shirtCenterX = w / 2f + shiftX

            val halfShoulder = (w * 0.42f) * scaleX
            val halfTorso = (w * 0.29f) * scaleX
            val halfHem = (w * 0.31f) * scaleX
            val sleeveDrop = shirtHeight * 0.28f
            val sleeveWidth = (w * 0.18f) * scaleX

            // Construct 2.5D Garment Path
            val shirtPath = Path().apply {
                // Collar center top
                val neckWidth = (w * 0.12f) * scaleX
                val neckDepth = if (isFacingFront) shirtHeight * 0.11f else shirtHeight * 0.04f

                moveTo(shirtCenterX - neckWidth, shirtTop + shirtHeight * 0.02f)

                // Left shoulder
                lineTo(shirtCenterX - halfShoulder, shirtTop + shirtHeight * 0.10f)

                // Left sleeve out & down
                lineTo(shirtCenterX - halfShoulder - sleeveWidth, shirtTop + sleeveDrop)
                lineTo(shirtCenterX - halfShoulder - sleeveWidth * 0.45f, shirtTop + sleeveDrop + shirtHeight * 0.12f)

                // Left armpit
                lineTo(shirtCenterX - halfTorso, shirtTop + shirtHeight * 0.32f)

                // Left torso taper down to hem
                cubicTo(
                    shirtCenterX - halfTorso * 0.92f, shirtCenterY,
                    shirtCenterX - halfHem * 0.96f, shirtBottom - shirtHeight * 0.12f,
                    shirtCenterX - halfHem, shirtBottom
                )

                // Bottom Hem curve
                quadraticTo(
                    shirtCenterX, shirtBottom + shirtHeight * 0.035f,
                    shirtCenterX + halfHem, shirtBottom
                )

                // Right torso up to armpit
                cubicTo(
                    shirtCenterX + halfHem * 0.96f, shirtBottom - shirtHeight * 0.12f,
                    shirtCenterX + halfTorso * 0.92f, shirtCenterY,
                    shirtCenterX + halfTorso, shirtTop + shirtHeight * 0.32f
                )

                // Right armpit & sleeve
                lineTo(shirtCenterX + halfShoulder + sleeveWidth * 0.45f, shirtTop + sleeveDrop + shirtHeight * 0.12f)
                lineTo(shirtCenterX + halfShoulder + sleeveWidth, shirtTop + sleeveDrop)

                // Right shoulder
                lineTo(shirtCenterX + halfShoulder, shirtTop + shirtHeight * 0.10f)
                lineTo(shirtCenterX + neckWidth, shirtTop + shirtHeight * 0.02f)

                // Collar curve
                quadraticTo(
                    shirtCenterX, shirtTop + neckDepth,
                    shirtCenterX - neckWidth, shirtTop + shirtHeight * 0.02f
                )
                close()
            }

            // Garment Color shading & fabric gradient
            val lightAngleX = shirtCenterX + (sin(radY) * w * 0.3f)
            val baseColor = shirtColor
            val isDark = (baseColor.red * 0.299 + baseColor.green * 0.587 + baseColor.blue * 0.114) < 0.35

            val highlightColor = if (isDark) {
                baseColor.copy(
                    red = (baseColor.red + 0.18f).coerceAtMost(1f),
                    green = (baseColor.green + 0.18f).coerceAtMost(1f),
                    blue = (baseColor.blue + 0.18f).coerceAtMost(1f)
                )
            } else {
                Color.White.copy(alpha = 0.85f)
            }

            val shadowColor = if (isDark) {
                Color(0xFF070709)
            } else {
                baseColor.copy(
                    red = (baseColor.red * 0.7f),
                    green = (baseColor.green * 0.7f),
                    blue = (baseColor.blue * 0.7f)
                )
            }

            // Draw garment base fill with dynamic lighting gradient
            drawPath(
                path = shirtPath,
                brush = Brush.linearGradient(
                    colors = listOf(highlightColor, baseColor, shadowColor),
                    start = Offset(lightAngleX - w * 0.25f, shirtTop),
                    end = Offset(lightAngleX + w * 0.4f, shirtBottom)
                )
            )

            // Inner collar depth (inside the neck)
            val neckWidth = (w * 0.12f) * scaleX
            val innerNeckPath = Path().apply {
                moveTo(shirtCenterX - neckWidth, shirtTop + shirtHeight * 0.02f)
                quadraticTo(
                    shirtCenterX, shirtTop - shirtHeight * 0.015f,
                    shirtCenterX + neckWidth, shirtTop + shirtHeight * 0.02f
                )
                val frontDepth = if (isFacingFront) shirtHeight * 0.11f else shirtHeight * 0.04f
                quadraticTo(
                    shirtCenterX, shirtTop + frontDepth,
                    shirtCenterX - neckWidth, shirtTop + shirtHeight * 0.02f
                )
                close()
            }
            drawPath(
                path = innerNeckPath,
                color = shadowColor
            )

            // Collar ribbing line
            val collarRibbing = Path().apply {
                val frontDepth = if (isFacingFront) shirtHeight * 0.11f else shirtHeight * 0.04f
                moveTo(shirtCenterX - neckWidth * 1.05f, shirtTop + shirtHeight * 0.02f)
                quadraticTo(
                    shirtCenterX, shirtTop + frontDepth + 6f,
                    shirtCenterX + neckWidth * 1.05f, shirtTop + shirtHeight * 0.02f
                )
            }
            drawPath(
                path = collarRibbing,
                color = highlightColor.copy(alpha = 0.5f),
                style = Stroke(width = 3.5f, cap = StrokeCap.Round)
            )

            // Sleeve crease & drape lines
            val sleeveLeftCrease = Path().apply {
                moveTo(shirtCenterX - halfShoulder * 0.95f, shirtTop + shirtHeight * 0.14f)
                lineTo(shirtCenterX - halfTorso, shirtTop + shirtHeight * 0.32f)
            }
            drawPath(
                path = sleeveLeftCrease,
                color = shadowColor.copy(alpha = 0.55f),
                style = Stroke(width = 2.5f, cap = StrokeCap.Round)
            )

            val sleeveRightCrease = Path().apply {
                moveTo(shirtCenterX + halfShoulder * 0.95f, shirtTop + shirtHeight * 0.14f)
                lineTo(shirtCenterX + halfTorso, shirtTop + shirtHeight * 0.32f)
            }
            drawPath(
                path = sleeveRightCrease,
                color = shadowColor.copy(alpha = 0.55f),
                style = Stroke(width = 2.5f, cap = StrokeCap.Round)
            )

            // Bottom hem double-stitch accent
            val hemStitch = Path().apply {
                moveTo(shirtCenterX - halfHem * 0.96f, shirtBottom - shirtHeight * 0.035f)
                quadraticTo(
                    shirtCenterX, shirtBottom + shirtHeight * 0.005f,
                    shirtCenterX + halfHem * 0.96f, shirtBottom - shirtHeight * 0.035f
                )
            }
            drawPath(
                path = hemStitch,
                color = shadowColor.copy(alpha = 0.4f),
                style = Stroke(width = 2f, cap = StrokeCap.Round)
            )

            // Subtle outer garment silhouette outline
            drawPath(
                path = shirtPath,
                color = Color.Black.copy(alpha = 0.25f),
                style = Stroke(width = 2f, join = StrokeJoin.Round)
            )
        }

        // Overlay Chest/Back Graphic with accurate perspective distortion
        val radY = (rotationY * PI / 180.0).toFloat()
        val cosRot = cos(radY)
        val isFacingFront = cosRot >= 0f
        val currentActiveView = if (isFacingFront) "front" else "back"

        // The design is shown on the front side when facing front, or back side when facing back
        val shouldShowDesign = (currentActiveView == "front" && isFacingFront) || (currentActiveView == "back" && !isFacingFront)

        if (shouldShowDesign && designId != "none" && abs(cosRot) > 0.12f) {
            val scaleX = abs(cosRot) * 0.88f
            val shiftX = sin(radY) * 35f

            Box(
                modifier = Modifier
                    .size(190.dp)
                    .graphicsLayer {
                        this.scaleX = scaleX
                        this.translationX = shiftX
                        this.translationY = -25f + tiltX * 1.5f
                        this.alpha = (abs(cosRot) * 1.4f).coerceIn(0f, 1f)
                    },
                contentAlignment = Alignment.Center
            ) {
                DesignRenderer(
                    designId = designId,
                    customBitmap = customBitmap,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
