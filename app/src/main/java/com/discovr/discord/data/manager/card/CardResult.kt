package com.discovr.discord.data.manager.card

interface CardResult {
    class LoadCardsDone : CardResult

    class LoadCardsFail : CardResult
}