plugins {
    id("nowinandroid.android.library")
}

android {
    namespace = "com.example.william.my.core.eventbus"
}

dependencies {
    //RxEventBus
    implementation(libs.rxjava3)
    //FlowEventBus
    //viewModelScope
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    //ProcessLifecycleOwner
    implementation(libs.androidx.lifecycle.process)
}