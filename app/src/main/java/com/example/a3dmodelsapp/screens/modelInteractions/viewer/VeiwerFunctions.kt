package com.example.a3dmodelsapp.screens.modelInteractions.viewer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.google.android.filament.MaterialInstance
import io.github.sceneview.loaders.MaterialLoader
import io.github.sceneview.material.setMetallic
import io.github.sceneview.material.setReflectance
import io.github.sceneview.material.setRoughness

@Composable
fun rememberMaterialInstance(
    materialLoader: MaterialLoader,
    color: Color,
    metallic: Float = 0.4f,
    roughness: Float = 0.4f,
    reflectance: Float = 0.5f,
): MaterialInstance {
    // CRITICAL: do NOT include `metallic / roughness / reflectance` in the
    // `remember(...)` key. Slider-driven demos (Geometry, Lighting,
    // CustomMesh, …) tweak these at 60 Hz; if they were keys, every
    // slider tick would destroy + recreate the MaterialInstance, hammering
    // Filament's main-thread JNI surface. Allocate once on `color` change
    // (color rarely toggles) and push parameter updates via setMetallic /
    // setRoughness / setReflectance in a LaunchedEffect at the call site.
    // The defaults below seed the initial allocation; subsequent updates
    // are the caller's responsibility. Caught by an independent #937 review.
    val instance = remember(materialLoader, color) {
        materialLoader.createColorInstance(color, metallic, roughness, reflectance)
    }
    // Reflect dynamic PBR updates without churning the instance.
    DisposableEffect(instance, metallic, roughness, reflectance) {
        instance.setMetallic(metallic)
        instance.setRoughness(roughness)
        instance.setReflectance(reflectance)
        onDispose { /* parameter setters are no-op on destroy; instance cleanup is the
                       outer onDispose below */ }
    }
    DisposableEffect(instance) {
        onDispose { materialLoader.destroyMaterialInstance(instance) }
    }
    return instance
}