package com.danielks.shoppinglist.domain.model

data class ListSummary(
    val id: String,
    val name: String,
    val status: ListStatus,
    val itemsCount: Int,
    val checkedCount: Int,
    val totalCents: Long,
    val updatedAt: Long
)