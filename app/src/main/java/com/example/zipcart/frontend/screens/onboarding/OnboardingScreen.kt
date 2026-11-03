package com.example.zipcart.frontend.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zipcart.ui.theme.ZipBackground
import com.example.zipcart.ui.theme.ZipGreen
import com.example.zipcart.ui.theme.ZipGreenDark
import com.example.zipcart.ui.theme.ZipTextSecondary
import com.example.zipcart.R



@Composable
fun OnboardingScreen(onGetStarted: () -> Unit, onSkip: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        // Put any fresh-produce photo at res/drawable/onboarding_bg.jpg
        Image(
            painter = painterResource(id = R.drawable.onboarding_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // Light fade on top (readable text) -> dark green fade at bottom (readable button)
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to ZipBackground.copy(alpha = 0.95f),
                        0.45f to Color.Transparent,
                        1f to ZipGreenDark.copy(alpha = 0.75f)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(24.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(ZipGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.ShoppingCart, null, tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "ZipCart",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = ZipGreenDark
                    )
                }
                Text(
                    "Skip",
                    style = MaterialTheme.typography.labelLarge,
                    color = ZipTextSecondary,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color.White.copy(alpha = 0.8f))
                        .clickable(onClick = onSkip)
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }

            Spacer(Modifier.height(32.dp))

            Text(
                "Fresh groceries,\ndelivered in 10 min",
                fontSize = 32.sp,
                lineHeight = 38.sp,
                fontWeight = FontWeight.Bold,
                color = ZipGreenDark
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Fruits, veggies and daily essentials straight to your doorstep.",
                style = MaterialTheme.typography.bodyMedium,
                color = ZipTextSecondary
            )
            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                repeat(3) { index ->
                    Box(
                        Modifier
                            .width(28.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(50))
                            .background(if (index == 2) ZipGreen else ZipGreen.copy(alpha = 0.3f))
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            // "Get Started" pill
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0x992E5A12))
                    .clickable(onClick = onGetStarted)
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, "Get started", tint = ZipGreenDark)
                }
                Text(
                    "Get Started",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "›››",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(end = 18.dp)
                )
            }
        }
    }
}