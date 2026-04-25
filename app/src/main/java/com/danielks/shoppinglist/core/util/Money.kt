package com.danielks.shoppinglist.core.util

import java.text.NumberFormat
import java.util.Locale

private val brl = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

fun formatBRLFromCents(cents: Long): String =
    brl.format(cents / 100.0)


