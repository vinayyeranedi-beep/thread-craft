package com.example.threadcraft.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.RotateRight
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.threadcraft.R
import com.example.threadcraft.data.BusinessConfig
import com.example.threadcraft.data.PricingConfig
import com.example.threadcraft.data.QuoteRepository
import com.example.threadcraft.data.SavedQuote
import com.example.threadcraft.data.demoDesigns
import com.example.threadcraft.data.shirtColors
import com.example.threadcraft.ui.components.ColorSwatches
import com.example.threadcraft.ui.components.DesignSelector
import com.example.threadcraft.ui.components.OrderBar
import com.example.threadcraft.ui.components.TshirtCanvas
import com.example.threadcraft.ui.components.ViewSwitch
import com.example.threadcraft.ui.theme.Ink
import com.example.threadcraft.ui.theme.InkCard
import com.example.threadcraft.ui.theme.InkLine
import com.example.threadcraft.ui.theme.InkSoft
import com.example.threadcraft.ui.theme.Paper
import com.example.threadcraft.ui.theme.PaperDim
import com.example.threadcraft.ui.theme.PaperMuted
import com.example.threadcraft.ui.theme.Signal
import com.example.threadcraft.ui.theme.Thread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@Composable
fun CustomizerScreen(
    quoteRepository: QuoteRepository,
    initialColorId: String = "black",
    initialDesignId: String = "vinayaka-chavithi",
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    var colorId by remember { mutableStateOf(initialColorId) }
    var view by remember { mutableStateOf("front") }
    var designId by remember { mutableStateOf(initialDesignId) }
    var uploadedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var uploadedName by remember { mutableStateOf<String?>(null) }
    var size by remember { mutableStateOf("M") }
    var qty by remember { mutableIntStateOf(1) }
    var autoRotate by remember { mutableStateOf(false) }
    var isQuoteSaved by remember { mutableStateOf(false) }
    var isPhotoDemoMode by remember { mutableStateOf(false) }

    val currentColor = shirtColors.firstOrNull { it.id == colorId } ?: shirtColors.first()
    val colorLabel = currentColor.label
    val designLabel = uploadedName ?: (demoDesigns.firstOrNull { it.id == designId }?.label ?: "No design")

    // Image Picker Launcher for custom artwork upload
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            scope.launch {
                try {
                    val bitmap = withContext(Dispatchers.IO) {
                        context.contentResolver.openInputStream(uri)?.use { stream ->
                            BitmapFactory.decodeStream(stream)
                        }
                    }
                    if (bitmap != null) {
                        uploadedBitmap = bitmap
                        uploadedName = "custom_print.png"
                        designId = "custom-upload"
                        isQuoteSaved = false
                        Toast.makeText(context, "Design loaded onto garment!", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Failed to load image: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(InkSoft)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Section Header
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "INTERACTIVE CUSTOMIZER",
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.6.sp,
                color = Thread
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MAKE IT YOURS",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = Paper
                )
                // Mode Toggle: 3D Interactive vs Realistic Demo Photo
                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Ink)
                        .border(1.dp, InkLine, CircleShape)
                        .padding(3.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(if (!isPhotoDemoMode) Signal else Color.Transparent)
                            .clickable { isPhotoDemoMode = false }
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "3D Studio",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (!isPhotoDemoMode) Paper else PaperDim
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(if (isPhotoDemoMode) Signal else Color.Transparent)
                            .clickable { isPhotoDemoMode = true }
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = null,
                                tint = if (isPhotoDemoMode) Paper else Thread,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "Demo Photo",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isPhotoDemoMode) Paper else PaperDim
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Customizer Stage Box (3D or Realistic Photo Demo)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)
                .clip(RoundedCornerShape(28.dp))
                .border(1.dp, InkLine, RoundedCornerShape(28.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Ink, InkSoft)
                    )
                )
                .testTag("customizer_stage")
        ) {
            if (isPhotoDemoMode) {
                // Realistic Garment Showcase with the Lord Ganesha print on black t-shirt
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.img_ganesha_demo),
                        contentDescription = "Demo showing on t-shirt: Vinayaka Chavithi edition",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Ambient gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.5f),
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.85f)
                                    )
                                )
                            )
                    )

                    // Top-Left Badge
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(14.dp)
                            .clip(CircleShape)
                            .background(Ink.copy(alpha = 0.85f))
                            .border(1.dp, Thread.copy(alpha = 0.5f), CircleShape)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = Thread,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "DEMO SHOWING • VINAYAKA CHAVITHI",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Thread
                        )
                    }

                    // Top-Right Quick Toggle to 3D
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(14.dp)
                            .clip(CircleShape)
                            .background(Ink.copy(alpha = 0.85f))
                            .border(1.dp, Color.White.copy(alpha = 0.15f), CircleShape)
                            .clickable { isPhotoDemoMode = false }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ViewInAr,
                                contentDescription = null,
                                tint = Paper,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "Open 3D",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Paper
                            )
                        }
                    }

                    // Bottom info banner
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(14.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(Ink.copy(alpha = 0.9f))
                            .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(18.dp))
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Lord Ganesha Festive Print",
                                    color = Paper,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Screen printed on 240 GSM Bio-Washed Combed Cotton",
                                    color = PaperDim,
                                    fontSize = 11.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Signal)
                                    .clickable { isPhotoDemoMode = false }
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = "Customize 3D",
                                    color = Paper,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            } else {
                // T-shirt Canvas with real-time vector / bitmap drawing and 3D angle support
                TshirtCanvas(
                    shirtColor = currentColor.color,
                    view = view,
                    designId = designId,
                    customBitmap = uploadedBitmap,
                    autoRotate = autoRotate,
                    onViewChanged = { newView -> view = newView },
                    modifier = Modifier.fillMaxSize()
                )

                // Top Floating Controls: Color swatches (Center) & View switch (Right)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Color swatches pill
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Ink.copy(alpha = 0.8f))
                            .border(1.dp, Color.White.copy(alpha = 0.12f), CircleShape)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        ColorSwatches(
                            selectedColorId = colorId,
                            onColorSelected = {
                                colorId = it
                                isQuoteSaved = false
                            },
                            size = 24.dp
                        )
                    }

                    // Front / Back toggle switch
                    ViewSwitch(
                        currentView = view,
                        onViewChanged = { view = it }
                    )
                }

                // Top-right Auto-Rotate button & Demo Photo button
                Column(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 56.dp, end = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(
                        onClick = { autoRotate = !autoRotate },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(if (autoRotate) Thread.copy(alpha = 0.3f) else Ink.copy(alpha = 0.8f))
                            .border(1.dp, if (autoRotate) Thread else Color.White.copy(alpha = 0.15f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.RotateRight,
                            contentDescription = "Auto Rotate",
                            tint = if (autoRotate) Thread else PaperDim,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Quick photo demo viewer button
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Ink.copy(alpha = 0.85f))
                            .border(1.dp, Thread.copy(alpha = 0.4f), CircleShape)
                            .clickable { isPhotoDemoMode = true }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = null,
                                tint = Thread,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "Photo Demo",
                                color = Paper,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Bottom Floating Controls: Design picker bar
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(12.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Ink.copy(alpha = 0.85f))
                        .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(18.dp))
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    DesignSelector(
                        selectedDesignId = designId,
                        uploadedName = uploadedName,
                        onSelectDesign = {
                            designId = it
                            uploadedBitmap = null
                            uploadedName = null
                            isQuoteSaved = false
                        },
                        onUploadClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        onClearUpload = {
                            uploadedBitmap = null
                            uploadedName = null
                            designId = "none"
                            isQuoteSaved = false
                        },
                        itemSize = 38.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Demo disclaimer
        Text(
            text = "Preview and pricing shown here are for demo purposes — final quote confirmed via WhatsApp.",
            color = PaperMuted,
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Comprehensive Order & Pricing Bar
        OrderBar(
            size = size,
            onSizeChanged = {
                size = it
                isQuoteSaved = false
            },
            qty = qty,
            onQtyChanged = {
                qty = it
                isQuoteSaved = false
            },
            colorLabel = colorLabel,
            designLabel = designLabel,
            isSaved = isQuoteSaved,
            onSendQuoteWhatsApp = {
                val estimate = PricingConfig.estimate(qty)
                val msg = PricingConfig.buildQuoteMessage(qty, size, colorLabel, designLabel, estimate)
                BusinessConfig.openWhatsApp(context, msg)
            },
            onSaveQuote = {
                val estimate = PricingConfig.estimate(qty)
                val newQuote = SavedQuote(
                    id = "quote-${UUID.randomUUID().toString().take(8)}",
                    date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date()),
                    colorId = colorId,
                    colorLabel = colorLabel,
                    colorHex = currentColor.hex,
                    designId = designId,
                    designLabel = designLabel,
                    size = size,
                    quantity = qty,
                    unitPrice = estimate.unitPrice,
                    totalPrice = estimate.totalPrice,
                    discountPct = estimate.discountPct,
                    view = view
                )
                quoteRepository.saveQuote(newQuote)
                isQuoteSaved = true
                Toast.makeText(context, "Quote saved to your Orders tab!", Toast.LENGTH_SHORT).show()
            }
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}
