package com.example.brewkery.ui.screens

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brewkery.ui.components.CartSummaryBar
import com.example.brewkery.ui.components.CategoryChip
import com.example.brewkery.ui.components.ErrorView
import com.example.brewkery.ui.components.Header
import com.example.brewkery.ui.components.ProductCard
import com.example.brewkery.ui.components.StoreInfoCard
import com.example.brewkery.ui.Caramel
import com.example.brewkery.ui.Coffee
import com.example.brewkery.ui.Cream

@Composable
fun HomeScreen(
    state: AppUiState,
    vm: AppViewModel,
    onProduct: (Int) -> Unit,
    onCart: () -> Unit,
    onOrder: () -> Unit
) {
    val menu = (state.loadState as? LoadState.Success)?.menu

    Scaffold(
        containerColor = Cream
    ) { pad ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(pad)
        ) {

            // ─────────────────────────────────────
            // HEADER
            // ─────────────────────────────────────

            Header(
                cartCount = state.itemCount,
                onCart = onCart,
                orderActive = state.orderTicket != null,
                orderTicket = state.orderTicket,
                onTrack = onOrder
            )

            // ─────────────────────────────────────
            // SEARCH
            // ─────────────────────────────────────

            OutlinedTextField(
                value = state.search,
                onValueChange = vm::setSearch,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 7.dp
                    ),
                placeholder = {
                    Text(
                        "Search roast, cold brew, pastry..."
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        null
                    )
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Caramel,
                    unfocusedBorderColor = Caramel,
                    focusedTextColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(18.dp)
            )

            Spacer(
                Modifier.height(5.dp)
            )

            // ─────────────────────────────────────
            // CATEGORIES
            // ─────────────────────────────────────

            LazyRow(
                contentPadding = PaddingValues(
                    horizontal = 20.dp,
                    vertical = 0.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                item {

                    CategoryChip(
                        "All Items",
                        "all",
                        state.selectedCategory == null
                    ) {
                        vm.setCategory(null)
                    }
                }

                items(
                    menu?.categories ?: emptyList()
                ) { c ->

                    CategoryChip(
                        "${c.icon} ${c.name}",
                        c.id,
                        state.selectedCategory == c.id
                    ) {
                        vm.setCategory(c.id)
                    }
                }
            }

            Spacer(
                Modifier.height(12.dp)
            )

            // ─────────────────────────────────────
            // ACTIVE ORDER
            // ─────────────────────────────────────


            // ─────────────────────────────────────
            // CONTENT
            // ─────────────────────────────────────

            when (val load = state.loadState) {

                // ─────────────────────────────
                // LOADING
                // ─────────────────────────────

                LoadState.Loading -> {

                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        CircularProgressIndicator(
                            color = Caramel
                        )
                    }
                }

                // ─────────────────────────────
                // ERROR
                // ─────────────────────────────

                is LoadState.Error -> {

                    ErrorView(
                        load.message
                    ) {
                        vm.loadMenu()
                    }
                }

                // ─────────────────────────────
                // SUCCESS
                // ─────────────────────────────

                is LoadState.Success -> {

                    val products = vm.visibleProducts()

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 20.dp,
                            end = 20.dp,
                            top = 20.dp,
                            bottom = 30.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {

                        // MENU TITLE

                        item {

                            Text(
                                "Our Menu",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Coffee
                            )
                        }

                        // PRODUCTS

                        items(products) {

                            ProductCard(it) {
                                onProduct(it.id)
                            }
                        }

                        // EMPTY STATE

                        if (products.isEmpty()) {

                            item {

                                Text(
                                    "No items found.",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        // ─────────────────────────
                        // CART SUMMARY
                        // ─────────────────────────
                        // Appears AFTER all products
                        // and scrolls with the list.

                        if (state.itemCount > 0) {

                            item {

                                Spacer(
                                    Modifier.height(8.dp)
                                )

                                CartSummaryBar(
                                    itemCount = state.itemCount,
                                    total = state.total,
                                    onCartClick = onCart
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}