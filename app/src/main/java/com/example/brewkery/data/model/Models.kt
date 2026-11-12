package com.example.brewkery.data.model

data class Meta(
    val app: String = "",
    val version: String = "",
    val tagline: String = "",
    val currency: String = "USD",
    val currency_symbol: String = "$",
    val delivery_fee: Double = 2.50,
    val tax_rate_percent: Double = 8.0,
    val estimated_delivery_time: String = "20 - 30 mins"
)

data class Category(
    val id: String = "",
    val name: String = "",
    val icon: String = "",
    val item_count: Int = 0
)

data class CustomizationOption(
    val id: String = "",
    val label: String? = null,
    val name: String? = null,
    val extra_price: Double = 0.0
) {
    val displayName: String get() = label ?: name.orEmpty()
}

data class Customizations(
    val sizes: List<CustomizationOption> = emptyList(),
    val sugar_levels: List<String> = emptyList(),
    val milk_options: List<CustomizationOption> = emptyList()
)

data class Product(
    val id: Int = 0,
    val category_id: String = "",
    val name: String = "",
    val tagline: String = "",
    val description: String = "",
    val base_price: Double = 0.0,
    val rating: Double = 0.0,
    val review_count: Int = 0,
    val prep_time: String = "",
    val calories: Int = 0,
    val image_url: String = "",
    val badge: String? = null,
    val ingredients: List<String> = emptyList(),
    val customizations: Customizations = Customizations()
)

data class MenuResponse(
    val meta: Meta = Meta(),
    val categories: List<Category> = emptyList(),
    val items: List<Product> = emptyList()
)

data class CartItem(
    val product: Product,
    val size: CustomizationOption,
    val milk: CustomizationOption,
    val sugar: String,
    val quantity: Int
) {
    val unitPrice: Double get() = product.base_price + size.extra_price + milk.extra_price
    val lineTotal: Double get() = unitPrice * quantity
    val key: String get() = "${product.id}_${size.id}_${milk.id}_$sugar"
}
