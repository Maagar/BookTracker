package com.example.booktracker.data.repository

import kotlinx.serialization.json.JsonObject

interface AuthenticationRepository {
    suspend fun signIn(email: String, password: String): Boolean
    suspend fun signUp(email: String, password: String, data: JsonObject): Boolean
    suspend fun saveToken()
    suspend fun isUserSignedIn(): Boolean
    suspend fun signOut()
}