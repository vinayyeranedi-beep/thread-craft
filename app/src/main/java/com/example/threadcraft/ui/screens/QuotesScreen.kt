package com.example.threadcraft.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.threadcraft.data.BusinessConfig
import com.example.threadcraft.data.PricingConfig
import com.example.threadcraft.data.QuoteRepository
import com.example.threadcraft.data.SavedQuote
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
fun QuotesScreen(
    quoteRepository: QuoteRepository,
    onNavigateToCustomizer: (colorId: String, designId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val quotes by quoteRepository.quotes.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Ink)
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(
            text = "SAVED ORDERS",
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.6.sp,
            color = Thread
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Your custom quotes",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = Paper
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Re-check mockups, review bulk discounts, or send directly to WhatsApp for printing.",
            fontSize = 14.sp,
            color = PaperDim
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (quotes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(InkSoft)
                            .border(1.dp, InkLine, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ReceiptLong,
                            contentDescription = null,
                            tint = PaperDim,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Text(
                        text = "No saved quotes yet",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Paper
                    )
                    Text(
                        text = "Design a T-shirt in the Studio and tap the bookmark icon to save it here.",
                        fontSize = 13.sp,
                        color = PaperMuted,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { onNavigateToCustomizer("black", "vinayaka-chavithi") },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Signal)
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Open Studio")
                    }
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(quotes, key = { it.id }) { item ->
                    QuoteCard(
                        quote = item,
                        onWhatsAppClick = {
                            val msg = buildString {
                                appendLine("Hi! I'd like to confirm this saved order quote:")
                                appendLine("• ${item.quantity} × T-shirt, size ${item.size}")
                                appendLine("• Colour: ${item.colorLabel}")
                                appendLine("• Design: ${item.designLabel}")
                                appendLine("Estimated: ${PricingConfig.formatPrice(item.totalPrice)} (${PricingConfig.formatPrice(item.unitPrice)}/unit)")
                            }
                            BusinessConfig.openWhatsApp(context, msg)
                        },
                        onDeleteClick = {
                            quoteRepository.deleteQuote(item.id)
                        },
                        onReopen = {
                            onNavigateToCustomizer(item.colorId, item.designId)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun QuoteCard(
    quote: SavedQuote,
    onWhatsAppClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onReopen: () -> Unit
) {
    val swatchColor = try {
        Color(android.graphics.Color.parseColor(quote.colorHex))
    } catch (_: Exception) {
        Color.DarkGray
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, InkLine, RoundedCornerShape(20.dp))
            .background(InkSoft)
            .padding(16.dp)
            .testTag("quote_card_${quote.id}")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(swatchColor)
                        .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
                )
                Text(
                    text = "${quote.colorLabel} • Size ${quote.size}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Paper
                )
            }

            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = PaperMuted,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Design: ${quote.designLabel}",
            fontSize = 13.sp,
            color = PaperDim
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(InkCard)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "${quote.quantity} pieces",
                    fontSize = 12.sp,
                    color = PaperDim
                )
                Text(
                    text = PricingConfig.formatPrice(quote.totalPrice),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = Paper
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                if (quote.discountPct > 0) {
                    Text(
                        text = "${quote.discountPct}% bulk off",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Thread
                    )
                }
                Text(
                    text = "${PricingConfig.formatPrice(quote.unitPrice)} / unit",
                    fontSize = 11.sp,
                    color = PaperMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onReopen,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = InkCard,
                    contentColor = Paper
                ),
                modifier = Modifier.weight(1f).height(40.dp)
            ) {
                Text("Edit in Studio", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = onWhatsAppClick,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Signal,
                    contentColor = Paper
                ),
                modifier = Modifier.weight(1.2f).height(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Send WhatsApp", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
