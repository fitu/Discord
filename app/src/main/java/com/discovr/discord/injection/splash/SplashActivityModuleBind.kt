package com.discovr.discord.injection.splash

import android.app.Activity
import com.discovr.discord.injection.util.ActivityScope
import com.discovr.discord.ui.splash.SplashActivity
import com.discovr.discord.ui.splash.SplashContract
import com.discovr.discord.ui.splash.SplashEvent
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import io.reactivex.Observable
import io.reactivex.subjects.Subject

@Module
abstract class SplashActivityModuleBind {
    @Binds
    @IntoMap
    @ActivityKey(SplashActivity::class)
    internal abstract fun bindActivityInjectorFactory(
            builder: SplashActivityComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @ActivityScope
    internal abstract fun provideActivity(activity: SplashActivity): SplashContract.View

    @Binds
    @ActivityScope
    internal abstract fun provideObservable(subject: Subject<SplashEvent>): Observable<SplashEvent>
}
