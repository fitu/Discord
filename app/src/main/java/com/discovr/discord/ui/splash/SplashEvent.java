package com.discovr.discord.ui.splash;

public interface SplashEvent {
    final class Start implements SplashEvent {
        private final boolean firstTime;

        Start(boolean firstTime) {
            this.firstTime = firstTime;
        }

        boolean isFirstTime() {
            return firstTime;
        }
    }
}
