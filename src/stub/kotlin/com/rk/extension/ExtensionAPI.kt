package com.rk.extension

import android.app.Activity
import android.app.Application
import android.os.Bundle

class ExtensionContext {
    fun logInfo(message: String) {}
    fun logDebug(message: String) {}
    fun logWarn(message: String) {}
    fun logError(message: String) {}
}

abstract class ExtensionAPI(val context: ExtensionContext) : Application.ActivityLifecycleCallbacks {
    abstract fun onLoad()
    open fun onDispose() {}
    open fun onInstalled() {}
    open fun beforeUpdate() {}
    open fun afterUpdate() {}
    open fun onUninstalled() {}

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {}
    override fun onActivityDestroyed(p0: Activity) {}
    override fun onActivityPaused(p0: Activity) {}
    override fun onActivityResumed(p0: Activity) {}
    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {}
    override fun onActivityStarted(p0: Activity) {}
    override fun onActivityStopped(p0: Activity) {}
}
