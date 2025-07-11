plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.adssdkproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.adssdkproject"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (project(":AdsLib")) // Include the AdsLib module
    //implementation("com.github.ShaniHalali:SDK_ADS_Android_Library:1.0.0") // Include the AdsLib library from GitHub

}