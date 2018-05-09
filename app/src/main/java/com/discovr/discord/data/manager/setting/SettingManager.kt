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
        if (action is SettingAction.FirstTime) {
            return actionFirstTime(action)
        }

        return actionPrefChange(action as SettingAction.PrefChange)
    }

    private fun actionFirstTime(action: SettingAction.FirstTime): Observable<SettingResult> {
        setValue(KEY_FIRST_TIME, !isFirstTime)
        results.onNext(SettingResult.FirstTime(action.id))
        return Observable.just(SettingResult.FirstTime(action.id))
    }

    private fun actionPrefChange(action: SettingAction.PrefChange): Observable<SettingResult> {
        setValue(action.tag, !action.currentValue)
        results.onNext(SettingResult.PrefChange(action.id, action.tag, !action.currentValue))
        return Observable.just(SettingResult.PrefChange(action.id, action.tag, !action.currentValue))
    }

    fun getValue(tag: Tag): Boolean {
        return sharedPreferences.getBoolean(tag.name, false)
    }

    private fun setValue(tag: String, value: Boolean) {
        sharedPreferences.edit()
                .putBoolean(tag, value)
                .apply()
    }
}
