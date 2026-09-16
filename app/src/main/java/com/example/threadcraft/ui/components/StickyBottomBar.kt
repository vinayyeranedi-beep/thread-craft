package com.example.threadcraft.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.threadcraft.ui.theme.Ink
import com.example.threadcraft.ui.theme.InkLine
import com.example.threadcraft.ui.theme.Paper
import com.example.threadcraft.ui.theme.Signal
import com.example.threadcraft.ui.theme.SignalGlow

@Composable
fun StickyBottomBar(
    onCustomizeClick: () -> Unit,
    onWhatsAppClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Ink.copy(alpha = 0.95f))
            .border(width = 1.dp, color = InkLine)
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .navigationBarsPadding()
            .testTag("sticky_bottom_bar")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Customize button
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
                    .clip(CircleShape)
                    .background(Paper)
                    .clickable(onClick = onCustomizeClick)
                    .testTag("sticky_customize_btn"),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Ink,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Customize",
                        color = Ink,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // WhatsApp Quote button
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
                    .clip(CircleShape)
                    .background(Signal)
                    .shadow(8.dp, CircleShape, spotColor = SignalGlow)
                    .clickable(onClick = onWhatsAppClick)
                    .testTag("sticky_whatsapp_btn"),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = null,
                        tint = Paper,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "WhatsApp",
                        color = Paper,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
