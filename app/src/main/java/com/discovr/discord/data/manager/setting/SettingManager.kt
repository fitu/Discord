package com.discovr.discord.data.manager.setting

import android.content.SharedPreferences
import com.discovr.discord.model.Tag
import io.reactivex.Observable
import io.reactivex.subjects.Subject
import javax.inject.Inject

class SettingManager
@Inject
constructor(private val results: Subject<SettingResult>,
            private val sharedPreferences: SharedPreferences) {

    companion object {
        const val KEY_FIRST_TIME = "KEY_FIRST_TIME"
    }

    val resultsObs: Observable<SettingResult>
        get() = results

    val isFirstTime: Boolean
        get() = sharedPreferences.getBoolean(KEY_FIRST_TIME, true)

    fun handleAction(action: SettingAction): Observable<SettingResult> {
        return actionFirstTime(action as SettingAction.FirstTime)
    }

    private fun actionFirstTime(action: SettingAction.FirstTime): Observable<SettingResult> {
        sharedPreferences.edit()
                .putBoolean(KEY_FIRST_TIME, !isFirstTime)
                .apply()

        results.onNext(SettingResult.FirstTime(action.id))
        return Observable.just(SettingResult.FirstTime(action.id))
    }

    fun getValue(tag: Tag): Boolean {
        return sharedPreferences.getBoolean(tag.name, false)
    }

    fun setValue(tag: Tag, value: Boolean) {
        sharedPreferences.edit()
                .putBoolean(tag.name, value)
                .apply()
    }
}
