package com.danielks.shoppinglist.feature.finalized.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danielks.shoppinglist.core.designsystem.component.EmptyState
import com.danielks.shoppinglist.core.ui.UiState
import com.danielks.shoppinglist.domain.model.ListSummary
import com.danielks.shoppinglist.feature.finalized.component.FinalizedListCard
import com.danielks.shoppinglist.preview.PreviewData

@Composable
fun FinalizedListsScreen(
    onOpenList: (String) -> Unit,
    viewModel: FinalizedListsViewModel = viewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value


    when (state) {

        UiState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        is UiState.Empty -> Box(modifier = Modifier.fillMaxSize()) {
            EmptyState(
                title = "Nenhuma lista finalizada",
                message = state.message.ifBlank { "Finalize uma lista ativa para ver aqui." }
            )
        }

        is UiState.Error -> Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Erro: ${state.message}",
                modifier = Modifier.padding(16.dp)
            )
        }

        is UiState.Success -> {
            val data: List<ListSummary> = state.data

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(data, key = { it.id }) { summary ->
                    FinalizedListCard(
                        summary = summary,
                        onClick = { onOpenList(summary.id) }
                    )
                }
            }
        }
    }
}
