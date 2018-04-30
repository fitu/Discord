package com.discovr.discord.ui.splash

import com.discovr.discord.data.manager.card.CardAction
import com.discovr.discord.data.manager.card.CardManager
import com.discovr.discord.data.manager.setting.SettingAction
import com.discovr.discord.data.manager.setting.SettingManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SplashPresenter
@Inject constructor(private val view: SplashContract.View,
                    private val events: Observable<SplashEvent>,
                    private val cardManager: CardManager,
                    private val settingManager: SettingManager) : SplashContract.Presenter {

    init {
        events.doOnSubscribe { compositeDisposable.add(it) }
                .observeOn(Schedulers.io())
                .flatMap<SplashModel> { this.handleEvents(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.render(it) }
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun isFirstTime(): Boolean {
        return settingManager.isFirstTime
    }

    private fun handleEvents(event: SplashEvent): Observable<SplashModel> {
        return startEvent(event as SplashEvent.Start)
    }

    private fun startEvent(startEvent: SplashEvent.Start): Observable<SplashModel> {
        if (!startEvent.isFirstTime)
            return Observable.just(SplashModel.NotFirstTime())

        return cardManager.handleAction(CardAction.LoadCards())
                .flatMap { settingManager.handleAction(SettingAction.FirstTime()) }
                .map { SplashModel.FirstTime() as SplashModel }
                .onErrorReturn { SplashModel.Error(it) }
    }

    override fun clear() {
        compositeDisposable.clear()
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }
}
