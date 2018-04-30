package com.discovr.discord.ui.splash

interface SplashModel {
    class Start : SplashModel

    class StartFail internal constructor(val errorMessage: String) : SplashModel

    class Error internal constructor(val error: Throwable) : SplashModel
}
