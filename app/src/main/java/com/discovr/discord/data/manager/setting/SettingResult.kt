package com.discovr.discord.data.manager.setting

interface SettingResult {
    val id: String

    class FirstTime internal constructor(override val id: String) : SettingResult
}