package com.discovr.discord.injection.main

import android.support.v4.app.Fragment

import com.discovr.discord.ui.main.card.CardFragment

import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

@Module
abstract class CardFragmentModuleBind {
    @Binds
    @IntoMap
    @FragmentKey(CardFragment::class)
    internal abstract fun bindFragmentInjectorFactory(
            builder: CardFragmentComponent.Builder): AndroidInjector.Factory<out Fragment>
}
