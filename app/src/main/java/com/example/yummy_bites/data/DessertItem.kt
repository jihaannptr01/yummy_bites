package com.example.yummy_bites.data

import com.example.yummy_bites.ui.util.toRupiah

enum class DessertType {
    STRAWBERRY_CAKE,
    CHOCO_PUDDING,
    CARAMEL_TEA,
    PINK_DONUT
}

data class DessertItem(
    val id: String,
    val name: String,
    val category: String,
    val price: Int,
    val rating: Double = 4.9,
    val description: String,
    val emoji: String,
    val isFavorite: Boolean = false,
    val type: DessertType
) {
    val formattedPrice: String get() = price.toRupiah()
}

data class CartItem(
    val dessert: DessertItem,
    val quantity: Int
)
