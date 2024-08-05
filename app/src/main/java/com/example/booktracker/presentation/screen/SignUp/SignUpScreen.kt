package com.example.booktracker.presentation.screen.SignUp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.booktracker.R
import com.example.booktracker.presentation.component.AuthInputField
import com.example.ui.theme.AppTypography

@Composable
fun SignUpScreen(viewModel: SignUpViewModel = hiltViewModel()) {
    val email = viewModel.email.collectAsState(initial = "")
    var username by remember { mutableStateOf("") }
    val password = viewModel.password.collectAsState(initial = "")
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = stringResource(R.string.sign_up),
            style = AppTypography.displaySmall
        )

        Column {
            AuthInputField(
                mainIcon = Icons.Outlined.Person,
                value = username,
                onValueChange = {username = it},
                label = stringResource(R.string.username),
                placeholder = stringResource(R.string.enter_username)
            )
            AuthInputField(
                mainIcon = Icons.Outlined.Email,
                value = email.value,
                onValueChange = { viewModel.onEmailChange(it) },
                label = stringResource(R.string.email),
                placeholder = stringResource(R.string.enter_email)
            )
            AuthInputField(
                mainIcon = Icons.Outlined.Lock,
                value = password.value,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = stringResource(R.string.password),
                placeholder = stringResource(R.string.enter_password),
                keyboardType = KeyboardType.Password,
                isPasswordField = true,
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = { passwordVisible = !passwordVisible }
            )

        }

        Button(onClick = { viewModel.onSignUp() }, modifier = Modifier.fillMaxWidth(0.6f)) {
            Text(
                text = stringResource(R.string.sign_up),
                style = AppTypography.bodyLarge
            )

        }
    }
}