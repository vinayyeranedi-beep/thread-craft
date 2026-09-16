package com.example.threadcraft.ui

import androidx.compose.animation.Crossfade
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.threadcraft.data.BusinessConfig
import com.example.threadcraft.data.QuoteRepository
import com.example.threadcraft.ui.components.StickyBottomBar
import com.example.threadcraft.ui.screens.AboutContactScreen
import com.example.threadcraft.ui.screens.CustomizerScreen
import com.example.threadcraft.ui.screens.GalleryScreen
import com.example.threadcraft.ui.screens.HomeScreen
import com.example.threadcraft.ui.screens.QuotesScreen
import com.example.threadcraft.ui.screens.ServicesScreen
import com.example.threadcraft.ui.theme.Ink
import com.example.threadcraft.ui.theme.InkCard
import com.example.threadcraft.ui.theme.InkLine
import com.example.threadcraft.ui.theme.InkSoft
import com.example.threadcraft.ui.theme.Paper
import com.example.threadcraft.ui.theme.PaperDim
import com.example.threadcraft.ui.theme.PaperMuted
import com.example.threadcraft.ui.theme.Signal
import com.example.threadcraft.ui.theme.Thread

enum class NavTab(val title: String, val icon: ImageVector) {
    STUDIO("Studio", Icons.Default.Palette),
    HOME("Home", Icons.Default.Home),
    SERVICES("Services", Icons.Default.Layers),
    GALLERY("Gallery", Icons.Default.Collections),
    ORDERS("Orders", Icons.Default.ReceiptLong),
    MORE("More", Icons.Default.Info)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreadCraftApp(
    quoteRepository: QuoteRepository,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var currentTab by remember { mutableStateOf(NavTab.STUDIO) }
    var customizerColorId by remember { mutableStateOf("black") }
    var customizerDesignId by remember { mutableStateOf("vinayaka-chavithi") }

    val quotes by quoteRepository.quotes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "THREADCRAFT",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp,
                            color = Paper
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Thread.copy(alpha = 0.2f))
                                .border(1.dp, Thread.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "DEMO",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Thread
                            )
                        }
                    }
                },
                actions = {
                    // WhatsApp quick contact
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clip(CircleShape)
                            .background(Signal.copy(alpha = 0.15f))
                            .border(1.dp, Signal.copy(alpha = 0.4f), CircleShape)
                            .clickable {
                                BusinessConfig.openWhatsApp(context, "Hi! I have an enquiry about ThreadCraft.")
                            }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                            .testTag("topbar_whatsapp_btn"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = "WhatsApp",
                                tint = Signal,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "Chat",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Signal
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Ink,
                    titleContentColor = Paper
                )
            )
        },
        bottomBar = {
            Column {
                // Show sticky mobile CTA only on browsing screens (Home, Services, Gallery, More)
                if (currentTab != NavTab.STUDIO) {
                    StickyBottomBar(
                        onCustomizeClick = { currentTab = NavTab.STUDIO },
                        onWhatsAppClick = {
                            BusinessConfig.openWhatsApp(context, "Hi! I'd like a custom t-shirt printing quote.")
                        }
                    )
                }

                NavigationBar(
                    containerColor = InkSoft,
                    contentColor = Paper,
                    tonalElevation = 8.dp,
                    modifier = Modifier.border(1.dp, InkLine)
                ) {
                    NavTab.entries.forEach { tab ->
                        val isSelected = currentTab == tab
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { currentTab = tab },
                            icon = {
                                if (tab == NavTab.ORDERS && quotes.isNotEmpty()) {
                                    BadgedBox(
                                        badge = {
                                            Badge(
                                                containerColor = Signal,
                                                contentColor = Paper
                                            ) {
                                                Text(quotes.size.toString())
                                            }
                                        }
                                    ) {
                                        Icon(tab.icon, contentDescription = tab.title)
                                    }
                                } else {
                                    Icon(tab.icon, contentDescription = tab.title)
                                }
                            },
                            label = {
                                Text(
                                    text = tab.title,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Paper,
                                selectedTextColor = Paper,
                                indicatorColor = Signal,
                                unselectedIconColor = PaperMuted,
                                unselectedTextColor = PaperMuted
                            ),
                            modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                        )
                    }
                }
            }
        },
        containerColor = Ink
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(
                targetState = currentTab,
                label = "screen_crossfade"
            ) { tab ->
                when (tab) {
                    NavTab.STUDIO -> {
                        CustomizerScreen(
                            quoteRepository = quoteRepository,
                            initialColorId = customizerColorId,
                            initialDesignId = customizerDesignId,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    NavTab.HOME -> {
                        HomeScreen(
                            onNavigateToCustomizer = { currentTab = NavTab.STUDIO },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    NavTab.SERVICES -> {
                        ServicesScreen(
                            onSelectServiceForCustomize = { serviceId ->
                                currentTab = NavTab.STUDIO
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    NavTab.GALLERY -> {
                        GalleryScreen(
                            onCustomizeStyle = { category ->
                                when (category.lowercase()) {
                                    "streetwear" -> {
                                        customizerColorId = "black"
                                        customizerDesignId = "street-style"
                                    }
                                    "college" -> {
                                        customizerColorId = "blue"
                                        customizerDesignId = "college"
                                    }
                                    "sports" -> {
                                        customizerColorId = "green"
                                        customizerDesignId = "sports"
                                    }
                                    else -> {
                                        customizerColorId = "black"
                                        customizerDesignId = "custom-print"
                                    }
                                }
                                currentTab = NavTab.STUDIO
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    NavTab.ORDERS -> {
                        QuotesScreen(
                            quoteRepository = quoteRepository,
                            onNavigateToCustomizer = { colorId, designId ->
                                customizerColorId = colorId
                                customizerDesignId = designId
                                currentTab = NavTab.STUDIO
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    NavTab.MORE -> {
                        AboutContactScreen(
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
