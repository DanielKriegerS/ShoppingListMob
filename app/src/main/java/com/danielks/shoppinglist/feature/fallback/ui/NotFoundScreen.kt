package com.danielks.shoppinglist.feature.fallback.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.danielks.shoppinglist.core.designsystem.component.AppTopBar
import com.danielks.shoppinglist.core.designsystem.component.EmptyState
import com.danielks.shoppinglist.core.designsystem.component.PrimaryButton

@Composable
fun NotFoundScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = { AppTopBar(title = "Não encontrado", showBack = true, onBack = onBack) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            EmptyState(
                title = "Lista não encontrada",
                message = "A lista que você tentou abrir não existe ou foi removida.",
                action = { PrimaryButton(text = "Voltar", onClick = onBack) }
            )
        }
    }
}
