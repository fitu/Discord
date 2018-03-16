package com.discovr.discord.ui.splash

import io.reactivex.Observable

interface SplashContract {
    interface View {
        fun render(model: SplashModel)
    }

    interface Presenter {
        fun isFirstTime(): Boolean

        fun clear()

        fun dispose()

        fun subscribe(events: Observable<SplashEvent>)
    }
}
