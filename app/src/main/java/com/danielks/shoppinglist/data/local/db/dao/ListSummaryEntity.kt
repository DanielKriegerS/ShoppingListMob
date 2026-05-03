package com.danielks.shoppinglist.data.local.db.dao

data class ListSummaryEntity(
    val id: String,
    val name: String,
    val status: String,
    val itemsCount: Int,
    val checkedCount: Int,
    val totalCents: Long,
    val updatedAt: Long
)