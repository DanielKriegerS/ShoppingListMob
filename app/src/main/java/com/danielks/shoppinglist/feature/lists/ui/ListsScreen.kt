package com.danielks.shoppinglist.feature.lists.ui

import com.danielks.shoppinglist.core.designsystem.component.EmptyState
import com.danielks.shoppinglist.core.ui.UiState
import com.danielks.shoppinglist.model.ShoppingList
import com.danielks.shoppinglist.preview.PreviewData
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.feature.lists.component.ListCard

@Composable
fun ListsScreen(
    onBack: () -> Unit,
    onOpenList: (String) -> Unit,
    state: UiState<List<ShoppingList>> = UiState.Success(listOf(PreviewData.active1, PreviewData.active2)),
    modifier: Modifier
) {
Box() {
    when (state) {
            UiState.Loading -> Box(modifier = modifier.fillMaxSize().padding()) { CircularProgressIndicator() }

            is UiState.Empty -> EmptyState(
                title = "Nenhuma lista ativa",
                message = state.message.ifBlank { "Crie uma nova lista para começar." }
            )

            is UiState.Error -> Text("Erro: ${state.message}", modifier = modifier.padding())

            is UiState.Success -> {
                val data = state.data
                if (data.isEmpty()) {
                    EmptyState("Nenhuma lista ativa", "Crie uma nova lista para começar.")
                } else {
                    LazyColumn(
                        modifier = modifier.padding().fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(data, key = { it.id }) { list ->
                            ListCard(list = list, onClick = { onOpenList(list.id) })
                        }
                    }
                }
            }
        }
    }
}