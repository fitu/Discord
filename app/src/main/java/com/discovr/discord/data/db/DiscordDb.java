package com.discovr.discord.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.discovr.discord.model.Card;

@Database(entities = {Card.class}, version = 1, exportSchema = false)
@TypeConverters(ListConverter.class)
public abstract class DiscordDb extends RoomDatabase {
    public static final String NAME = "DISCORD_DB";

    public abstract CardDao cardDao();
}
