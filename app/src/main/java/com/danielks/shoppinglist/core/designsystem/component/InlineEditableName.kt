package com.danielks.shoppinglist.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InlineEditableName(
    value: String,
    onCommit: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Toque para editar"
) {
    var editing by rememberSaveable (value) { mutableStateOf(false) }
    var draft by rememberSaveable(value) { mutableStateOf(value) }

    LaunchedEffect(value) {
        if (!editing) draft = value
    }

    if (!editing) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable { editing = true }
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value.ifBlank { placeholder },
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                color = if (value.isBlank()) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
            )
            Icon(Icons.Outlined.Edit, contentDescription = "Editar")
        }
    } else {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = draft,
                onValueChange = { draft = it },
                label = { Text("Item") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(8.dp))

            IconButton(
                onClick = {
                    val trimmed = draft.trim()
                    onCommit(trimmed)
                    editing = false
                }
            ) {
                Icon(Icons.Outlined.Check, contentDescription = "Confirmar")
            }

            IconButton(
                onClick = {
                    draft = value
                    editing = false
                }
            ) {
                Icon(Icons.Outlined.Close, contentDescription = "Cancelar")
            }
        }
    }
}