package com.example.brewkery.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brewkery.ui.CoffeeLight

@Composable
fun Header(
    cartCount: Int,
    onCart: () -> Unit,
    orderActive: Boolean = false,
    orderTicket: String? = null,
    onTrack: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        // ─────────────────────────────────────────
        // HEADER
        // ─────────────────────────────────────────

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // BK LOGO
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "BK",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            // BRAND
            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Fresh Roast & Bakes",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = CoffeeLight
                )

                Text(
                    text = "Brewkery Artisans",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            // CART
            BadgedBox(
                badge = {

                    if (cartCount > 0) {

                        Badge(
                            containerColor = Color(0xFFE64A2E)
                        ) {

                            Text(
                                text = cartCount.toString(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            ) {

                IconButton(
                    onClick = onCart,
                    modifier = Modifier
                        .size(40.dp)
                        .border(
                            width = 1.5.dp,
                            color = Color(0xFFEED8C9),
                            shape = CircleShape
                        )
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        )
                ) {

                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = "Cart",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // ─────────────────────────────────────────
        // STORE / ACTIVE ORDER CARD
        // ─────────────────────────────────────────

        StoreInfoCard(
            orderActive = orderActive,
            orderTicket = orderTicket,
            onTrack = onTrack
        )
    }
}