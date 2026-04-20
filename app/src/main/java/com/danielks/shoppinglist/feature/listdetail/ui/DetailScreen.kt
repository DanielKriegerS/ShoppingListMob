package com.danielks.shoppinglist.feature.listdetail.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.core.util.subtotal
import com.danielks.shoppinglist.feature.listdetail.component.AddItemBar
import com.danielks.shoppinglist.feature.listdetail.component.ListHeader
import com.danielks.shoppinglist.feature.listdetail.component.ListItemRow
import com.danielks.shoppinglist.model.ShoppingItem
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
    val totalValue = items.sumOf { it.subtotal() }
    val checkedTotalValue = items.filter { it.checked }.sumOf { it.subtotal() }


    Column(modifier = modifier.padding().fillMaxSize()) {

            ListHeader(
                name = listName,
                itemsCount = items.size,
                checkedCount = checkedCount,
                totalValue = totalValue,
                isFinished = false,
                checkedTotalValue = checkedTotalValue
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
                modifier = Modifier.fillMaxSize(),
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
                        onChangeValue = { newValue ->
                            items = items.map {
                                if (it.id == item.id) it.copy(value = newValue) else it
                            }
                        }
                    )
                }
            }
        }
}