package com.example.a3dmodelsapp.database.models

data class Model(
    val id: Int,
    override val name: String,
    val description: String,
    val width: Double,
    val height: Double,
    val length: Double,
    val size: Double,
    val file_url: String,
    val image_url: String,
    val user_login: String,
): Searchable
