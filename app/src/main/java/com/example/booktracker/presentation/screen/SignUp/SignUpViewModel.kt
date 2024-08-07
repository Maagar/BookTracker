package com.example.booktracker.presentation.screen.SignUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
): ViewModel() {
    private val _email = MutableStateFlow("")
    val email: Flow<String> = _email

    private val _password = MutableStateFlow("")
    val password: Flow<String> = _password

    private val _username = MutableStateFlow("")
    val username: Flow<String> = _username

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onUsernameChange(username: String) {
        _username.value = username
    }

    fun resetEmail() {
        _password.value = ""
    }

    fun resetPassword() {
        _password.value = ""
    }

    fun onSignUp() {
        viewModelScope.launch {
            authenticationRepository.signUp(
                email = _email.value,
                password = _password.value,
                data = buildJsonObject { put("username", _username.value) }
            )
        }
    }
}