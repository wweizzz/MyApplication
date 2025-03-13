plugins {
    alias(libs.plugins.nowinandroid.android.library)
}

android {
    namespace = "com.example.william.my.core.eventbus"
}

dependencies {
    //RxEventBus
    api(libs.rxandroid)
    //FlowEventBus viewModelScope
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    //FlowEventBus ProcessLifecycleOwner
    implementation(libs.androidx.lifecycle.process)
}