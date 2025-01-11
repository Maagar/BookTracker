package com.example.booktracker.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserPreferencesKeys {
    val TOKEN = stringPreferencesKey("token")
    val SORT_BY_DATE = stringPreferencesKey("sort_by_date")
    val SHOW_FINISHED = stringPreferencesKey("show_finished")
}

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val userSession: Flow<UserSession?> = context.dataStore.data
        .map { prefs ->
            val token = prefs[UserPreferencesKeys.TOKEN]
            if (token != null) {
                UserSession(token)
            } else null
        }

    val sortByDate: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[UserPreferencesKeys.SORT_BY_DATE]?.toBoolean() ?: true }

    val showFinished: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[UserPreferencesKeys.SHOW_FINISHED]?.toBoolean() ?: false }

    suspend fun saveUserSession(token: String) {
        context.dataStore.edit { prefs ->
            prefs[UserPreferencesKeys.TOKEN] = token
        }
    }

    suspend fun saveSortBy(sortByDate: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[UserPreferencesKeys.SORT_BY_DATE] = sortByDate.toString()
        }
    }

    suspend fun saveShowFinished(showFinished: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[UserPreferencesKeys.SHOW_FINISHED] = showFinished.toString()
        }
    }

    suspend fun clearUserSession() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }


}

data class UserSession(val token: String)