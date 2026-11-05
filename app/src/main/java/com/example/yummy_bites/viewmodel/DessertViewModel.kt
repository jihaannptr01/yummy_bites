package com.example.yummy_bites.viewmodel

import androidx.lifecycle.ViewModel
import com.example.yummy_bites.data.CartItem
import com.example.yummy_bites.data.DessertItem
import com.example.yummy_bites.data.DessertType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

enum class AppScreen {
    HOME,
    FAVORITES,
    CART,
    ORDER_SUCCESS
}

data class OrderReceipt(
    val orderNumber: String,
    val subtotal: Int,
    val deliveryFee: Int,
    val totalPaid: Int
)

data class DessertUiState(
    val desserts: List<DessertItem> = initialDesserts,
    val selectedCategory: String = "All",
    val searchQuery: String = "",
    val cartItems: List<CartItem> = emptyList(),
    val selectedDessertForDetail: DessertItem? = null,
    val detailQuantity: Int = 1,
    val currentScreen: AppScreen = AppScreen.HOME,
    val lastOrderReceipt: OrderReceipt? = null
)

private val initialDesserts = listOf(
    DessertItem(
        id = "1",
        name = "Strawberry Cloud Cake",
        category = "Cake",
        price = 25000,
        rating = 4.9,
        description = "Soft vanilla sponge, fluffy cream, and sweet strawberries on top.",
        emoji = "🍓",
        isFavorite = true,
        type = DessertType.STRAWBERRY_CAKE
    ),
    DessertItem(
        id = "2",
        name = "Choco Bear Pudding",
        category = "Pudding",
        price = 15000,
        rating = 4.9,
        description = "A silky chocolate pudding with a cute bear topping. Rich, creamy, and full of chocolatey happiness!",
        emoji = "🧸",
        isFavorite = true,
        type = DessertType.CHOCO_PUDDING
    ),
    DessertItem(
        id = "3",
        name = "Caramel Milk Tea",
        category = "Drinks",
        price = 18000,
        rating = 4.9,
        description = "A classic milk tea with rich caramel flavor and chewy tapioca pearls. Sweet, smooth, and always a good idea.",
        emoji = "🧋",
        isFavorite = false,
        type = DessertType.CARAMEL_TEA
    ),
    DessertItem(
        id = "4",
        name = "Donuts",
        category = "Cake",
        price = 15000,
        rating = 4.9,
        description = "A soft, fluffy donut topped with delicious glazes and delightful toppings.",
        emoji = "🍩",
        isFavorite = false,
        type = DessertType.PINK_DONUT
    )
)

class DessertViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DessertUiState())
    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()

    fun selectCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun toggleFavorite(dessertId: String) {
        _uiState.update { state ->
            val updatedList = state.desserts.map { dessert ->
                if (dessert.id == dessertId) {
                    dessert.copy(isFavorite = !dessert.isFavorite)
                } else dessert
            }
            state.copy(desserts = updatedList)
        }
    }

    fun openDetail(dessert: DessertItem) {
        _uiState.update {
            it.copy(
                selectedDessertForDetail = dessert,
                detailQuantity = 1
            )
        }
    }

    fun closeDetail() {
        _uiState.update { it.copy(selectedDessertForDetail = null) }
    }

    fun setDetailQuantity(quantity: Int) {
        if (quantity >= 1) {
            _uiState.update { it.copy(detailQuantity = quantity) }
        }
    }

    fun addToCart(dessert: DessertItem, quantity: Int = 1) {
        _uiState.update { state ->
            val existingIndex = state.cartItems.indexOfFirst { it.dessert.id == dessert.id }
            val newCart = if (existingIndex >= 0) {
                state.cartItems.mapIndexed { index, cartItem ->
                    if (index == existingIndex) {
                        cartItem.copy(quantity = cartItem.quantity + quantity)
                    } else cartItem
                }
            } else {
                state.cartItems + CartItem(dessert, quantity)
            }
            state.copy(cartItems = newCart, selectedDessertForDetail = null)
        }
    }

    fun updateCartQuantity(dessertId: String, delta: Int) {
        _uiState.update { state ->
            val newCart = state.cartItems.mapNotNull { item ->
                if (item.dessert.id == dessertId) {
                    val newQty = item.quantity + delta
                    if (newQty > 0) item.copy(quantity = newQty) else null
                } else item
            }
            state.copy(cartItems = newCart)
        }
    }

    fun removeFromCart(dessertId: String) {
        _uiState.update { state ->
            val newCart = state.cartItems.filterNot { it.dessert.id == dessertId }
            state.copy(cartItems = newCart)
        }
    }

    fun checkout() {
        val state = _uiState.value
        if (state.cartItems.isEmpty()) return

        val subtotal = state.cartItems.sumOf { it.dessert.price * it.quantity }
        val deliveryFee = 5000
        val totalPaid = subtotal + deliveryFee
        val orderNumber = "SP-${Random.nextInt(100, 999)}"

        val receipt = OrderReceipt(
            orderNumber = orderNumber,
            subtotal = subtotal,
            deliveryFee = deliveryFee,
            totalPaid = totalPaid
        )

        _uiState.update {
            it.copy(
                cartItems = emptyList(),
                lastOrderReceipt = receipt,
                currentScreen = AppScreen.ORDER_SUCCESS
            )
        }
    }

    fun navigateToScreen(screen: AppScreen) {
        _uiState.update {
            it.copy(
                currentScreen = screen,
                selectedDessertForDetail = null
            )
        }
    }
}
