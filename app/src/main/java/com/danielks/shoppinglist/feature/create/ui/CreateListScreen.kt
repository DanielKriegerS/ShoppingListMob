package com.danielks.shoppinglist.feature.create.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danielks.shoppinglist.feature.create.component.CreateListForm

@Composable
fun CreateListScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateListViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    CreateListForm(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        state = uiState,
        onListNameChange = viewModel::onListNameChange,
        onAddItem = viewModel::onAddItem,
        onRemoveItem = viewModel::onRemoveItem,
        onSave = viewModel::onSave
    )
}