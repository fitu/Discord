package com.discovr.discord.data.manager.card

interface CardResult {
    val id: String

    class LoadCardsDone internal constructor(override val id: String) : CardResult

    class LoadCardsFail internal constructor(override val id: String) : CardResult
}