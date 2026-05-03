package com.danielks.shoppinglist.feature.listdetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielks.shoppinglist.core.ui.UiState
import com.danielks.shoppinglist.di.Graph
import com.danielks.shoppinglist.domain.model.ShoppingList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private val repo = Graph.repository
    private val listIdFlow = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<UiState<ShoppingList>> =
        listIdFlow
            .filterNotNull()
            .distinctUntilChanged()
            .flatMapLatest { id -> repo.observeListDetail(id) }
            .map { list ->
                if (list == null) UiState.Error("Lista não encontrada.")
                else UiState.Success(list)
            }
            .catch { e -> emit(UiState.Error(e.message ?: "Erro ao carregar lista.")) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState.Loading)

    fun load(listId: String) {
        listIdFlow.value = listId
    }

    fun addItem(listId: String, name: String) = viewModelScope.launch {
        repo.addItem(listId, name)
    }

    fun removeItem(itemId: String) = viewModelScope.launch {
        repo.removeItem(itemId)
    }

    fun setChecked(itemId: String, checked: Boolean) = viewModelScope.launch {
        repo.setItemChecked(itemId, checked)
    }

    fun setQuantity(itemId: String, quantity: Int) = viewModelScope.launch {
        repo.setItemQuantity(itemId, quantity)
    }

    fun setValueCents(itemId: String, valueCents: Long) = viewModelScope.launch {
        repo.setItemValueCents(itemId, valueCents)
    }

    fun setName(itemId: String, name: String) = viewModelScope.launch {
        repo.setItemName(itemId, name)
    }

    fun finalizeList(listId: String) = viewModelScope.launch {
        repo.finalizeList(listId)
    }
}