package com.danielks.shoppinglist.feature.lists.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danielks.shoppinglist.core.designsystem.component.EmptyState
import com.danielks.shoppinglist.core.ui.UiState
import com.danielks.shoppinglist.domain.model.ListSummary
import com.danielks.shoppinglist.feature.lists.component.ListCard


@Composable
fun ListsScreen(
    onOpenList: (String) -> Unit,
    viewModel: ListsViewModel = viewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    var toDelete by remember { mutableStateOf<ListSummary?>(null) }


    if (toDelete != null) {
        AlertDialog(
            onDismissRequest = { toDelete = null },
            title = { Text("Excluir lista?") },
            text = { Text("Tem certeza que deseja excluir \"${toDelete!!.name}\"? Esta ação não pode ser desfeita.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteActiveList(toDelete!!.id)
                        toDelete = null
                    }
                ) { Text("Excluir") }
            },
            dismissButton = {
                TextButton(onClick = { toDelete = null }) { Text("Cancelar") }
            }
        )
    }


    when (state) {

        UiState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        is UiState.Empty -> Box(modifier = Modifier.fillMaxSize()) {
            EmptyState(
                title = "Nenhuma lista ativa",
                message = state.message.ifBlank { "Crie uma nova lista para começar." }
            )
        }

        is UiState.Error -> Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Erro: ${state.message}",
                modifier = Modifier.padding(16.dp)
            )
        }

        is UiState.Success -> {
            val data = state.data

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(data, key = { it.id }) { summary ->
                    ListCard(
                        summary = summary,
                        onClick = { onOpenList(summary.id) },
                        onDeleteClick = { toDelete = summary }
                    )
                }
            }
        }
    }
}
