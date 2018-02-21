package com.discovr.discord.injection.main

import android.app.Activity
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
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
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

@Module(subcomponents = arrayOf(CardFragmentComponent::class))
abstract class MainActivityModuleBind {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    internal abstract fun bindActivityInjectorFactory(
            builder: MainActivityComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @ActivityScope
    internal abstract fun bindActivity(activity: MainActivity): MainContract.Activity

    @Binds
    @ActivityScope
    internal abstract fun bindFragment(fragment: CardFragment): MainContract.CardFragment
}
