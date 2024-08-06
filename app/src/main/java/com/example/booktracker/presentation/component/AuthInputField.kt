package com.example.booktracker.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.booktracker.R

@Composable
fun AuthInputField(
    mainIcon: ImageVector,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordField: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordVisibilityChange: (() -> Unit)? = null,
    errorMessage: MutableState<String?>,
) {
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
        Icon(
            imageVector = mainIcon,
            contentDescription = "",
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            visualTransformation = if (isPasswordField && !passwordVisible) PasswordVisualTransformation()
            else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            placeholder = { Text(text = placeholder) },
            singleLine = true,
            modifier = Modifier
                .width(280.dp)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused && !isFocused) {
                        errorMessage.value = null
                        isFocused = focusState.isFocused
                    }
                }
                .focusRequester(focusRequester),
            isError = (!errorMessage.value.isNullOrBlank()),
            supportingText = {
                if (!errorMessage.value.isNullOrBlank()) {
                    Text(text = errorMessage.value!!)
                }
            },
            trailingIcon = {
                if (isPasswordField && onPasswordVisibilityChange != null) {
                    val visibilityIcon =
                        if (passwordVisible) rememberVisibilityOff() else rememberVisibility()
                    val description = if (passwordVisible)
                        stringResource(R.string.password_is_visible) else stringResource(R.string.password_is_not_visible)
                    IconButton(onClick = onPasswordVisibilityChange) {
                        Icon(
                            imageVector = visibilityIcon,
                            contentDescription = description,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        )
    }
}