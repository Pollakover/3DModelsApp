package com.example.a3dmodelsapp.screens.login.loginScreen

import android.app.Activity.MODE_PRIVATE
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
import androidx.core.content.edit
import com.example.a3dmodelsapp.R
import com.example.a3dmodelsapp.screens.login.Screen
import com.example.a3dmodelsapp.ui.theme.CustomTextStyles
import com.example.a3dmodelsapp.ui.theme.borderColor
import com.example.a3dmodelsapp.ui.theme.fontFamily
import com.example.a3dmodelsapp.ui.theme.primary


@Composable
fun LoginScreen(navController: NavController) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            //.background(//color = MaterialTheme.colorScheme.surface)
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
                    modifier = Modifier.size(100.dp),
                    contentDescription = "Logo"
                )

                Text(
                    text = "Войдите в аккаунт",
                    fontFamily = fontFamily,
                    ////color = MaterialTheme.colorScheme.onSurface,
                    style = CustomTextStyles.heading1_semi_bold
                )
                Text(
                    text = "Заполните поля для входа в систему.",
                    fontFamily = fontFamily,
                    ////color = MaterialTheme.colorScheme.onSecondary,
                    style = CustomTextStyles.body1_regular
                )

            }

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
                        ////color = MaterialTheme.colorScheme.onBackground,
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
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                1.dp,
                                color = borderColor,
                                shape = RoundedCornerShape(8.dp)
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
                                        ////color = MaterialTheme.colorScheme.onSecondary,
                                        style = CustomTextStyles.body1_regular
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Пароль",
                        fontFamily = fontFamily,
                        ////color = MaterialTheme.colorScheme.onBackground,
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
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                1.dp,
                                color = borderColor,
                                shape = RoundedCornerShape(8.dp)
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
                                        ////color = MaterialTheme.colorScheme.onSecondary,
                                        style = CustomTextStyles.body1_regular
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }

                val context = LocalContext.current
                Button(
                    onClick = {
                        if (checkFields(context, login, password)) {
                            //loginUser(login, password, context)
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        //containerColor = primary_500,
                        //contentColor = white
                    )
                ) {
                    Text(
                        "Войти",
                        fontFamily = fontFamily,
                        ////color = white,
                        style = CustomTextStyles.body1_medium,
                        modifier = Modifier
                            .padding(5.dp)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "У вас нет аккаунта?",
                        fontFamily = fontFamily,
                        ////color = MaterialTheme.colorScheme.onSecondary,
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
}

//private fun loginUser(login: String, password: String, context: Context) {
//    val call = ApiClient.authApi.login(LoginRequest(login, password))
//    call.enqueue(object : Callback<AuthResponse> {
//        override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
//            if (response.isSuccessful) {
//                // Сохраняем только логин пользователя
//                saveUserLogin(context, login)
//
//                val intent = Intent(context, MainActivity::class.java).apply {
//                    putExtra("USER_LOGIN", login)
//                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                }
//                context.startActivity(intent)
//                Toast.makeText(context, "Успешный вход!", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Ошибка входа", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
//            Toast.makeText(context, "Ошибка сети: ${t.message}", Toast.LENGTH_SHORT).show()
//            Log.d(TAG, "ERROR: ${t.message.toString()}")
//        }
//    })
//}

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