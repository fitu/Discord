package com.discovr.discord.injection.app

import android.arch.persistence.room.Room

import com.discovr.discord.data.db.CardDao
import com.discovr.discord.data.db.DiscordDb
import com.discovr.discord.DiscordApplication

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class DbModule {

    @Provides
    @Singleton
    internal fun provideRoomDb(application: DiscordApplication): DiscordDb {
        return Room.databaseBuilder(application, DiscordDb::class.java, DiscordDb.NAME).build()
    }
}
