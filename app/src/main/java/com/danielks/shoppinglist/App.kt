package com.danielks.shoppinglist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.danielks.shoppinglist.core.designsystem.theme.ShoppingTheme
import com.danielks.shoppinglist.core.settings.ThemeMode
import com.danielks.shoppinglist.core.settings.ThemePreferences
import com.danielks.shoppinglist.navigation.AppRoot
import kotlinx.coroutines.launch


@Composable
fun App() {
    val context = LocalContext.current
    val prefs = remember { ThemePreferences(context) }
    val scope = rememberCoroutineScope()

    val themeMode by prefs.themeMode.collectAsStateWithLifecycle(initialValue = ThemeMode.SYSTEM)

    // Decide se o app vai estar em dark baseado no modo escolhido:
    ShoppingTheme(
        // se for SYSTEM, o AppTheme usa isSystemInDarkTheme() internamente
        // então aqui passamos o modo e calculamos dentro do AppTheme (vamos ajustar o AppTheme abaixo)
        themeMode = themeMode,
        dynamicColor = true
    ) {
        AppRoot(
            themeMode = themeMode,
            onThemeModeChange = { mode ->
                scope.launch { prefs.setThemeMode(mode) }
            }
        )
    }
}
