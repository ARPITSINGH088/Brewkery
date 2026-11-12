@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.brewkery
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border

import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.brewkery.data.model.*
import com.example.brewkery.ui.*
import com.example.brewkery.ui.screens.AppViewModel
import com.example.brewkery.ui.screens.CartScreen
import com.example.brewkery.ui.screens.DetailScreen
import com.example.brewkery.ui.screens.HomeScreen
import com.example.brewkery.ui.screens.OrderScreen
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { BrewkeryApp() }
    }
}

@Composable
fun BrewkeryApp(vm: AppViewModel = viewModel()) {
    val nav = rememberNavController()
    val state by vm.state.collectAsState()
    MaterialTheme(
        colorScheme = lightColorScheme(
            background = Cream,
            surface = Card,
            primary = Coffee,
            secondary = Caramel
        )
    ) {
        NavHost(navController = nav, startDestination = "home") {
            composable("home") {
                HomeScreen(
                    state,
                    vm,
                    onProduct = { id -> nav.navigate("detail/$id") },
                    onCart = { nav.navigate("cart") },
                    onOrder = { nav.navigate("order") })
            }
            composable(
                "detail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStack ->
                val id = backStack.arguments?.getInt("id") ?: 1
                LaunchedEffect(id) { vm.openDetail(id) }
                DetailScreen(
                    state,
                    vm,
                    onBack = { vm.clearDetail(); nav.popBackStack() },
                    onCart = { nav.navigate("cart") })
            }
            composable("cart") {
                CartScreen(
                    state,
                    vm,
                    onBack = { nav.popBackStack() },
                    onPlaceOrder = { vm.placeOrder(); nav.navigate("order") })
            }
            composable("order") {
                OrderScreen(
                    state = state,
                    vm = vm,
                    onBack = {
                        nav.navigate("home") {
                            popUpTo("home") {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}





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



