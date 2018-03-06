package com.discovr.discord.injection.splash

import com.discovr.discord.data.manager.CardManager
import com.discovr.discord.data.manager.SettingManager
import com.discovr.discord.injection.util.ActivityScope
import com.discovr.discord.ui.splash.SplashContract
import com.discovr.discord.ui.splash.SplashPresenter
import dagger.Module
import dagger.Provides

@Module
class SplashActivityModuleProvide {
    @Provides
    @ActivityScope
    internal fun providePresenter(
            activity: SplashContract.View,
            cardManager: CardManager,
            settingManager: SettingManager): SplashContract.Presenter {
        return SplashPresenter(activity, cardManager, settingManager)
    }
}