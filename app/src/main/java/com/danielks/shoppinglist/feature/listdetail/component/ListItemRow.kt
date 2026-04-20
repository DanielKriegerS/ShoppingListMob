package com.danielks.shoppinglist.feature.listdetail.component


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.core.util.subtotal
import com.danielks.shoppinglist.model.ShoppingItem

@Composable
fun ListItemRow(
    item: ShoppingItem,
    onToggle: () -> Unit,
    onChangeQuantity: (Int) -> Unit,
    onChangeValue: (Double) -> Unit,
    onRemove: () -> Unit
) {
    var valueText by remember(item.id) { mutableStateOf(item.value.toString()) }

    ElevatedCard(Modifier.fillMaxWidth()) {
        Column(Modifier.fillMaxWidth().padding(12.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = item.checked, onCheckedChange = { onToggle() })
                Spacer(Modifier.width(8.dp))

                Text(
                    item.name,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyLarge
                )

                IconButton(onClick = onRemove) {
                    Icon(Icons.Outlined.Delete, contentDescription = "Remover")
                }
            }

            Spacer(Modifier.height(8.dp))

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                QuantityStepper(value = item.quantity, onValueChange = onChangeQuantity)

                Spacer(Modifier.width(8.dp))

                OutlinedTextField(
                    value = valueText,
                    onValueChange = { txt ->
                        valueText = txt
                        val parsed = txt.replace(",", ".").toDoubleOrNull()
                        if (parsed != null) onChangeValue(parsed)
                    },
                    label = { Text("Valor") },
                    singleLine = true,
                    modifier = Modifier.width(120.dp)
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = com.danielks.shoppinglist.core.util.formatBRL(item.subtotal()),
                    style = MaterialTheme.typography.titleSmall
                )
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