package com.discovr.discord.ui.splash;

import io.reactivex.Observable;

public interface SplashContract {
    interface View {
        Observable<SplashEvent.Start> startEvent();

        void render(SplashModel model);
    }

    interface Presenter {
        void start();

        void clear();

        void dispose();
    }
}
