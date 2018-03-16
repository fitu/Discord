package com.discovr.discord.ui.main

import android.view.Menu
import android.view.MenuItem
import com.discovr.discord.data.manager.SettingManager
import com.discovr.discord.model.Tag
import com.discovr.discord.ui.main.util.IconHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainPresenter
@Inject constructor(private val view: MainContract.Activity,
                    private val settingManager: SettingManager,
                    private val iconHelper: IconHelper) : MainContract.ActivityPresenter {

    private val compositeDisposable = CompositeDisposable()

    override fun subscribe(actions: Observable<MainAction>?) {
        actions!!.doOnSubscribe({ compositeDisposable.add(it) })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ this.actions(it) })
    }

    private fun actions(action: MainAction) {
        when (action) {
            is MainAction.DrinkClick -> drinkClick(action.item)
            is MainAction.HardcoreClick -> hardcoreClick(action.item)
        }
    }

    override fun setUpMenu(menu: Menu) {
        iconHelper.setUpMenu(menu)
    }

    override fun drinkClick(item: MenuItem) {
        val isDrinkSet = settingManager.getValue(Tag.DRINK)
        iconHelper.drinkClick(item, isDrinkSet)
    }

    override fun hardcoreClick(item: MenuItem) {
        val isHardcodeSet = settingManager.getValue(Tag.HARDCORE)
        iconHelper.hardcoreClick(item, isHardcodeSet)
    }

    override fun clear() {
        compositeDisposable.clear()
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }
}
