package com.discovr.discord.injection.main

import android.app.Activity
import com.discovr.discord.injection.util.ActivityScope
import com.discovr.discord.ui.main.MainActivity
import com.discovr.discord.ui.main.MainContract
import com.discovr.discord.ui.main.card.CardFragment
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [CardFragmentComponent::class])
abstract class MainActivityModuleBind {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    internal abstract fun bindActivityInjectorFactory(
            builder: MainActivityComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @ActivityScope
    internal abstract fun bindActivity(activity: MainActivity): MainContract.Activity

    @Binds
    @ActivityScope
    internal abstract fun bindFragment(fragment: CardFragment): MainContract.CardFragment
}
