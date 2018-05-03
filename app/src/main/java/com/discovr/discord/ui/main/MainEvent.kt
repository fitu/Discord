package com.discovr.discord.ui.main

import android.view.MenuItem
import com.discovr.discord.model.Tag

interface MainEvent {
    class MenuEvent(val menuItems: Sequence<MenuItem>,
                    val areSets: HashMap<Tag, Boolean>,
                    val colorIds: HashMap<Tag, Int>) : MainEvent

    class FragmentStart : MainEvent

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

