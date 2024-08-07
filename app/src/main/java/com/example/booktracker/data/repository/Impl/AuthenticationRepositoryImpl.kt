package com.example.booktracker.data.repository.Impl

import com.example.booktracker.data.repository.AuthenticationRepository
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.serialization.json.JsonObject
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: Auth
): AuthenticationRepository{
    override suspend fun signIn(email: String, password: String): Boolean {
        return try {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun signUp(email: String, password: String, data: JsonObject): Boolean {
        return try {
            auth.signUpWith(Email) {
                this.email = email
                this.password = password
                this.data = data
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}