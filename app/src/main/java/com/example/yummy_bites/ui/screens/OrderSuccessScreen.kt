package com.example.yummy_bites.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.yummy_bites.ui.components.OrderCompleteIllustration
import com.example.yummy_bites.ui.components.PrimaryPinkButton
import com.example.yummy_bites.ui.components.SugarPopHeader
import com.example.yummy_bites.ui.theme.BorderSoft
import com.example.yummy_bites.ui.theme.PricePink
import com.example.yummy_bites.ui.theme.TextDark
import com.example.yummy_bites.ui.theme.TextMuted
import com.example.yummy_bites.ui.util.toRupiah
import com.example.yummy_bites.viewmodel.OrderReceipt

@Composable
fun OrderSuccessScreen(
    receipt: OrderReceipt?,
    onBackToMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SugarPopHeader()

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Yay! Order Complete!",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            ),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Your sweet treats are ready to make you happy.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 2.dp, bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Center Illustration Card with fixed 180.dp diameter
        OrderCompleteIllustration(
            containerSize = 180.dp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Thank you, Sweetie! ♡",
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Your order is wrapped with love.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp),
            textAlign = TextAlign.Center
        )

        // Order details receipt card
        if (receipt != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.4f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Order number", color = TextMuted, fontSize = 14.sp)
                        Text(
                            text = receipt.orderNumber,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = TextDark
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total paid", color = TextMuted, fontSize = 14.sp)
                        Text(
                            text = receipt.totalPaid.toRupiah(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = PricePink
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Action Button
        PrimaryPinkButton(
            text = "Back to Menu",
            onClick = onBackToMenu,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}
