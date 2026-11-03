package com.example.zipcart.frontend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zipcart.ui.theme.ZipGreen
import com.example.zipcart.ui.theme.ZipSurface
import com.example.zipcart.ui.theme.ZipTextSecondary


@Composable
fun CategoryChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(72.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(if (selected) ZipGreen.copy(alpha = 0.18f) else ZipSurface),
            contentAlignment = Alignment.Center
        ) {
            Text(emojiFor(label), fontSize = 26.sp)
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = label.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodySmall,
            color = if (selected) ZipGreen else ZipTextSecondary,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private fun emojiFor(category: String): String = when {
    category == "All" -> "🛍️"
    category.contains("fruit", true) -> "🍎"
    category.contains("veg", true) -> "🥦"
    category.contains("dairy", true) -> "🥛"
    category.contains("drink", true) -> "🧃"
    category.contains("meat", true) -> "🍗"
    category.contains("pantry", true) -> "🍚"
    else -> "🛒"
}