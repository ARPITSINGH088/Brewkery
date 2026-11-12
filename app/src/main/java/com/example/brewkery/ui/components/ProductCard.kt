package com.example.brewkery.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.brewkery.data.model.Product
import com.example.brewkery.ui.Caramel
import java.util.Locale

@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),

        shape = RoundedCornerShape(22.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        border = BorderStroke(
            width = 1.dp,
            color = Caramel
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            // ─────────────────────────────────
            // EXISTING PRODUCT CONTENT
            // ─────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 55.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // PRODUCT IMAGE
                AsyncImage(
                    model = product.image_url,
                    contentDescription = product.name,

                    modifier = Modifier
                        .size(80.dp)
                        .clip(
                            RoundedCornerShape(18.dp)
                        ),

                    contentScale = ContentScale.Crop
                )

                Spacer(
                    modifier = Modifier.width(18.dp)
                )

                // PRODUCT INFORMATION
                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    // Bestseller badge
                    product.badge?.let { badge ->

                        Box(
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(7.dp)
                                )
                                .background(
                                    Color(0xFFFFF0B8)
                                )
                                .padding(
                                    horizontal = 5.dp,
                                    vertical = 0.5.dp
                                )
                        ) {
                            Text(
                                text = badge.uppercase(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF7A3D13)
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(7.dp)
                        )
                    }

                    // Product name
                    Text(
                        text = product.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF17120F),
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    // Rating + reviews
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "⭐",
                            fontSize = 8.sp
                        )

                        Spacer(
                            modifier = Modifier.width(4.dp)
                        )

                        Text(
                            text = String.format(
                                Locale.US,
                                "%.1f",
                                product.rating
                            ),
                            fontSize = 12.sp,
                            color = Color(0xFF806A5D)
                        )

                        Text(
                            text = " (${product.review_count})",
                            fontSize = 12.sp,
                            color = Color(0xFF806A5D)
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    // Price
                    Text(
                        text = String.format(
                            Locale.US,
                            "$%.2f",
                            product.base_price
                        ),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFE64A2E)
                    )
                }
            }

            // ─────────────────────────────────
            // CUSTOMIZE BUTTON - BOTTOM RIGHT
            // ─────────────────────────────────
            Button(
                onClick = onClick,

                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .width(85.dp)
                    .height(25.dp),

                shape = RoundedCornerShape(8.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE64A2E),
                    contentColor = Color.White
                ),

                contentPadding = PaddingValues(
                    horizontal = 0.dp,
                    vertical = 0.dp
                )
            ) {
                Text(
                    text = "+ Customize",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
