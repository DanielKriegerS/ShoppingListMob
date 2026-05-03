package com.danielks.shoppinglist.domain.model

fun ShoppingItem.isReadyToFinalize(): Boolean =
    name.isNotBlank() && checked && valueCents > 0L

fun List<ShoppingItem>.canFinalize(): Boolean =
    isNotEmpty() && all { it.isReadyToFinalize() }
