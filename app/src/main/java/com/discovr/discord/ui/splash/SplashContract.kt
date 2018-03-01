package com.discovr.discord.ui.splash

import io.reactivex.Observable

interface SplashContract {
    interface View {
        fun startEvent(): Observable<SplashEvent.Start>

        fun render(model: SplashModel)
    }

    interface Presenter {
        fun start()

        fun clear()

        fun dispose()
    }
}
