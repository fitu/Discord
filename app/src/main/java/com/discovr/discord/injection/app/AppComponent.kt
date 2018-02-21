package com.discovr.discord.injection.app

import com.discovr.discord.DiscordApplication
import com.discovr.discord.injection.main.MainActivityModuleBind
import com.discovr.discord.injection.splash.SplashActivityModuleBind

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component

@Singleton
@Component(modules = arrayOf(AppModule::class, DbModule::class, ManagerModule::class, SplashActivityModuleBind::class, MainActivityModuleBind::class))
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: DiscordApplication): Builder

        fun build(): AppComponent
    }

    fun inject(application: DiscordApplication)
}
