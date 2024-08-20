plugins {
    alias(libs.plugins.jetbrainsKotlin)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kapt)
    alias(libs.plugins.daggerHilt)
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.movieapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.movieapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.material)
    implementation(libs.daggerHiltAndroid)
    implementation(libs.pagination)
    kapt(libs.daggerHiltCompiler)
    implementation(libs.lifecycle)
    kapt(libs.lifecycleKapt)

    implementation(libs.androidx.fragment)
    implementation(libs.androidx.navigationFragment)
    implementation(libs.androidx.navigationRuntime)
    implementation(libs.androidx.navigationUi)

    implementation(libs.coil)

    implementation(libs.glide)

    implementation(libs.androidx.activity)
    implementation(project(":domain"))
    implementation(project(":data"))
}
