package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "al_noor_settings")

class PreferenceManager(private val context: Context) {
    companion object {
        val KEY_LANGUAGE = stringPreferencesKey("language") // "bn" or "en"
        val KEY_THEME = stringPreferencesKey("theme") // "dark", "light", "system"
        val KEY_DISTRICT = stringPreferencesKey("district") // "Dhaka"
        val KEY_NOTIFICATION = booleanPreferencesKey("notifications_enabled")
    }

    val languageFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_LANGUAGE] ?: "bn" // Default to Bangla!
    }

    val themeFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_THEME] ?: "system"
    }

    val districtFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_DISTRICT] ?: "Dhaka"
    }

    val notificationsEnabledFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[KEY_NOTIFICATION] ?: true
    }

    suspend fun saveLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_LANGUAGE] = language
        }
    }

    suspend fun saveTheme(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_THEME] = theme
        }
    }

    suspend fun saveDistrict(district: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_DISTRICT] = district
        }
    }

    suspend fun saveNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_NOTIFICATION] = enabled
        }
    }
}
