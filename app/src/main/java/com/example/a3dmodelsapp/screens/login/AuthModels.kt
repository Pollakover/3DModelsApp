package com.example.a3dmodelsapp.screens.login

data class LoginRequest(
    val login: String,
    val password: String
)

data class RegisterRequest(
    val login: String,
    val password: String,
    val email: String
)

data class AuthResponse(
    val token: String
)

data class GetUserByLoginRequest(
    val login: String
)

data class UserResponse(
    val login: String,
    val email: String?
)