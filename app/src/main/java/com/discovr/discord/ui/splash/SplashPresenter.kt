package com.discovr.discord.ui.splash

import com.discovr.discord.data.manager.CardManager
import com.discovr.discord.data.manager.SettingManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SplashPresenter
@Inject constructor(private val view: SplashContract.View,
                    private val cardManager: CardManager,
                    private val settingManager: SettingManager) : SplashContract.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun start() {
        view.startEvent()
                .doOnSubscribe({ compositeDisposable.add(it) })
                .subscribeOn(Schedulers.io())
                .flatMap<SplashModel>({ this.setUp(it) })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view.render(it) })
    }

    private fun setUp(event: SplashEvent.Start): Observable<SplashModel> {
        return if (!event.isFirstTime) {
            Observable.just(SplashModel.NotFirstTime())
        } else settingManager.setUpApp()
                .flatMap { cardManager.loadCards() }
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
