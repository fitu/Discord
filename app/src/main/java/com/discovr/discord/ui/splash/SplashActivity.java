package com.discovr.discord.ui.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.discovr.discord.R;
import com.discovr.discord.data.manager.SettingManager;
import com.discovr.discord.ui.main.MainActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Observable;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {
    private static final String TAG = "SplashActivity";
    @Inject SplashContract.Presenter presenter;
    @Inject SettingManager settingManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public Observable<SplashEvent.Start> startEvent() {
        return Observable.just(new SplashEvent.Start(settingManager.isFirstTime()));
    }

    @Override
    public void render(SplashModel model) {
        if (model instanceof SplashModel.NotFirstTime) {
            goToMain();
            return;
        }

        if (model instanceof SplashModel.FirstTime) {
            goToMain();
            return;
        }

        if (model instanceof SplashModel.Error) {
            renderError(((SplashModel.Error) model));
            return;
        }

        throw new IllegalArgumentException("Don't know how to render model " + model);
    }

    private void goToMain() {
        MainActivity.start(this);
        finish();
    }

    private void renderError(SplashModel.Error model) {
        Log.e(TAG, model.getError().getMessage());
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}
