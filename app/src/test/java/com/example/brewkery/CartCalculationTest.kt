package com.example.brewkery

import com.example.brewkery.data.model.*
import org.junit.Assert.assertEquals
import org.junit.Test

class CartCalculationTest {
    @Test
    fun cartTotals_areCalculatedCorrectly() {
        val product = Product(id = 1, name = "Test Coffee", base_price = 4.85)
        val size = CustomizationOption(id = "m", label = "Grande", extra_price = 0.65)
        val milk = CustomizationOption(id = "a", name = "Almond", extra_price = 0.50)
        val item = CartItem(product, size, milk, "Regular", 2)
        val subtotal = item.lineTotal
        val tax = subtotal * 0.08
        val total = subtotal + 2.50 + tax
        assertEquals(12.0, subtotal, 0.001)
        assertEquals(0.96, tax, 0.001)
        assertEquals(15.46, total, 0.001)
    }
}
