package com.danielks.shoppinglist.feature.finalized.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.core.designsystem.component.AppTopBar
import com.danielks.shoppinglist.feature.finalizeddetail.ui.FinalizedListCard
import com.danielks.shoppinglist.preview.PreviewData

@Composable
fun FinalizedListsScreen(
    onBack: () -> Unit,
    onOpenList: (String) -> Unit
) {
    val data = listOf(PreviewData.finalized1)

    Scaffold(
        topBar = { AppTopBar(title = "Listas Finalizadas", showBack = true, onBack = onBack) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(data, key = { it.id }) { list ->
                FinalizedListCard(listName = list.name, itemsCount = list.items.size) {
                    onOpenList(list.id)
                }
            }
        }
    }
}