plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.maw"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.maw"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // Jetpack Compose
    val composeVersion = "1.6.1"
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-graphics:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    
    // Lifecycle Compose state management
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    // Image loading with Coil
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Firebase (Firestore & Auth & Common BOM)
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // Gemini API Direct REST Support with Retrofit & OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

    // Local unit tests
    testImplementation("junit:junit:4.13.2")
}

project.afterEvaluate {
    tasks.findByName("assembleDebug")?.finalizedBy("copyApkToBuildOutputs")
}

tasks.register("copyApkToBuildOutputs") {
    doLast {
        val apkFile = file("${project.buildDir}/outputs/apk/debug/app-debug.apk")
        val destDir = file("${project.rootDir}/build-outputs")
        if (apkFile.exists()) {
            destDir.mkdirs()
            val destFile = file("${destDir}/app-debug.apk")
            apkFile.copyTo(destFile, overwrite = true)
            println("Successfully copied APK to build-outputs/app-debug.apk!")
        } else {
            // Also try fallback from system .build-outputs
            val systemApk = file("${project.rootDir}/.build-outputs/app-debug.apk")
            if (systemApk.exists()) {
                destDir.mkdirs()
                val destFile = file("${destDir}/app-debug.apk")
                systemApk.copyTo(destFile, overwrite = true)
                println("Successfully copied APK from fallback .build-outputs to build-outputs!")
            }
        }
    }
}

