package com.discovr.discord.ui.main

import android.view.Menu
import android.view.MenuItem
import com.discovr.discord.data.manager.SettingManager
import com.discovr.discord.model.Tag
import com.discovr.discord.ui.main.util.IconHelper
import javax.inject.Inject

class MainPresenter
@Inject constructor(private val activity: MainContract.Activity,
                    private val settingManager: SettingManager,
                    private val iconHelper: IconHelper) : MainContract.ActivityPresenter {

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
}
