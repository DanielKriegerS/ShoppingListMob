package com.danielks.shoppinglist.feature.finalizeddetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielks.shoppinglist.core.ui.UiState
import com.danielks.shoppinglist.di.Graph
import com.danielks.shoppinglist.domain.model.ShoppingList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FinalizedDetailViewModel : ViewModel() {

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

    fun reopen(listId: String) = viewModelScope.launch {
        repo.reopenList(listId)
    }
}