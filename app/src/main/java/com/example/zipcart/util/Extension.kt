package com.example.zipcart.util

import java.util.Locale

fun Double.toPrice(): String = String.format(Locale.US, "\$%.2f", this)