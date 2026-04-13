package com.danielks.shoppinglist.feature.create.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.core.designsystem.component.PrimaryButton


@Composable
fun CreateListForm(
    listName: String,
    onListNameChange: (String) -> Unit,
    items: List<String>,
    onAddItem: (String) -> Unit,
    onRemoveItem: (Int) -> Unit,
    onSave: () -> Unit
) {
    var newItem by remember { mutableStateOf("") }

    OutlinedTextField(
        value = listName,
        onValueChange = onListNameChange,
        label = { Text("Nome da lista") },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(Modifier.height(12.dp))

    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = newItem,
            onValueChange = { newItem = it },
            label = { Text("Adicionar item") },
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(8.dp))
        Button(onClick = { onAddItem(newItem); newItem = "" }) {
            Text("Add")
        }
    }

    Spacer(Modifier.height(12.dp))

    Text("Itens", style = MaterialTheme.typography.titleSmall)
    Spacer(Modifier.height(8.dp))

    if (items.isEmpty()) {
        Text("Nenhum item adicionado ainda.", style = MaterialTheme.typography.bodyMedium)
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            itemsIndexed(items) { index, item ->
                ElevatedCard {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(item, modifier = Modifier.weight(1f))
                        IconButton(onClick = { onRemoveItem(index) }) {
                            Icon(Icons.Outlined.Delete, contentDescription = "Remover")
                        }
                    }
                }
            }
        }
    }

    Spacer(Modifier.height(12.dp))
    PrimaryButton(
        text = "Salvar lista",
        modifier = Modifier.fillMaxWidth(),
        onClick = onSave
    )
}
