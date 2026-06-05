package com.example.a3dmodelsapp.screens.modelInteractions.viewer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.google.android.filament.MaterialInstance
import io.github.sceneview.loaders.MaterialLoader

@Composable
fun rememberUnlitMaterialInstance(
    materialLoader: MaterialLoader,
    color: Color,
): MaterialInstance {
    val instance = remember(materialLoader, color) {
        materialLoader.createUnlitColorInstance(color)
    }
    DisposableEffect(instance) {
        onDispose { materialLoader.destroyMaterialInstance(instance) }
    }
    return instance
}