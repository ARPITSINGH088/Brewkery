package com.example.brewkery.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StoreInfoCard(
    orderActive: Boolean = false,
    orderTicket: String? = null,
    onTrack: () -> Unit = {}
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 14.dp,
                vertical = 10.dp
            ),
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF1E6)
        ),
        border = BorderStroke(
            width = 2.dp,
            color = Color(0xFFF0D8C7)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 24.dp,
                    vertical = 5.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ─────────────────────────────
            // LEFT ICON
            // Same size/position as Store Info
            // ─────────────────────────────

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color.White)
                    .border(
                        width = 2.dp,
                        color = Color(0xFFF0D8C7),
                        shape = RoundedCornerShape(18.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                if (orderActive) {

                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF00C853))
                    )

                } else {

                    Text(
                        text = "🛵",
                        fontSize = 25.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.width(18.dp)
            )

            // ─────────────────────────────
            // CENTER CONTENT
            // ─────────────────────────────

            Column(
                modifier = Modifier.weight(1f)
            ) {

                if (orderActive) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 0.dp)
                    ) {

                        Text(
                            text = "ACTIVE ORDER",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF009B6A)
                        )

                        Spacer(
                            modifier = Modifier.width(10.dp)
                        )

                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE64A2E))
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(0.dp)
                    )

                    Text(
                        text = "Active Order #${orderTicket ?: ""}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF17120F),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "Preparing (Arriving in 25 mins)",
                        fontSize = 10.sp,
                        color = Color(0xFF806A5D),
                        maxLines = 1
                    )

                } else {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "STORE INFO",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE64A2E)
                        )

                        Spacer(
                            modifier = Modifier.width(10.dp)
                        )

                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE64A2E)
                                ),

                            )
                    }

                    Text(
                        text = "Delivery in 20–30 mins",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF17120F)
                    )

                    Text(
                        text = "$2.50 flat fee",
                        fontSize = 13.sp,
                        color = Color(0xFF806A5D)
                    )
                }
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            // ─────────────────────────────
            // RIGHT BUTTON
            // Same position as Open button
            // ─────────────────────────────

            if (orderActive) {

                Button(
                    onClick = onTrack,
                    modifier = Modifier
                        .width(85.dp)
                        .height(30.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF009B6A),
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 18.dp,
                        vertical = 0.dp
                    )
                ) {

                    Text(
                        text = "Track",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            } else {

                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.height(48.dp),
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(
                        width = 2.dp,
                        color = Color(0xFFEED8C9)
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF17120F)
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 14.dp,
                        vertical = 0.dp
                    )
                ) {

                    Text(
                        text = "Open",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}