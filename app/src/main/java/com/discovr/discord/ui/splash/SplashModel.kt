package com.discovr.discord.ui.splash

interface SplashModel {
    class FirstTime : SplashModel

    class NotFirstTime : SplashModel

    class Error internal constructor(val error: Throwable) : SplashModel
}
