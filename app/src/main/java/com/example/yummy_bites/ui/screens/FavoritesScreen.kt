package com.example.yummy_bites.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yummy_bites.data.DessertItem
import com.example.yummy_bites.ui.components.DessertGraphic
import com.example.yummy_bites.ui.components.PrimaryPinkButton
import com.example.yummy_bites.ui.components.SugarPopHeader
import com.example.yummy_bites.ui.theme.BorderSoft
import com.example.yummy_bites.ui.theme.PricePink
import com.example.yummy_bites.ui.theme.PrimaryPink
import com.example.yummy_bites.ui.theme.TextMuted

@Composable
fun FavoritesScreen(
    favorites: List<DessertItem>,
    onDessertClick: (DessertItem) -> Unit,
    onFavoriteToggle: (DessertItem) -> Unit,
    onBackToMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        SugarPopHeader()

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "My Favorite ♡",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        )
        Text(
            text = "Our Best Seller",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
        )

        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No favorites added yet ♡",
                    color = TextMuted,
                    fontSize = 14.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(favorites, key = { it.id }) { dessert ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onDessertClick(dessert) },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.4f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DessertGraphic(
                                type = dessert.type,
                                containerSize = 70.dp
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = dessert.name,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = dessert.formattedPrice,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PricePink
                                )
                            }

                            IconButton(
                                onClick = { onFavoriteToggle(dessert) }
                            ) {
                                Icon(
                                    imageVector = if (dessert.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = PrimaryPink
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Back to menu button
        PrimaryPinkButton(
            text = "Back to menu",
            onClick = onBackToMenu,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}
