plugins {
    alias(libs.plugins.nowinandroid.android.library)
}

android {
    namespace = "com.example.william.my.core.widget"
}

dependencies {
    //BottomSheetDialogFragment
    implementation(libs.google.material)
}