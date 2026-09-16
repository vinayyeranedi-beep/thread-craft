package com.example.threadcraft.data

import android.content.Context
import android.content.Intent
import android.net.Uri

object BusinessConfig {
    const val NAME = "THREADCRAFT"
    const val TAGLINE = "Custom T-Shirt Printing"
    val HEADLINE = listOf("WEAR YOUR", "IDEAS.")
    const val SUBHEAD = "Premium custom T-shirt printing for events, teams, brands and everyday style."

    const val PHONE = "+91 90000 00000"
    const val WHATSAPP_NUMBER = "919000000000"
    const val INSTAGRAM = "@threadcraft.demo"
    const val LOCATION = "Vizianagaram, Andhra Pradesh"
    const val IS_DEMO = true

    fun buildWhatsAppUri(message: String = "Hi! I'd like to talk about a custom print order."): Uri {
        return Uri.parse("https://wa.me/$WHATSAPP_NUMBER?text=${Uri.encode(message)}")
    }

    fun openWhatsApp(context: Context, message: String) {
        val intent = Intent(Intent.ACTION_VIEW, buildWhatsAppUri(message)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        try {
            context.startActivity(intent)
        } catch (_: Exception) {
            // Fallback to generic share intent if WhatsApp is not directly accessible
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, message)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(Intent.createChooser(shareIntent, "Send Quote Request"))
        }
    }

    fun callPhone(context: Context) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${PHONE.replace(" ", "")}")).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

    fun openInstagram(context: Context) {
        val handle = INSTAGRAM.removePrefix("@")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/$handle")).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

    fun openMap(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${Uri.encode(LOCATION)}")).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }
}
