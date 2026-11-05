package com.example.yummy_bites.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yummy_bites.data.DessertItem
import com.example.yummy_bites.ui.components.DessertCard
import com.example.yummy_bites.ui.components.SugarPopHeader
import com.example.yummy_bites.ui.theme.BorderSoft
import com.example.yummy_bites.ui.theme.PrimaryPink
import com.example.yummy_bites.ui.theme.TextDark
import com.example.yummy_bites.ui.theme.TextMuted

@Composable
fun HomeScreen(
    desserts: List<DessertItem>,
    selectedCategory: String,
    searchQuery: String,
    onCategorySelected: (String) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onDessertClick: (DessertItem) -> Unit,
    onAddToCart: (DessertItem) -> Unit,
    onFavoriteToggle: (DessertItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf("All", "Cake", "Pudding", "Drinks")

    val filteredDesserts = desserts.filter { item ->
        val matchesCategory = (selectedCategory == "All" || item.category.equals(selectedCategory, ignoreCase = true))
        val matchesSearch = searchQuery.isBlank() ||
                item.name.contains(searchQuery, ignoreCase = true) ||
                item.description.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        SugarPopHeader()

        Spacer(modifier = Modifier.height(4.dp))

        // Greeting
        Text(
            text = "Hi, Sweetie! ♡",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )
        Text(
            text = "Find a little treat to make your day sweeter.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
        )

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            placeholder = {
                Text("Find your sweet...", color = TextMuted, fontSize = 14.sp)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = PrimaryPink
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(26.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = PrimaryPink,
                unfocusedBorderColor = BorderSoft,
                cursorColor = PrimaryPink
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Category Filter Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(categories) { category ->
                val isSelected = category.equals(selectedCategory, ignoreCase = true)
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) PrimaryPink else Color.White,
                    border = if (isSelected) null else BorderStroke(1.dp, BorderSoft),
                    onClick = { onCategorySelected(category) }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 18.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = category,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else TextDark
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Section Title
        Text(
            text = "Today's little happiness ✨",
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Dessert Grid
        if (filteredDesserts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No sweet treats found ♡",
                    color = TextMuted,
                    fontSize = 14.sp
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredDesserts, key = { it.id }) { dessert ->
                    DessertCard(
                        dessert = dessert,
                        onCardClick = { onDessertClick(dessert) },
                        onAddToCart = { onAddToCart(dessert) },
                        onFavoriteToggle = { onFavoriteToggle(dessert) }
                    )
                }
            }
        }
    }
}
