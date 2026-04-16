package com.danielks.shoppinglist.feature.create.ui

data class CreateListUiState(
    val listName: String = "",
    val items: List<String> = emptyList(),
    val isSaving: Boolean = false,
    val canSave: Boolean = false
)