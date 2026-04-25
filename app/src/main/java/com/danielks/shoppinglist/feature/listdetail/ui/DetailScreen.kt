package com.danielks.shoppinglist.feature.listdetail.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.core.designsystem.component.PrimaryButton
import com.danielks.shoppinglist.core.util.checkedTotalCents
import com.danielks.shoppinglist.core.util.totalCents
import com.danielks.shoppinglist.feature.listdetail.component.AddItemBar
import com.danielks.shoppinglist.feature.listdetail.component.ListHeader
import com.danielks.shoppinglist.feature.listdetail.component.ListItemRow
import com.danielks.shoppinglist.model.ShoppingItem
import com.danielks.shoppinglist.model.canFinalize
import com.danielks.shoppinglist.preview.PreviewData

@Composable
fun DetailScreen(
    listId: String,
    onBack: () -> Unit,
    modifier: Modifier
) {
    var items by remember { mutableStateOf(PreviewData.active1.items) }
    val listName = remember { PreviewData.active1.name }
    val checkedCount = items.count { it.checked }
    val totalCents = items.totalCents()
    val checkedTotalCents = items.checkedTotalCents()

    val canFinalize by remember(items) { derivedStateOf { items.canFinalize() } }

        Column(modifier = modifier.padding().fillMaxSize()) {

            ListHeader(
                name = listName,
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
                        items = items + ShoppingItem(
                            id = System.currentTimeMillis().toString(),
                            name = trimmed
                        )
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
                            items = items.map {
                                if (it.id == item.id) it.copy(checked = !it.checked) else it
                            }
                        },
                        onChangeQuantity = { q ->
                            items = items.map {
                                if (it.id == item.id) it.copy(quantity = q) else it
                            }
                        },
                        onRemove = {
                            items = items.filterNot { it.id == item.id }
                        },
                        onChangeValueCents = { newCents ->
                            items = items.map {
                                if (it.id == item.id) it.copy(valueCents = newCents) else it
                            }
                        },
                        onChangeName = { newName ->
                            items = items.map {
                                if (it.id == item.id) it.copy(name = newName) else it
                            }
                        }
                    )
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .imePadding()
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
                    onClick = { },
                    enabled = canFinalize,
                    modifier = Modifier.fillMaxWidth()
                )
            }
    }
}