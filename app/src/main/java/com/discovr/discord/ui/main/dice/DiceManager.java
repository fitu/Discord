package com.discovr.discord.ui.main.dice;

import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.view.View;

import com.discovr.discord.R;
import com.discovr.discord.ui.main.MainContract;
import com.discovr.discord.ui.main.MainEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

public class DiceManager {
    private final Subject<MainEvent> subject;
    private final Random random;
    private final CompositeDisposable compositeDisposable;

    private MainContract.ActivityPresenter presenter;
    private List<String> dices = new ArrayList<>();

    @Inject
    public DiceManager(Subject<MainEvent> subject) {
        // TODO subject or observable?
        this.subject = subject;
        this.random = new Random(System.nanoTime());
        this.compositeDisposable = new CompositeDisposable();
        subscribe();
    }

    private void subscribe() {
        subject.doOnSubscribe(compositeDisposable::add)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::actions);
    }

    private void actions(MainEvent event) {
        if (event instanceof MainEvent.RollDice) {
            rollDice();
        }
    }

    public void setPresenter(MainContract.ActivityPresenter presenter) {
        this.presenter = presenter;
    }

    public void rollDice() {
        // TODO remove this
        MediaPlayer mediaPlayer = MediaPlayer.create(presenter.getActivity(), R.raw.roll);
        try {
            mediaPlayer.prepare();
        } catch (Exception e) {
        }

        compositeDisposable.add(
                Observable.just(mediaPlayer)
                        .doOnNext(MediaPlayer::start)
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(mp -> !dices.isEmpty())
                        .repeat(50)
                        .map(integer -> random.nextInt(dices.size()))
                        .doOnNext(integer -> presenter.setDiceText(dices.get(integer)))
                        .delay(15, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .subscribe()
        );
    }

    public void removeDices() {
        presenter.showDiceVisibility(View.INVISIBLE);
        // TODO clear or dispose?
        compositeDisposable.clear();
    }

    public void showDices(List<String> dices) {
        dices.clear();
        presenter.showDiceVisibility(View.VISIBLE);
        for (int i = 0; i < dices.size(); i++) {
            dices.add(dices.get(i));
        }

        presenter.setDiceText(dices.get(random.nextInt(dices.size())));
    }

    public void shakeDice(SensorEvent sensorEvent) {
        final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
        final int SHAKE_SLOP_TIME_MS = 500;

        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        float gX = x / SensorManager.GRAVITY_EARTH;
        float gY = y / SensorManager.GRAVITY_EARTH;
        float gZ = z / SensorManager.GRAVITY_EARTH;

        // gForce will be close to 1 when there is no movement.
        Float f = gX * gX + gY * gY + gZ * gZ;
        Double d = Math.sqrt(f.doubleValue());
        float gForce = d.floatValue();

        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            final long now = System.currentTimeMillis();

            // ignore shake events too close to each other (500ms)
            if (SHAKE_SLOP_TIME_MS > now)
                return;

            rollDice();
        }
    }
}
