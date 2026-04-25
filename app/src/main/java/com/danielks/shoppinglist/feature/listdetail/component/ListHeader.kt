package com.danielks.shoppinglist.feature.listdetail.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.core.util.formatBRLFromCents

@Composable
fun ListHeader(
    name: String,
    itemsCount: Int,
    checkedCount: Int,
    totalCents: Long,
    checkedTotalCents: Long? = null,
    isFinished: Boolean
) {
    var itemsHeaderTextLine by remember { mutableStateOf("") }

    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        Text(name, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(4.dp))

        if (!isFinished) {
            itemsHeaderTextLine = "Itens: $itemsCount • Marcados: $checkedCount"
        } else {
            itemsHeaderTextLine = "Itens comprados: $itemsCount"
        }

        Text(
            itemsHeaderTextLine,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Total: ${formatBRLFromCents(totalCents)}",
            style = MaterialTheme.typography.titleMedium
        )

        if (checkedTotalCents != null) {
            Text(
                "Marcados: ${formatBRLFromCents(checkedTotalCents)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}