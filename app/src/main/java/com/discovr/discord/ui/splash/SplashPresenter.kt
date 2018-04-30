package com.discovr.discord.ui.splash

import com.discovr.discord.data.manager.card.CardAction
import com.discovr.discord.data.manager.card.CardManager
import com.discovr.discord.data.manager.card.CardResult
import com.discovr.discord.data.manager.setting.SettingAction
import com.discovr.discord.data.manager.setting.SettingManager
import com.discovr.discord.data.manager.setting.SettingResult
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

        settingManager.resultsObs.doOnSubscribe { compositeDisposable.add(it) }
                .observeOn(Schedulers.io())
                // TODO check filters
                .filter { it is SettingResult.FirstTime }
                .flatMap<SplashModel> { this.handleSettingResults(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.render(it) }

        cardManager.resultsObs.doOnSubscribe { compositeDisposable.add(it) }
                .observeOn(Schedulers.io())
                // TODO check filters
                .filter { it is CardResult.LoadCardsDone || it is CardResult.LoadCardsFail }
                .flatMap<SplashModel> { this.handleCardResults(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.render(it) }
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun isFirstTime(): Boolean {
        // TODO not a stream
        return settingManager.isFirstTime
    }

    private fun handleEvents(event: SplashEvent): Observable<SplashModel> {
        return startEvent(event as SplashEvent.Start)
    }

    private fun startEvent(startEvent: SplashEvent.Start): Observable<SplashModel> {
        if (!startEvent.isFirstTime)
            return Observable.just(SplashModel.Start())

        return settingManager.handleAction(SettingAction.FirstTime())
                .flatMap { cardManager.handleAction(CardAction.LoadCards()) }
                .flatMap { this.handleCardResults(it) }
                .onErrorReturn { SplashModel.Error(it) }
    }

    private fun handleCardResults(result: CardResult): Observable<SplashModel> {
        return Observable.just(result)
                .map {
                    if (it is CardResult.LoadCardsDone)
                        SplashModel.Start()
                    else
                        SplashModel.StartFail("There was a problem loading the cards")
                }
    }

    private fun handleSettingResults(result: SettingResult): Observable<SplashModel> {
        return Observable.empty()
    }

    override fun clear() {
        compositeDisposable.clear()
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }
}
