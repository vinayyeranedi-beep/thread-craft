package com.example.threadcraft.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.threadcraft.ui.theme.Paper
import com.example.threadcraft.ui.theme.PaperDim
import com.example.threadcraft.ui.theme.Signal

@Composable
fun ViewSwitch(
    currentView: String,
    onViewChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isBack = currentView == "back"
    val animOffset by animateFloatAsState(
        targetValue = if (isBack) 1f else 0f,
        animationSpec = tween(durationMillis = 280),
        label = "view_pill_anim"
    )

    Box(
        modifier = modifier
            .width(136.dp)
            .height(38.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.08f))
            .border(1.dp, Color.White.copy(alpha = 0.15f), CircleShape)
            .padding(3.dp)
            .testTag("view_switch_container")
    ) {
        // Sliding indicator pill
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(64.dp)
                .offset { IntOffset(x = (animOffset * 66.dp.toPx()).toInt(), y = 0) }
                .clip(CircleShape)
                .background(Signal)
        )

        // Buttons
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { onViewChanged("front") }
                    .testTag("view_front_btn"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "FRONT",
                    color = if (!isBack) Paper else PaperDim,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { onViewChanged("back") }
                    .testTag("view_back_btn"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "BACK",
                    color = if (isBack) Paper else PaperDim,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
            }
        }
    }
}
