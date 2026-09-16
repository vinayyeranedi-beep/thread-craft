package com.example.threadcraft.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color as AndroidColor
import android.graphics.LinearGradient
import android.graphics.Paint as AndroidPaint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.example.threadcraft.R
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun DesignRenderer(
    designId: String,
    customBitmap: Bitmap? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val ganeshaBitmap = remember(context) {
        try {
            BitmapFactory.decodeResource(context.resources, R.drawable.img_ganesha_print)
        } catch (e: Exception) {
            null
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            if (canvasWidth <= 0f || canvasHeight <= 0f) return@Canvas

            when (designId) {
                "custom-upload" -> {
                    if (customBitmap != null && !customBitmap.isRecycled) {
                        val imageBitmap = customBitmap.asImageBitmap()
                        val srcW = imageBitmap.width
                        val srcH = imageBitmap.height
                        val scale = minOf(canvasWidth / srcW, canvasHeight / srcH)
                        val dstW = (srcW * scale).toInt()
                        val dstH = (srcH * scale).toInt()
                        val dstX = ((canvasWidth - dstW) / 2f).toInt()
                        val dstY = ((canvasHeight - dstH) / 2f).toInt()

                        drawImage(
                            image = imageBitmap,
                            dstOffset = IntOffset(dstX, dstY),
                            dstSize = IntSize(dstW, dstH)
                        )
                    }
                }
                "vinayaka-chavithi" -> {
                    if (ganeshaBitmap != null && !ganeshaBitmap.isRecycled) {
                        val imageBitmap = ganeshaBitmap.asImageBitmap()
                        val srcW = imageBitmap.width
                        val srcH = imageBitmap.height
                        val scale = minOf(canvasWidth / srcW, canvasHeight / srcH)
                        val dstW = (srcW * scale).toInt()
                        val dstH = (srcH * scale).toInt()
                        val dstX = ((canvasWidth - dstW) / 2f).toInt()
                        val dstY = ((canvasHeight - dstH) / 2f).toInt()

                        drawImage(
                            image = imageBitmap,
                            dstOffset = IntOffset(dstX, dstY),
                            dstSize = IntSize(dstW, dstH)
                        )
                    } else {
                        drawIntoCanvas { canvas ->
                            val native = canvas.nativeCanvas
                            val cx = canvasWidth / 2f
                            val cy = canvasHeight / 2f

                            // Background card gradient
                            val bgPaint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
                                shader = LinearGradient(
                                    0f, 0f, canvasWidth, canvasHeight,
                                    intArrayOf(
                                        AndroidColor.parseColor("#1C1B1C"),
                                        AndroidColor.parseColor("#2D1808"),
                                        AndroidColor.parseColor("#F39A1D")
                                    ),
                                    floatArrayOf(0f, 0.45f, 1f),
                                    Shader.TileMode.CLAMP
                                )
                            }
                            val r = canvasWidth * 0.48f
                            val rect = RectF(cx - r, cy - r, cx + r, cy + r)
                            native.drawRoundRect(rect, 24f, 24f, bgPaint)

                            // Golden halo aura
                            val haloPaint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
                                color = AndroidColor.parseColor("#40FFC162")
                                style = AndroidPaint.Style.FILL
                            }
                            val auraPath = android.graphics.Path().apply {
                                moveTo(cx, cy - r * 0.75f)
                                lineTo(cx + r * 0.75f, cy)
                                lineTo(cx, cy + r * 0.75f)
                                lineTo(cx - r * 0.75f, cy)
                                close()
                            }
                            native.drawPath(auraPath, haloPaint)

                            // Ganesha stylized head & crown
                            val goldFill = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
                                color = AndroidColor.parseColor("#F4B14A")
                                style = AndroidPaint.Style.FILL
                            }
                            val goldStroke = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
                                color = AndroidColor.parseColor("#F7D38D")
                                style = AndroidPaint.Style.STROKE
                                strokeWidth = 4f
                            }

                            // Face base
                            native.drawOval(
                                RectF(cx - r * 0.45f, cy - r * 0.35f, cx + r * 0.45f, cy + r * 0.25f),
                                goldFill
                            )
                            native.drawOval(
                                RectF(cx - r * 0.45f, cy - r * 0.35f, cx + r * 0.45f, cy + r * 0.25f),
                                goldStroke
                            )

                            // Crown
                            val crownPath = android.graphics.Path().apply {
                                moveTo(cx - r * 0.2f, cy - r * 0.25f)
                                lineTo(cx - r * 0.12f, cy - r * 0.65f)
                                lineTo(cx, cy - r * 0.8f)
                                lineTo(cx + r * 0.12f, cy - r * 0.65f)
                                lineTo(cx + r * 0.2f, cy - r * 0.25f)
                                close()
                            }
                            val crownPaint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
                                color = AndroidColor.parseColor("#D88D25")
                                style = AndroidPaint.Style.FILL
                            }
                            native.drawPath(crownPath, crownPaint)

                            // Ears
                            val leftEar = RectF(cx - r * 0.65f, cy - r * 0.25f, cx - r * 0.3f, cy + r * 0.15f)
                            val rightEar = RectF(cx + r * 0.3f, cy - r * 0.25f, cx + r * 0.65f, cy + r * 0.15f)
                            native.drawOval(leftEar, goldFill)
                            native.drawOval(rightEar, goldFill)

                            // Trunk
                            val trunkPath = android.graphics.Path().apply {
                                moveTo(cx - r * 0.12f, cy)
                                quadTo(cx - r * 0.05f, cy + r * 0.45f, cx + r * 0.22f, cy + r * 0.42f)
                                quadTo(cx + r * 0.28f, cy + r * 0.32f, cx + r * 0.18f, cy + r * 0.28f)
                                quadTo(cx + r * 0.05f, cy + r * 0.32f, cx + r * 0.02f, cy)
                                close()
                            }
                            val trunkPaint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
                                color = AndroidColor.parseColor("#D8891B")
                                style = AndroidPaint.Style.FILL
                            }
                            native.drawPath(trunkPath, trunkPaint)

                            // Tilak / sacred dot
                            val tilakPaint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
                                color = AndroidColor.parseColor("#D6301F")
                                style = AndroidPaint.Style.FILL
                            }
                            native.drawCircle(cx, cy - r * 0.15f, r * 0.05f, tilakPaint)

                            // Script text
                            val textPaint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
                                color = AndroidColor.parseColor("#F8E7B2")
                                textSize = r * 0.22f
                                typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                                textAlign = AndroidPaint.Align.CENTER
                            }
                            native.drawText("Vinayaka", cx, cy + r * 0.62f, textPaint)
                            textPaint.typeface = Typeface.create(Typeface.SERIF, Typeface.ITALIC)
                            textPaint.textSize = r * 0.20f
                            native.drawText("Chavithi", cx, cy + r * 0.82f, textPaint)
                        }
                    }
                }
                "minimal-logo" -> {
                    drawIntoCanvas { canvas ->
                        val native = canvas.nativeCanvas
                        val cx = canvasWidth / 2f
                        val cy = canvasHeight / 2f
                        val radius = minOf(canvasWidth, canvasHeight) * 0.38f

                        val strokePaint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
                            color = AndroidColor.parseColor("#EDE8DD")
                            style = AndroidPaint.Style.STROKE
                            strokeWidth = 6f
                        }
                        native.drawCircle(cx, cy, radius, strokePaint)

                        val textPaint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
                            color = AndroidColor.parseColor("#EDE8DD")
                            textSize = radius * 0.72f
                            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                            textAlign = AndroidPaint.Align.CENTER
                        }
                        val fontMetrics = textPaint.fontMetrics
                        val textY = cy - (fontMetrics.ascent + fontMetrics.descent) / 2f
                        native.drawText("TC", cx, textY, textPaint)
                    }
                }
                "street-style" -> {
                    drawIntoCanvas { canvas ->
                        val native = canvas.nativeCanvas
                        val cx = canvasWidth / 2f
                        val cy = canvasHeight / 2f

                        native.save()
                        native.translate(cx, cy)
                        native.rotate(-4f)

                        val textPaint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
                            color = AndroidColor.parseColor("#EDE8DD")
                            textSize = minOf(canvasWidth, canvasHeight) * 0.32f
                            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                            textAlign = AndroidPaint.Align.CENTER
                        }
                        val fm = textPaint.fontMetrics
                        native.drawText("STREET", 0f, -(fm.ascent + fm.descent) / 2f - 10f, textPaint)

                        val linePaint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
                            color = AndroidColor.parseColor("#D6301F")
                            style = AndroidPaint.Style.STROKE
                            strokeWidth = 8f
                            strokeCap = AndroidPaint.Cap.ROUND
                        }
                        val halfW = canvasWidth * 0.42f
                        native.drawLine(-halfW, 20f, halfW, 0f, linePaint)

                        native.restore()
                    }
                }
                "college" -> {
                    drawIntoCanvas { canvas ->
                        val native = canvas.nativeCanvas
                        val cx = canvasWidth / 2f
                        val cy = canvasHeight / 2f
                        val sizeRef = minOf(canvasWidth, canvasHeight)

                        // Outer gold curved arc
                        val arcPaint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
                            color = AndroidColor.parseColor("#C6A24D")
                            style = AndroidPaint.Style.STROKE
                            strokeWidth = 6f
                        }
                        val r = sizeRef * 0.36f
                        native.drawArc(RectF(cx - r, cy - r, cx + r, cy + r), 200f, 140f, false, arcPaint)

                        val headerPaint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
                            color = AndroidColor.parseColor("#C6A24D")
                            textSize = sizeRef * 0.09f
                            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                            textAlign = AndroidPaint.Align.CENTER
                        }
                        native.drawText("CLASS OF", cx, cy - r + sizeRef * 0.05f, headerPaint)

                        val numberPaint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
                            color = AndroidColor.parseColor("#EDE8DD")
                            textSize = sizeRef * 0.42f
                            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                            textAlign = AndroidPaint.Align.CENTER
                        }
                        val fm = numberPaint.fontMetrics
                        native.drawText("26", cx, cy + sizeRef * 0.14f - (fm.ascent + fm.descent) / 2f, numberPaint)
                    }
                }
                "sports" -> {
                    drawIntoCanvas { canvas ->
                        val native = canvas.nativeCanvas
                        val cx = canvasWidth / 2f
                        val cy = canvasHeight / 2f
                        val boxSize = minOf(canvasWidth, canvasHeight) * 0.72f

                        // Outer box
                        val boxPaint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
                            color = AndroidColor.parseColor("#EDE8DD")
                            style = AndroidPaint.Style.STROKE
                            strokeWidth = 4f
                        }
                        native.drawRect(
                            RectF(cx - boxSize / 2f, cy - boxSize / 2f, cx + boxSize / 2f, cy + boxSize / 2f),
                            boxPaint
                        )

                        // Athletic Number 7
                        val numPaint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG).apply {
                            color = AndroidColor.parseColor("#EDE8DD")
                            textSize = boxSize * 0.78f
                            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                            textAlign = AndroidPaint.Align.CENTER
                        }
                        val fm = numPaint.fontMetrics
                        native.drawText("7", cx, cy - (fm.ascent + fm.descent) / 2f, numPaint)
                    }
                }
                "custom-print" -> {
                    // Modern triad overlapping geometric stars
                    val cx = canvasWidth / 2f
                    val cy = canvasHeight / 2f
                    val colors = listOf(Color(0xFFD6301F), Color(0xFFC6A24D), Color(0xFFEDE8DD))

                    for (i in 0 until 5) {
                        val c = colors[i % colors.size]
                        val r = (canvasWidth * 0.4f) - (i * canvasWidth * 0.06f)
                        val rot = (i * Math.PI / 6.0).toFloat()

                        val path = androidx.compose.ui.graphics.Path().apply {
                            val p1x = cx + r * cos(rot)
                            val p1y = cy + r * sin(rot)
                            val p2x = cx + r * cos(rot + 2.1f)
                            val p2y = cy + r * sin(rot + 2.1f)
                            val p3x = cx + r * cos(rot + 4.2f)
                            val p3y = cy + r * sin(rot + 4.2f)
                            moveTo(p1x, p1y)
                            lineTo(p2x, p2y)
                            lineTo(p3x, p3y)
                            close()
                        }
                        drawPath(path, c.copy(alpha = 0.85f))
                    }
                }
                else -> {
                    // "none" -> clean blank canvas
                }
            }
        }
    }
}
