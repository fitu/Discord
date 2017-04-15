package com.discovr.discord.injection.app;

import android.arch.persistence.room.Room;

import com.discovr.discord.data.db.CardDao;
import com.discovr.discord.data.db.DiscordDb;
import com.discovr.discord.DiscordApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @Singleton
    static DiscordDb provideRoomDb(DiscordApplication application) {
        return Room.databaseBuilder(application, DiscordDb.class, DiscordDb.NAME).build();
    }
}
