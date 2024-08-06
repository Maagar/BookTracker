package com.example.booktracker.presentation.screen.SignIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
): ViewModel() {
    private val _email = MutableStateFlow("")
    val email:Flow<String> = _email

    private val _password = MutableStateFlow("")
    val password: Flow<String> = _password

    fun onEmailChange(email: String){
        _email.value = email
    }

    fun onPasswordChange(password: String){
        _password.value = password
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
        }
    }
}