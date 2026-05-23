package com.example.a3dmodelsapp.screens.login.loginScreen

import android.app.Activity.MODE_PRIVATE
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.core.content.edit
import com.example.a3dmodelsapp.MainActivity
import com.example.a3dmodelsapp.R
import com.example.a3dmodelsapp.database.ApiClient
import com.example.a3dmodelsapp.screens.login.AuthResponse
import com.example.a3dmodelsapp.screens.login.LoginRequest
import com.example.a3dmodelsapp.screens.login.Screen
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.borderColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.gradient1
import com.example.a3dmodelsapp.ui.theme.primary
import com.example.a3dmodelsapp.ui.theme.secondary


@Composable
fun LoginScreen(navController: NavController) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .padding(10.dp, 0.dp, 10.dp, 0.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(R.drawable.icon),
                    modifier = Modifier.size(150.dp),
                    contentDescription = "Logo"
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = secondary
                    ),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Войдите в аккаунт",
                            fontFamily = fontFamily,
                            style = CustomTextStyles.heading_large
                        )
//                        Text(
//                            text = "Заполните поля для входа в систему.",
//                            fontFamily = fontFamily,
//                            style = CustomTextStyles.body1_regular
//                        )
                        //Поля ввода
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {

                                Text(
                                    text = "Логин",
                                    fontFamily = fontFamily,
                                    style = CustomTextStyles.body2_medium
                                )

                                BasicTextField(
                                    value = login,
                                    onValueChange = { newText ->
                                        if (newText.length <= 25) {
                                            login = newText
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(
                                            1.dp,
                                            color = borderColor,
                                            CircleShape,
                                            //shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(14.dp, 10.dp, 14.dp, 10.dp),
                                    textStyle = TextStyle(
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontSize = 16.sp,
                                        lineHeight = 24.sp,
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = fontFamily
                                    ),
                                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface), // Цвет курсора
                                    decorationBox = { innerTextField ->
                                        Box(
                                            modifier = Modifier.fillMaxWidth(),
                                            contentAlignment = Alignment.CenterStart
                                        ) {
                                            if (login.isEmpty()) {
                                                Text(
                                                    text = "Введите логин",
                                                    fontFamily = fontFamily,
                                                    style = CustomTextStyles.body1_regular
                                                )
                                            }
                                            innerTextField()
                                        }
                                    },
                                    singleLine = true
                                )
                            }

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "Пароль",
                                    fontFamily = fontFamily,
                                    style = CustomTextStyles.body2_medium
                                )

                                BasicTextField(
                                    value = password,
                                    onValueChange = { newText ->
                                        if (newText.length <= 25) {
                                            password = newText
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(
                                            1.dp,
                                            color = borderColor,
                                            CircleShape,
                                        )
                                        .padding(14.dp, 10.dp, 14.dp, 10.dp),
                                    textStyle = TextStyle(
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontSize = 16.sp,
                                        lineHeight = 24.sp,
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = fontFamily
                                    ),
                                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                                    decorationBox = { innerTextField ->
                                        Box(
                                            modifier = Modifier.fillMaxWidth(),
                                            contentAlignment = Alignment.CenterStart
                                        ) {
                                            if (password.isEmpty()) {
                                                Text(
                                                    text = "Введите пароль",
                                                    fontFamily = fontFamily,
                                                    style = CustomTextStyles.body1_regular
                                                )
                                            }
                                            innerTextField()
                                        }
                                    },
                                    singleLine = true
                                )
                            }

                            val context = LocalContext.current
                            Button(
                                onClick = {
                                    if (checkFields(context, login, password)) {
                                        loginUser(login, password, context)
                                    }
                                },
                                shape = CircleShape,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                )
                            ) {
                                Text(
                                    "Войти",
                                    fontFamily = fontFamily,
                                    style = CustomTextStyles.body1_medium,
                                    modifier = Modifier
                                        .padding(5.dp)
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "У вас нет аккаунта?",
                                    fontFamily = fontFamily,
                                    style = CustomTextStyles.body2_regular
                                )
                                Text(
                                    text = "Зарегистрируйтесь",
                                    fontFamily = fontFamily,
                                    color = primary,
                                    style = CustomTextStyles.body2_medium,
                                    modifier = Modifier.clickable {
                                        navController.navigate(Screen.SignupScreen.route) {
                                            popUpTo(navController.graph.startDestinationId)
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }


                        }
                    }


                }
                val context1 = LocalContext.current
                Box(
                    modifier = Modifier
                        .padding(3.dp)
                        .clip(CircleShape)
                        .clickable {
                            val intent = Intent(context1, MainActivity::class.java).apply {
                                putExtra("", "")
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                            context1.startActivity(intent)
                        }
                        .clip(CircleShape)
                ) {
                    Text(
                        text = "Продолжить без аккаунта",
                        fontFamily = fontFamily,
                        color = borderColor,
                        style = CustomTextStyles.body2_regular,
                        modifier = Modifier
                            .padding(3.dp)
                    )
                }
            }
        }
    }
}

private fun loginUser(login: String, password: String, context: Context) {
    val call = ApiClient.authApi.login(LoginRequest(login, password))
    call.enqueue(object : Callback<AuthResponse> {
        override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
            if (response.isSuccessful) {
                // Сохраняем только логин пользователя
                saveUserLogin(context, login)

                val intent = Intent(context, MainActivity::class.java).apply {
                    putExtra("USER_LOGIN", login)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)
                Toast.makeText(context, "Успешный вход!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Ошибка входа", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
            Toast.makeText(context, "Ошибка сети: ${t.message}", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "ERROR: ${t.message.toString()}")
        }
    })
}

private fun saveUserLogin(context: Context, login: String) {
    context.getSharedPreferences("user_preferences", MODE_PRIVATE).edit {
        putString("user_login", login)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    val navController = rememberNavController()
    LoginScreen(navController)
}

fun checkFields(
    context: Context,
    login: String,
    password: String

): Boolean {
    // Проверка на пустые поля
    if (login.isBlank() || password.isBlank()) {
        Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}