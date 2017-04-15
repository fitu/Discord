package com.discovr.discord.injection.main;

import android.support.v4.app.Fragment;

import com.discovr.discord.injection.util.scope.FragmentScope;
import com.discovr.discord.ui.main.discord.DiscordDialog;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module
public abstract class DiscordDialogModule {
    @Binds
    @IntoMap
    @FragmentKey(DiscordDialog.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindDiscordDialogInjectorFactory(
            DiscordDialogComponent.Builder builder);
}