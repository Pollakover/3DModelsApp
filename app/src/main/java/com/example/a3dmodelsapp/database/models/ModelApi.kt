package com.example.a3dmodelsapp.database.models

import com.example.a3dmodelsapp.database.models.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ModelApi {

    @GET("models/fetch")
    suspend fun fetchModels(): List<Model>

    @Multipart
    @POST("upload")
    suspend fun uploadFile(
        @Part preview: MultipartBody.Part,
        @Part file: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("width") width: RequestBody?,
        @Part("height") height: RequestBody?,
        @Part("length") length: RequestBody?,

        @Part("user_login") user_login: RequestBody
    ): Response<UploadResponse>

    @HTTP(
        method = "DELETE",
        path = "models/delete",
        hasBody = true
    )
    suspend fun deleteModel(
        @Body request: DeleteModelRequest
    ): Response<DeleteModelResponse>

    @HTTP(
        method = "PATCH",
        path = "models/update",
        hasBody = true
    )
    suspend fun updateModel(
        @Body request: UpdateModelRequest
    ): Response<UpdateModelResponse>

}