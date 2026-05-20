plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.a3dmodelsapp"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.a3dmodelsapp"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation("androidx.compose.ui:ui-text-google-fonts:1.10.6")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.material3)
    implementation(libs.compose.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation (libs.gson)
    implementation (libs.retrofit)
    implementation(libs.okhttp)
    implementation (libs.converter.gson)
    implementation(libs.coil.compose)


//    implementation("io.github.jan-tennert.supabase:supabase-kt:3.6.0")
//
//    implementation("io.github.jan-tennert.supabase:postgrest-kt:3.6.0")
//    implementation("io.github.jan-tennert.supabase:storage-kt:3.6.0")
//    implementation("io.github.jan-tennert.supabase:auth-kt:3.6.0")

//    implementation("io.github.jan-tennert.supabase:storage-kt:3.5.0")
//    //implementation("io.ktor:ktor-client-[engine]:VERSION")
//    implementation("io.ktor:ktor-client-android:3.5.0")


    implementation("io.github.jan-tennert.supabase:supabase-kt:3.5.0")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:3.5.0")
    implementation("io.github.jan-tennert.supabase:storage-kt:3.5.0")
    implementation("io.github.jan-tennert.supabase:auth-kt:3.5.0")

    implementation("io.ktor:ktor-client-android:3.5.0")
    implementation("io.ktor:ktor-client-content-negotiation:3.5.0")
    implementation("io.ktor:ktor-client-logging:3.5.0")
    implementation("io.ktor:ktor-client-auth:3.5.0")


}