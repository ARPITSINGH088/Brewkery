package com.example.brewkery.ui.screens
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brewkery.ui.Caramel
import com.example.brewkery.ui.Coffee
import com.example.brewkery.ui.CoffeeLight
import com.example.brewkery.ui.Cream
import com.example.brewkery.ui.Green

@Composable
fun OrderScreen(state: AppUiState, vm: AppViewModel, onBack: () -> Unit) {
    val ticket = state.orderTicket ?: "BK-${RandomPlaceholder}"
    Scaffold(containerColor = Cream) { pad ->
        Box(Modifier
            .fillMaxSize()
            .padding(pad), contentAlignment = Alignment.Center) {
            Column(Modifier.padding(28.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("☕", fontSize = 48.sp); Text(
                "Order Dispatched",
                color = Caramel,
                fontWeight = FontWeight.Bold
            ); Text(
                "Brewing in Progress!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Coffee,
                modifier = Modifier.padding(top = 8.dp)
            ); Text(
                "Your ticket was dispatched to our barista.",
                color = CoffeeLight,
                modifier = Modifier.padding(vertical = 12.dp)
            ); Text("Order Ticket #$ticket", fontWeight = FontWeight.Bold, color = Coffee); Spacer(
                Modifier.height(20.dp)
            ); AssistChip(
                onClick = {},
                label = { Text("PREPARING") }); Spacer(Modifier.height(14.dp)); Text(
                "Estimated Wait: 20 – 30 minutes",
                color = CoffeeLight
            ); Text(
                "Status: Barista accepted your order!",
                color = Green,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp)
            ); Spacer(Modifier.height(28.dp)); Button(
                onClick = onBack,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Coffee)
            ) { Text("Back to Menu") }
            }
        }
    }
}
private const val RandomPlaceholder = "74921"