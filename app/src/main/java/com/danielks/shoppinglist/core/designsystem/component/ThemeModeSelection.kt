package com.danielks.shoppinglist.core.designsystem.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.core.settings.ThemeMode

@Composable
fun ThemeModeSection(
    current: ThemeMode,
    onChange: (ThemeMode) -> Unit
) {
    Text(
        text = "Aparência",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )

    ThemeModeRow(
        label = "Seguir sistema",
        selected = current == ThemeMode.SYSTEM,
        onClick = { onChange(ThemeMode.SYSTEM) }
    )

    ThemeModeRow(
        label = "Claro",
        selected = current == ThemeMode.LIGHT,
        onClick = { onChange(ThemeMode.LIGHT) }
    )

    ThemeModeRow(
        label = "Escuro",
        selected = current == ThemeMode.DARK,
        onClick = { onChange(ThemeMode.DARK) }
    )
}

@Composable
private fun ThemeModeRow(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        RadioButton(selected = selected, onClick = onClick)
    }
}