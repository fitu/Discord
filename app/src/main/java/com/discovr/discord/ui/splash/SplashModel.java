package com.discovr.discord.ui.splash;

public interface SplashModel {
    final class FirstTime implements SplashModel {
    }

    final class NotFirstTime implements SplashModel {
    }

    final class Error implements SplashModel {
        private final Throwable error;

        Error(Throwable error) {
            this.error = error;
        }

        Throwable getError() {
            return error;
        }
    }
}
