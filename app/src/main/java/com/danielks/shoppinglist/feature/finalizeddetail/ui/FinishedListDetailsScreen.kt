package com.danielks.shoppinglist.feature.finalizeddetail.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.core.util.formatBRL
import com.danielks.shoppinglist.core.util.subtotal
import com.danielks.shoppinglist.core.util.total
import com.danielks.shoppinglist.feature.listdetail.component.ListHeader
import com.danielks.shoppinglist.model.ShoppingItem
import com.danielks.shoppinglist.preview.PreviewData

@Composable
fun FinalizedListDetailScreen(
    listId: String,
    onBack: () -> Unit,
    modifier: Modifier
) {
    val list = PreviewData.finalized1
    val totalValue = list.total()

    Column(modifier = modifier.fillMaxSize()) {

        ListHeader(
            name = list.name,
            itemsCount = list.items.size,
            checkedCount = 0,
            totalValue = totalValue,
            isFinished = true,
            checkedTotalValue = null
        )

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

@Composable
private fun ReadonlyItemRow(item: ShoppingItem) {
    ElevatedCard(Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("${item.quantity}x ${item.name}", style = MaterialTheme.typography.bodyLarge)
                Text(
                    "Unit: ${formatBRL(item.value)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                formatBRL(item.subtotal()),
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}