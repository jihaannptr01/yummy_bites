package com.example.yummy_bites.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.yummy_bites.data.DessertType

@Composable
fun DessertGraphic(
    type: DessertType,
    modifier: Modifier = Modifier,
    containerSize: Dp = 120.dp
) {
    Box(
        modifier = modifier
            .size(containerSize)
            .clip(RoundedCornerShape(16.dp))
            .background(
                when (type) {
                    DessertType.STRAWBERRY_CAKE -> Color(0xFFFFF0F5)
                    DessertType.CHOCO_PUDDING -> Color(0xFFFAF0E6)
                    DessertType.CARAMEL_TEA -> Color(0xFFFFF8DC)
                    DessertType.PINK_DONUT -> Color(0xFFFFF0F3)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        when (type) {
            DessertType.STRAWBERRY_CAKE -> StrawberryCakeDrawing(Modifier.fillMaxSize().padding(12.dp))
            DessertType.CHOCO_PUDDING -> ChocoPuddingDrawing(Modifier.fillMaxSize().padding(12.dp))
            DessertType.CARAMEL_TEA -> CaramelTeaDrawing(Modifier.fillMaxSize().padding(12.dp))
            DessertType.PINK_DONUT -> PinkDonutDrawing(Modifier.fillMaxSize().padding(12.dp))
        }
    }
}

@Composable
fun StrawberryCakeDrawing(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Plate
        drawOval(
            color = Color(0xFFE0E0E0),
            topLeft = Offset(w * 0.1f, h * 0.75f),
            size = Size(w * 0.8f, h * 0.2f)
        )

        // Cake base slice
        val cakePath = Path().apply {
            moveTo(w * 0.2f, h * 0.75f)
            lineTo(w * 0.8f, h * 0.75f)
            lineTo(w * 0.75f, h * 0.35f)
            lineTo(w * 0.25f, h * 0.35f)
            close()
        }
        drawPath(cakePath, color = Color(0xFFFFF3E0)) // Sponge

        // Cake layers (jam)
        drawRect(
            color = Color(0xFFEC407A),
            topLeft = Offset(w * 0.22f, h * 0.55f),
            size = Size(w * 0.56f, h * 0.05f)
        )

        // Frosting top
        drawRoundRect(
            color = Color.White,
            topLeft = Offset(w * 0.23f, h * 0.3f),
            size = Size(w * 0.54f, h * 0.1f),
            cornerRadius = CornerRadius(12f, 12f)
        )

        // Strawberry on top
        drawCircle(
            color = Color(0xFFE53935),
            radius = w * 0.12f,
            center = Offset(w * 0.5f, h * 0.25f)
        )
        // Strawberry leaf
        drawCircle(
            color = Color(0xFF4CAF50),
            radius = w * 0.04f,
            center = Offset(w * 0.5f, h * 0.13f)
        )
    }
}

@Composable
fun ChocoPuddingDrawing(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Cup/Bowl
        val cupPath = Path().apply {
            moveTo(w * 0.2f, h * 0.35f)
            lineTo(w * 0.25f, h * 0.8f)
            quadraticTo(w * 0.5f, h * 0.9f, w * 0.75f, h * 0.8f)
            lineTo(w * 0.8f, h * 0.35f)
            close()
        }
        drawPath(cupPath, color = Color(0xFFFFFFFF))
        drawPath(cupPath, color = Color(0xFFD7CCC8), style = Stroke(width = 4f))

        // Chocolate pudding surface
        drawOval(
            color = Color(0xFF4E342E),
            topLeft = Offset(w * 0.2f, h * 0.3f),
            size = Size(w * 0.6f, h * 0.15f)
        )

        // Bear Topping - Bear head
        drawCircle(
            color = Color(0xFF6D4C41),
            radius = w * 0.14f,
            center = Offset(w * 0.5f, h * 0.25f)
        )
        // Ears
        drawCircle(
            color = Color(0xFF6D4C41),
            radius = w * 0.05f,
            center = Offset(w * 0.38f, h * 0.13f)
        )
        drawCircle(
            color = Color(0xFF6D4C41),
            radius = w * 0.05f,
            center = Offset(w * 0.62f, h * 0.13f)
        )
        // Bear Snout
        drawCircle(
            color = Color(0xFFD7CCC8),
            radius = w * 0.06f,
            center = Offset(w * 0.5f, h * 0.28f)
        )
        // Nose
        drawCircle(
            color = Color(0xFF3E2723),
            radius = w * 0.02f,
            center = Offset(w * 0.5f, h * 0.27f)
        )
    }
}

