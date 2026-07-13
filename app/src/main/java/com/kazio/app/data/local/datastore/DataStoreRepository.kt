package com.kazio.app.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.kazio.app.domain.model.UserPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "kazio_preferences")

@Singleton
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferencesKeys {
        val IS_REGISTERED = booleanPreferencesKey("is_registered")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_PIN = stringPreferencesKey("user_pin")
        val IS_ONBOARDING_SEEN = booleanPreferencesKey("is_onboarding_seen")
    }

    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val isRegistered = preferences[PreferencesKeys.IS_REGISTERED] ?: false
            val userName = preferences[PreferencesKeys.USER_NAME] ?: ""
            val userPin = preferences[PreferencesKeys.USER_PIN] ?: ""
            val isOnboardingSeen = preferences[PreferencesKeys.IS_ONBOARDING_SEEN] ?: false

            UserPreferences(
                isRegistered = isRegistered,
                userName = userName,
                userPin = userPin,
                isOnboardingSeen = isOnboardingSeen
            )
        }

    suspend fun registerUser(name: String, pin: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = name
            preferences[PreferencesKeys.USER_PIN] = pin
            preferences[PreferencesKeys.IS_REGISTERED] = true
        }
    }

    suspend fun updateOnboardingSeen(seen: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_ONBOARDING_SEEN] = seen
        }
    }

    suspend fun clearUser() {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_REGISTERED] = false
            preferences[PreferencesKeys.USER_NAME] = ""
            preferences[PreferencesKeys.USER_PIN] = ""
        }
    }
}
