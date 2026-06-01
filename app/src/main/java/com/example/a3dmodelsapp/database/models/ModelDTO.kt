package com.example.a3dmodelsapp.database.models

import kotlinx.serialization.Serializable

@Serializable
data class UploadResponse(
    val success: Boolean,
    val fileUrl: String,
    val previewUrl: String? = null,
    val id: Int
)

@Serializable
data class DeleteModelRequest(
    val id: Int
)

@Serializable
data class DeleteModelResponse(
    val success: Boolean,
    val id: Int,
    val message: String? = null
)

@Serializable
data class UpdateModelRequest(
    val id: Int,
    val name: String,
    val description: String,
    val categories: List<String>
)

@Serializable
data class UpdateModelResponse(
    val model: Model
)