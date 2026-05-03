package com.danielks.shoppinglist.feature.lists.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.core.util.formatBRLFromCents
import com.danielks.shoppinglist.domain.model.ListSummary

@Composable
fun ListCard(
    summary: ListSummary,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(10.dp)) {
            Row(
                Modifier.fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.inverseOnSurface),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column (modifier = Modifier.weight(9f), horizontalAlignment = Alignment.CenterHorizontally){
                    Text(summary.name, style = MaterialTheme.typography.titleMedium)
                }

                Column (modifier = Modifier.weight(1f)){
                    IconButton(onClick = onDeleteClick) {
                        Icon(Icons.Outlined.Delete, contentDescription = "Excluir lista")
                    }
                }

            }

            Spacer(Modifier.height(5.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("${summary.itemsCount} itens", style = MaterialTheme.typography.bodyMedium)
                Text(
                    formatBRLFromCents(summary.totalCents),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(2.dp))

            TextButton(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
                Text("Abrir")
            }
        }
    }
}