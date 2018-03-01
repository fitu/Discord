package com.discovr.discord.ui.splash

interface SplashEvent {
    class Start internal constructor(val isFirstTime: Boolean) : SplashEvent
}
