package com.danielks.shoppinglist.feature.finalized.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.core.util.formatBRLFromCents
import com.danielks.shoppinglist.domain.model.ListSummary

@Composable
fun FinalizedListCard(
    summary: ListSummary,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(14.dp)) {
            Text(summary.name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("${summary.itemsCount} itens", style = MaterialTheme.typography.bodyMedium)
                Text(formatBRLFromCents(summary.totalCents), style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(Modifier.height(4.dp))
            Text(
                "Somente leitura",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}