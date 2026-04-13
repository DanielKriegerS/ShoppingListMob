package com.danielks.shoppinglist.feature.finalizeddetail.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.core.designsystem.component.AppTopBar
import com.danielks.shoppinglist.feature.listdetail.component.ListHeader
import com.danielks.shoppinglist.model.ShoppingItem
import com.danielks.shoppinglist.preview.PreviewData

@Composable
fun FinalizedListDetailScreen(
    listId: String,
    onBack: () -> Unit
) {
    val list = PreviewData.finalized1
    val checkedCount = list.items.count { it.checked }

    Scaffold(
        topBar = { AppTopBar(title = "Lista Finalizada", showBack = true, onBack = onBack) }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            ListHeader(name = list.name, itemsCount = list.items.size, checkedCount = checkedCount)

            Divider()

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(list.items, key = { it.id }) { item ->
                    ReadonlyItemRow(item)
                }
            }
        }
    }
}

@Composable
private fun ReadonlyItemRow(item: ShoppingItem) {
    androidx.compose.material3.ElevatedCard(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth().padding(12.dp)) {
            androidx.compose.material3.Text("${item.quantity}x ${item.name}")
        }
    }
}