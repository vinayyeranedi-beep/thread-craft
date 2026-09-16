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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SportsHandball
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.threadcraft.data.servicesList
import com.example.threadcraft.ui.theme.Ink
import com.example.threadcraft.ui.theme.InkCard
import com.example.threadcraft.ui.theme.InkLine
import com.example.threadcraft.ui.theme.InkSoft
import com.example.threadcraft.ui.theme.Paper
import com.example.threadcraft.ui.theme.PaperDim
import com.example.threadcraft.ui.theme.PaperMuted
import com.example.threadcraft.ui.theme.Signal
import com.example.threadcraft.ui.theme.Thread

private fun getServiceIcon(id: String): ImageVector {
    return when (id) {
        "custom" -> Icons.Default.DesignServices
        "college" -> Icons.Default.School
        "corporate" -> Icons.Default.Work
        "sports" -> Icons.Default.SportsHandball
        "bulk" -> Icons.Default.Inventory
        "design" -> Icons.Default.Group
        else -> Icons.Default.Check
    }
}

@Composable
fun ServicesScreen(
    onSelectServiceForCustomize: (String) -> Unit,
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
        // Tagline Pill
        Text(
            text = "SERVICES",
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.6.sp,
            color = Thread
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "What we print",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = Paper
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Six ways to put your idea on fabric. From single one-offs to thousands for your company or college.",
            fontSize = 14.sp,
            color = PaperDim,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            servicesList.forEach { service ->
                val icon = getServiceIcon(service.id)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .border(1.dp, InkLine, RoundedCornerShape(20.dp))
                        .background(InkSoft)
                        .padding(18.dp)
                        .testTag("service_card_${service.id}")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(InkCard),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = service.title,
                                tint = Thread,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Text(
                            text = service.tag,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PaperMuted
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = service.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Paper
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = service.description,
                        fontSize = 13.sp,
                        color = PaperDim,
                        lineHeight = 19.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { onSelectServiceForCustomize(service.id) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = InkCard,
                                contentColor = Paper
                            ),
                            modifier = Modifier.weight(1f).height(40.dp)
                        ) {
                            Text("Customize", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                val message = "Hi! I'm interested in ${service.title} printing for an upcoming order."
                                BusinessConfig.openWhatsApp(context, message)
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Signal,
                                contentColor = Paper
                            ),
                            modifier = Modifier.weight(1f).height(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Enquire", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}
