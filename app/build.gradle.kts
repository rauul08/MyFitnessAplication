plugins {
    alias(libs.plugins.android.application) // Plugin de aplicación Android
    alias(libs.plugins.kotlin.android)     // Plugin de Kotlin para Android
    id("com.google.gms.google-services")   // Plugin de Google Services para Firebase
}

android {
    namespace = "com.example.myfitnessaplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myfitnessaplication"
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
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.storage.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.compose.material3:material3:1.3.1")

    // Coil para manejo de imágenes
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Hilt
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Iconos extendidos
    implementation("androidx.compose.material:material-icons-extended:1.7.8")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0")) // BoM de Firebase
    implementation("com.google.firebase:firebase-auth") // Autenticación de Firebase
    implementation("com.google.firebase:firebase-analytics") // Firebase Analytics (opcional)
    implementation("com.google.firebase:firebase-firestore-ktx")
}