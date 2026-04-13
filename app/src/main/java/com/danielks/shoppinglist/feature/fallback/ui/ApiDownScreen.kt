package com.danielks.shoppinglist.feature.fallback.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.danielks.shoppinglist.core.designsystem.component.AppTopBar
import com.danielks.shoppinglist.core.designsystem.component.ErrorState

@Composable
fun ApiDownScreen(
    onRetry: () -> Unit,
    modifier : Modifier
) {
        Box(modifier = modifier.padding()) {
            ErrorState(
                title = "API indisponível",
                message = "Não foi possível conectar ao servidor. Tente novamente em instantes.",
                primaryActionText = "Tentar novamente",
                onPrimaryAction = onRetry
            )
        }
}