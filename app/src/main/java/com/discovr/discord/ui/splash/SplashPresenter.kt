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
                    events: Observable<SplashEvent>,
                    private val cardManager: CardManager,
                    private val settingManager: SettingManager) : SplashContract.Presenter {

    companion object {
        // TODO check what's happen when obfuscate it
        val TAG: String = SplashPresenter::class.java.simpleName
    }

    private val compositeDisposable: CompositeDisposable?

    init {
        compositeDisposable = CompositeDisposable()

        events.doOnSubscribe { compositeDisposable.add(it) }
                .observeOn(Schedulers.io())
                .flatMap<SplashModel> { this.handleEvents(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.render(it) }

        settingManager.resultsObs.doOnSubscribe { compositeDisposable.add(it) }
                .observeOn(Schedulers.io())
                .filter { it.id != TAG }
                .filter { it is SettingResult.FirstTime }
                .flatMap<SplashModel> { this.handleSettingResults(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.render(it) }

        cardManager.resultsObs.doOnSubscribe { compositeDisposable.add(it) }
                .observeOn(Schedulers.io())
                .filter { it.id != TAG }
                .filter { it is CardResult.LoadCardsDone || it is CardResult.LoadCardsFail }
                .flatMap<SplashModel> { this.handleCardResults(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.render(it) }
    }

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

        return settingManager.handleAction(SettingAction.FirstTime(TAG))
                .flatMap { cardManager.handleAction(CardAction.LoadCards(TAG)) }
                .map {
                    if (it is CardResult.LoadCardsDone)
                        SplashModel.Start()
                    else
                        SplashModel.StartFail("There was a problem loading the cards")
                }
                .onErrorReturn { SplashModel.Error(it) }
    }

    private fun handleCardResults(result: CardResult): Observable<SplashModel> {
        return Observable.empty()
    }

    private fun handleSettingResults(result: SettingResult): Observable<SplashModel> {
        return Observable.empty()
    }

    override fun clear() {
        compositeDisposable!!.clear()
    }

    override fun dispose() {
        compositeDisposable!!.dispose()
    }
}
