package com.discovr.discord.injection.app;

import com.discovr.discord.DiscordApplication;
import com.discovr.discord.injection.main.MainActivityModule;
import com.discovr.discord.injection.splash.SplashActivityModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DbModule.class, ManagerModule.class, SplashActivityModule.class,
        MainActivityModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(DiscordApplication application);

        AppComponent build();
    }

    void inject(DiscordApplication application);
}
