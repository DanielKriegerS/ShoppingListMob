package com.danielks.shoppinglist.feature.listdetail.component


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.model.ShoppingItem

@Composable
fun ListItemRow(
    item: ShoppingItem,
    onToggle: () -> Unit,
    onChangeQuantity: (Int) -> Unit,
    onRemove: () -> Unit
) {
    ElevatedCard(Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = item.checked, onCheckedChange = { onToggle() })
            Spacer(Modifier.width(8.dp))

            Text(
                item.name,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = if (item.checked) TextDecoration.LineThrough else TextDecoration.None
                )
            )

            QuantityStepper(value = item.quantity, onValueChange = onChangeQuantity)

            IconButton(onClick = onRemove) {
                Icon(Icons.Outlined.Delete, contentDescription = "Remover")
            }
        }
    }
}

@Composable
private fun QuantityStepper(
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { if (value > 1) onValueChange(value - 1) }) {
            Icon(Icons.Outlined.ArrowDropDown, contentDescription = "Diminuir")
        }
        Text(value.toString(), modifier = Modifier.widthIn(min = 24.dp))
        IconButton(onClick = { onValueChange(value + 1) }) {
            Icon(Icons.Outlined.Add, contentDescription = "Aumentar")
        }
    }
}