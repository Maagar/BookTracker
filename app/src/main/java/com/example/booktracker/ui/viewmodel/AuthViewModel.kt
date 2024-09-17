package com.example.booktracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.repository.AuthenticationRepository
import com.example.booktracker.domain.model.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    private val _isSignedIn = MutableStateFlow<Boolean?>(null)
    val isSignedIn: Flow<Boolean?> = _isSignedIn

    val userState = _isSignedIn.map { hasSignedIn ->
        when (hasSignedIn) {
            true -> UserState.SignedIn
            false -> UserState.NotSignedIn
            else -> UserState.Loading
        }
    }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = UserState.Loading)

    init {
        viewModelScope.launch {
            authenticationRepository.checkAndRefreshSession()
        }
        checkIfUserSignedIn()
    }

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
            try {
                val result = authenticationRepository.signUp(
                    email = _email.value,
                    password = _password.value,
                    data = buildJsonObject { put("username", _username.value) }
                )
                if (result) {
                    onSignIn()
                }

            } catch (e:Exception) {

            }

        }
    }


    private fun checkIfUserSignedIn() {
        viewModelScope.launch {
            _isSignedIn.value = authenticationRepository.isUserSignedIn()
        }
    }
    
    fun onSignOut() {
        viewModelScope.launch {
            authenticationRepository.signOut()
        }
    }
}