package com.example.a3dmodelsapp.screens.userInfo

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.edit
import androidx.wear.compose.material3.Text
import com.example.a3dmodelsapp.R
import com.example.a3dmodelsapp.database.ApiClient
import com.example.a3dmodelsapp.screens.login.GetUserByLoginRequest
import com.example.a3dmodelsapp.screens.login.LoginActivity
import com.example.a3dmodelsapp.screens.login.UserResponse
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.backgroundColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.secondary
import com.example.a3dmodelsapp.ui.theme.textColor

@Composable
fun UserInfoScreen(userLogin: String) {

    var user by remember { mutableStateOf<UserResponse?>(null) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    val logoutDialogState = remember { mutableStateOf(false) }

    LaunchedEffect(userLogin) {
        try {
            user = ApiClient.authApi.getUserByLogin(GetUserByLoginRequest(userLogin))
            loading = false
        } catch (e: Exception) {
            error = e.message ?: "Failed to load user data"
            loading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)

    ) {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp))
        {
            if (userLogin.isEmpty()) {
                Text(
                    "Вы не вошли в аккаунт.",
                    style = CustomTextStyles.body1_regular
                )
            } else {
                when {
                    loading -> CircularProgressIndicator()
                    error != null -> Text("Error: $error", color = Color.Red)
                    user != null -> {
                        Text(
                            text = "Логин: ${user?.login ?: ""}",
                            color = textColor,
                            style = CustomTextStyles.body1_bold
                        )
                        androidx.compose.material3.Text(
                            text = "Почта: ${user?.email ?: ""}",
                            color = textColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }

        }

        if (userLogin.isEmpty()) {
            val context = LocalContext.current
            Button(
                onClick = { logout(context) },
                shape = CircleShape,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Войти",
                        fontFamily = fontFamily,
                        style = CustomTextStyles.body1_medium,
                        color = backgroundColor
                    )
                }
            }
        } else {
            Button(
                onClick = { logoutDialogState.value = true },
                shape = CircleShape,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.logout_24px),
                        contentDescription = "",
                        tint = backgroundColor
                    )
                    Text(
                        "Выйти",
                        fontFamily = fontFamily,
                        style = CustomTextStyles.body1_medium,
                        color = backgroundColor
                    )
                }
            }
            if (logoutDialogState.value) {
                ExitDialog(logoutDialogState)
            }
        }
    }
}

@Composable
fun ExitDialog(state: MutableState<Boolean>) {
    if (state.value) {
        Dialog(onDismissRequest = { state.value = false }) {
            Card(
                modifier = Modifier
                    .width(320.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = secondary
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.logout_24px),
                            contentDescription = "",
                            tint = primary,
                            modifier = Modifier.size(30.dp)
                        )
                        Text(
                            text = "Вы уверены, что хотите выйти из своей учетной записи?",
                            modifier = Modifier.fillMaxWidth(),
                            style = CustomTextStyles.body2_regular,
                            fontFamily = fontFamily
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val context = LocalContext.current
                        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                            TextButton(
                                onClick = {
                                    state.value = false
                                    logout(context)
                                },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = primary,
                                    containerColor = Color.Transparent
                                ),
                                elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
                            ) {
                                Text(
                                    text = "Да",
                                    fontFamily = fontFamily
                                )
                            }

                            TextButton(
                                onClick = { state.value = false },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = primary,
                                    containerColor = Color.Transparent
                                ),
                            ) {
                                Text(
                                    text = "Нет",
                                    fontFamily = fontFamily
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun logout(context: Context) {
    context.getSharedPreferences("user_preferences", MODE_PRIVATE).edit {
        remove("user_login")
    }

    val intent = Intent(context, LoginActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    context.startActivity(intent)
}

@Composable
@Preview
fun UserInfoScreenPrev() {
    val userLogin = "3d"
    UserInfoScreen(userLogin)
}