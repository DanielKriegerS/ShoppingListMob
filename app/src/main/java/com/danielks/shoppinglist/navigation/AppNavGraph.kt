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

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController, startDestination = Destinations.ABOUT) {

        composable(Destinations.ABOUT) {
            AboutScreen(
                onGoCreate = { navController.navigate(Destinations.CREATE_LIST) },
                onGoLists = { navController.navigate(Destinations.LISTS) },
                onGoFinalized = { navController.navigate(Destinations.FINALIZED_LISTS) },
                onGoApiDown = { navController.navigate(Destinations.API_DOWN) }
            )
        }

        composable(Destinations.CREATE_LIST) {
            CreateListScreen(onBack = { navController.popBackStack() })
        }

        composable(Destinations.LISTS) {
            ListsScreen(
                onBack = { navController.popBackStack() },
                onOpenList = { id -> navController.navigate(Destinations.listDetail(id)) }
            )
        }

        composable(Destinations.FINALIZED_LISTS) {
            FinalizedListsScreen(
                onBack = { navController.popBackStack() },
                onOpenList = { id -> navController.navigate(Destinations.finalizedListDetail(id)) }
            )
        }

        composable(Destinations.NOT_FOUND) {
            NotFoundScreen(onBack = { navController.popBackStack() })
        }

        composable(Destinations.API_DOWN) {
            ApiDownScreen(onRetry = { navController.popBackStack() })
        }

        composable(
            route = Destinations.LIST_DETAIL,
            arguments = listOf(navArgument("listId") { type = NavType.StringType })
        ) { backStackEntry ->
            val listId = backStackEntry.arguments?.getString("listId").orEmpty()

            if (listId.isBlank()) {
                NotFoundScreen(onBack = { navController.popBackStack() })
            } else {
                ListDetailScreen(
                    listId = listId,
                    onBack = { navController.popBackStack() }
                )
            }
        }

        composable(
            route = Destinations.FINALIZED_LIST_DETAIL,
            arguments = listOf(navArgument("listId") { type = NavType.StringType })
        ) { backStackEntry ->
            val listId = backStackEntry.arguments?.getString("listId").orEmpty()

            if (listId.isBlank()) {
                NotFoundScreen(onBack = { navController.popBackStack() })
            } else {
                FinalizedListDetailScreen(
                    listId = listId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}