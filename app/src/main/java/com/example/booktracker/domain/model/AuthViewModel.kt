package com.example.booktracker.domain.model

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
class AuthViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
): ViewModel() {
    private val _email = MutableStateFlow("")
    val email:Flow<String> = _email

    private val _password = MutableStateFlow("")
    val password: Flow<String> = _password

    private val _username = MutableStateFlow("")
    val username: Flow<String> = _username

    private val _isSignedIn = MutableStateFlow<Boolean>(false)
    val isSignedIn: Flow<Boolean> = _isSignedIn

    fun onEmailChange(email: String){
        _email.value = email
    }

    fun onPasswordChange(password: String){
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

    private val _signInResult = MutableStateFlow<Boolean?>(null)
    val signInResult: Flow<Boolean?> = _signInResult

    fun resetSignInResult() {
        _signInResult.value = null
    }

    fun onSignIn() {
        viewModelScope.launch{
            _signInResult.value = authenticationRepository.signIn(
                email = _email.value,
                password = _password.value
            )
            if (_signInResult.value!!) {
                authenticationRepository.saveToken()
            }
        }
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

    fun isUserSignedIn() {
        viewModelScope.launch {
            if(authenticationRepository.isUserSignedIn()) {
                _isSignedIn.value = true
            } else {
                _isSignedIn.value = false
            }
        }
    }
}