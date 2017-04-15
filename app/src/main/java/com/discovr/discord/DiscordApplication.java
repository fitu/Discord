package com.discovr.discord;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;
import com.discovr.discord.injection.app.AppComponent;
import com.discovr.discord.injection.app.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.fabric.sdk.android.Fabric;

public class DiscordApplication extends Application implements HasActivityInjector {
    @Inject DispatchingAndroidInjector<Activity> injector;
    @Inject SharedPreferences sharedPreferences;
    private static AppComponent appComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        setUpDagger();
    }

    private void setUpDagger() {
        appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build();

        appComponent.inject(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        if (BuildConfig.DEBUG) {
            setDebugConfig();
        }
    }

    private void setDebugConfig() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return injector;
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
