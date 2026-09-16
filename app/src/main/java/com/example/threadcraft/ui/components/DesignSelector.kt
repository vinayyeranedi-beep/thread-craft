package com.example.threadcraft.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.threadcraft.data.demoDesigns
import com.example.threadcraft.ui.theme.Ink
import com.example.threadcraft.ui.theme.Paper
import com.example.threadcraft.ui.theme.PaperDim
import com.example.threadcraft.ui.theme.Signal
import com.example.threadcraft.ui.theme.Thread

private fun getDesignIcon(id: String): ImageVector {
    return when (id) {
        "none" -> Icons.Default.Block
        "vinayaka-chavithi" -> Icons.Default.SelfImprovement
        "minimal-logo" -> Icons.Default.RadioButtonUnchecked
        "street-style" -> Icons.Default.LocalFireDepartment
        "college" -> Icons.Default.School
        "sports" -> Icons.Default.Tag
        "custom-print" -> Icons.Default.AutoAwesome
        else -> Icons.Default.Star
    }
}

@Composable
fun DesignSelector(
    selectedDesignId: String,
    uploadedName: String?,
    onSelectDesign: (String) -> Unit,
    onUploadClick: () -> Unit,
    onClearUpload: () -> Unit,
    itemSize: Dp = 44.dp,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier.horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        demoDesigns.forEach { d ->
            val isActive = selectedDesignId == d.id && uploadedName == null
            val icon = getDesignIcon(d.id)

            Box(
                modifier = Modifier
                    .size(itemSize)
                    .then(
                        if (isActive) {
                            Modifier
                                .shadow(6.dp, RoundedCornerShape(12.dp), spotColor = Thread)
                                .border(1.5.dp, Thread, RoundedCornerShape(12.dp))
                                .background(Thread.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                        } else {
                            Modifier
                                .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
                                .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
                        }
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onSelectDesign(d.id) }
                    .testTag("design_btn_${d.id}"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = d.label,
                    tint = if (isActive) Paper else PaperDim,
                    modifier = Modifier.size(itemSize * 0.48f)
                )
            }
        }

        // Custom image upload slot
        if (!uploadedName.isNullOrBlank()) {
            Row(
                modifier = Modifier
                    .height(itemSize)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.5.dp, Thread, RoundedCornerShape(12.dp))
                    .background(Thread.copy(alpha = 0.2f))
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "Custom Upload",
                    tint = Thread,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = uploadedName,
                    color = Paper,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(70.dp)
                )
                IconButton(
                    onClick = onClearUpload,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Remove Upload",
                        tint = PaperDim,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .height(itemSize)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Signal)
                    .clickable(onClick = onUploadClick)
                    .padding(horizontal = 12.dp)
                    .testTag("upload_design_btn"),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Upload",
                    tint = Paper,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "UPLOAD",
                    color = Paper,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
            }
        }
    }
}
