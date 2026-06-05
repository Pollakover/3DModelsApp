package com.example.a3dmodelsapp.screens.login

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a3dmodelsapp.screens.login.loginScreen.LoginScreen
import com.example.a3dmodelsapp.screens.login.signUpScreen.SignUpScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.SignupScreen.route) {
            SignUpScreen(navController = navController)
        }
    }
}