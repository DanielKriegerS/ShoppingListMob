package com.danielks.shoppinglist.data.local.db.dao

import androidx.room.Embedded
import androidx.room.Relation
import com.danielks.shoppinglist.data.local.db.entity.ShoppingItemEntity
import com.danielks.shoppinglist.data.local.db.entity.ShoppingListEntity

data class ListWithItemsEntity(
    @Embedded val list: ShoppingListEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "listId"
    )
    val items: List<ShoppingItemEntity>
)