package com.example.a3dmodelsapp.database

import com.example.a3dmodelsapp.database.categories.CategoryApi
import com.example.a3dmodelsapp.database.models.ModelApi
import com.example.a3dmodelsapp.screens.login.AuthApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://192.168.1.6:8080/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApi : AuthApi = retrofit.create(AuthApi::class.java)

    val modelApi: ModelApi = retrofit.create(ModelApi::class.java)

    val categoryApi: CategoryApi = retrofit.create(CategoryApi::class.java)
}