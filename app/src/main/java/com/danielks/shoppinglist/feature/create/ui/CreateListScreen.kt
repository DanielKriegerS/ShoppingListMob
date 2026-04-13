package com.danielks.shoppinglist.feature.create.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.core.designsystem.component.AppTopBar
import com.danielks.shoppinglist.feature.create.component.CreateListForm


@Composable
fun CreateListScreen(
    onBack: () -> Unit,
    modifier: Modifier
) {
    var listName by remember { mutableStateOf("") }
    var items by remember { mutableStateOf(listOf<String>()) }

        Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
            CreateListForm(
                listName = listName,
                onListNameChange = { listName = it },
                items = items,
                onAddItem = { name ->
                    if (name.isNotBlank()) items = items + name.trim()
                },
                onRemoveItem = { index ->
                    items = items.toMutableList().also { it.removeAt(index) }
                },
                onSave = {
                }
            )
        }
}
