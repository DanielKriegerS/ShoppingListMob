package com.danielks.shoppinglist.preview

import com.danielks.shoppinglist.model.ListStatus
import com.danielks.shoppinglist.model.ShoppingItem
import com.danielks.shoppinglist.model.ShoppingList


object PreviewData {
    val milk = ShoppingItem("1", "Leite", 2)
    val bread = ShoppingItem("2", "Pão", 6, checked = true)
    val coffee = ShoppingItem("3", "Café", 1)

    val active1 = ShoppingList("A1", "Compras da Semana", listOf(milk, bread, coffee), status = ListStatus.ACTIVE)
    val active2 = ShoppingList("A2", "Hortifruti", listOf(ShoppingItem("4", "Banana", 12)), status = ListStatus.ACTIVE)

    val finalized1 = ShoppingList(
        "F1",
        "Churrasco",
        listOf(
            ShoppingItem("10", "Carne", 3, checked = true),
            ShoppingItem("11", "Carvão", 2, checked = true)
        ),
        status = ListStatus.FINALIZED
    )
}