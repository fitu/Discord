package com.discovr.discord.ui.main

import android.view.MenuItem

interface MainAction {
    class SwipeLeft : MainAction

    class SwipeRight : MainAction

    class DrinkClick(val item: MenuItem) : MainAction

    class HardcoreClick(val item: MenuItem) : MainAction
}
