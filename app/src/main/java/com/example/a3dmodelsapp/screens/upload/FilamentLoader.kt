package com.example.a3dmodelsapp.screens.upload

import com.google.android.filament.Filament

object FilamentLoader {

    var initialized = false

    fun init() {
        if (initialized) return

        System.loadLibrary("filament-jni")
        System.loadLibrary("filament-utils-jni")
        System.loadLibrary("gltfio-jni")

        Filament.init()

        initialized = true
    }
}