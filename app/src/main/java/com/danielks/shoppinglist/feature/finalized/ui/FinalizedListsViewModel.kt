package com.danielks.shoppinglist.feature.finalized.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielks.shoppinglist.core.ui.UiState
import com.danielks.shoppinglist.di.Graph
import com.danielks.shoppinglist.domain.model.ListSummary
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FinalizedListsViewModel : ViewModel() {

    private val repo = Graph.repository

    val uiState: StateFlow<UiState<List<ListSummary>>> =
        repo.observeFinalizedSummaries()
            .map { list ->
                if (list.isEmpty()) UiState.Empty("Nenhuma lista finalizada.")
                else UiState.Success(list)
            }
            .catch { e ->
                emit(UiState.Error(e.message ?: "Erro ao carregar listas finalizadas."))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UiState.Loading
            )
}