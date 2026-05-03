package com.danielks.shoppinglist

import android.app.Application
import com.danielks.shoppinglist.di.Graph

class ShoppingListApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}
