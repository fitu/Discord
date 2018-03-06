package com.discovr.discord.ui.main

import android.view.Menu
import android.view.MenuItem

interface MainContract {
    interface Activity

    interface ActivityPresenter {
        fun drinkClick(item: MenuItem)

        fun hardcoreClick(item: MenuItem)

        fun setUpMenu(menu: Menu)
    }

    interface CardFragment

    interface CardFragmentPresenter
}