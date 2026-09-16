package com.example.threadcraft.data

import androidx.compose.ui.graphics.Color
import java.text.NumberFormat
import java.util.Locale

data class ShirtColor(
    val id: String,
    val label: String,
    val hex: String,
    val color: Color
)

val shirtColors = listOf(
    ShirtColor("black", "Black", "#111114", Color(0xFF111114)),
    ShirtColor("white", "White", "#F3F1EA", Color(0xFFF3F1EA)),
    ShirtColor("red", "Red", "#8A2620", Color(0xFF8A2620)),
    ShirtColor("blue", "Blue", "#20344F", Color(0xFF20344F)),
    ShirtColor("green", "Green", "#2B3B2E", Color(0xFF2B3B2E)),
    ShirtColor("yellow", "Yellow", "#C9A227", Color(0xFFC9A227))
)

data class DemoDesign(
    val id: String,
    val label: String
)

val demoDesigns = listOf(
    DemoDesign("none", "No Design"),
    DemoDesign("vinayaka-chavithi", "Vinayaka Chavithi"),
    DemoDesign("minimal-logo", "Minimal Logo"),
    DemoDesign("street-style", "Street Style"),
    DemoDesign("college", "College"),
    DemoDesign("sports", "Sports"),
    DemoDesign("custom-print", "Custom Print")
)

val shirtSizes = listOf("S", "M", "L", "XL", "XXL")

data class PricingTier(
    val minQty: Int,
    val discountPct: Int
)

data class PriceEstimate(
    val unitPrice: Int,
    val totalPrice: Int,
    val discountPct: Int
)

object PricingConfig {
    const val BASE_PRICE = 499
    const val CURRENCY_SYMBOL = "₹"

    val tiers = listOf(
        PricingTier(minQty = 1, discountPct = 0),
        PricingTier(minQty = 10, discountPct = 10),
        PricingTier(minQty = 50, discountPct = 20),
        PricingTier(minQty = 200, discountPct = 30)
    )

    fun estimate(qty: Int): PriceEstimate {
        val safeQty = maxOf(1, qty)
        val tier = tiers.reversed().firstOrNull { safeQty >= it.minQty } ?: tiers.first()
        val unit = (BASE_PRICE * (1.0 - tier.discountPct / 100.0)).toInt()
        val total = unit * safeQty
        return PriceEstimate(unitPrice = unit, totalPrice = total, discountPct = tier.discountPct)
    }

    fun formatPrice(amount: Int): String {
        return "₹" + NumberFormat.getNumberInstance(Locale("en", "IN")).format(amount)
    }

    fun buildQuoteMessage(
        qty: Int,
        size: String,
        colorLabel: String,
        designLabel: String,
        estimate: PriceEstimate
    ): String {
        return buildString {
            appendLine("Hi! I'd like a quote for:")
            appendLine("• $qty × T-shirt, size $size")
            appendLine("• Colour: $colorLabel")
            appendLine("• Design: $designLabel")
            appendLine("Estimated: ${formatPrice(estimate.totalPrice)} (${formatPrice(estimate.unitPrice)}/unit)")
        }
    }
}
