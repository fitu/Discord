package com.discovr.discord.injection.splash;

import android.app.Activity;

import com.discovr.discord.data.manager.CardManager;
import com.discovr.discord.data.manager.SettingManager;
import com.discovr.discord.injection.util.scope.ActivityScope;
import com.discovr.discord.ui.splash.SplashActivity;
import com.discovr.discord.ui.splash.SplashContract;
import com.discovr.discord.ui.splash.SplashPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module
public abstract class SplashActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(SplashActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindActivityInjectorFactory(
            SplashActivityComponent.Builder builder);

    @Binds
    @ActivityScope
    abstract SplashContract.View provideActivity(SplashActivity activity);

    @Provides
    @ActivityScope
    static SplashContract.Presenter providePresenter(
            SplashContract.View activity,
            CardManager cardManager,
            SettingManager settingManager) {
        return new SplashPresenter(activity, cardManager, settingManager);
    }
}