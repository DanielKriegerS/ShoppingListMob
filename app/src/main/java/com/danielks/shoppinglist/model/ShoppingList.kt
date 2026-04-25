package com.danielks.shoppinglist.model

import java.util.UUID

data class ShoppingList(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val items: List<ShoppingItem> = emptyList(),
    val status: ListStatus = ListStatus.ACTIVE,
    val createdAt: Long = System.currentTimeMillis()
) {
    val totalValue: Long
    get() = items.sumOf { it.total() }
}