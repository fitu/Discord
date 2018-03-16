package com.discovr.discord.ui.main

import android.view.MenuItem
import com.discovr.discord.model.Tag

interface MainEvent {
    class MenuEvent(val menuItem: MenuItem,
                    val isSet: Boolean,
                    val colorId: Int) : MainEvent

    class SwipeLeft : MainEvent

    class SwipeRight : MainEvent

    class DrinkClick(val item: MenuItem,
                     val isSet: Boolean,
                     val colorId: Int,
                     val tag: Tag) : MainEvent

    class HardcoreClick(val item: MenuItem,
                        val isSet: Boolean,
                        val colorId: Int,
                        val tag: Tag) : MainEvent
}

