package com.discovr.discord.ui.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.discovr.discord.R
import com.discovr.discord.ui.main.MainActivity
import dagger.android.AndroidInjection
import io.reactivex.subjects.Subject
import timber.log.Timber
import javax.inject.Inject

// TODO create BaseActivity to inherit common methods
class SplashActivity : AppCompatActivity(), SplashContract.View {
    var presenter: SplashContract.Presenter? = null @Inject set
    var events: Subject<SplashEvent>? = null @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        events!!.onNext(SplashEvent.Start(presenter!!.isFirstTime()))
    }

    override fun render(model: SplashModel) {
        if (model is SplashModel.Start) {
            goToMain()
            return
        }

        if (model is SplashModel.StartFail) {
            logError(model.errorMessage)
            return
        }

        if (model is SplashModel.Error) {
            logError(model.error.message)
            return
        }

        throw IllegalArgumentException("Don't know how to render model $model")
    }

    private fun goToMain() {
        MainActivity.start(this)
        finish()
    }

    private fun logError(errorMessage: String?) {
        Timber.e(errorMessage)
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
