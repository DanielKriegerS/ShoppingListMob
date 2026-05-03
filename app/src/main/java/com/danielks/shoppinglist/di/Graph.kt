package com.danielks.shoppinglist.di

import android.content.Context
import androidx.room.Room
import com.danielks.shoppinglist.data.local.db.AppDatabase
import com.danielks.shoppinglist.data.repository.ShoppingRepositoryImpl
import com.danielks.shoppinglist.domain.repository.ShoppingRepository

object Graph {
    lateinit var db: AppDatabase
        private set

    lateinit var repository: ShoppingRepository
        private set

    fun provide(context: Context) {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "shoppinglist.db"
        )
            // para DEV: ok; para produção, quando mudar schema, cria migration
            //.fallbackToDestructiveMigration()
            .build()

        repository = ShoppingRepositoryImpl(
            listDao = db.shoppingListDao(),
            itemDao = db.shoppingItemDao()
        )
    }
}