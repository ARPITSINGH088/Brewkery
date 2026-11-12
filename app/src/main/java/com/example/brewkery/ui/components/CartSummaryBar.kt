package com.example.brewkery.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brewkery.ui.Caramel
import com.example.brewkery.ui.Coffee
import com.example.brewkery.ui.CoffeeLight
import java.util.Locale

@Composable
fun CartSummaryBar(
    itemCount: Int,
    total: Double,
    onCartClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 5.dp,
                end = 5.dp,
                bottom = 1.dp
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(
                    RoundedCornerShape(20.dp)
                )
                .background(
                    Color(0xFF160B08)
                )
                .clickable {
                    onCartClick()
                }
                .padding(
                    horizontal = 24.dp,
                    vertical = 12.dp
                ),

            verticalAlignment = Alignment.CenterVertically
        ) {

            // ─────────────────────────────
            // ITEM COUNT
            // ─────────────────────────────

            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(
                        Color(0xFFE64A2E)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = itemCount.toString(),
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            // ─────────────────────────────
            // CART INFORMATION
            // ─────────────────────────────

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "View Your Cart",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(
                    modifier = Modifier.height(0.dp)
                )

                Text(
                    text = String.format(
                        Locale.US,
                        "$%.2f",
                        total
                    ),
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            // ─────────────────────────────
            // CHECKOUT
            // ─────────────────────────────

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Proceed to Checkout",
                    color = Color(0xFFFFA800),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.width(6.dp)
                )

                Text(
                    text = "→",
                    color = Color(0xFFFFA800),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SummaryRow(label: String, value: Double, bold: Boolean = false) {
    Row(Modifier
        .fillMaxWidth()
        .padding(vertical = 3.dp)) {
        Text(
            label,
            Modifier.weight(1f),
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
            color = Coffee
        ); Text(
        "$${String.format(Locale.US, "%.2f", value)}",
        fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
        color = Coffee
    )
    }
}



private const val RandomPlaceholder = "74921"

@Composable
fun ErrorView(message: String, retry: () -> Unit) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Icon(
                Icons.Default.CloudOff,
                null,
                tint = Caramel,
                modifier = Modifier.size(48.dp)
            ); Text("Couldn't load the menu", fontWeight = FontWeight.Bold, color = Coffee); Text(
            message,
            color = CoffeeLight
        ); Spacer(Modifier.height(12.dp)); Button(onClick = retry) { Text("Retry") }
        }
    }
}