package com.discovr.discord.ui.main

import com.discovr.discord.data.manager.card.CardManager
import com.discovr.discord.data.manager.setting.SettingManager
import com.discovr.discord.model.Tag
import com.discovr.discord.ui.main.util.IconHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter
@Inject constructor(private val view: MainContract.Activity,
                    events: Observable<MainEvent>,
                    // TODO not used
                    private val cardManager: CardManager,
                    private val settingManager: SettingManager,
                    private val iconHelper: IconHelper) : MainContract.ActivityPresenter {

    companion object {
        // TODO check what's happen when obfuscate it
        val TAG: String = MainPresenter::class.java.simpleName
    }

    private val compositeDisposable: CompositeDisposable?

    init {
        compositeDisposable = CompositeDisposable()

        events.doOnSubscribe { compositeDisposable.add(it) }
                .observeOn(Schedulers.io())
                .flatMap<MainModel> { this.handleEvents(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.render(it) }
    }

    override fun getValue(tag: Tag): Boolean {
        // TODO not a stream
        return settingManager.getValue(tag)
    }

    private fun handleEvents(event: MainEvent): Observable<MainModel> {
        if (event is MainEvent.DrinkClick) {
            return drinkEvent(event)
        }

        if (event is MainEvent.HardcoreClick) {
            return hardcoreEvent(event)
        }

        return menuEvent(event as MainEvent.MenuEvent)
    }

    private fun drinkEvent(event: MainEvent.DrinkClick): Observable<MainModel> {
        return Observable.just(iconHelper.drinkClick(event))
                .map { MainModel.DrinkClick() as MainModel }
                .onErrorReturn({ MainModel.Error(it) })
    }

    private fun hardcoreEvent(event: MainEvent.HardcoreClick): Observable<MainModel> {
        return Observable.just(iconHelper.hardcoreClick(event))
                .map { MainModel.HardcoreClick() as MainModel }
                .onErrorReturn({ MainModel.Error(it) })
    }

    private fun menuEvent(event: MainEvent.MenuEvent): Observable<MainModel> {
        return Observable.just(iconHelper.setIconDrawable(event))
                .map { MainModel.MenuDone() as MainModel }
                .onErrorReturn({ MainModel.Error(it) })
    }

    override fun clear() {
        compositeDisposable!!.clear()
    }

    override fun dispose() {
        compositeDisposable!!.dispose()
    }
}
