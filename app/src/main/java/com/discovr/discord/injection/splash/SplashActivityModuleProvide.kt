package com.discovr.discord.injection.splash

import com.discovr.discord.data.manager.card.CardManager
import com.discovr.discord.data.manager.setting.SettingManager
import com.discovr.discord.injection.util.ActivityScope
import com.discovr.discord.ui.splash.SplashContract
import com.discovr.discord.ui.splash.SplashEvent
import com.discovr.discord.ui.splash.SplashPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

@Module
class SplashActivityModuleProvide {
    @Provides
    @ActivityScope
    internal fun providePresenter(activity: SplashContract.View,
                                  events: Observable<SplashEvent>,
                                  cardManager: CardManager,
                                  settingManager: SettingManager): SplashContract.Presenter {
        return SplashPresenter(activity, events, cardManager, settingManager)
    }

    @Provides
    @ActivityScope
    internal fun provideSubject(): Subject<SplashEvent> {
        return PublishSubject.create()
    }
}