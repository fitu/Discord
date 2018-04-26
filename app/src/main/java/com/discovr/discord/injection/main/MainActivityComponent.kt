package com.discovr.discord.injection.main

import com.discovr.discord.injection.util.ActivityScope
import com.discovr.discord.ui.main.MainActivity

import dagger.Subcomponent
import dagger.android.AndroidInjector

@ActivityScope
@Subcomponent(modules = arrayOf(MainActivityModuleBind::class, MainActivityModuleProvide::class, CardFragmentModuleBind::class))
interface MainActivityComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}