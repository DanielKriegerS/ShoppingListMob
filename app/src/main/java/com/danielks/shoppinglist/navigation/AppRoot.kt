package com.danielks.shoppinglist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.danielks.shoppinglist.feature.about.ui.AboutScreen
import com.danielks.shoppinglist.feature.create.ui.CreateListScreen
import com.danielks.shoppinglist.feature.fallback.ui.ApiDownScreen
import com.danielks.shoppinglist.feature.fallback.ui.NotFoundScreen
import com.danielks.shoppinglist.feature.finalized.ui.FinalizedListsScreen
import com.danielks.shoppinglist.feature.finalizeddetail.ui.FinalizedListDetailScreen
import com.danielks.shoppinglist.feature.listdetail.ui.ListDetailScreen
import com.danielks.shoppinglist.feature.lists.ui.ListsScreen
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.danielks.shoppinglist.core.appinfo.AppInfo
import com.danielks.shoppinglist.core.designsystem.component.AppFooter
import kotlinx.coroutines.launch
import com.danielks.shoppinglist.core.designsystem.component.AppTopBar
import com.danielks.shoppinglist.core.designsystem.component.ThemeModeSection
import com.danielks.shoppinglist.core.designsystem.component.TitleRow
import com.danielks.shoppinglist.core.settings.ThemeMode
import com.danielks.shoppinglist.core.ui.UiState
import com.danielks.shoppinglist.preview.PreviewData

@Composable
fun AppRoot(
    navController: NavHostController = rememberNavController(),
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit,
    appInfo: AppInfo
    ) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val topLevelRoutes = remember {
        setOf(
            Destinations.ABOUT,
            Destinations.CREATE_LIST,
            Destinations.LISTS,
            Destinations.FINALIZED_LISTS
        )
    }

    val isTopLevel = currentRoute in topLevelRoutes
    val topBarTitle = when (currentRoute) {
        Destinations.ABOUT -> "Home"
        Destinations.CREATE_LIST -> "Criar lista"
        Destinations.LISTS -> "Listas ativas"
        Destinations.FINALIZED_LISTS -> "Finalizadas"
        Destinations.API_DOWN -> "Servidor offline"
        Destinations.NOT_FOUND -> "Não encontrado"
        else -> "Detalhe"
    }
    val showFooter = shouldShowFooter(currentRoute)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                TitleRow("Shopping List")
                drawerItems.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item.label) },
                        selected = currentRoute == item.route,
                        icon = { Icon(item.icon, contentDescription = null) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(item.route) {
                                popUpTo(Destinations.ABOUT) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }


                Divider(Modifier.padding(vertical = 8.dp))


                ThemeModeSection(
                    current = themeMode,
                    onChange = onThemeModeChange
                )
            }
        }
    ) {


        Scaffold(
            topBar = {
                AppTopBar(
                    title = topBarTitle,
                    showBack = !isTopLevel,
                    onBack = {
                        navController.popBackStack()
                    },
                    showMenu = isTopLevel,
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            },
            bottomBar = {
                if (showFooter) {
                    AppFooter(
                        appName = appInfo.appName,
                        versionName = appInfo.versionName,
                        credits = appInfo.credits
                    )
                }
            }
                ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Destinations.ABOUT,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Destinations.ABOUT) {
                    AboutScreen(
                        modifier = Modifier
                    )
                }

                composable(Destinations.CREATE_LIST) {
                    CreateListScreen(
                        onBack = { navController.popBackStack() },
                        modifier = Modifier
                    )
                }

                composable(Destinations.LISTS) {
                    ListsScreen(
                        onBack = { navController.popBackStack() },
                        onOpenList = { id -> navController.navigate(Destinations.listDetail(id)) },
                        state = UiState.Success(listOf(PreviewData.active1, PreviewData.active2)),
                        modifier = Modifier
                    )
                }

                composable(Destinations.FINALIZED_LISTS) {
                    FinalizedListsScreen(
                        onBack = { navController.popBackStack() },
                        onOpenList = { id ->
                            navController.navigate(
                                Destinations.finalizedListDetail(
                                    id
                                )
                            )
                        },
                        modifier = Modifier
                    )
                }

                composable(
                    route = Destinations.LIST_DETAIL,
                    arguments = listOf(navArgument("listId") { type = NavType.StringType })
                ) { entry ->
                    val listId = entry.arguments?.getString("listId").orEmpty()
                    if (listId.isBlank()) {
                        NotFoundScreen(
                            onBack = { navController.popBackStack() },
                            modifier = Modifier
                        )
                    } else {
                        ListDetailScreen(
                            listId = listId, onBack = { navController.popBackStack() },
                            modifier = Modifier
                        )
                    }
                }

                composable(
                    route = Destinations.FINALIZED_LIST_DETAIL,
                    arguments = listOf(navArgument("listId") { type = NavType.StringType })
                ) { entry ->
                    val listId = entry.arguments?.getString("listId").orEmpty()
                    if (listId.isBlank()) {
                        NotFoundScreen(
                            onBack = { navController.popBackStack() },
                            modifier = Modifier
                        )
                    } else {
                        FinalizedListDetailScreen(
                            listId = listId, onBack = { navController.popBackStack() },
                            modifier = Modifier
                        )
                    }
                }

                composable(Destinations.NOT_FOUND) {
                    NotFoundScreen(
                        onBack = { navController.popBackStack() },
                        modifier = Modifier
                    )
                }

                composable(Destinations.API_DOWN) {
                    ApiDownScreen(
                        onRetry = { navController.popBackStack() },
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

private fun shouldShowFooter(route: String?): Boolean {
    if (route == null) return true

    if (route == Destinations.ABOUT) return false
    if (route == Destinations.API_DOWN) return false
    if (route == Destinations.NOT_FOUND) return false

    if (route.startsWith("list_detail/")) return false
    if (route.startsWith("finalized_list_detail/")) return false

    return true
}