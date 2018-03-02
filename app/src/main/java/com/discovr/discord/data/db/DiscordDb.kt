package com.discovr.discord.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

import com.discovr.discord.model.Card

@Database(entities = [Card::class], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class DiscordDb : RoomDatabase() {

    abstract fun cardDao(): CardDao

    companion object {
        const val NAME = "DISCORD_DB"
    }
}