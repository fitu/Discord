package com.discovr.discord.injection.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.discovr.discord.DiscordApplication;
import com.discovr.discord.injection.main.MainActivityComponent;
import com.discovr.discord.injection.splash.SplashActivityComponent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = {SplashActivityComponent.class, MainActivityComponent.class})
public class AppModule {

    @Singleton
    @Provides
    static Context provideContext(DiscordApplication application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    static SharedPreferences provideSharedPreferences(DiscordApplication application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}