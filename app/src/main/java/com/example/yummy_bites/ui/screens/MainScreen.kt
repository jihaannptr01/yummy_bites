package com.example.yummy_bites.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yummy_bites.ui.theme.BackgroundCream
import com.example.yummy_bites.ui.theme.PrimaryPink
import com.example.yummy_bites.ui.theme.PrimaryPinkLight
import com.example.yummy_bites.ui.theme.TextMuted
import com.example.yummy_bites.viewmodel.AppScreen
import com.example.yummy_bites.viewmodel.DessertViewModel

@Composable
fun MainScreen(
    viewModel: DessertViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val totalCartCount = uiState.cartItems.sumOf { it.quantity }

    Scaffold(
        containerColor = BackgroundCream,
        bottomBar = {
            if (uiState.selectedDessertForDetail == null && uiState.currentScreen != AppScreen.ORDER_SUCCESS) {
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 8.dp
                ) {
                    NavigationBarItem(
                        selected = uiState.currentScreen == AppScreen.HOME,
                        onClick = { viewModel.navigateToScreen(AppScreen.HOME) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home"
                            )
                        },
                        label = {
                            Text(
                                text = "Home",
                                fontSize = 11.sp,
                                fontWeight = if (uiState.currentScreen == AppScreen.HOME) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryPink,
                            selectedTextColor = PrimaryPink,
                            indicatorColor = PrimaryPinkLight,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        )
                    )

                    NavigationBarItem(
                        selected = uiState.currentScreen == AppScreen.FAVORITES,
                        onClick = { viewModel.navigateToScreen(AppScreen.FAVORITES) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Favorites"
                            )
                        },
                        label = {
                            Text(
                                text = "Favorites",
                                fontSize = 11.sp,
                                fontWeight = if (uiState.currentScreen == AppScreen.FAVORITES) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryPink,
                            selectedTextColor = PrimaryPink,
                            indicatorColor = PrimaryPinkLight,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        )
                    )

                    NavigationBarItem(
                        selected = uiState.currentScreen == AppScreen.CART,
                        onClick = { viewModel.navigateToScreen(AppScreen.CART) },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (totalCartCount > 0) {
                                        Badge(
                                            containerColor = PrimaryPink,
                                            contentColor = Color.White
                                        ) {
                                            Text("$totalCartCount")
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingBag,
                                    contentDescription = "My Cart"
                                )
                            }
                        },
                        label = {
                            Text(
                                text = "My Cart",
                                fontSize = 11.sp,
                                fontWeight = if (uiState.currentScreen == AppScreen.CART) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryPink,
                            selectedTextColor = PrimaryPink,
                            indicatorColor = PrimaryPinkLight,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
        ) {
            when (uiState.currentScreen) {
                AppScreen.HOME -> {
                    HomeScreen(
                        desserts = uiState.desserts,
                        selectedCategory = uiState.selectedCategory,
                        searchQuery = uiState.searchQuery,
                        onCategorySelected = { viewModel.selectCategory(it) },
                        onSearchQueryChanged = { viewModel.updateSearchQuery(it) },
                        onDessertClick = { viewModel.openDetail(it) },
                        onAddToCart = { item ->
                            viewModel.addToCart(item, 1)
                            Toast.makeText(context, "Added ${item.name} to cart! ♡", Toast.LENGTH_SHORT).show()
                        },
                        onFavoriteToggle = { viewModel.toggleFavorite(it.id) }
                    )
                }

                AppScreen.FAVORITES -> {
                    FavoritesScreen(
                        favorites = uiState.desserts.filter { it.isFavorite },
                        onDessertClick = { viewModel.openDetail(it) },
                        onFavoriteToggle = { viewModel.toggleFavorite(it.id) },
                        onBackToMenu = { viewModel.navigateToScreen(AppScreen.HOME) }
                    )
                }

                AppScreen.CART -> {
                    CartScreen(
                        cartItems = uiState.cartItems,
                        onUpdateQuantity = { id, delta -> viewModel.updateCartQuantity(id, delta) },
                        onRemoveItem = { id -> viewModel.removeFromCart(id) },
                        onCheckout = { viewModel.checkout() },
                        onBackToMenu = { viewModel.navigateToScreen(AppScreen.HOME) }
                    )
                }

                AppScreen.ORDER_SUCCESS -> {
                    OrderSuccessScreen(
                        receipt = uiState.lastOrderReceipt,
                        onBackToMenu = { viewModel.navigateToScreen(AppScreen.HOME) }
                    )
                }
            }

            // Detail Overlay Screen
            val detailItem = uiState.selectedDessertForDetail
            AnimatedVisibility(
                visible = detailItem != null,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundCream)
            ) {
                if (detailItem != null) {
                    DetailScreen(
                        dessert = detailItem,
                        quantity = uiState.detailQuantity,
                        onQuantityChange = { viewModel.setDetailQuantity(it) },
                        onAddToCart = { item, qty ->
                            viewModel.addToCart(item, qty)
                            Toast.makeText(context, "Added $qty x ${item.name} to cart! ♡", Toast.LENGTH_SHORT).show()
                        },
                        onBack = { viewModel.closeDetail() }
                    )
                }
            }
        }
    }
}
