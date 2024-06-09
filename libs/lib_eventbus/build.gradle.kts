plugins {
    alias(libs.plugins.nowinandroid.android.library)
}

android {
    namespace = "com.example.william.my.core.eventbus"
}

dependencies {
    //RxEventBus
    api(libs.rxjava3)
    //FlowEventBus
    //viewModelScope
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    //ProcessLifecycleOwner
    implementation(libs.androidx.lifecycle.process)
}