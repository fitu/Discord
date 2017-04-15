package com.discovr.discord.injection.main;

import com.discovr.discord.injection.util.scope.FragmentScope;
import com.discovr.discord.ui.main.card.CardFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@FragmentScope
@Subcomponent(modules = { CardFragmentModule.class })
public interface CardFragmentComponent extends AndroidInjector<CardFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<CardFragment> {}
}