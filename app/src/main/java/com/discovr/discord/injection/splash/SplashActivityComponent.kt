package com.discovr.discord.injection.splash

import com.discovr.discord.injection.util.ActivityScope
import com.discovr.discord.ui.splash.SplashActivity

import dagger.Subcomponent
import dagger.android.AndroidInjector

@ActivityScope
@Subcomponent(modules = arrayOf(SplashActivityModuleBind::class, SplashActivityModuleProvide::class))
interface SplashActivityComponent : AndroidInjector<SplashActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SplashActivity>()
}
