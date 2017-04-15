package com.discovr.discord.data.manager;

import android.content.SharedPreferences;

import com.discovr.discord.model.Tag;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SettingManager {
    public static final String KEY_FIRST_TIME = "KEY_FIRST_TIME";

    private final SharedPreferences sharedPreferences;

    @Inject
    public SettingManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public boolean isFirstTime() {
        return sharedPreferences.getBoolean(KEY_FIRST_TIME, true);
    }

    public Observable<Boolean> setUpApp() {
        return Observable.just(setDefaultValues());
    }

    private boolean setDefaultValues() {
        sharedPreferences.edit()
                .putBoolean(Tag.DRINK, false)
                .putBoolean(Tag.HARDCORE, false)
                .apply();
        return true;
    }

    public boolean notFirstTime(boolean isFirstTime) {
        sharedPreferences.edit()
                .putBoolean(KEY_FIRST_TIME, !isFirstTime)
                .apply();
        return true;
    }

    public boolean getBooleanValue(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void saveBooleanValue(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }
}
