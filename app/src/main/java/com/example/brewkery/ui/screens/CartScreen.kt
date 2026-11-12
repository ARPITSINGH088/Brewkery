@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.brewkery.ui.screens

import com.example.brewkery.ui.components.CartItemRow
import com.example.brewkery.ui.components.SummaryRow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brewkery.ui.Coffee
import com.example.brewkery.ui.Cream
import java.util.Locale

@Composable
fun CartScreen(state: AppUiState, vm: AppViewModel, onBack: () -> Unit, onPlaceOrder: () -> Unit) {
    Scaffold(
        containerColor = Cream,
        topBar = {
            TopAppBar(
                title = { Text("Your Cart", color = Coffee) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            null,
                            tint = Coffee
                        )
                    }
                },
                actions = {
                    if (state.cart.isNotEmpty()) TextButton(onClick = vm::clearCart) {
                        Text("Clear Cart")
                    }
                })
        }) { pad ->
        if (state.cart.isEmpty()) Box(
            Modifier
                .fillMaxSize()
                .padding(pad),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Your cart is empty",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Coffee
                ); Spacer(Modifier.height(10.dp)); Button(onClick = onBack) { Text("Browse Menu") }
            }
        }
        else Column(Modifier
            .fillMaxSize()
            .padding(pad)
            .padding(horizontal = 20.dp)) {
            LazyColumn(
                Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.cart, key = { it.key }) { item ->
                    CartItemRow(
                        item,
                        onMinus = { vm.changeQuantity(item.key, -1) },
                        onPlus = { vm.changeQuantity(item.key, 1) })
                }
            }
            SummaryRow("Subtotal", state.subtotal); SummaryRow(
            "Delivery Fee",
            state.deliveryFee
        ); SummaryRow("Est. Tax (8.0%)", state.tax)
            HorizontalDivider(Modifier.padding(vertical = 8.dp)); SummaryRow(
            "Total Payable",
            state.total,
            true
        )
            Button(
                onClick = onPlaceOrder,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp)
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Coffee)
            ) { Text("Place Order Now • $${String.format(Locale.US, "%.2f", state.total)}") }
        }
    }
}