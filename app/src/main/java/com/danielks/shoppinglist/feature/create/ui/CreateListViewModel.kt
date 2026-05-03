package com.danielks.shoppinglist.feature.create.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielks.shoppinglist.di.Graph
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CreateListViewModel : ViewModel() {

    private val repo = Graph.repository

    private val _uiState = MutableStateFlow(CreateListUiState())
    val uiState: StateFlow<CreateListUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<Event>()
    val events = _events.asSharedFlow()

    sealed interface Event {
        data class Saved(val listId: String) : Event
        data class Error(val message: String) : Event
    }

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
                if (index in list.indices) list.removeAt(index)
            }
            it.copy(
                items = newItems,
                canSave = canSave(it.listName, newItems)
            )
        }
    }

    fun onSave() {
        val state = _uiState.value
        if (!state.canSave || state.isSaving) return

        val name = state.listName.trim()
        val items = state.items.map { it.trim() }.filter { it.isNotBlank() }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            runCatching {
                repo.createList(name = name, initialItems = items)
            }.onSuccess { listId ->
                _uiState.value = CreateListUiState() // limpa a tela
                _events.emit(Event.Saved(listId))
            }.onFailure { e ->
                _uiState.update { it.copy(isSaving = false) }
                _events.emit(Event.Error(e.message ?: "Erro ao salvar lista."))
            }
        }
    }

    private fun canSave(name: String, items: List<String>): Boolean {
        return name.isNotBlank() && items.isNotEmpty()
    }
}