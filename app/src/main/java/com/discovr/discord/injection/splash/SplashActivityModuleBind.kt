package com.discovr.discord.injection.splash

import android.app.Activity
import com.discovr.discord.injection.util.ActivityScope
import com.discovr.discord.ui.splash.SplashActivity
import com.discovr.discord.ui.splash.SplashContract
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SplashActivityModuleBind {
    @Binds
    @IntoMap
    @ActivityKey(SplashActivity::class)
    internal abstract fun bindActivityInjectorFactory(
            builder: SplashActivityComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @ActivityScope
    internal abstract fun provideActivity(activity: SplashActivity): SplashContract.View
}
