package com.example.yummy_bites.ui.util

import java.util.Locale

fun Int.toRupiah(): String {
    return "Rp" + String.format(Locale.GERMANY, "%,d", this)
}
