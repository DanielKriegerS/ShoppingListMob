package com.danielks.shoppinglist.core.util

import com.danielks.shoppinglist.model.ShoppingItem
import kotlin.collections.filter
import kotlin.collections.sumOf
import kotlin.sequences.sumOf
import kotlin.text.sumOf


fun ShoppingItem.subtotalCents(): Long =
valueCents * quantity

fun List<ShoppingItem>.totalCents(): Long =
    sumOf { it.subtotalCents() }

fun List<ShoppingItem>.checkedTotalCents(): Long =
    filter { it.checked }.sumOf { it.subtotalCents() }

