package com.example.threadcraft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.threadcraft.data.QuoteRepository
import com.example.threadcraft.ui.ThreadCraftApp
import com.example.threadcraft.ui.theme.Ink
import com.example.threadcraft.ui.theme.ThreadCraftTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val quoteRepository = QuoteRepository(applicationContext)

        setContent {
            ThreadCraftTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Ink
                ) {
                    ThreadCraftApp(quoteRepository = quoteRepository)
                }
            }
        }
    }
}
