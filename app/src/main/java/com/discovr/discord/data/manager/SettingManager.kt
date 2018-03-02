package com.discovr.discord.data.manager

import android.content.SharedPreferences

import com.discovr.discord.model.Tag

import javax.inject.Inject

import io.reactivex.Observable

class SettingManager
@Inject
constructor(private val sharedPreferences: SharedPreferences) {

    val isFirstTime: Boolean
        get() = sharedPreferences.getBoolean(KEY_FIRST_TIME, true)

    companion object {
        const val KEY_FIRST_TIME = "KEY_FIRST_TIME"
    }

    fun setUpApp(): Observable<Boolean> {
        return Observable.just(setDefaultValues())
    }

    private fun setDefaultValues(): Boolean {
        sharedPreferences.edit()
                .putBoolean(Tag.DRINK.name, false)
                .putBoolean(Tag.HARDCORE.name, false)
                .apply()
        return true
    }

    fun notFirstTime(isFirstTime: Boolean): Boolean {
        sharedPreferences.edit()
                .putBoolean(KEY_FIRST_TIME, !isFirstTime)
                .apply()
        return true
    }
}
