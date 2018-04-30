package com.discovr.discord.ui.splash

interface SplashContract {
    interface View {
        fun render(model: SplashModel)
    }

    interface Presenter {
        fun isFirstTime(): Boolean

        fun clear()

        fun dispose()
    }
}
