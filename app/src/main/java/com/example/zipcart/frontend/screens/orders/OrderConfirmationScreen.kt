package com.example.zipcart.frontend.screens.orders

import com.example.zipcart.ui.theme.ZipBackground
import com.example.zipcart.ui.theme.ZipGreen
import com.example.zipcart.ui.theme.ZipGreenDark
import com.example.zipcart.ui.theme.ZipTextSecondary
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun OrderConfirmationScreen(
    orderId: Long,
    onViewOrders: () -> Unit,
    onContinueShopping: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ZipBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(ZipGreen),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Check, null, tint = Color.White, modifier = Modifier.size(56.dp))
        }
        Spacer(Modifier.height(24.dp))
        Text("Order placed!", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text(
            "Order #$orderId is on its way.\nArriving in about 10 minutes.",
            color = ZipTextSecondary,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(40.dp))
        Button(
            onClick = onViewOrders,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ZipGreen)
        ) { Text("View my orders", style = MaterialTheme.typography.titleMedium) }
        Spacer(Modifier.height(12.dp))
        OutlinedButton(
            onClick = onContinueShopping,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(24.dp)
        ) { Text("Continue shopping", color = ZipGreenDark) }
    }
}