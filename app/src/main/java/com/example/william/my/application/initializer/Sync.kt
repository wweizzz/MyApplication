package com.example.william.my.application.initializer

import android.content.Context
import android.util.Log
import androidx.startup.AppInitializer
import androidx.startup.Initializer

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

    override fun create(context: Context): Sync {
        return Sync
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(StartupInitializer::class.java)
    }
}

class StartupInitializer : Initializer<String> {

    private val tag = "StartupInitializer"

    override fun create(context: Context): String {
        Log.e(tag, "StartupInitializer init")
        return "Startup Init"
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        // No dependencies on other libraries.
        return emptyList()
    }
}