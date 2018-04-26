package com.discovr.discord.ui.main

import com.discovr.discord.data.manager.card.CardManager
import com.discovr.discord.data.manager.setting.SettingManager
import com.discovr.discord.model.Tag
import com.discovr.discord.ui.main.util.IconHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainPresenter
@Inject constructor(private val view: MainContract.Activity,
                    private val cardManager: CardManager,
                    private val settingManager: SettingManager,
                    private val iconHelper: IconHelper) : MainContract.ActivityPresenter {

    init {
        // TODO why commented?
        //   cardManager.subscribe()
    }

    private val compositeDisposable = CompositeDisposable()

    override fun getValue(tag: Tag): Boolean {
        return settingManager.getValue(tag)
    }

    override fun subscribe(events: Observable<MainEvent>) {
        events.doOnSubscribe({ compositeDisposable.add(it) })
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter({ it is MainEvent.MenuEvent || it is MainEvent.DrinkClick || it is MainEvent.HardcoreClick })
                .flatMap<MainModel>({ this.handleEvent(it) })
                .subscribe({ view.render(it) })
    }

    private fun handleEvent(event: MainEvent): Observable<MainModel> {
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
                .map { MainModel.DrinkClick(event.item, it) as MainModel }
                .onErrorReturn({ MainModel.Error(it) })
    }

    private fun hardcoreEvent(event: MainEvent.HardcoreClick): Observable<MainModel> {
        return Observable.just(iconHelper.hardcoreClick(event))
                .map { MainModel.HardcoreClick(event.item, it) as MainModel }
                .onErrorReturn({ MainModel.Error(it) })
    }

    private fun menuEvent(event: MainEvent.MenuEvent): Observable<MainModel> {
        return Observable.just(iconHelper.setIconColor(event))
                .map { MainModel.Menu(event.menuItem, it) as MainModel }
                .onErrorReturn({ MainModel.Error(it) })
    }

    override fun clear() {
        compositeDisposable.clear()
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }
}
