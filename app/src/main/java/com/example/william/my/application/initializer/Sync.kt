package com.example.william.my.application.initializer

import android.content.Context
import androidx.startup.AppInitializer
import androidx.startup.Initializer
import com.example.william.my.basic.basic_module.utils.Utils

object Sync {
    // This method is a workaround to manually initialize the sync process instead of relying on
    // automatic initialization with Androidx Startup. It is called from the app module's
    // Application.onCreate() and should be only done once.
    fun initialize(context: Context) {
        AppInitializer.getInstance(context)
            .initializeComponent(StartupInitializer::class.java)
    }
}

/**
 * Registers work to sync the data layer periodically on app startup.
 */
class SyncInitializer : Initializer<Sync> {

    private val TAG = this.javaClass.simpleName

    override fun create(context: Context): Sync {
        Utils.logcat(TAG, "SyncInitializer init")
        return Sync
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(StartupInitializer::class.java)
    }
}

class StartupInitializer : Initializer<String> {

    private val TAG = this.javaClass.simpleName

    override fun create(context: Context): String {
        Utils.logcat(TAG, "StartupInitializer init")
        return "Startup Init"
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        // No dependencies on other libraries.
        return emptyList()
    }
}