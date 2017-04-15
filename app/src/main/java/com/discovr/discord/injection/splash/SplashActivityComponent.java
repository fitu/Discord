package com.discovr.discord.injection.splash;

import com.discovr.discord.injection.util.scope.ActivityScope;
import com.discovr.discord.ui.splash.SplashActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ActivityScope
@Subcomponent(modules = {SplashActivityModule.class})
public interface SplashActivityComponent extends AndroidInjector<SplashActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SplashActivity> {
    }
}
