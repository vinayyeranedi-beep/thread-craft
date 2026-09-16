package com.example.threadcraft.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Signal,
    onPrimary = Paper,
    primaryContainer = SignalDim,
    onPrimaryContainer = Paper,
    secondary = Thread,
    onSecondary = Ink,
    secondaryContainer = ThreadDim,
    onSecondaryContainer = ThreadGlow,
    background = Ink,
    onBackground = Paper,
    surface = InkSoft,
    onSurface = Paper,
    surfaceVariant = InkCard,
    onSurfaceVariant = PaperDim,
    outline = InkLine,
    outlineVariant = InkLineLight
)

@Composable
fun ThreadCraftTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
