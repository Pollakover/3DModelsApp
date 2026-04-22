package com.example.a3dmodelsapp.screens.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.a3dmodelsapp.MainActivity
import com.example.a3dmodelsapp.ui.theme._3DModelsAppTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        val savedLogin = sharedPreferences.getString("user_login", null)

        if (!savedLogin.isNullOrEmpty()) {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("USER_LOGIN", savedLogin)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
            return
        }

        setContent {
            _3DModelsAppTheme{
                Scaffold(
                    content = { padding: PaddingValues ->
                        Column(
                            modifier = Modifier
                                .padding(padding)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Navigation()
                        }

                    }
                )
            }


        }
    }
}