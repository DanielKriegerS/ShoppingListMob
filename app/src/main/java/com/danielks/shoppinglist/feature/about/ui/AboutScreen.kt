package com.danielks.shoppinglist.feature.about.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Sobre o app", style = MaterialTheme.typography.titleLarge)
        Text(
            "App para criação e gerenciamento de listas de compras.\n" +
                    "• Crie listas\n• Edite itens\n• Finalize e consulte depois",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
