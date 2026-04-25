package com.danielks.shoppinglist.core.designsystem.component


import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.danielks.shoppinglist.core.util.formatBRLFromCents


@Composable
fun MoneyTextField(
    valueCents: Long,
    onValueCentsChange: (Long) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Valor",
    placeholder: String = ""
) {
    var digits by remember(valueCents) {
        mutableStateOf(if (valueCents == 0L) "" else valueCents.toString())
    }

    val formatted = remember(digits) {
        if (digits.isBlank()) "" else formatBRLFromCents(digits.toLong())
    }

    var tfv by remember(formatted) {
        mutableStateOf(TextFieldValue(formatted, selection = TextRange(formatted.length)))
    }

    LaunchedEffect(formatted) {
        tfv = TextFieldValue(formatted, selection = TextRange(formatted.length))
    }

    OutlinedTextField(
        value = tfv,
        onValueChange = { newValue ->
            val newDigits = newValue.text.filter { it.isDigit() }.take(12)
            digits = newDigits

            val cents = newDigits.toLongOrNull() ?: 0L
            onValueCentsChange(cents)

            val newFormatted = if (newDigits.isBlank()) "" else formatBRLFromCents(cents)
            tfv = TextFieldValue(newFormatted, selection = TextRange(newFormatted.length))
        },
        label = { Text(label) },
        placeholder = { if (placeholder.isNotBlank()) Text(placeholder) },
        modifier = modifier,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}
