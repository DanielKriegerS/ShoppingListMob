package com.danielks.shoppinglist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.danielks.shoppinglist.app.appinfo.AndroidAppInfoProvider
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
    val appInfoProvider = remember { AndroidAppInfoProvider(context) }
    val appInfo = remember { appInfoProvider.get() }


    val themeMode by prefs.themeMode.collectAsStateWithLifecycle(initialValue = ThemeMode.SYSTEM)

    ShoppingTheme(
        themeMode = themeMode,
        dynamicColor = true
    ) {
        AppRoot(
            themeMode = themeMode,
            onThemeModeChange = { mode ->
                scope.launch { prefs.setThemeMode(mode) }
            },
            appInfo = appInfo
        )
    }
}
