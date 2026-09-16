package com.example.threadcraft.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.threadcraft.data.shirtColors
import com.example.threadcraft.ui.theme.Ink
import com.example.threadcraft.ui.theme.Thread

@Composable
fun ColorSwatches(
    selectedColorId: String,
    onColorSelected: (String) -> Unit,
    size: Dp = 34.dp,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        shirtColors.forEach { c ->
            val isActive = selectedColorId == c.id
            Box(
                modifier = Modifier
                    .size(if (isActive) size + 4.dp else size)
                    .then(
                        if (isActive) {
                            Modifier
                                .shadow(8.dp, CircleShape, spotColor = Thread)
                                .border(2.dp, Thread, CircleShape)
                        } else {
                            Modifier.border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
                        }
                    )
                    .clip(CircleShape)
                    .background(c.color)
                    .clickable { onColorSelected(c.id) }
                    .testTag("color_swatch_${c.id}"),
                contentAlignment = Alignment.Center
            ) {
                if (isActive) {
                    Box(
                        modifier = Modifier
                            .size(size * 0.35f)
                            .clip(CircleShape)
                            .background(if (c.id == "white" || c.id == "yellow") Ink else Color.White)
                    )
                }
            }
        }
    }
}
