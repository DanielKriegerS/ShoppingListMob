package com.danielks.shoppinglist.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.feature.about.component.InfoTextSize

@Composable
fun InfoList(
    lines: List<String>,
    modifier: Modifier = Modifier,
    size: InfoTextSize = InfoTextSize.MEDIUM,
    verticalSpacingDp: Int = 6
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(verticalSpacingDp.dp)
    ) {
        lines.forEach { line ->
            InfoText(text = line, size = size)
        }
    }
}