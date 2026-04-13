package com.danielks.shoppinglist.core.settings

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class ThemePreferences(private val context: Context) {

    companion object {
        private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
    }

    val themeMode: Flow<ThemeMode> =
        context.dataStore.data.map { prefs ->
            val raw = prefs[THEME_MODE_KEY] ?: ThemeMode.SYSTEM.name
            runCatching { ThemeMode.valueOf(raw) }.getOrElse { ThemeMode.SYSTEM }
        }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { prefs ->
            prefs[THEME_MODE_KEY] = mode.name
        }
    }
}
