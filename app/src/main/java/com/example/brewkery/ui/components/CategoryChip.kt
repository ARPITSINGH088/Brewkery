package com.example.brewkery.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brewkery.ui.Coffee

@Composable
fun CategoryChip(
    label: String,
    id: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,

        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color.White,

            selectedContainerColor = Color.Black,

            labelColor = Coffee,

            selectedLabelColor = Color.White,

            iconColor = Coffee,

            selectedLeadingIconColor = Color.White,

            selectedTrailingIconColor = Color.White
        ),

        label = {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = if (selected) {
                    FontWeight.Bold
                } else {
                    FontWeight.Medium
                }
            )
        },

        shape = RoundedCornerShape(20.dp),

        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = if (selected) {
                Color.Black
            } else {
                Color(0xFFE5D5CA)
            }
        )
    )
}