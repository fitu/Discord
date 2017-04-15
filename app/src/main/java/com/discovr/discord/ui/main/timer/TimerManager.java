package com.discovr.discord.ui.main.timer;

import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.View;

import com.discovr.discord.R;
import com.discovr.discord.ui.main.MainContract;
import com.discovr.discord.ui.main.MainEvent;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

public class TimerManager {
    private static final long DEFAULT_VIBRATION_TIME = 800;

    private final Vibrator vibrator;
    // TODO subscribe
    private final Subject<MainEvent> subject;
    private final CompositeDisposable compositeDisposable;

    private MainContract.ActivityPresenter presenter;

    @Inject
    public TimerManager(Subject<MainEvent> subject, Vibrator vibrator) {
        // TODO subject or observable?
        this.subject = subject;
        this.vibrator = vibrator;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void setPresenter(MainContract.ActivityPresenter presenter) {
        this.presenter = presenter;
    }

    public void removeTimer() {
        presenter.showTimerVisibility(View.INVISIBLE);
        compositeDisposable.clear();
    }

    public void showTimer(int timer) {
        presenter.showTimerVisibility(View.VISIBLE);
        presenter.setTimerText(String.valueOf(timer));
    }

    public void startTimer(int timer) {
        // TODO refactor this
        MediaPlayer mediaPlayerTimeout = MediaPlayer.create(presenter.getActivity(), R.raw.timeout);
        try {
            mediaPlayerTimeout.prepare();
        } catch (Exception e) {
        }

        MediaPlayer mediaPlayerTimer = MediaPlayer.create(presenter.getActivity(), R.raw.timer);
        try {
            mediaPlayerTimeout.prepare();
            mediaPlayerTimer.start();
        } catch (Exception e) {
        }

        compositeDisposable.add(
                Observable
                        .interval(1, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(v -> timer - v)
                        .doOnNext(seconds -> presenter.setTimerText(String.valueOf(seconds)))
                        .takeUntil(v -> v == 0)
                        .doOnComplete(() -> {
                            vibrator.vibrate(DEFAULT_VIBRATION_TIME);
                            mediaPlayerTimeout.start();
                        })
                        .subscribeOn(Schedulers.io())
                        .subscribe()
        );
    }

}
