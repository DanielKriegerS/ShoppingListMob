package com.danielks.shoppinglist.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val drawerItems = listOf(
    DrawerItem(Destinations.ABOUT, "Home", Icons.Outlined.Home),
    DrawerItem(Destinations.CREATE_LIST, "Criar lista", Icons.Outlined.AddCircle),
    DrawerItem(Destinations.LISTS, "Listas ativas", Icons.Outlined.List),
    DrawerItem(Destinations.FINALIZED_LISTS, "Finalizadas", Icons.Outlined.Done),
)
