package com.danielks.shoppinglist.data.mapper

import com.danielks.shoppinglist.data.local.db.dao.ListSummaryEntity
import com.danielks.shoppinglist.data.local.db.dao.ListWithItemsEntity
import com.danielks.shoppinglist.data.local.db.entity.ShoppingItemEntity
import com.danielks.shoppinglist.data.local.db.entity.ShoppingListEntity
import com.danielks.shoppinglist.domain.model.*

fun ShoppingListEntity.toDomain(items: List<ShoppingItemEntity>): ShoppingList =
    ShoppingList(
        id = id.toString(),
        name = name,
        status = ListStatus.valueOf(status),
        createdAt = createdAt,
        updatedAt = updatedAt,
        items = items.map { it.toDomain() }
    )

fun ShoppingItemEntity.toDomain(): ShoppingItem =
    ShoppingItem(
        id = id.toString(),
        listId = listId.toString(),
        name = name,
        quantity = quantity,
        checked = checked,
        valueCents = valueCents
    )

fun ListWithItemsEntity.toDomain(): ShoppingList =
    list.toDomain(items)

fun ListSummaryEntity.toDomain(): ListSummary =
    ListSummary(
        id = id,
        name = name,
        status = ListStatus.valueOf(status),
        itemsCount = itemsCount,
        checkedCount = checkedCount,
        totalCents = totalCents,
        updatedAt = updatedAt
    )