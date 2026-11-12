package com.example.brewkery.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.brewkery.data.model.CustomizationOption
import java.util.Locale

@Composable
fun OptionItem(
    option: CustomizationOption,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val borderColor =
        if (selected) {
            Color(0xFFF04B27)
        } else {
            Color(0xFFE8D1C2)
        }

    val backgroundColor =
        if (selected) {
            Color(0xFFFFEEE7)
        } else {
            Color.White
        }

    Row(
        modifier = modifier
            .height(50.dp)
            .clip(
                RoundedCornerShape(12.dp)
            )
            .background(backgroundColor)
            .border(
                width = if (selected) 2.dp else 1.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 18.dp
            ),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = option.displayName,

            modifier = Modifier.weight(1f),

            fontSize = 12.sp,

            fontWeight =
                if (selected) {
                    FontWeight.ExtraBold
                } else {
                    FontWeight.Medium
                },

            color =
                if (selected) {
                    Color(0xFFE04A2A)
                } else {
                    Color(0xFF806A5D)
                },

            maxLines = 1,

            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = if (option.extra_price == 0.0) {

                "+$0.00"

            } else {

                String.format(
                    Locale.US,
                    "+$%.2f",
                    option.extra_price
                )
            },

            fontSize =8.sp,

            fontWeight = FontWeight.Bold,

            color =
                if (selected) {
                    Color(0xFFE04A2A)
                } else {
                    Color(0xFF806A5D)
                }
        )
    }
}