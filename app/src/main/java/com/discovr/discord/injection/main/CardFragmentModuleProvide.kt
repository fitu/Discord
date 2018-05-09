package com.discovr.discord.injection.main

import com.discovr.discord.data.manager.card.CardManager
import com.discovr.discord.data.manager.setting.SettingManager
import com.discovr.discord.injection.util.FragmentScope
import com.discovr.discord.ui.main.MainContract
import com.discovr.discord.ui.main.MainEvent
import com.discovr.discord.ui.main.card.CardPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.Observable

@Module
class CardFragmentModuleProvide {
    @Provides
    @FragmentScope
    internal fun provideFragmentPresenter(fragment: MainContract.CardFragment,
                                          events: Observable<MainEvent>,
                                          settingManager: SettingManager,
                                          cardManager: CardManager): MainContract.CardFragmentPresenter {
        return CardPresenter(fragment, events, settingManager, cardManager)
    }
}