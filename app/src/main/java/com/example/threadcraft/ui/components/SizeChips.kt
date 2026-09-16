package com.example.threadcraft.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.threadcraft.data.shirtSizes
import com.example.threadcraft.ui.theme.Ink
import com.example.threadcraft.ui.theme.InkLine
import com.example.threadcraft.ui.theme.Paper
import com.example.threadcraft.ui.theme.PaperDim
import com.example.threadcraft.ui.theme.Signal

@Composable
fun SizeChips(
    selectedSize: String,
    onSizeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        shirtSizes.forEach { s ->
            val isActive = selectedSize == s
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .then(
                        if (isActive) {
                            Modifier.background(Signal)
                        } else {
                            Modifier
                                .border(1.dp, InkLine, RoundedCornerShape(8.dp))
                                .background(Ink)
                        }
                    )
                    .clickable { onSizeSelected(s) }
                    .testTag("size_chip_$s"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = s,
                    color = if (isActive) Paper else PaperDim,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
