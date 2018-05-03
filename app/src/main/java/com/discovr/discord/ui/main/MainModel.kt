package com.discovr.discord.ui.main

import com.discovr.discord.model.Card

interface MainModel {
    class FragmentStart(val cards: List<Card>) : MainModel

    class MenuDone : MainModel

    class DrinkClick : MainModel

    class HardcoreClick : MainModel

    class Error internal constructor(val error: Throwable) : MainModel
}

