package com.discovr.discord.injection.main;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Vibrator;

import com.discovr.discord.data.manager.SettingManager;
import com.discovr.discord.injection.util.scope.ActivityScope;
import com.discovr.discord.ui.main.MainActivity;
import com.discovr.discord.ui.main.MainContract;
import com.discovr.discord.ui.main.MainEvent;
import com.discovr.discord.ui.main.MainPresenter;
import com.discovr.discord.ui.main.card.CardFragment;
import com.discovr.discord.ui.main.dialogs.RulesDialog;
import com.discovr.discord.ui.main.dice.DiceManager;
import com.discovr.discord.ui.main.timer.TimerManager;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import static android.content.Context.VIBRATOR_SERVICE;

@Module(subcomponents = {CardFragmentComponent.class, DiscordDialogComponent.class})
public abstract class MainActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindActivityDeckInjectorFactory(
            MainActivityComponent.Builder builder);

    @Binds
    @ActivityScope
    abstract MainContract.Activity bindMainActivity(MainActivity activity);

    @Binds
    @ActivityScope
    abstract MainContract.CardFragment bindCardFragment(CardFragment fragment);

    @Provides
    @ActivityScope
    static MainContract.ActivityPresenter provideActivityPresenter(
            MainContract.Activity activity,
            DiceManager diceManager,
            TimerManager timerManager,
            SettingManager settingManager) {
        return new MainPresenter(activity, diceManager, timerManager, settingManager);
    }

    @Provides
    @ActivityScope
    static CardFragment provideCardFragment() {
        return new CardFragment();
    }

    @Provides
    @ActivityScope
    static RulesDialog provideDialogRules() {
        return new RulesDialog();
    }

    @Provides
    @ActivityScope
    static TimerManager provideTimerManager(Subject<MainEvent> subject, Vibrator vibrator) {
        return new TimerManager(subject, vibrator);
    }

    @Provides
    @ActivityScope
    static Vibrator provideVibrator(MainActivity activity) {
        return (Vibrator) activity.getSystemService(VIBRATOR_SERVICE);
    }

    @Provides
    @ActivityScope
    static DiceManager provideDiceManager(Subject<MainEvent> subject) {
        return new DiceManager(subject);
    }

    @Provides
    @ActivityScope
    static Sensor provideSensorAccelerometer(SensorManager sensorManager) {
        return sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Provides
    @ActivityScope
    static SensorManager provideSensorManager(MainActivity activity) {
        return (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
    }

    @Provides
    @ActivityScope
    static Subject<MainEvent> provideSubject() {
        return PublishSubject.create();
    }
}