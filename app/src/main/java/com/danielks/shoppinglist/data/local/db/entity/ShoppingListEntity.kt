package com.danielks.shoppinglist.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "shopping_list")
data class ShoppingListEntity(
    @PrimaryKey val id: String,
    val name: String,
    val status: String,
    val createdAt: Long,
    val updatedAt: Long
)