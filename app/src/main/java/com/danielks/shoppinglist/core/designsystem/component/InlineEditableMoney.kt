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
import com.danielks.shoppinglist.core.util.formatBRLFromCents

@Composable
fun InlineEditableMoney(
    valueCents: Long,
    onCommit: (Long) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Toque para informar"
) {
    var editing by rememberSaveable(valueCents) { mutableStateOf(false) }
    var draft by rememberSaveable(valueCents) { mutableStateOf(valueCents) }

    LaunchedEffect(valueCents) {
        if (!editing) draft = valueCents
    }

    if (!editing) {
        Row(
            modifier = modifier
                .clickable { editing = true }
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (valueCents > 0L) formatBRLFromCents(valueCents) else placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = if (valueCents > 0L) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.width(6.dp))
            Icon(Icons.Outlined.Edit, contentDescription = "Editar valor")
        }
    } else {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MoneyTextField(
                valueCents = draft,
                onValueCentsChange = { draft = it },
                modifier = Modifier.width(160.dp),
                label = "Valor"
            )

            Spacer(Modifier.width(6.dp))

            IconButton(
                onClick = {
                    onCommit(draft)
                    editing = false
                }
            ) {
                Icon(Icons.Outlined.Check, contentDescription = "Confirmar")
            }

            IconButton(
                onClick = {
                    draft = valueCents
                    editing = false
                }
            ) {
                Icon(Icons.Outlined.Close, contentDescription = "Cancelar")
            }
        }
    }
}