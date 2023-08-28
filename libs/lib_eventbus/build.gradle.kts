plugins {
    id("nowinandroid.android.library")
}

android {
    namespace = "com.example.william.my.core.eventbus"
}

dependencies {
    //FlowEventBus
    //viewModelScope
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    //ProcessLifecycleOwner
    implementation(libs.androidx.lifecycle.process)
}