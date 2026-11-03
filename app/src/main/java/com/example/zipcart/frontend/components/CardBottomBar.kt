package com.example.zipcart.frontend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.zipcart.ui.theme.ZipGreen
import com.example.zipcart.ui.theme.ZipGreenDark


/** Green pill: price on the left, white action button on the right. */
@Composable
fun CartBottomBar(
    priceText: String,
    actionLabel: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(50))
            .background(ZipGreen)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = priceText,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp)
        )
        Button(
            onClick = onClick,
            enabled = enabled && !loading,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = ZipGreenDark,
                disabledContainerColor = Color.White.copy(alpha = 0.7f)
            ),
            modifier = Modifier.height(48.dp)
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = ZipGreenDark
                )
            } else {
                Text(actionLabel, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}