package com.discovr.discord.ui.splash;

import com.discovr.discord.data.manager.CardManager;
import com.discovr.discord.data.manager.SettingManager;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SplashPresenter implements SplashContract.Presenter {
    private final SplashContract.View view;
    private final CompositeDisposable compositeDisposable;
    private final CardManager cardManager;
    private final SettingManager settingManager;

    @Inject
    public SplashPresenter(SplashContract.View view, CardManager cardManager, SettingManager settingManager) {
        this.view = view;
        this.cardManager = cardManager;
        this.settingManager = settingManager;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void start() {
        view.startEvent()
                .doOnSubscribe(compositeDisposable::add)
                .subscribeOn(Schedulers.io())
                .flatMap(this::setUp)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::render);
    }

    private Observable<SplashModel> setUp(SplashEvent.Start event) {
        if (!event.isFirstTime()) {
            return Observable.just(new SplashModel.NotFirstTime());
        }

        return settingManager.setUpApp()
                .flatMap(ignored -> cardManager.loadCards())
                .doOnNext(settingManager::notFirstTime)
                .map(discard -> ((SplashModel) new SplashModel.FirstTime()))
                .onErrorReturn(SplashModel.Error::new);
    }

    @Override
    public void clear() {
        compositeDisposable.clear();
    }

    @Override
    public void dispose() {
        compositeDisposable.dispose();
    }
}
