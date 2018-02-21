package com.discovr.discord.injection.app

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

import com.discovr.discord.DiscordApplication
import com.discovr.discord.injection.main.MainActivityComponent
import com.discovr.discord.injection.splash.SplashActivityComponent

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module(subcomponents = arrayOf(SplashActivityComponent::class, MainActivityComponent::class))
class AppModule {

    @Singleton
    @Provides
    internal fun provideContext(application: DiscordApplication): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    internal fun provideSharedPreferences(application: DiscordApplication): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }
}