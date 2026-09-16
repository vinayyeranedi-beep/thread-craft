package com.example.threadcraft.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.threadcraft.R
import com.example.threadcraft.data.GalleryItem
import com.example.threadcraft.data.galleryItems
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
fun GalleryScreen(
    onCustomizeStyle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Streetwear", "College", "Corporate", "Sports", "Events")

    val filteredItems = remember(selectedCategory) {
        if (selectedCategory == "All") galleryItems
        else galleryItems.filter { it.category == selectedCategory }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Ink)
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(
            text = "RECENT WORK",
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.6.sp,
            color = Thread
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "A few things we've printed",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = Paper
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "From drops to team uniforms. Tap any style to customize in the studio.",
            fontSize = 14.sp,
            color = PaperDim
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Category Filter Chips Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { cat ->
                val isActive = selectedCategory == cat
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .then(
                            if (isActive) {
                                Modifier.background(Signal)
                            } else {
                                Modifier
                                    .border(1.dp, InkLine, CircleShape)
                                    .background(InkSoft)
                            }
                        )
                        .clickable { selectedCategory = cat }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                        .testTag("gallery_filter_$cat"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cat,
                        color = if (isActive) Paper else PaperDim,
                        fontSize = 12.sp,
                        fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Gallery Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 80.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            if (selectedCategory == "All" || selectedCategory == "Events") {
                item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                    // Featured T-Shirt Demo Card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .border(1.dp, InkLine, RoundedCornerShape(20.dp))
                            .clickable { onCustomizeStyle("Events") }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_ganesha_demo),
                            contentDescription = "Vinayaka Chavithi T-Shirt Demo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        // Gradient Overlay
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
                        // Top Badge
                        Row(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(12.dp)
                                .clip(CircleShape)
                                .background(Ink.copy(alpha = 0.85f))
                                .border(1.dp, Thread.copy(alpha = 0.5f), CircleShape)
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Thread,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "FEATURED DEMO • VINAYAKA CHAVITHI",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Thread
                            )
                        }

                        // Bottom Info
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(12.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(Ink.copy(alpha = 0.88f))
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Festive Edition Print",
                                    color = Paper,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Gold Foil & Screen on Black Cotton",
                                    color = PaperDim,
                                    fontSize = 10.sp
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "Customize",
                                    color = Thread,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = Thread,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    }
                }
            }

            items(filteredItems, key = { it.id }) { item ->
                GalleryCard(
                    item = item,
                    onClick = { onCustomizeStyle(item.category) }
                )
            }
        }
    }
}

@Composable
private fun GalleryCard(
    item: GalleryItem,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .border(1.dp, InkLine, RoundedCornerShape(18.dp))
            .background(InkSoft)
            .clickable(onClick = onClick)
            .testTag("gallery_card_${item.id}")
    ) {
        // Aesthetic Graphic Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.1f)
                .background(
                    Brush.linearGradient(
                        colors = listOf(item.fromColor, item.toColor)
                    )
                )
                .padding(12.dp)
        ) {
            // Category Badge
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Ink.copy(alpha = 0.7f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = item.category.uppercase(),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = item.accentColor
                )
            }

            // Decorative shirt shape silhouette
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.08f))
                    .border(1.dp, item.accentColor.copy(alpha = 0.4f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = item.accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Details below
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = item.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Paper,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Customise",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Thread
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Thread,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}
