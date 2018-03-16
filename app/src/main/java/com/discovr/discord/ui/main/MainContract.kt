package com.discovr.discord.ui.main

import android.view.Menu
import android.view.MenuItem
import io.reactivex.Observable

interface MainContract {
    interface Activity

    interface ActivityPresenter {
        fun drinkClick(item: MenuItem)

        fun hardcoreClick(item: MenuItem)

        fun setUpMenu(menu: Menu)

        fun subscribe(actions: Observable<MainAction>?)
        fun dispose()
        fun clear()
    }

    interface CardFragment

    interface CardFragmentPresenter
}