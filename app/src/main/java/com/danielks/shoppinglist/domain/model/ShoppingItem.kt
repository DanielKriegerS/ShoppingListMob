package com.danielks.shoppinglist.domain.model

data class ShoppingItem(
    val id: String,
    val name: String,
    val quantity: Int = 1,
    val checked: Boolean = false,
    val valueCents: Long = 0L,
    val listId: String
) {
    fun total(): Long = valueCents * quantity
}
