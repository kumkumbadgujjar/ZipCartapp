package com.example.zipcart.frontend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.zipcart.ui.theme.ZipGreen
import com.example.zipcart.ui.theme.ZipSurface
import com.example.zipcart.ui.theme.ZipTextSecondary


enum class NavTab { Home, Orders, Cart, Profile }

/** Floating pill navigation. */
@Composable
fun ZipBottomNav(
    selected: NavTab,
    cartCount: Int,
    onHome: () -> Unit,
    onOrders: () -> Unit,
    onCart: () -> Unit,
    onProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(50),
            color = ZipSurface,
            shadowElevation = 10.dp
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavItem(Icons.Filled.Home, "Home", selected == NavTab.Home, 0, onHome)
                NavItem(Icons.Filled.Receipt, "Orders", selected == NavTab.Orders, 0, onOrders)
                NavItem(Icons.Filled.ShoppingCart, "Cart", selected == NavTab.Cart, cartCount, onCart)
                NavItem(Icons.Filled.Person, "Profile", selected == NavTab.Profile, 0, onProfile)
            }
        }
    }
}

@Composable
private fun NavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    badgeCount: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (selected) ZipGreen else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = if (selected) 16.dp else 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        BadgedBox(badge = {
            if (badgeCount > 0) Badge { Text("$badgeCount") }
        }) {
            Icon(
                icon,
                contentDescription = label,
                tint = if (selected) Color.White else ZipTextSecondary
            )
        }
        if (selected) {
            Text(label, color = Color.White, style = MaterialTheme.typography.labelLarge)
        }
    }
}