@Composable
fun CaramelTeaDrawing(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Glass Body
        val glassPath = Path().apply {
            moveTo(w * 0.3f, h * 0.2f)
            lineTo(w * 0.33f, h * 0.85f)
            quadraticTo(w * 0.5f, h * 0.9f, w * 0.67f, h * 0.85f)
            lineTo(w * 0.7f, h * 0.2f)
            close()
        }
        drawPath(glassPath, color = Color(0xFFD7CCC8)) // Liquid base

        // Caramel Layer Top
        drawRect(
            color = Color(0xFF8D6E63),
            topLeft = Offset(w * 0.3f, h * 0.2f),
            size = Size(w * 0.4f, h * 0.15f)
        )

        // Cream / Foam Top
        drawRoundRect(
            color = Color.White,
            topLeft = Offset(w * 0.28f, h * 0.15f),
            size = Size(w * 0.44f, h * 0.08f),
            cornerRadius = CornerRadius(8f, 8f)
        )

        // Boba Pearls at bottom
        drawCircle(color = Color(0xFF3E2723), radius = w * 0.035f, center = Offset(w * 0.4f, h * 0.78f))
        drawCircle(color = Color(0xFF3E2723), radius = w * 0.035f, center = Offset(w * 0.5f, h * 0.81f))
        drawCircle(color = Color(0xFF3E2723), radius = w * 0.035f, center = Offset(w * 0.6f, h * 0.78f))
        drawCircle(color = Color(0xFF3E2723), radius = w * 0.035f, center = Offset(w * 0.45f, h * 0.73f))
        drawCircle(color = Color(0xFF3E2723), radius = w * 0.035f, center = Offset(w * 0.55f, h * 0.73f))

        // Straw
        drawLine(
            color = Color(0xFFEC407A),
            start = Offset(w * 0.55f, h * 0.05f),
            end = Offset(w * 0.48f, h * 0.85f),
            strokeWidth = 10f
        )
    }
}

@Composable
fun PinkDonutDrawing(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Outer Dough Ring
        drawCircle(
            color = Color(0xFFFFCC80),
            radius = w * 0.38f,
            center = Offset(w * 0.5f, h * 0.5f)
        )

        // Pink Icing Ring
        drawCircle(
            color = Color(0xFFF48FB1),
            radius = w * 0.34f,
            center = Offset(w * 0.5f, h * 0.5f)
        )

        // Center Hole
        drawCircle(
            color = Color(0xFFFFF0F3),
            radius = w * 0.13f,
            center = Offset(w * 0.5f, h * 0.5f)
        )

        // Sprinkles / Toppings
        val sprinkleColors = listOf(Color.White, Color(0xFFE53935), Color(0xFFFFEB3B), Color(0xFF4CAF50))
        val sprinkleOffsets = listOf(
            Offset(w * 0.35f, h * 0.3f),
            Offset(w * 0.65f, h * 0.32f),
            Offset(w * 0.7f, h * 0.58f),
            Offset(w * 0.3f, h * 0.62f),
            Offset(w * 0.5f, h * 0.25f),
            Offset(w * 0.5f, h * 0.75f)
        )
        sprinkleOffsets.forEachIndexed { i, offset ->
            drawCircle(color = sprinkleColors[i % sprinkleColors.size], radius = w * 0.025f, center = offset)
        }
    }
}

@Composable
fun OrderCompleteIllustration(
    modifier: Modifier = Modifier,
    containerSize: Dp = 180.dp
) {
    Box(
        modifier = modifier
            .size(containerSize)
            .clip(CircleShape)
            .background(Color(0xFFFFF0F5)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val w = size.width
            val h = size.height

            // Pedestal / Plate
            drawOval(
                color = Color(0xFFF8BBD0),
                topLeft = Offset(w * 0.15f, h * 0.75f),
                size = Size(w * 0.7f, h * 0.14f)
            )

            // Cake Bottom Tier
            drawRoundRect(
                color = Color.White,
                topLeft = Offset(w * 0.25f, h * 0.5f),
                size = Size(w * 0.5f, h * 0.26f),
                cornerRadius = CornerRadius(16f, 16f)
            )
            // Cake Bottom Tier Pink Frosting
            drawRoundRect(
                color = Color(0xFFF48FB1),
                topLeft = Offset(w * 0.25f, h * 0.5f),
                size = Size(w * 0.5f, h * 0.09f),
                cornerRadius = CornerRadius(16f, 16f)
            )

            // Cake Top Tier
            drawRoundRect(
                color = Color.White,
                topLeft = Offset(w * 0.33f, h * 0.3f),
                size = Size(w * 0.34f, h * 0.22f),
                cornerRadius = CornerRadius(12f, 12f)
            )
            // Cake Top Tier Pink Frosting
            drawRoundRect(
                color = Color(0xFFF48FB1),
                topLeft = Offset(w * 0.33f, h * 0.3f),
                size = Size(w * 0.34f, h * 0.08f),
                cornerRadius = CornerRadius(12f, 12f)
            )

            // Heart icon on cake
            drawCircle(
                color = Color(0xFFEC407A),
                radius = w * 0.045f,
                center = Offset(w * 0.5f, h * 0.63f)
            )

            // Cherry on Top
            drawCircle(
                color = Color(0xFFE91E63),
                radius = w * 0.07f,
                center = Offset(w * 0.5f, h * 0.23f)
            )
            // Stem
            drawLine(
                color = Color(0xFF3E2723),
                start = Offset(w * 0.5f, h * 0.2f),
                end = Offset(w * 0.56f, h * 0.12f),
                strokeWidth = 5f
            )
        }
    }
}
