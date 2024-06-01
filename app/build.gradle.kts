import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.diploma"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.diploma"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            val keystoreProperties = Properties()
            val keystorePropertiesFile = file("keystore/keystore.properties")

            storeFile = file("keystore/keystore.jks")

            if (keystorePropertiesFile.exists()) {
                keystorePropertiesFile.inputStream().let(keystoreProperties::load)
                storePassword = keystoreProperties.getProperty("storePassword")
                keyAlias = keystoreProperties.getProperty("keyAlias")
                keyPassword = keystoreProperties.getProperty("keyPassword")
            } else {
                storePassword = System.getenv("KEYSTORE_PASSWORD")
                keyAlias = System.getenv("RELEASE_SIGN_KEY_ALIAS")
                keyPassword = System.getenv("RELEASE_SIGN_KEY_PASSWORD")
            }
        }

        create("debugSigning") {
            initWith(getByName("release"))
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debugSigning")
        }

        release {
            signingConfig = signingConfigs.getByName("release")

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

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.camera:camera-core:1.3.3")
    implementation("com.google.android.gms:play-services-mlkit-barcode-scanning:18.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("io.insert-koin:koin-android:3.5.3")
    implementation("io.insert-koin:koin-androidx-compose:3.5.3")
    implementation("io.insert-koin:koin-ktor:3.5.3")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.4.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation("com.squareup.moshi:moshi:1.15.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.1")

    val nav_version = "2.7.7"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    val camerax_version = "1.4.0-alpha04"
    implementation("androidx.camera:camera-core:$camerax_version")
    implementation("androidx.camera:camera-camera2:$camerax_version")
    implementation("androidx.camera:camera-lifecycle:$camerax_version")
    implementation("androidx.camera:camera-video:$camerax_version")
    implementation("androidx.camera:camera-view:$camerax_version")
    implementation("androidx.camera:camera-extensions:$camerax_version")
    implementation("androidx.camera:camera-mlkit-vision:$camerax_version")
    implementation("com.google.mlkit:barcode-scanning:17.2.0")

    implementation("com.google.accompanist:accompanist-permissions:0.35.0-alpha")

    implementation("me.saket.cascade:cascade:2.3.0")
    implementation("me.saket.cascade:cascade-compose:2.3.0")

    implementation("androidx.datastore:datastore-preferences:1.1.1")

    val paging_version = "3.3.0"
    implementation("androidx.paging:paging-compose:$paging_version")
    implementation("androidx.paging:paging-runtime-ktx:$paging_version")
}