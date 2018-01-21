package com.discovr.discord.ui.main;

import com.discovr.discord.data.manager.SettingManager;

import java.util.Random;

import javax.inject.Inject;

public class MainPresenter implements MainContract.ActivityPresenter {
    private final MainContract.Activity activity;
    private final SettingManager settingManager;
    private final Random random;


    @Inject
    public MainPresenter(MainContract.Activity activity, SettingManager settingManager) {
        this.activity = activity;
        this.settingManager = settingManager;
        this.random = new Random(System.nanoTime());
    }
}
