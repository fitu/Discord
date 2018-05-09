package com.discovr.discord.data.manager.setting

interface SettingResult {
    val id: String

    class FirstTime internal constructor(override val id: String) : SettingResult

    class PrefChange internal constructor(override val id: String, val tag: String, val newValue: Boolean) : SettingResult
}