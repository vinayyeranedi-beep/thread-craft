package com.example.threadcraft.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.threadcraft.data.BusinessConfig
import com.example.threadcraft.data.faqs
import com.example.threadcraft.data.testimonials
import com.example.threadcraft.ui.theme.Ink
import com.example.threadcraft.ui.theme.InkCard
import com.example.threadcraft.ui.theme.InkLine
import com.example.threadcraft.ui.theme.InkSoft
import com.example.threadcraft.ui.theme.Paper
import com.example.threadcraft.ui.theme.PaperDim
import com.example.threadcraft.ui.theme.PaperMuted
import com.example.threadcraft.ui.theme.Signal
import com.example.threadcraft.ui.theme.Thread

@Composable
fun AboutContactScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Ink)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Section: Word of Mouth (Testimonials)
        Text(
            text = "WORD OF MOUTH",
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.6.sp,
            color = Thread
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "What customers say",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = Paper
        )
        Spacer(modifier = Modifier.height(14.dp))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            testimonials.forEach { t ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .border(1.dp, InkLine, RoundedCornerShape(18.dp))
                        .background(InkSoft)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FormatQuote,
                        contentDescription = null,
                        tint = Thread,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "\"${t.quote}\"",
                        fontSize = 14.sp,
                        color = Paper,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = t.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Paper
                    )
                    Text(
                        text = t.role,
                        fontSize = 11.sp,
                        color = PaperMuted
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        // Section: FAQ
        Text(
            text = "QUESTIONS",
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.6.sp,
            color = Thread
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Before you order",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = Paper
        )
        Spacer(modifier = Modifier.height(14.dp))

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            faqs.forEachIndexed { index, faq ->
                var expanded by remember { mutableStateOf(index == 0) }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, InkLine, RoundedCornerShape(16.dp))
                        .background(InkSoft)
                        .clickable { expanded = !expanded }
                        .padding(16.dp)
                        .testTag("faq_item_$index")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = faq.question,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Paper,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = PaperDim,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    AnimatedVisibility(
                        visible = expanded,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = faq.answer,
                                fontSize = 13.sp,
                                color = PaperDim,
                                lineHeight = 19.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        // Section: Get in Touch & Direct Contact
        Text(
            text = "CONTACT",
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.6.sp,
            color = Thread
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Get in touch",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = Paper
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Have a question or ready to discuss an order? We're a message away.",
            fontSize = 14.sp,
            color = PaperDim
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            ContactActionRow(
                icon = Icons.Default.Chat,
                label = "WhatsApp",
                value = BusinessConfig.PHONE,
                onClick = { BusinessConfig.openWhatsApp(context, "Hi! I have a question about custom t-shirt printing.") }
            )
            ContactActionRow(
                icon = Icons.Default.Call,
                label = "Phone",
                value = BusinessConfig.PHONE,
                onClick = { BusinessConfig.callPhone(context) }
            )
            ContactActionRow(
                icon = Icons.Default.PhotoCamera,
                label = "Instagram",
                value = BusinessConfig.INSTAGRAM,
                onClick = { BusinessConfig.openInstagram(context) }
            )
            ContactActionRow(
                icon = Icons.Default.LocationOn,
                label = "Location",
                value = BusinessConfig.LOCATION,
                onClick = { BusinessConfig.openMap(context) }
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        // Footer Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .border(1.dp, InkLine, RoundedCornerShape(18.dp))
                .background(InkCard)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "THREADCRAFT",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp,
                color = Paper
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Custom T-shirt printing in Vizianagaram, AP",
                fontSize = 12.sp,
                color = PaperMuted
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Thread.copy(alpha = 0.2f))
                    .border(1.dp, Thread, CircleShape)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "PORTFOLIO DEMO",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Thread
                )
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
private fun ContactActionRow(
    icon: ImageVector,
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, InkLine, RoundedCornerShape(14.dp))
            .background(InkSoft)
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(InkCard),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = Signal,
                    modifier = Modifier.size(18.dp)
                )
            }
            Column {
                Text(
                    text = label,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = PaperMuted
                )
                Text(
                    text = value,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Paper
                )
            }
        }

        Text(
            text = "Connect",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Thread
        )
    }
}
