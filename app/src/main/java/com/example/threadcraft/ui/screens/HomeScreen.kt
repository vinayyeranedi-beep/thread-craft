package com.example.threadcraft.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.threadcraft.R
import com.example.threadcraft.data.BusinessConfig
import com.example.threadcraft.data.howItWorksSteps
import com.example.threadcraft.data.shirtColors
import com.example.threadcraft.data.whyChooseUsStats
import com.example.threadcraft.ui.components.TshirtCanvas
import com.example.threadcraft.ui.theme.Ink
import com.example.threadcraft.ui.theme.InkCard
import com.example.threadcraft.ui.theme.InkLine
import com.example.threadcraft.ui.theme.InkSoft
import com.example.threadcraft.ui.theme.Paper
import com.example.threadcraft.ui.theme.PaperDim
import com.example.threadcraft.ui.theme.PaperMuted
import com.example.threadcraft.ui.theme.Signal
import com.example.threadcraft.ui.theme.SignalGlow
import com.example.threadcraft.ui.theme.Thread

@Composable
fun HomeScreen(
    onNavigateToCustomizer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var previewColorId by remember { mutableStateOf("black") }
    var previewView by remember { mutableStateOf("front") }
    var isPhotoDemo by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Ink)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Tagline Pill
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(InkSoft)
                .border(1.dp, InkLine, CircleShape)
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Signal)
                )
                Text(
                    text = "CUSTOM T-SHIRT PRINTING",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    letterSpacing = 1.4.sp,
                    color = Thread
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Giant Display Headline: "WEAR YOUR \n IDEAS."
        Column {
            Text(
                text = "WEAR YOUR",
                fontSize = 44.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-1).sp,
                color = Paper,
                lineHeight = 44.sp
            )
            Text(
                text = "IDEAS.",
                fontSize = 44.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-1).sp,
                color = Signal,
                lineHeight = 44.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Subhead description
        Text(
            text = BusinessConfig.SUBHEAD,
            fontSize = 15.sp,
            color = PaperDim,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Hero CTA Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = onNavigateToCustomizer,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Signal,
                    contentColor = Paper
                ),
                modifier = Modifier
                    .weight(1.2f)
                    .height(48.dp)
                    .shadow(10.dp, CircleShape, spotColor = SignalGlow)
                    .testTag("hero_customize_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Customize T-Shirt",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            OutlinedButton(
                onClick = { BusinessConfig.openWhatsApp(context, "Hi! I'd like a custom print quote.") },
                shape = CircleShape,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Paper
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(listOf(InkLine, PaperDim.copy(alpha = 0.4f)))
                ),
                modifier = Modifier
                    .weight(0.9f)
                    .height(48.dp)
                    .testTag("hero_quote_btn")
            ) {
                Text(
                    text = "Get a Quote",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 3 Key Stats
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, InkLine, RoundedCornerShape(16.dp))
                .background(InkSoft)
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("6", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Paper)
                Text("print methods", fontSize = 11.sp, color = PaperMuted)
            }
            Box(modifier = Modifier.size(1.dp, 28.dp).background(InkLine))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("72h", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Thread)
                Text("turnaround", fontSize = 11.sp, color = PaperMuted)
            }
            Box(modifier = Modifier.size(1.dp, 28.dp).background(InkLine))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("1–5000", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Paper)
                Text("order size", fontSize = 11.sp, color = PaperMuted)
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Interactive T-Shirt Preview Card with Demo Showing
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Mode Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Thread,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "DEMO SHOWING",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = Thread
                    )
                }

                // Switcher pill
                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(InkSoft)
                        .border(1.dp, InkLine, CircleShape)
                        .padding(3.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(if (isPhotoDemo) Signal else Color.Transparent)
                            .clickable { isPhotoDemo = true }
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Photo Demo",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isPhotoDemo) Paper else PaperDim
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(if (!isPhotoDemo) Signal else Color.Transparent)
                            .clickable { isPhotoDemo = false }
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "3D Studio",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (!isPhotoDemo) Paper else PaperDim
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.dp, InkLine, RoundedCornerShape(24.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(InkCard, InkSoft)
                        )
                    )
            ) {
                if (isPhotoDemo) {
                    Image(
                        painter = painterResource(id = R.drawable.img_ganesha_demo),
                        contentDescription = "T-shirt demo showing Lord Ganesha print",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Gradient scrim
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Black.copy(alpha = 0.4f),
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.85f)
                                    )
                                )
                            )
                    )

                    // Top badge
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(14.dp)
                            .clip(CircleShape)
                            .background(Ink.copy(alpha = 0.85f))
                            .border(1.dp, Thread.copy(alpha = 0.4f), CircleShape)
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(Thread)
                        )
                        Text(
                            text = "VINAYAKA CHAVITHI EDITION",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Thread
                        )
                    }

                    // Bottom info bar & Action
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(14.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Ink.copy(alpha = 0.9f))
                            .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(16.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Lord Ganesha Festive T-Shirt",
                                    color = Paper,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "240 GSM Premium Bio-Washed Cotton",
                                    color = PaperDim,
                                    fontSize = 10.sp
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Signal)
                                    .clickable(onClick = onNavigateToCustomizer)
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "Customize",
                                        color = Paper,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = null,
                                        tint = Paper,
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                            }
                        }
                    }
                } else {
                    TshirtCanvas(
                        shirtColor = Color(0xFF111114),
                        view = previewView,
                        designId = "vinayaka-chavithi",
                        autoRotate = true,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Overlay prompt
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 14.dp)
                            .clip(CircleShape)
                            .background(Ink.copy(alpha = 0.85f))
                            .border(1.dp, Color.White.copy(alpha = 0.15f), CircleShape)
                            .clickable(onClick = onNavigateToCustomizer)
                            .padding(horizontal = 14.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.TouchApp,
                            contentDescription = null,
                            tint = Thread,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Tap to open in 3D Studio",
                            color = Paper,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        // "WHY CHOOSE US" Section
        Text(
            text = "WHY CHOOSE US",
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.5.sp,
            color = Thread
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Built for real wear",
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            color = Paper
        )
        Spacer(modifier = Modifier.height(14.dp))

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            whyChooseUsStats.forEach { stat ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, InkLine, RoundedCornerShape(16.dp))
                        .background(InkSoft)
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(InkCard),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${stat.value}${stat.suffix}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = Thread
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stat.label,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Paper
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = stat.description,
                            fontSize = 12.sp,
                            color = PaperDim
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        // "HOW IT WORKS - THE PROCESS" Section
        Text(
            text = "THE PROCESS",
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.5.sp,
            color = Thread
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Four steps from idea to tee",
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            color = Paper
        )
        Spacer(modifier = Modifier.height(14.dp))

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            howItWorksSteps.forEach { step ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, InkLine, RoundedCornerShape(16.dp))
                        .background(InkSoft)
                        .padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = step.number,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Signal
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = step.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Paper
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = step.description,
                            fontSize = 13.sp,
                            color = PaperDim,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        // Large CTA Card matching web
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, InkLine, RoundedCornerShape(24.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF1E1012), InkSoft)
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "READY TO WEAR YOUR IDEA?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = Paper
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Start with a single tee or order 5,000 for your next big event. Let's make it real.",
                    fontSize = 13.sp,
                    color = PaperDim,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onNavigateToCustomizer,
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Signal),
                        modifier = Modifier.weight(1f).height(46.dp)
                    ) {
                        Text("Start Customizing", fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = { BusinessConfig.openWhatsApp(context, "Hi! I want to discuss a bulk custom print order.") },
                        shape = CircleShape,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Paper),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = Brush.horizontalGradient(listOf(InkLine, PaperDim.copy(alpha = 0.5f)))
                        ),
                        modifier = Modifier.weight(1f).height(46.dp)
                    ) {
                        Text("Talk on WhatsApp", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}
