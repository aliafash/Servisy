plugins {
    id("com.android.application") version "8.3.2"
    id("org.jetbrains.kotlin.android") version "1.9.22"
}

repositories {
    google()
    mavenCentral()
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

        val adminUser = System.getenv("ADMIN_USERNAME") ?: "WAM2026"
        val adminDeletePass = System.getenv("ADMIN_DELETE_PASSWORD") ?: "maher736462"
        val adminLoginPass = System.getenv("ADMIN_LOGIN_PASSWORD") ?: "maher--736462"

        buildConfigField("String", "ADMIN_USERNAME", "\"$adminUser\"")
        buildConfigField("String", "ADMIN_DELETE_PASSWORD", "\"$adminDeletePass\"")
        buildConfigField("String", "ADMIN_LOGIN_PASSWORD", "\"$adminLoginPass\"")
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
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.ui:ui-graphics:1.5.4")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.material:material-icons-extended:1.5.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // Firebase
    // implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    // implementation("com.google.firebase:firebase-firestore-ktx")
    // implementation("com.google.firebase:firebase-auth-ktx")

    // Coil
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
}

// tasks.register("copyApkToRoot") {
//     dependsOn("assembleDebug")
//     doLast {
//         val buildApk = file("build/outputs/apk/debug/app-debug.apk")
//         val rootApk = file("${rootDir}/app-debug.apk")
//         val buildOutputsApk = file("${rootDir}/.build-outputs/app-debug.apk")
//         if (buildApk.exists()) {
//             buildApk.copyTo(rootApk, overwrite = true)
//             buildApk.copyTo(buildOutputsApk, overwrite = true)
//             println("APK copied successfully to root and .build-outputs!")
//         } else {
//             println("Source APK not found at ${buildApk.absolutePath}")
//         }
//     }
// }
