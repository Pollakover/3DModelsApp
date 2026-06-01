package com.example.a3dmodelsapp.database.categories

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CategoryApi {

    @POST("categories/fetch")
    suspend fun fetchCategories(@Body request: FetchCategoriesRequest): List<Category>

    @POST("categories/add")
    suspend fun addCategory(@Body request: AddCategoryRequest)
}

data class FetchCategoriesRequest(
    val model_id: Int
)

data class AddCategoryRequest(
    val model_id: Int,
    val name: String
)