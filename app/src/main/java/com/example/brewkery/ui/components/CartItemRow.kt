package com.example.brewkery.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.brewkery.data.model.CartItem
import com.example.brewkery.ui.Card
import com.example.brewkery.ui.Coffee
import com.example.brewkery.ui.CoffeeLight
import java.util.Locale

@Composable
fun CartItemRow(item: CartItem, onMinus: () -> Unit, onPlus: () -> Unit) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Card)
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = item.product.image_url,
                contentDescription = item.product.name,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(14.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(10.dp)); Column(Modifier.weight(1f)) {
            Text(
                item.product.name,
                fontWeight = FontWeight.Bold,
                color = Coffee,
                maxLines = 2
            ); Text(
            "${item.size.displayName} • ${item.milk.displayName}",
            fontSize = 10.sp,
            color = CoffeeLight
        ); Text(
            "$${String.format(Locale.US, "%.2f", item.lineTotal)}",
            fontWeight = FontWeight.Bold,
            color = Coffee
        )
        }
            IconButton(onClick = onMinus) {
                Icon(
                    Icons.Default.Remove,
                    null
                )
            }; Text(item.quantity.toString()); IconButton(onClick = onPlus) {
            Icon(
                Icons.Default.Add,
                null
            )
        }
        }
    }
}


