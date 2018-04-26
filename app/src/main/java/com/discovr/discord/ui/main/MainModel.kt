package com.discovr.discord.ui.main

import android.graphics.drawable.Drawable
import android.view.MenuItem
import com.discovr.discord.model.Card

interface MainModel {
    class FragmentStart(val cards: List<Card>) : MainModel

    class Menu(val menuItem: MenuItem, val newIcon: Drawable) : MainModel

    class DrinkClick(val menuItem: MenuItem, val newIcon: Drawable) : MainModel

    class HardcoreClick(val menuItem: MenuItem, val newIcon: Drawable) : MainModel

    class Error internal constructor(val error: Throwable) : MainModel
}

