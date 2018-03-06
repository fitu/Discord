package com.discovr.discord.ui.main.util

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.discovr.discord.R
import com.discovr.discord.data.manager.SettingManager
import com.discovr.discord.model.Tag
import org.jetbrains.anko.itemsSequence
import javax.inject.Inject

class IconHelper
@Inject constructor(private val context: Context,
                    private val settingManager: SettingManager) {

    fun setUpMenu(menu: Menu) {
        for (item in menu.itemsSequence()) {
            when (item.itemId) {
                R.id.actionDrink -> {
                    if (settingManager.getValue(Tag.DRINK))
                        changeColor(item, item.icon, android.R.color.holo_green_light)
                }
                R.id.actionHardcore -> {
                    if (settingManager.getValue(Tag.HARDCORE))
                        changeColor(item, item.icon, android.R.color.holo_red_light)
                }
            }
        }
    }

    fun drinkClick(item: MenuItem, isSet: Boolean) {
        val newIcon = item.icon
        val newColor = if (isSet) android.R.color.black else android.R.color.holo_green_light
        changeColor(item, newIcon, newColor)
        savePreference(Tag.DRINK, !isSet)
    }

    fun hardcoreClick(item: MenuItem, isSet: Boolean) {
        val newIcon = item.icon
        val newColor = if (isSet) android.R.color.black else android.R.color.holo_red_light
        changeColor(item, newIcon, newColor)
        savePreference(Tag.HARDCORE, !isSet)
    }

    private fun changeColor(item: MenuItem, newIcon: Drawable, newColor: Int) {
        newIcon.mutate()
        newIcon.setColorFilter(ContextCompat.getColor(context, newColor), PorterDuff.Mode.SRC_IN)
        item.icon = newIcon
    }

    private fun savePreference(tag: Tag, newValue: Boolean) {
        settingManager.setValue(tag, newValue)
    }
}
