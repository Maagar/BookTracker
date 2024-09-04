package com.example.booktracker.presentation.screen.SignIn

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.booktracker.R
import com.example.booktracker.ui.viewmodel.AuthViewModel
import com.example.booktracker.presentation.component.AuthInputField
import com.example.booktracker.presentation.component.ErrorSnackbar
import com.example.booktracker.presentation.component.icon.Lock
import com.example.booktracker.presentation.component.icon.Mail
import com.example.booktracker.utils.validateEmail
import com.example.booktracker.utils.validatePassword
import com.example.ui.theme.AppTypography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    toSignUpScreen: (() -> Unit),
    toHomeScreen: (() -> Unit),
    signInViewModel: AuthViewModel = hiltViewModel(),
) {
    val email = signInViewModel.email.collectAsState(initial = "")
    val emailError = remember { mutableStateOf<String?>(null) }
    val password = signInViewModel.password.collectAsState(initial = "")
    val passwordError = remember { mutableStateOf<String?>(null) }
    var passwordVisible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val swipeState = rememberSwipeToDismissBoxState()

    val signInResult by signInViewModel.signInResult.collectAsStateWithLifecycle(initialValue = null)

    SnackbarHost(hostState = snackbarHostState) { snackbarData ->
        ErrorSnackbar(snackbarData = snackbarData, state = swipeState)
    }

    LaunchedEffect(signInResult) {
        signInResult?.let { success ->
            if (!success) {
                signInViewModel.resetPassword()
                signInViewModel.resetSignInResult()
                snackbarHostState.currentSnackbarData?.dismiss()
                swipeState.snapTo(SwipeToDismissBoxValue.Settled)
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Sign in failed.",
                        duration = SnackbarDuration.Short,
                    )
                }
            } else if (success){
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
            text = "Sign in\nto BookTracker",
            style = AppTypography.displaySmall,
            textAlign = TextAlign.Start
        )

        Column(horizontalAlignment = Alignment.End) {
            AuthInputField(
                mainIcon = Mail,
                value = email.value,
                onValueChange = { signInViewModel.onEmailChange(it) },
                label = stringResource(R.string.email),
                placeholder = stringResource(R.string.enter_email),
                errorMessage = emailError,
            )
            AuthInputField(
                mainIcon = Lock,
                value = password.value,
                onValueChange = { signInViewModel.onPasswordChange(it) },
                label = stringResource(R.string.password),
                placeholder = stringResource(R.string.enter_password),
                keyboardType = KeyboardType.Password,
                isPasswordField = true,
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
                errorMessage = passwordError
            )

            Text(
                text = stringResource(R.string.forgot_password),
                style = AppTypography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { /*TODO*/ }
                    )
            )
        }
        Button(
            onClick = {
                emailError.value = validateEmail(email.value)
                passwordError.value = validatePassword(password.value)
                if (emailError.value.isNullOrBlank() && passwordError.value.isNullOrBlank()) {
                    signInViewModel.onSignIn()
                }
            },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                style = AppTypography.bodyLarge
            )
        }

        HorizontalDivider()

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.new_to_booktracker),
                style = AppTypography.labelMedium,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.create_an_account),
                style = AppTypography.labelMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = toSignUpScreen
                )
            )
        }
    }


}