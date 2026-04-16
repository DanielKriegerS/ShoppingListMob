package com.danielks.shoppinglist.core.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.feature.about.component.InfoTextSize

@Composable
fun InfoText(
    text : String,
    modifier: Modifier = Modifier,
    size: InfoTextSize = InfoTextSize.MEDIUM,
    styleOverride: TextStyle? = null
) {
    val baseStyle = when (size) {
        InfoTextSize.LARGE -> MaterialTheme.typography.titleMedium
        InfoTextSize.MEDIUM -> MaterialTheme.typography.bodyMedium
        InfoTextSize.SMALL -> MaterialTheme.typography.bodySmall
    }

    Row(modifier = modifier
        .padding(5.dp)
    ) {
    Text(
        text = text,
        modifier = modifier,
        style = styleOverride ?: baseStyle
        )
    }
}
