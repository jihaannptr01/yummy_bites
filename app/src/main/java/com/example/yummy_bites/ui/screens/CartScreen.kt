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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yummy_bites.data.CartItem
import com.example.yummy_bites.ui.components.DessertGraphic
import com.example.yummy_bites.ui.components.PrimaryPinkButton
import com.example.yummy_bites.ui.components.QuantityCounter
import com.example.yummy_bites.ui.components.SugarPopHeader
import com.example.yummy_bites.ui.theme.BorderSoft
import com.example.yummy_bites.ui.theme.DividerColor
import com.example.yummy_bites.ui.theme.PricePink
import com.example.yummy_bites.ui.theme.TextDark
import com.example.yummy_bites.ui.theme.TextMuted
import com.example.yummy_bites.ui.util.toRupiah

@Composable
fun CartScreen(
    cartItems: List<CartItem>,
    onUpdateQuantity: (String, Int) -> Unit,
    onRemoveItem: (String) -> Unit,
    onCheckout: () -> Unit,
    onBackToMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    val subtotal = cartItems.sumOf { it.dessert.price * it.quantity }
    val deliveryFee = if (cartItems.isNotEmpty()) 5000 else 0
    val total = subtotal + deliveryFee

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        SugarPopHeader()

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "My Sweet Cart ♡",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        )
        Text(
            text = "Your happiness is almost on its way.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
        )

        // Back to menu link
        Row(
            modifier = Modifier
                .clickable { onBackToMenu() }
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = TextDark,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "Back to menu",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = TextDark
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Your cart is empty! Add some sweet treats ♡",
                    color = TextMuted,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 12.dp)
            ) {
                items(cartItems, key = { it.dessert.id }) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.4f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Top
                            ) {
                                DessertGraphic(
                                    type = item.dessert.type,
                                    containerSize = 64.dp
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = item.dessert.name,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = item.dessert.formattedPrice,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PricePink
                                    )
                                }

                                IconButton(
                                    onClick = { onRemoveItem(item.dessert.id) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Remove",
                                        tint = TextMuted,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Quantity Selector bottom right of card
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                QuantityCounter(
                                    quantity = item.quantity,
                                    onDecrease = { onUpdateQuantity(item.dessert.id, -1) },
                                    onIncrease = { onUpdateQuantity(item.dessert.id, 1) }
                                )
                            }
                        }
                    }
                }

                // Order Summary Card
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.4f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Order summary ✨",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Subtotal", color = TextMuted, fontSize = 13.sp)
                                Text(subtotal.toRupiah(), fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark)
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Delivery", color = TextMuted, fontSize = 13.sp)
                                Text(deliveryFee.toRupiah(), fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark)
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            HorizontalDivider(color = DividerColor, thickness = 1.dp)
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Total", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextDark)
                                Text(total.toRupiah(), fontWeight = FontWeight.Bold, fontSize = 15.sp, color = PricePink)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Action Checkout Button
        PrimaryPinkButton(
            text = "Let's Checkout! 🍒",
            onClick = onCheckout,
            enabled = cartItems.isNotEmpty()
        )

        Text(
            text = "A little sweetness goes a long way ♡",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}
