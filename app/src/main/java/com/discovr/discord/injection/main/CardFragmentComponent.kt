package com.discovr.discord.injection.main

import com.discovr.discord.injection.util.FragmentScope
import com.discovr.discord.ui.main.card.CardFragment

import dagger.Subcomponent
import dagger.android.AndroidInjector

@FragmentScope
@Subcomponent(modules = arrayOf(CardFragmentModuleBind::class, CardFragmentModuleProvide::class))
interface CardFragmentComponent : AndroidInjector<CardFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<CardFragment>()
}