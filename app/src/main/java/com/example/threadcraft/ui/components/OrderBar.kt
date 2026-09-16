package com.example.threadcraft.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.threadcraft.data.PricingConfig
import com.example.threadcraft.ui.theme.InkCard
import com.example.threadcraft.ui.theme.InkLine
import com.example.threadcraft.ui.theme.InkSoft
import com.example.threadcraft.ui.theme.Paper
import com.example.threadcraft.ui.theme.PaperDim
import com.example.threadcraft.ui.theme.PaperMuted
import com.example.threadcraft.ui.theme.Signal
import com.example.threadcraft.ui.theme.SignalGlow
import com.example.threadcraft.ui.theme.Thread

@Composable
fun OrderBar(
    size: String,
    onSizeChanged: (String) -> Unit,
    qty: Int,
    onQtyChanged: (Int) -> Unit,
    colorLabel: String,
    designLabel: String,
    onSendQuoteWhatsApp: () -> Unit,
    onSaveQuote: () -> Unit,
    isSaved: Boolean = false,
    modifier: Modifier = Modifier
) {
    val estimate = PricingConfig.estimate(qty)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .border(1.dp, InkLine, RoundedCornerShape(24.dp))
            .background(InkSoft)
            .padding(16.dp)
            .testTag("order_bar_container"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Size and Quantity selection row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "SIZE",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    letterSpacing = 1.2.sp,
                    color = PaperMuted
                )
                Spacer(modifier = Modifier.height(6.dp))
                SizeChips(selectedSize = size, onSizeSelected = onSizeChanged)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "QUANTITY",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    letterSpacing = 1.2.sp,
                    color = PaperMuted
                )
                Spacer(modifier = Modifier.height(6.dp))
                QuantityStepper(quantity = qty, onQuantityChanged = onQtyChanged)
            }
        }

        // Price summary and Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Text(
                    text = "ESTIMATED PRICE",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    letterSpacing = 1.2.sp,
                    color = PaperMuted
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = PricingConfig.formatPrice(estimate.totalPrice),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = Paper
                    )
                    if (estimate.discountPct > 0) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${estimate.discountPct}% bulk off",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Thread
                        )
                    }
                }
                Text(
                    text = "${PricingConfig.formatPrice(estimate.unitPrice)} / unit",
                    fontSize = 12.sp,
                    color = PaperDim
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // Save quote bookmark button
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .border(1.dp, if (isSaved) Thread else InkLine, CircleShape)
                        .background(if (isSaved) Thread.copy(alpha = 0.2f) else InkCard)
                        .clickable(onClick = onSaveQuote)
                        .testTag("save_quote_btn"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isSaved) Icons.Default.Check else Icons.Default.BookmarkBorder,
                        contentDescription = "Save Quote",
                        tint = if (isSaved) Thread else PaperDim,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // WhatsApp Quote button
                Button(
                    onClick = onSendQuoteWhatsApp,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Signal,
                        contentColor = Paper
                    ),
                    modifier = Modifier
                        .height(44.dp)
                        .shadow(10.dp, CircleShape, spotColor = SignalGlow)
                        .testTag("whatsapp_quote_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = "WhatsApp",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Get this quote",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
