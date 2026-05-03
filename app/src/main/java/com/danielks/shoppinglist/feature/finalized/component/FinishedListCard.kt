package com.danielks.shoppinglist.feature.finalized.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.core.util.formatBRLFromCents
import com.danielks.shoppinglist.domain.model.ShoppingList

@Composable
fun FinalizedListCard(
    list: ShoppingList,
    onClick: () -> Unit
) {
    val total = list.totalValue

    ElevatedCard(Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(Modifier.padding(14.dp)) {
            Text(list.name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Itens: ${list.items.size}", style = MaterialTheme.typography.bodyMedium)
                Text(
                    formatBRLFromCents(total),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}