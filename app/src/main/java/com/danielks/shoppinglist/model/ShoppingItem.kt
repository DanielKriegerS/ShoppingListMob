package com.danielks.shoppinglist.model

import java.util.UUID

data class ShoppingItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val quantity: Int = 1,
    val checked: Boolean = false,
    val value: Double? = null
) {
    fun total(): Double = (value ?: 0.0) * quantity
}
