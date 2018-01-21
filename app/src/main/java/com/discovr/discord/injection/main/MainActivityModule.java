package com.discovr.discord.injection.main;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Vibrator;

import com.discovr.discord.data.manager.SettingManager;
import com.discovr.discord.injection.util.scope.ActivityScope;
import com.discovr.discord.ui.main.MainAction;
import com.discovr.discord.ui.main.MainActivity;
import com.discovr.discord.ui.main.MainContract;
import com.discovr.discord.ui.main.MainPresenter;
import com.discovr.discord.ui.main.card.CardFragment;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import static android.content.Context.VIBRATOR_SERVICE;

@Module(subcomponents = {CardFragmentComponent.class})
public abstract class MainActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindActivityInjectorFactory(
            MainActivityComponent.Builder builder);

    @Binds
    @ActivityScope
    abstract MainContract.Activity bindActivity(MainActivity activity);

    @Binds
    @ActivityScope
    abstract MainContract.CardFragment bindFragment(CardFragment fragment);

    @Provides
    @ActivityScope
    static MainContract.ActivityPresenter provideActivityPresenter(
            MainContract.Activity activity, SettingManager settingManager) {
        return new MainPresenter(activity, settingManager);
    }

    @Provides
    @ActivityScope
    static CardFragment provideCardFragment() {
        return new CardFragment();
    }

    @Provides
    @ActivityScope
    static Vibrator provideVibrator(MainActivity activity) {
        return (Vibrator) activity.getSystemService(VIBRATOR_SERVICE);
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
    static Subject<MainAction> provideSubject() {
        return PublishSubject.create();
    }
}