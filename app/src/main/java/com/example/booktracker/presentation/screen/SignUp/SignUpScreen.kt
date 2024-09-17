package com.example.booktracker.presentation.screen.SignUp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.booktracker.R
import com.example.booktracker.ui.viewmodel.AuthViewModel
import com.example.booktracker.presentation.component.AuthInputField
import com.example.booktracker.presentation.component.icon.Lock
import com.example.booktracker.presentation.component.icon.Mail
import com.example.booktracker.presentation.component.icon.Person
import com.example.booktracker.utils.validateEmail
import com.example.booktracker.utils.validatePassword
import com.example.booktracker.utils.validateUsername
import com.example.ui.theme.AppTypography

@Composable
fun SignUpScreen(viewModel: AuthViewModel = hiltViewModel(), toSignInScreen: (() -> Unit), toHomeScreen: ()-> Unit) {
    val email = viewModel.email.collectAsState(initial = "")
    val emailError = remember { mutableStateOf<String?>(null) }
    val username = viewModel.username.collectAsState(initial = "")
    val usernameError = remember { mutableStateOf<String?>(null) }
    val password = viewModel.password.collectAsState(initial = "")
    val passwordError = remember { mutableStateOf<String?>(null) }
    var passwordVisible by remember { mutableStateOf(false) }

    val signInResult by viewModel.signInResult.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(signInResult) {
        signInResult?.let { success ->
            if (success){
                toHomeScreen()
            }
        }
    }

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
                mainIcon = Person,
                value = username.value,
                onValueChange = { viewModel.onUsernameChange(it) },
                label = stringResource(R.string.username),
                placeholder = stringResource(R.string.enter_username),
                errorMessage = usernameError
            )
            AuthInputField(
                mainIcon = Mail,
                value = email.value,
                onValueChange = { viewModel.onEmailChange(it) },
                label = stringResource(R.string.email),
                placeholder = stringResource(R.string.enter_email),
                errorMessage = emailError
            )
            AuthInputField(
                mainIcon = Lock,
                value = password.value,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = stringResource(R.string.password),
                placeholder = stringResource(R.string.enter_password),
                keyboardType = KeyboardType.Password,
                isPasswordField = true,
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
                errorMessage = passwordError
            )

        }

        Button(onClick = {
            emailError.value = validateEmail(email.value)
            passwordError.value = validatePassword(password.value)
            usernameError.value = validateUsername(username.value)
            if (emailError.value.isNullOrBlank() && passwordError.value.isNullOrBlank() && usernameError.value.isNullOrBlank()) {
                viewModel.onSignUp()
            }
        }, modifier = Modifier.fillMaxWidth(0.6f)) {
            Text(
                text = stringResource(R.string.sign_up),
                style = AppTypography.bodyLarge
            )

        }

        HorizontalDivider()

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.already_have_an_account),
                style = AppTypography.labelMedium,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Sign in",
                style = AppTypography.labelMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = toSignInScreen
                )
            )
        }
    }
}