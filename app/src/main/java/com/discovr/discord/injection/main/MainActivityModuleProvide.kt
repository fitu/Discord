package com.discovr.discord.injection.main

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Vibrator
import com.discovr.discord.data.manager.SettingManager
import com.discovr.discord.injection.util.scope.ActivityScope
import com.discovr.discord.ui.main.MainAction
import com.discovr.discord.ui.main.MainActivity
import com.discovr.discord.ui.main.MainContract
import com.discovr.discord.ui.main.MainPresenter
import com.discovr.discord.ui.main.card.CardFragment
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject


@Module(subcomponents = arrayOf(CardFragmentComponent::class))
class MainActivityModuleProvide {
    @Provides
    @ActivityScope
    internal fun provideActivityPresenter(
            activity: MainContract.Activity, settingManager: SettingManager): MainContract.ActivityPresenter {
        return MainPresenter(activity, settingManager)
    }

    @Provides
    @ActivityScope
    internal fun provideCardFragment(): CardFragment {
        return CardFragment()
    }

    @Provides
    @ActivityScope
    internal fun provideVibrator(activity: MainActivity): Vibrator {
        return activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    @Provides
    @ActivityScope
    internal fun provideSensorAccelerometer(sensorManager: SensorManager): Sensor {
        return sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    @Provides
    @ActivityScope
    internal fun provideSensorManager(activity: MainActivity): SensorManager {
        return activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    @Provides
    @ActivityScope
    internal fun provideSubject(): Subject<MainAction> {
        return PublishSubject.create()
    }
}
