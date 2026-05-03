package com.danielks.shoppinglist.data.repository

import com.danielks.shoppinglist.data.local.db.dao.ShoppingItemDao
import com.danielks.shoppinglist.data.local.db.dao.ShoppingListDao
import com.danielks.shoppinglist.data.local.db.entity.ShoppingItemEntity
import com.danielks.shoppinglist.data.local.db.entity.ShoppingListEntity
import com.danielks.shoppinglist.data.mapper.toDomain
import com.danielks.shoppinglist.domain.model.ShoppingList
import com.danielks.shoppinglist.domain.repository.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class ShoppingRepositoryImpl(
    private val listDao: ShoppingListDao,
    private val itemDao: ShoppingItemDao
) : ShoppingRepository {

    override fun observeActiveSummaries() =
        listDao.observeActiveSummaries().map { it.map { e -> e.toDomain() } }

    override fun observeFinalizedSummaries() =
        listDao.observeFinalizedSummaries().map { it.map { e -> e.toDomain() } }

    override fun observeListDetail(listId: String): Flow<ShoppingList?> =
        listDao.observeListWithItems(listId).map { it?.toDomain() }

    override suspend fun createList(name: String, initialItems: List<String>): String {
        val now = System.currentTimeMillis()
        val listId = UUID.randomUUID().toString()

        listDao.upsertList(
            ShoppingListEntity(
                id = listId,
                name = name.trim(),
                status = "ACTIVE",
                createdAt = now,
                updatedAt = now
            )
        )

        val items = initialItems
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .map { itemName ->
                ShoppingItemEntity(
                    id = UUID.randomUUID().toString(),
                    listId = listId,
                    name = itemName,
                    quantity = 1,
                    checked = false,
                    valueCents = 0L
                )
            }

        if (items.isNotEmpty()) itemDao.upsertItems(items)
        return listId
    }

    override suspend fun addItem(listId: String, name: String) {
        itemDao.upsertItem(
            ShoppingItemEntity(
                id = UUID.randomUUID().toString(),
                listId = listId,
                name = name.trim(),
                quantity = 1,
                checked = false,
                valueCents = 0L
            )
        )
        touch(listId)
    }

    override suspend fun removeItem(itemId: String) {
        itemDao.deleteItem(itemId)
        // opcional: tocar updatedAt da lista (ver comentário abaixo)
    }

    override suspend fun setItemChecked(itemId: String, checked: Boolean) {
        itemDao.setChecked(itemId, checked)
        itemDao.getListIdForItem(itemId)?.let { touch(it) }
    }

    override suspend fun setItemQuantity(itemId: String, quantity: Int) {
        itemDao.setQuantity(itemId, quantity)
    }

    override suspend fun setItemValueCents(itemId: String, valueCents: Long) {
        itemDao.setValueCents(itemId, valueCents)
    }

    override suspend fun setItemName(itemId: String, name: String) {
        itemDao.setName(itemId, name.trim())
    }

    override suspend fun finalizeList(listId: String) {
        listDao.finalizeList(listId, System.currentTimeMillis())
    }

    override suspend fun reopenList(listId: String) {
        listDao.reopenList(listId, System.currentTimeMillis())
    }

    override suspend fun deleteActiveList(listId: String) {
        listDao.deleteActiveList(listId)
    }

    // Atualiza updatedAt da lista — útil para ordering
    private suspend fun touch(listId: String) {
        listDao.touch(listId, System.currentTimeMillis()) // ✅ só UPDATE
    }
}