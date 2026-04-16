package com.danielks.shoppinglist.feature.create.ui

import androidx.lifecycle.ViewModel
import com.danielks.shoppinglist.model.ListStatus
import com.danielks.shoppinglist.model.ShoppingItem
import com.danielks.shoppinglist.model.ShoppingList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class CreateListViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateListUiState())
    val uiState: StateFlow<CreateListUiState> = _uiState.asStateFlow()

    fun onListNameChange(name: String) {
        _uiState.update {
            it.copy(
                listName = name,
                canSave = canSave(name, it.items)
            )
        }
    }

    fun onAddItem(rawItem: String) {
        val item = rawItem.trim()
        if (item.isBlank()) return

        _uiState.update {
            val newItems = it.items + item
            it.copy(
                items = newItems,
                canSave = canSave(it.listName, newItems)
            )
        }
    }

    fun onRemoveItem(index: Int) {
        _uiState.update {
            val newItems = it.items.toMutableList().also { list ->
                list.removeAt(index)
            }
            it.copy(
                items = newItems,
                canSave = canSave(it.listName, newItems)
            )
        }
    }

    fun onSave() {
        val state = _uiState.value
        if (!state.canSave) return

        // 🔜 Aqui entra API / Repository
        val newList = ShoppingList(
            id = UUID.randomUUID().toString(),
            name = state.listName,
            items = state.items.map {
                ShoppingItem(
                    id = UUID.randomUUID().toString(),
                    name = it,
                    quantity = 1,
                    checked = false
                )
            },
            status = ListStatus.ACTIVE
        )

        // repository.createList(newList)
    }

    private fun canSave(name: String, items: List<String>): Boolean {
        return name.isNotBlank() && items.isNotEmpty()
    }
}