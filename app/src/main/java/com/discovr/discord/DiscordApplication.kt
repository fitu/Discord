package com.discovr.discord


import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.StrictMode
import com.crashlytics.android.Crashlytics
import com.discovr.discord.injection.app.AppComponent
import com.discovr.discord.injection.app.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import javax.inject.Inject

class DiscordApplication : Application(),
        HasActivityInjector {

    var injector: DispatchingAndroidInjector<Activity>? = null @Inject set

    companion object {
        var appComponent: AppComponent? = null
            private set
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        setUpDagger()
    }

    private fun setUpDagger() {
        appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build()

        appComponent!!.inject(this)
    }

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())

        if (BuildConfig.DEBUG) {
            setDebugConfig()
        }
    }

    private fun setDebugConfig() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build())
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return injector
    }
}
