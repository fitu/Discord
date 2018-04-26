package com.discovr.discord.injection.main

import com.discovr.discord.injection.util.FragmentScope
import com.discovr.discord.ui.main.MainContract
import com.discovr.discord.ui.main.card.CardPresenter
import dagger.Module
import dagger.Provides

@Module
class CardFragmentModuleProvide {
    @Provides
    @FragmentScope
    internal fun provideFragmentPresenter(fragment: MainContract.CardFragment): MainContract.CardFragmentPresenter {
        return CardPresenter(fragment)
    }
}