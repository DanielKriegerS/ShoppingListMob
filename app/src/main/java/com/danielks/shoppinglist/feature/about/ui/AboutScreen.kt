package com.danielks.shoppinglist.feature.about.ui

import android.R.attr.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.core.designsystem.component.PrimaryButton
import com.danielks.shoppinglist.core.designsystem.component.AppTopBar


@Composable
fun AboutScreen(
    onGoCreate: () -> Unit,
    onGoLists: () -> Unit,
    onGoFinalized: () -> Unit,
    onGoApiDown: () -> Unit,
    modifier: Modifier
) {
        Column(
            modifier = modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "App para criação e gerenciamento de listas de compras.",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))

            PrimaryButton(text = "Criar nova lista", modifier = Modifier.fillMaxWidth(), onClick = onGoCreate)
            PrimaryButton(text = "Ver listas ativas", modifier = Modifier.fillMaxWidth(), onClick = onGoLists)
            PrimaryButton(text = "Ver listas finalizadas", modifier = Modifier.fillMaxWidth(), onClick = onGoFinalized)

            Divider(Modifier.padding(vertical = 8.dp))

            // Botão só para testar fallback agora
            OutlinedButton(onClick = onGoApiDown, modifier = Modifier.fillMaxWidth()) {
                Text("Simular API indisponível (fallback)")
            }
        }
}
