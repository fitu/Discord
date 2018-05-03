package com.discovr.discord.ui.main.util

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import com.discovr.discord.R
import com.discovr.discord.data.manager.setting.SettingManager
import com.discovr.discord.model.Tag
import com.discovr.discord.ui.main.MainEvent
import javax.inject.Inject

class IconHelper
@Inject constructor(private val context: Context,
                    private val settingManager: SettingManager) {

    fun setIconDrawable(event: MainEvent.MenuEvent) {
        event.menuItems.forEach {
            when (it.itemId) {
                R.id.actionDrink -> {
                    val newIcon: Drawable = if (event.areSets[Tag.DRINK] == true)
                        getNewIcon(it.icon, event.colorIds[Tag.DRINK] ?: android.R.color.black)
                    else getNewIcon(it.icon, android.R.color.black)
                    it.icon = newIcon
                }
                R.id.actionHardcore -> {
                    val newIcon: Drawable =
                            if (event.areSets[Tag.HARDCORE] == true)
                                getNewIcon(it.icon, event.colorIds[Tag.HARDCORE] ?: android.R.color.black)
                            else getNewIcon(it.icon, android.R.color.black)
                    it.icon = newIcon
                }
            }
        }
    }

    fun drinkClick(event: MainEvent.DrinkClick) {
        val newIcon: Drawable = changeIcon(event.item, event.isSet, event.colorId, event.tag)
        event.item.icon = newIcon
    }

    fun hardcoreClick(event: MainEvent.HardcoreClick) {
        val newIcon: Drawable = changeIcon(event.item, event.isSet, event.colorId, event.tag)
        event.item.icon = newIcon
    }

    private fun changeIcon(item: MenuItem, isSet: Boolean, colorId: Int, tag: Tag) : Drawable {
        // TODO remove setting manager from here
        settingManager.setValue(tag, !isSet)

        val newIcon = item.icon
        val newColor = if (isSet) android.R.color.black else colorId

        return getNewIcon(newIcon, newColor)
    }

    private fun getNewIcon(newIcon: Drawable, newColor: Int) : Drawable {
        newIcon.mutate().setColorFilter(ContextCompat.getColor(context, newColor), PorterDuff.Mode.SRC_IN)
        return newIcon
    }
}
