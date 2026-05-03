package com.danielks.shoppinglist.feature.finalizeddetail.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danielks.shoppinglist.core.designsystem.component.PrimaryButton
import com.danielks.shoppinglist.core.ui.UiState
import com.danielks.shoppinglist.core.util.formatBRLFromCents
import com.danielks.shoppinglist.feature.listdetail.component.ListHeader
import com.danielks.shoppinglist.domain.model.ShoppingItem
import com.danielks.shoppinglist.domain.model.ShoppingList

@Composable
fun FinalizedListDetailScreen(
    listId: String,
    onBack: () -> Unit,
    viewModel: FinalizedDetailViewModel = viewModel()
) {
    LaunchedEffect(listId) { viewModel.load(listId) }

    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    var confirmReopen by remember { mutableStateOf(false) }


    when (state) {
        UiState.Loading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        is UiState.Error -> Box(modifier = Modifier.fillMaxSize()) {
            Text("Erro: ${state.message}", modifier = Modifier.padding(16.dp))
        }

        is UiState.Empty -> Box(modifier = Modifier.fillMaxSize()) {
            Text("Nada por aqui", modifier = Modifier.padding(16.dp))
        }

        is UiState.Success -> {
            val list: ShoppingList = state.data
            val totalCents = list.items.sumOf { it.valueCents * it.quantity } // ou use sua extension totalCents()

            if (confirmReopen) {
                AlertDialog(
                    onDismissRequest = { confirmReopen = false },
                    title = { Text("Reabrir lista?") },
                    text = {
                        Text("A lista \"${list.name}\" voltará para as listas ativas e poderá ser editada novamente.")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.reopen(list.id)
                                confirmReopen = false
                                onBack()
                            }
                        ) { Text("Reabrir") }
                    },
                    dismissButton = {
                        TextButton(onClick = { confirmReopen = false }) { Text("Cancelar") }
                    }
                )
            }

            Column(modifier = Modifier.fillMaxSize()) {

                ListHeader(
                    name = list.name,
                    itemsCount = list.items.size,
                    checkedCount = 0,
                    totalCents = totalCents,
                    checkedTotalCents = null,
                    isFinished = true
                )

                Divider()

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(list.items, key = { it.id }) { item ->
                        ReadonlyItemRow(item)
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(12.dp)
                ) {
                    PrimaryButton(
                        text = "Reabrir",
                        onClick = { confirmReopen = true },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun ReadonlyItemRow(item: ShoppingItem) {
    val subtotal = item.valueCents * item.quantity

    ElevatedCard(Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("${item.quantity}x ${item.name}", style = MaterialTheme.typography.bodyLarge)
                Text(
                    "Unit: ${formatBRLFromCents(item.valueCents)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                formatBRLFromCents(subtotal),
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}
