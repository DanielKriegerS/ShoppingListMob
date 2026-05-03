package com.danielks.shoppinglist.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danielks.shoppinglist.data.local.db.dao.ShoppingItemDao
import com.danielks.shoppinglist.data.local.db.dao.ShoppingListDao
import com.danielks.shoppinglist.data.local.db.entity.ShoppingItemEntity
import com.danielks.shoppinglist.data.local.db.entity.ShoppingListEntity

@Database(
    entities = [
        ShoppingListEntity::class,
        ShoppingItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun shoppingItemDao(): ShoppingItemDao
}