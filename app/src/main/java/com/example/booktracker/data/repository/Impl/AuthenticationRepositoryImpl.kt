package com.example.booktracker.data.repository.Impl

import com.example.booktracker.data.local.UserPreferences
import com.example.booktracker.data.repository.AuthenticationRepository
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.JsonObject
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: Auth,
    private val userPreferences: UserPreferences
) : AuthenticationRepository {

    override suspend fun signIn(email: String, password: String): Boolean {
        return runCatching {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            true
        }.getOrDefault(false)
    }

    override suspend fun signUp(email: String, password: String, data: JsonObject): Boolean {
        return runCatching {
            auth.signUpWith(Email) {
                this.email = email
                this.password = password
                this.data = data
            }
        }.isSuccess
    }

    override suspend fun saveToken() {
        val accessToken = auth.currentAccessTokenOrNull()
        if (accessToken != null) {
            userPreferences.saveUserSession(accessToken)
        }
    }

    override suspend fun isUserSignedIn(): Boolean {
        val userSession = userPreferences.userSession.firstOrNull()
        return if (userSession != null && userSession.token.isNotEmpty()) {
            runCatching {
                auth.retrieveUser(userSession.token)
                auth.refreshCurrentSession()
                saveToken()
            }.isSuccess
        } else {
            false
        }
    }

    override suspend fun checkAndRefreshSession() {
        val userSession = userPreferences.userSession.firstOrNull()
        if (userSession != null && userSession.token.isNotEmpty()) {
            runCatching {
                auth.retrieveUser(userSession.token)
                auth.refreshCurrentSession()
                val refreshedAccessToken = auth.currentAccessTokenOrNull()
                if (refreshedAccessToken != null) {
                    userPreferences.saveUserSession(refreshedAccessToken)
                }
            }.onFailure {
                userPreferences.clearUserSession()
            }
        }
    }

    override suspend fun signOut() {
        runCatching {
            auth.signOut()
        }.onFailure { e ->
            e.printStackTrace()
        }.also {
            userPreferences.clearUserSession()
        }
    }
}