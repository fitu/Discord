package com.discovr.discord.data.manager.setting

interface SettingAction {
    val id: String

    class FirstTime internal constructor(override val id: String) : SettingAction

    class PrefChange internal constructor(override val id: String, val tag: String, val currentValue: Boolean) : SettingAction
}