package com.discovr.discord.data.manager.card

interface CardAction {
    val id: String

    class LoadCards internal constructor(override val id: String) : CardAction
}
