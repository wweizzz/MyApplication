plugins {
    alias(libs.plugins.nowinandroid.android.library)
}

android {
    namespace = "com.example.william.my.core.widget"
    buildFeatures {
        renderScript = true
    }
}

dependencies {
    //BottomSheetDialogFragment
    api(libs.google.material)
}