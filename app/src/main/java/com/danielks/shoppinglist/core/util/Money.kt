package com.danielks.shoppinglist.core.util

import java.text.NumberFormat
import java.util.Locale



private val ptBr = Locale("pt", "BR")
private val brl = NumberFormat.getCurrencyInstance(ptBr)

fun formatBRL(value: Double?): String =
    value?.let { brl.format(it) } ?: "—"
