package com.discovr.discord.injection.main;

import com.discovr.discord.injection.util.scope.FragmentScope;
import com.discovr.discord.ui.main.discord.DiscordDialog;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@FragmentScope
@Subcomponent(modules = { DiscordDialogModule.class })
public interface DiscordDialogComponent extends AndroidInjector<DiscordDialog> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DiscordDialog> {}
}
