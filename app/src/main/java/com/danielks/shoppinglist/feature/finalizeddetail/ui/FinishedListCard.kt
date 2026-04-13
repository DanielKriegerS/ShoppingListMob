package com.danielks.shoppinglist.feature.finalizeddetail.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FinalizedListCard(
    listName: String,
    itemsCount: Int,
    onClick: () -> Unit
) {
    ElevatedCard(Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(Modifier.padding(14.dp)) {
            Text(listName, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            Text("Itens: $itemsCount • Somente leitura", style = MaterialTheme.typography.bodyMedium)
        }
    }
}