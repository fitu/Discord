package com.discovr.discord.ui.splash

import com.discovr.discord.data.manager.CardManager
import com.discovr.discord.data.manager.SettingManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

// TODO inject events
class SplashPresenter
@Inject constructor(private val view: SplashContract.View,
                    private val cardManager: CardManager,
                    private val settingManager: SettingManager) : SplashContract.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun isFirstTime(): Boolean {
        return settingManager.isFirstTime
    }

    override fun subscribe(events: Observable<SplashEvent>) {
        events.doOnSubscribe({ compositeDisposable.add(it) })
                .observeOn(Schedulers.io())
                .flatMap<SplashModel>({ this.handleEvent(it) })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view.render(it) })
    }

    private fun handleEvent(event: SplashEvent): Observable<SplashModel> {
        if (event is SplashEvent.Start) {
            return startEvent(event)
        }

        return Observable.just(SplashModel.Error(Throwable("Can't handle event")))
    }

    private fun startEvent(startEvent: SplashEvent.Start): Observable<SplashModel> {
        return if (!startEvent.isFirstTime)
            Observable.just(SplashModel.NotFirstTime())
        else
            cardManager.loadCards()
                    .doOnNext({ settingManager.notFirstTime(it) })
                    .map { SplashModel.FirstTime() as SplashModel }
                    .onErrorReturn({ SplashModel.Error(it) })
    }

    override fun clear() {
        compositeDisposable.clear()
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }
}
