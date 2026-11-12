package com.example.brewkery.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.brewkery.data.model.CustomizationOption
import com.example.brewkery.ui.Caramel
import com.example.brewkery.ui.Coffee
import com.example.brewkery.ui.CoffeeLight
import com.example.brewkery.ui.Cream
import com.example.brewkery.ui.components.OptionItem
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailScreen(
    state: AppUiState,
    vm: AppViewModel,
    onBack: () -> Unit,
    onCart: () -> Unit
) {

    val product = state.detail

    if (state.detailLoading || product == null) {

        Box(
            Modifier
                .fillMaxSize()
                .background(Cream),
            contentAlignment = Alignment.Center
        ) {

            if (state.detailError == null) {

                CircularProgressIndicator(
                    color = Caramel
                )

            } else {

                Text(
                    state.detailError
                )
            }
        }

        return
    }

    var sizeId by rememberSaveable(product.id) {
        mutableStateOf(
            product.customizations.sizes.firstOrNull()?.id
        )
    }

    var milkId by rememberSaveable(product.id) {
        mutableStateOf(
            product.customizations.milk_options.firstOrNull()?.id
        )
    }

    var sugar by rememberSaveable(product.id) {
        mutableStateOf(
            product.customizations.sugar_levels.firstOrNull().orEmpty()
        )
    }

    var quantity by rememberSaveable(product.id) {
        mutableIntStateOf(1)
    }

    val size = product.customizations.sizes.find {
        it.id == sizeId
    }

    val milk = product.customizations.milk_options.find {
        it.id == milkId
    }

    val unit =
        product.base_price +
                (size?.extra_price ?: 0.0) +
                (milk?.extra_price ?: 0.0)

    Scaffold(
        containerColor = Cream
    ) { pad ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            // ─────────────────────────────────────
            // TOP BAR
            // ─────────────────────────────────────

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp)
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(
                            width = 1.5.dp,
                            color = Color(0xFFEED8C9),
                            shape = CircleShape
                        )
                        .clickable {
                            onBack()
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF17120F),
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = "ITEM CUSTOMIZER",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF17120F)
                )

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(
                            width = 2.dp,
                            color = Color(0xFFEED8C9),
                            shape = CircleShape
                        )
                        .clickable {
                            // Favorite action
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = Color(0xFFFF4563),
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

            // ─────────────────────────────────────
            // PRODUCT IMAGE + BESTSELLER
            // ─────────────────────────────────────

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {

                AsyncImage(
                    model = product.image_url,
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(
                            RoundedCornerShape(20.dp)
                        ),
                    contentScale = ContentScale.Crop
                )

                product.badge?.let {

                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .width(110.dp)
                            .padding(
                                start = 8.dp,
                                top = 10.dp
                            )
                            .clip(
                                RoundedCornerShape(20.dp)
                            )
                            .background(
                                Color(0xCC29231F)
                            )
                            .padding(
                                horizontal = 10.dp,
                                vertical = 4.dp
                            )
                    ) {

                        Text(
                            text = "BESTSELLER",
                            color = Color(0xFFFFA800),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(
                Modifier.height(24.dp)
            )

            // ─────────────────────────────────────
            // PRODUCT NAME + PRICE
            // ─────────────────────────────────────

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = product.name,
                    modifier = Modifier.weight(1f),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF17120F),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(
                    Modifier.width(12.dp)
                )

                Text(
                    text = String.format(
                        Locale.US,
                        "$%.2f",
                        product.base_price
                    ),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFE64A2E)
                )
            }

            Spacer(
                Modifier.height(2.dp)
            )

            // ─────────────────────────────────────
            // RATING
            // ─────────────────────────────────────

            Text(
                text = "★ ${product.rating} • ${product.review_count} reviews • ${product.prep_time}",
                modifier = Modifier.padding(
                    horizontal = 20.dp
                ),
                color = CoffeeLight,
                fontSize = 12.sp
            )

            Spacer(
                Modifier.height(8.dp)
            )

            // ─────────────────────────────────────
            // DESCRIPTION
            // ─────────────────────────────────────

            Text(
                product.description,
                modifier = Modifier.padding(
                    horizontal = 20.dp
                ),
                color = CoffeeLight,
                lineHeight = 17.sp
            )

            Spacer(
                Modifier.height(18.dp)
            )

            // ─────────────────────────────────────
            // KEY INGREDIENTS
            // ─────────────────────────────────────

            Text(
                "Key Ingredients",
                modifier = Modifier.padding(
                    horizontal = 20.dp
                ),
                fontWeight = FontWeight.Bold,
                color = Coffee
            )

            Spacer(
                Modifier.height(8.dp)
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                product.ingredients.forEach { ingredient ->

                    Box(
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .border(
                                width = 1.5.dp,
                                color = Color(0xFFE8D1C2),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .background(Color.White)
                            .padding(
                                horizontal = 16.dp,
                                vertical = 5.dp
                            )
                    ) {

                        Text(
                            text = ingredient,
                            fontSize = 12.sp,
                            color = CoffeeLight,
                            maxLines = 1
                        )
                    }
                }
            }

            // ─────────────────────────────────────
            // SIZE SELECTION
            // ─────────────────────────────────────

            OptionSection(
                title = "Size Selection",
                options = product.customizations.sizes,
                selected = size
            ) {
                sizeId = it.id
            }

            // ─────────────────────────────────────
            // MILK OPTIONS
            // ─────────────────────────────────────

            OptionSection(
                title = "Milk Options / Spreads",
                options = product.customizations.milk_options,
                selected = milk
            ) {
                milkId = it.id
            }

            // ─────────────────────────────────────
            // SUGAR LEVELS
            // ─────────────────────────────────────

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 8.dp,
                        vertical = 10.dp
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(
                    width = 1.5.dp,
                    color = Color(0xFFE8D1C2)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 0.dp
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {

                    Text(
                        text = "Sugar Levels / Serving",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF17120F)
                    )

                    Spacer(
                        Modifier.height(14.dp)
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        items(
                            product.customizations.sugar_levels
                        ) { option ->

                            val selected = sugar == option

                            Box(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(14.dp)
                                    )
                                    .background(
                                        if (selected) {
                                            Color(0xFF160B08)
                                        } else {
                                            Color.White
                                        }
                                    )
                                    .border(
                                        width = 1.5.dp,
                                        color = if (selected) {
                                            Color(0xFF160B08)
                                        } else {
                                            Color(0xFFE8D1C2)
                                        },
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .clickable {
                                        sugar = option
                                    }
                                    .padding(
                                        horizontal = 18.dp,
                                        vertical = 5.dp
                                    )
                            ) {

                                Text(
                                    text = option,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (selected) {
                                        Color.White
                                    } else {
                                        Color(0xFF806A5D)
                                    },
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }

            Spacer(
                Modifier.height(18.dp)
            )

            // ─────────────────────────────────────
            // QUANTITY + ADD TO CART
            // ─────────────────────────────────────

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // QUANTITY BOX

                Row(
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                        .clip(
                            RoundedCornerShape(15.dp)
                        )
                        .background(Color.White)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE8D1C2),
                            shape = RoundedCornerShape(15.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    IconButton(
                        onClick = {
                            if (quantity > 1) {
                                quantity--
                            }
                        }
                    ) {

                        Text(
                            text = "−",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF17120F)
                        )
                    }

                    Text(
                        text = quantity.toString(),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF17120F)
                    )

                    IconButton(
                        onClick = {
                            quantity++
                        }
                    ) {

                        Text(
                            text = "+",
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF17120F)
                        )
                    }
                }

                Spacer(
                    Modifier.width(10.dp)
                )

                // ADD TO CART BUTTON

                Button(
                    onClick = {

                        vm.addToCart(
                            product,
                            size ?: CustomizationOption(),
                            milk ?: CustomizationOption(),
                            sugar,
                            quantity
                        )

                        onCart()
                    },

                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),

                    shape = RoundedCornerShape(15.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD94824),
                        contentColor = Color.White
                    ),

                    contentPadding = PaddingValues(
                        horizontal = 10.dp
                    )
                ) {

                    Text(
                        text = "Add to Cart",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(
                        Modifier.width(10.dp)
                    )

                    Text(
                        text = "•",
                        fontSize = 20.sp
                    )

                    Spacer(
                        Modifier.width(10.dp)
                    )

                    Text(
                        text = "$${String.format(
                            Locale.US,
                            "%.2f",
                            unit * quantity
                        )}",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(
                Modifier.height(10.dp)
            )
        }
    }
}

@Composable
fun OptionSection(
    title: String,
    options: List<CustomizationOption>,
    selected: CustomizationOption?,
    onSelected: (CustomizationOption) -> Unit
) {

    if (options.isEmpty()) return

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 10.dp
            ),

        shape = RoundedCornerShape(22.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        border = BorderStroke(
            width = 1.5.dp,
            color = Color(0xFFE8D1C2)
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF17120F)
            )

            Spacer(
                Modifier.height(5.dp)
            )

            // SIZE = HORIZONTAL OPTIONS
            if (title == "Size Selection") {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    options.forEach { option ->

                        OptionItem(
                            option = option,
                            selected = selected?.id == option.id,
                            onClick = {
                                onSelected(option)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

            }

            // MILK = VERTICAL OPTIONS
            else {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    options.forEach { option ->

                        OptionItem(
                            option = option,
                            selected = selected?.id == option.id,
                            onClick = {
                                onSelected(option)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}