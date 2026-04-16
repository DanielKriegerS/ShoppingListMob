package com.danielks.shoppinglist.feature.listdetail.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListHeader(
    name: String,
    itemsCount: Int,
    checkedCount: Int
) {
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        Text(name, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(4.dp))
        Text("Total de itens: $itemsCount • No carrinho: $checkedCount", style = MaterialTheme.typography.bodyMedium)
    }
}