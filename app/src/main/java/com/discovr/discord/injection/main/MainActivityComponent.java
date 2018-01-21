package com.discovr.discord.injection.main;

import com.discovr.discord.injection.util.scope.ActivityScope;
import com.discovr.discord.ui.main.MainActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ActivityScope
@Subcomponent(modules = {MainActivityModule.class, CardFragmentModule.class})
public interface MainActivityComponent extends AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {
    }
}