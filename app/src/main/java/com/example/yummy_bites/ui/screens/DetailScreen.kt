package com.example.yummy_bites.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.example.yummy_bites.ui.components.QuantityCounter
import com.example.yummy_bites.ui.components.RatingBadge
import com.example.yummy_bites.ui.components.SugarPopHeader
import com.example.yummy_bites.ui.theme.BorderSoft
import com.example.yummy_bites.ui.theme.PricePink
import com.example.yummy_bites.ui.theme.TextDark
import com.example.yummy_bites.ui.theme.TextMuted

@Composable
fun DetailScreen(
    dessert: DessertItem,
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    onAddToCart: (DessertItem, Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        SugarPopHeader()

        Spacer(modifier = Modifier.height(4.dp))

        // Screen title
        Text(
            text = "Sweet little detail",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        )
        Text(
            text = "A tiny treat, a big happy feeling ♡",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
        )

        // Back to menu link
        Row(
            modifier = Modifier
                .clickable { onBack() }
                .padding(vertical = 8.dp),
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

        // Detail Main Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.4f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Large Graphic
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    DessertGraphic(
                        type = dessert.type,
                        containerSize = 180.dp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Dessert Name + Emoji
                Text(
                    text = "${dessert.name} ${dessert.emoji}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Description
                Text(
                    text = dessert.description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    ),
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Price Tag
                Text(
                    text = dessert.formattedPrice,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PricePink
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Rating Badge
                RatingBadge(rating = dessert.rating)

                Spacer(modifier = Modifier.height(20.dp))

                // Quantity selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quantity",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDark
                    )

                    QuantityCounter(
                        quantity = quantity,
                        onDecrease = { if (quantity > 1) onQuantityChange(quantity - 1) },
                        onIncrease = { onQuantityChange(quantity + 1) }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Yummy! Add to Cart Button
                PrimaryPinkButton(
                    text = "Yummy! Add to Cart",
                    onClick = { onAddToCart(dessert, quantity) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
