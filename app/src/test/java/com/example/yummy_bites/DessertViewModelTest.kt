package com.example.yummy_bites

import com.example.yummy_bites.viewmodel.AppScreen
import com.example.yummy_bites.viewmodel.DessertViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DessertViewModelTest {

    private lateinit var viewModel: DessertViewModel

    @Before
    fun setUp() {
        viewModel = DessertViewModel()
    }

    @Test
    fun `initial state has default desserts and home screen`() {
        val state = viewModel.uiState.value
        assertEquals(4, state.desserts.size)
        assertEquals("All", state.selectedCategory)
        assertEquals(AppScreen.HOME, state.currentScreen)
        assertTrue(state.cartItems.isEmpty())
    }

    @Test
    fun `category filter update`() {
        viewModel.selectCategory("Cake")
        assertEquals("Cake", viewModel.uiState.value.selectedCategory)
    }

    @Test
    fun `toggle favorite status`() {
        val initialFavorite = viewModel.uiState.value.desserts.first { it.id == "3" }.isFavorite
        assertFalse(initialFavorite)

        viewModel.toggleFavorite("3")
        val updatedFavorite = viewModel.uiState.value.desserts.first { it.id == "3" }.isFavorite
        assertTrue(updatedFavorite)
    }

    @Test
    fun `add to cart increases quantity or creates item`() {
        val dessert = viewModel.uiState.value.desserts.first()
        viewModel.addToCart(dessert, 2)

        val cart = viewModel.uiState.value.cartItems
        assertEquals(1, cart.size)
        assertEquals(2, cart.first().quantity)
        assertEquals(dessert.id, cart.first().dessert.id)
    }

    @Test
    fun `checkout clears cart and creates receipt`() {
        val dessert = viewModel.uiState.value.desserts.first()
        viewModel.addToCart(dessert, 2)

        viewModel.checkout()

        val state = viewModel.uiState.value
        assertTrue(state.cartItems.isEmpty())
        assertEquals(AppScreen.ORDER_SUCCESS, state.currentScreen)
        assertNotNull(state.lastOrderReceipt)
        assertEquals(2 * 25000 + 5000, state.lastOrderReceipt?.totalPaid)
    }

    @Test
    fun `detail screen open and close`() {
        val dessert = viewModel.uiState.value.desserts.first()
        viewModel.openDetail(dessert)

        assertEquals(dessert, viewModel.uiState.value.selectedDessertForDetail)

        viewModel.closeDetail()
        assertNull(viewModel.uiState.value.selectedDessertForDetail)
    }
}
