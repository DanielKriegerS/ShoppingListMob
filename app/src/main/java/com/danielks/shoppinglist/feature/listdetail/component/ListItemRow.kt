package com.danielks.shoppinglist.feature.listdetail.component


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.core.designsystem.component.InlineEditableMoney
import com.danielks.shoppinglist.core.designsystem.component.InlineEditableName
import com.danielks.shoppinglist.core.util.formatBRLFromCents
import com.danielks.shoppinglist.core.util.subtotalCents
import com.danielks.shoppinglist.domain.model.ShoppingItem

@Composable
fun ListItemRow(
    item: ShoppingItem,
    onToggle: () -> Unit,
    onChangeQuantity: (Int) -> Unit,
    onChangeValueCents: (Long) -> Unit,
    onChangeName: (String) -> Unit,
    onRemove: () -> Unit
) {
    ElevatedCard(Modifier.fillMaxWidth()) {
        Column(Modifier.fillMaxWidth().padding(12.dp)) {

            // Linha 1: checkbox + nome (sob demanda) + remover
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = item.checked, onCheckedChange = { onToggle() })
                Spacer(Modifier.width(8.dp))

                Box(modifier = Modifier.weight(1f)) {
                    InlineEditableName(
                        value = item.name,
                        onCommit = onChangeName
                    )
                }

                Spacer(Modifier.width(6.dp))

                IconButton(onClick = onRemove, modifier = Modifier.size(36.dp)) {
                    Icon(Icons.Outlined.Delete, contentDescription = "Remover")
                }
            }

            Spacer(Modifier.height(8.dp))

            // Linha 2: quantidade + valor (sob demanda)
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QuantityStepper(
                    value = item.quantity,
                    onValueChange = onChangeQuantity
                )

                InlineEditableMoney(
                    valueCents = item.valueCents,
                    onCommit = onChangeValueCents
                )
            }

            Spacer(Modifier.height(6.dp))

            // Linha 3: subtotal alinhado à direita (não quebra)
            Text(
                text = formatBRLFromCents(item.subtotalCents()),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.align(Alignment.End),
                maxLines = 1
            )
        }
    }
}


@Composable
private fun QuantityStepper(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { if (value > 1) onValueChange(value - 1) },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(Icons.Outlined.Delete, contentDescription = "Diminuir")
        }

        Text(
            text = value.toString(),
            modifier = Modifier.widthIn(min = 20.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )

        IconButton(
            onClick = { onValueChange(value + 1) },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(Icons.Outlined.Add, contentDescription = "Aumentar")
        }
    }
}
