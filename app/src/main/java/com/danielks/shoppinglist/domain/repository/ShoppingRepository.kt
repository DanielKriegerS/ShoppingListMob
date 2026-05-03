package com.danielks.shoppinglist.domain.repository

import com.danielks.shoppinglist.domain.model.ListSummary
import com.danielks.shoppinglist.domain.model.ShoppingList
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
    fun observeActiveSummaries(): Flow<List<ListSummary>>
    fun observeFinalizedSummaries(): Flow<List<ListSummary>>
    fun observeListDetail(listId: String): Flow<ShoppingList?>

    suspend fun createList(name: String, initialItems: List<String>): String

    suspend fun addItem(listId: String, name: String)
    suspend fun removeItem(itemId: String)

    suspend fun setItemChecked(itemId: String, checked: Boolean)
    suspend fun setItemQuantity(itemId: String, quantity: Int)
    suspend fun setItemValueCents(itemId: String, valueCents: Long)
    suspend fun setItemName(itemId: String, name: String)

    suspend fun finalizeList(listId: String)
    suspend fun reopenList(listId: String)

    suspend fun deleteActiveList(listId: String)
}