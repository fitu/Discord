package com.discovr.discord.ui.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import com.discovr.discord.R
import com.discovr.discord.data.manager.SettingManager
import com.discovr.discord.ui.main.MainActivity

import javax.inject.Inject

import dagger.android.AndroidInjection
import io.reactivex.Observable

class SplashActivity : AppCompatActivity(),
        SplashContract.View {

    var presenter: SplashContract.Presenter? = null @Inject set
    var settingManager: SettingManager? = null @Inject set

    companion object {
        private const val TAG = "SplashActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        presenter!!.start()
    }

    override fun startEvent(): Observable<SplashEvent.Start> {
        return Observable.just(SplashEvent.Start(settingManager!!.isFirstTime))
    }

    override fun render(model: SplashModel) {
        if (model is SplashModel.NotFirstTime) {
            goToMain()
            return
        }

        if (model is SplashModel.FirstTime) {
            goToMain()
            return
        }

        if (model is SplashModel.Error) {
            renderError(model)
            return
        }

        throw IllegalArgumentException("Don't know how to render model " + model)
    }

    private fun goToMain() {
        MainActivity.start(this)
        finish()
    }

    private fun renderError(model: SplashModel.Error) {
        Log.e(TAG, model.error.message)
        finish()
    }

    override fun onStop() {
        super.onStop()
        presenter!!.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter!!.dispose()
    }
}
