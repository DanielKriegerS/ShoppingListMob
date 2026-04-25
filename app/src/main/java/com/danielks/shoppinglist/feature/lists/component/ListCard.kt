package com.danielks.shoppinglist.feature.lists.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.model.ShoppingList

@Composable
fun ListCard(
    list: ShoppingList,
    onClick: () -> Unit
) {
    val total = list.totalValue

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(14.dp)) {
            Text(list.name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("${list.items.size} itens", style = MaterialTheme.typography.bodyMedium)
                Text(
                    com.danielks.shoppinglist.core.util.formatBRLFromCents(total),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}