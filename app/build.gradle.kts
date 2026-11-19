plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.asf_jets"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.asf_jets"
        minSdk = 27
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation("com.tbuonomo:dotsindicator:5.1.0")
    implementation("androidx.gridlayout:gridlayout:1.0.0")
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    implementation ("com.github.bumptech.glide:glide:5.0.9")
    annotationProcessor ("com.github.bumptech.glide:compiler:5.0.9")
    implementation("androidx.recyclerview:recyclerview:1.4.0")
}