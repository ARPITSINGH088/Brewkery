package com.example.brewkery.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brewkery.data.model.*
import com.example.brewkery.data.remote.RetrofitInstance
import com.example.brewkery.data.repository.BrewkeryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.random.Random

sealed interface LoadState {
    data object Loading : LoadState
    data class Success(val menu: MenuResponse) : LoadState
    data class Error(val message: String) : LoadState
}

data class AppUiState(
    val loadState: LoadState = LoadState.Loading,
    val selectedCategory: String? = null,
    val search: String = "",
    val cart: List<CartItem> = emptyList(),
    val orderTicket: String? = null,
    val detail: Product? = null,
    val detailLoading: Boolean = false,
    val detailError: String? = null
) {
    val subtotal: Double get() = cart.sumOf { it.lineTotal }
    val deliveryFee: Double get() = if (cart.isEmpty()) 0.0 else 2.50
    val tax: Double get() = subtotal * 0.08
    val total: Double get() = subtotal + deliveryFee + tax
    val itemCount: Int get() = cart.sumOf { it.quantity }

}

class AppViewModel(
    private val repository: BrewkeryRepository = BrewkeryRepository(RetrofitInstance.api)
) : ViewModel() {
    private val _state = MutableStateFlow(AppUiState())
    val state: StateFlow<AppUiState> = _state.asStateFlow()

    init { loadMenu() }

    fun loadMenu() {
        viewModelScope.launch {
            _state.update { it.copy(loadState = LoadState.Loading) }
            runCatching { repository.getMenu() }
                .onSuccess { menu -> _state.update { it.copy(loadState = LoadState.Success(menu)) } }
                .onFailure { e -> _state.update { it.copy(loadState = LoadState.Error(e.message ?: "Unable to load menu")) } }
        }
    }

    fun setCategory(categoryId: String?) = _state.update { it.copy(selectedCategory = categoryId) }
    fun setSearch(value: String) = _state.update { it.copy(search = value) }

    fun openDetail(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(detailLoading = true, detailError = null) }
            runCatching { repository.getItem(id) }
                .onSuccess { product -> _state.update { it.copy(detail = product, detailLoading = false) } }
                .onFailure { e -> _state.update { it.copy(detailLoading = false, detailError = e.message ?: "Unable to load item") } }
        }
    }

    fun clearDetail() = _state.update { it.copy(detail = null, detailError = null) }

    fun addToCart(product: Product, size: CustomizationOption, milk: CustomizationOption, sugar: String, quantity: Int) {
        val newItem = CartItem(product, size, milk, sugar, quantity)
        _state.update { current ->
            val index = current.cart.indexOfFirst { it.key == newItem.key }
            val updated = if (index >= 0) current.cart.mapIndexed { i, item ->
                if (i == index) item.copy(quantity = item.quantity + quantity) else item
            } else current.cart + newItem
            current.copy(cart = updated)
        }
    }

    fun changeQuantity(key: String, delta: Int) {
        _state.update { current ->
            val updated = current.cart.mapNotNull { item ->
                if (item.key != key) item else {
                    val q = item.quantity + delta
                    if (q <= 0) null else item.copy(quantity = q)
                }
            }
            current.copy(cart = updated)
        }
    }

    fun clearCart() = _state.update { it.copy(cart = emptyList()) }

    fun placeOrder(): String {
        val ticket = "BK-${Random.nextInt(10000, 99999)}"
        _state.update { it.copy(cart = emptyList(), orderTicket = ticket) }
        return ticket
    }

    fun dismissOrder() = _state.update { it.copy(orderTicket = null) }

    fun visibleProducts(): List<Product> {
        val success = _state.value.loadState as? LoadState.Success ?: return emptyList()
        return success.menu.items.filter { item ->
            val categoryMatch = _state.value.selectedCategory == null || item.category_id == _state.value.selectedCategory
            val q = _state.value.search.trim().lowercase(Locale.getDefault())
            val searchMatch = q.isBlank() || item.name.lowercase(Locale.getDefault()).contains(q) || item.tagline.lowercase(Locale.getDefault()).contains(q)
            categoryMatch && searchMatch
        }
    }
}
