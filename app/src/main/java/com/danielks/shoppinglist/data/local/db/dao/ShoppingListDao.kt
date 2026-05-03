package com.danielks.shoppinglist.data.local.db.dao

import androidx.room.*
import com.danielks.shoppinglist.data.local.db.entity.ShoppingListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {

    @Upsert
    suspend fun upsertList(list: ShoppingListEntity)

    @Query("SELECT * FROM shopping_list WHERE id = :id LIMIT 1")
    suspend fun getListById(id: String): ShoppingListEntity?

    @Query("UPDATE shopping_list SET updatedAt = :updatedAt WHERE id = :id")
    suspend fun touch(id: String, updatedAt: Long)

    @Transaction
    @Query("SELECT * FROM shopping_list WHERE id = :id LIMIT 1")
    fun observeListWithItems(id: String): Flow<ListWithItemsEntity?>

    @Query("""
        SELECT 
            l.id AS id,
            l.name AS name,
            l.status AS status,
            COUNT(i.id) AS itemsCount,
            COALESCE(SUM(CASE WHEN i.checked = 1 THEN 1 ELSE 0 END), 0) AS checkedCount,
            COALESCE(SUM(i.quantity * i.valueCents), 0) AS totalCents,
            l.updatedAt AS updatedAt
        FROM shopping_list l
        LEFT JOIN shopping_item i ON i.listId = l.id
        WHERE l.status = 'ACTIVE'
        GROUP BY l.id
        ORDER BY l.updatedAt DESC
    """)
    fun observeActiveSummaries(): Flow<List<ListSummaryEntity>>

    @Query("""
        SELECT 
            l.id AS id,
            l.name AS name,
            l.status AS status,
            COUNT(i.id) AS itemsCount,
            COALESCE(SUM(CASE WHEN i.checked = 1 THEN 1 ELSE 0 END), 0) AS checkedCount,
            COALESCE(SUM(i.quantity * i.valueCents), 0) AS totalCents,
            l.updatedAt AS updatedAt
        FROM shopping_list l
        LEFT JOIN shopping_item i ON i.listId = l.id
        WHERE l.status = 'FINALIZED'
        GROUP BY l.id
        ORDER BY l.updatedAt DESC
    """)
    fun observeFinalizedSummaries(): Flow<List<ListSummaryEntity>>

    @Query("UPDATE shopping_list SET status = 'FINALIZED', updatedAt = :updatedAt WHERE id = :id")
    suspend fun finalizeList(id: String, updatedAt: Long)

    @Query("UPDATE shopping_list SET status = 'ACTIVE', updatedAt = :updatedAt WHERE id = :id")
    suspend fun reopenList(id: String, updatedAt: Long)

    @Query("DELETE FROM shopping_list WHERE id = :id AND status = 'ACTIVE'")
    suspend fun deleteActiveList(id: String)
}