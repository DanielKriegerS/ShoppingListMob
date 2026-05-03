package com.danielks.shoppinglist.feature.listdetail.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danielks.shoppinglist.core.designsystem.component.EmptyState
import com.danielks.shoppinglist.core.designsystem.component.PrimaryButton
import com.danielks.shoppinglist.core.ui.UiState
import com.danielks.shoppinglist.core.util.checkedTotalCents
import com.danielks.shoppinglist.core.util.totalCents
import com.danielks.shoppinglist.feature.listdetail.component.AddItemBar
import com.danielks.shoppinglist.feature.listdetail.component.ListHeader
import com.danielks.shoppinglist.feature.listdetail.component.ListItemRow
import com.danielks.shoppinglist.domain.model.ShoppingItem
import com.danielks.shoppinglist.domain.model.canFinalize
import com.danielks.shoppinglist.preview.PreviewData

@Composable
fun DetailScreen(
    listId: String,
    onBack: () -> Unit,
    modifier: Modifier,
    viewModel: DetailViewModel = viewModel()
) {
    LaunchedEffect(listId) { viewModel.load(listId) }

    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    when (state) {
        UiState.Loading -> Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        is UiState.Error -> Box(modifier.fillMaxSize()) {
            Text("Erro: ${state.message}", modifier = Modifier.padding(16.dp))
        }

        is UiState.Empty -> Box(modifier.fillMaxSize()) {
            EmptyState("Nada por aqui", "Não foi possível carregar a lista.")
        }

        is UiState.Success -> {
            val list = state.data
            val items = list.items

            val checkedCount = items.count { it.checked }
            val totalCents = items.totalCents()
            val checkedTotalCents = items.checkedTotalCents()

            val canFinalize by remember(items) { derivedStateOf { items.canFinalize() } }

            Column(modifier = modifier.fillMaxSize()) {

                ListHeader(
                    name = list.name,
                    itemsCount = items.size,
                    checkedCount = checkedCount,
                    totalCents = totalCents,
                    isFinished = false,
                    checkedTotalCents = checkedTotalCents
                )

                AddItemBar(
                    onAdd = { name ->
                        val trimmed = name.trim()
                        if (trimmed.isNotBlank()) {
                            viewModel.addItem(list.id, trimmed)
                        }
                    }
                )

                Divider()

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(items, key = { it.id }) { item ->
                        ListItemRow(
                            item = item,
                            onToggle = {
                                viewModel.setChecked(item.id, !item.checked)
                            },
                            onChangeQuantity = { q ->
                                viewModel.setQuantity(item.id, q)
                            },
                            onRemove = {
                                viewModel.removeItem(item.id)
                            },
                            onChangeValueCents = { newCents ->
                                viewModel.setValueCents(item.id, newCents)
                            },
                            onChangeName = { newName ->
                                viewModel.setName(item.id, newName)
                            }
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .imePadding()
                        .padding(12.dp)
                ) {
                    if (!canFinalize) {
                        val msg = when {
                            items.isEmpty() -> "Adicione itens para finalizar."
                            items.any { it.name.isBlank() } -> "Preencha o nome de todos os itens."
                            items.any { !it.checked } -> "Marque todos os itens."
                            items.any { it.valueCents <= 0L } -> "Preencha o valor de todos os itens."
                            else -> "Complete todos os itens para finalizar."
                        }

                        Text(
                            msg,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(6.dp))
                    }

                    PrimaryButton(
                        text = "Finalizar",
                        onClick = {
                            viewModel.finalizeList(list.id)
                            onBack()
                        },
                        enabled = canFinalize,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}