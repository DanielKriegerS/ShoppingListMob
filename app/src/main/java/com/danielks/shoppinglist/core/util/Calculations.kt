package com.danielks.shoppinglist.core.util

import com.danielks.shoppinglist.model.ShoppingItem
import com.danielks.shoppinglist.model.ShoppingList

fun ShoppingItem.subtotal(): Double = (value ?: 0.0) * quantity

fun ShoppingList.total(): Double = items.sumOf { it.subtotal() }

fun ShoppingList.checkedTotal(): Double =
    items.filter { it.checked }.sumOf { it.subtotal() }

