package com.danielks.shoppinglist.data.local.db.dao

import androidx.room.*
import com.danielks.shoppinglist.data.local.db.entity.ShoppingItemEntity

@Dao
interface ShoppingItemDao {

    @Upsert
    suspend fun upsertItem(item: ShoppingItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertItems(items: List<ShoppingItemEntity>)

    @Query("DELETE FROM shopping_item WHERE id = :id")
    suspend fun deleteItem(id: String)

    @Query("UPDATE shopping_item SET checked = :checked WHERE id = :id")
    suspend fun setChecked(id: String, checked: Boolean)

    @Query("UPDATE shopping_item SET quantity = :quantity WHERE id = :id")
    suspend fun setQuantity(id: String, quantity: Int)

    @Query("UPDATE shopping_item SET valueCents = :valueCents WHERE id = :id")
    suspend fun setValueCents(id: String, valueCents: Long)

    @Query("UPDATE shopping_item SET name = :name WHERE id = :id")
    suspend fun setName(id: String, name: String)

    @Query("SELECT listId FROM shopping_item WHERE id = :itemId LIMIT 1")
    suspend fun getListIdForItem(itemId: String): String?

}