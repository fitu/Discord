package com.discovr.discord.ui.main.util

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import com.discovr.discord.data.manager.setting.SettingManager
import com.discovr.discord.model.Tag
import com.discovr.discord.ui.main.MainEvent
import javax.inject.Inject

class IconHelper
@Inject constructor(private val context: Context,
                    private val settingManager: SettingManager) {

    fun setIconColor(event: MainEvent.MenuEvent) : Drawable {
        if (event.isSet) {
            return getNewIcon(event.menuItem.icon, event.colorId)
        }

        return getNewIcon(event.menuItem.icon, android.R.color.black)
    }

    fun drinkClick(event: MainEvent.DrinkClick) : Drawable {
        return changeIcon(event.item, event.isSet, event.colorId, event.tag)
    }

    fun hardcoreClick(event: MainEvent.HardcoreClick) : Drawable {
        return changeIcon(event.item, event.isSet, event.colorId, event.tag)
    }

    private fun changeIcon(item: MenuItem, isSet: Boolean, colorId: Int, tag: Tag) : Drawable {
        settingManager.setValue(tag, !isSet)

        val newIcon = item.icon
        val newColor = if (isSet) android.R.color.black else colorId

        return getNewIcon(newIcon, newColor)
    }

    private fun getNewIcon(newIcon: Drawable, newColor: Int) : Drawable {
        newIcon.mutate()
        newIcon.setColorFilter(ContextCompat.getColor(context, newColor), PorterDuff.Mode.SRC_IN)
        return newIcon
    }
}
