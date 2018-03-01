package com.discovr.discord.ui.main

import com.discovr.discord.data.manager.SettingManager
import java.util.*
import javax.inject.Inject

class MainPresenter
@Inject constructor(private val activity: MainContract.Activity,
                    private val settingManager: SettingManager) : MainContract.ActivityPresenter {
    private val random: Random = Random(System.nanoTime())
}
