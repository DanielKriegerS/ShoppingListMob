package com.danielks.shoppinglist.feature.fallback.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.danielks.shoppinglist.core.designsystem.component.EmptyState
import com.danielks.shoppinglist.core.designsystem.component.PrimaryButton

@Composable
fun NotFoundScreen(
    onBack: () -> Unit,
    modifier: Modifier
) {
        Box(modifier = modifier.padding()) {
            EmptyState(
                title = "Lista não encontrada",
                message = "A lista que você tentou abrir não existe ou foi removida.",
                action = { PrimaryButton(text = "Voltar", onClick = onBack) }
            )
        }
}
