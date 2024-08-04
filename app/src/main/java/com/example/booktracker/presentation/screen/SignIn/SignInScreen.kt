package com.example.booktracker.presentation.screen.SignIn

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.booktracker.R
import com.example.booktracker.presentation.component.AuthInputField
import com.example.ui.theme.AppTypography

@Composable
fun SignInScreen(onClickSignUp: (() -> Unit)) {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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

        Column {
            AuthInputField(
                mainIcon = Icons.Outlined.Email,
                value = email,
                onValueChange = { email = it },
                label = stringResource(R.string.email),
                placeholder = stringResource(R.string.enter_email)
            )
            AuthInputField(
                mainIcon = Icons.Outlined.Lock,
                value = password,
                onValueChange = { password = it },
                label = stringResource(R.string.password),
                placeholder = stringResource(R.string.enter_password),
                keyboardType = KeyboardType.Password,
                isPasswordField = true,
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = { passwordVisible = !passwordVisible }
            )

            Text(
                text = stringResource(R.string.forgot_password),
                style = AppTypography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp).clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { /*TODO*/ }
                )
            )
        }
        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth(0.6f)) {
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
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClickSignUp
                )
            )
        }
    }


}