package com.discovr.discord.injection.app

import android.content.Context
import android.content.SharedPreferences

import com.discovr.discord.data.db.DiscordDb
import com.discovr.discord.data.manager.CardManager
import com.discovr.discord.data.parser.CardYamlParser
import com.discovr.discord.data.manager.SettingManager
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class ManagerModule {

    @Singleton
    @Provides
    internal fun provideSettingManager(sharedPreferences: SharedPreferences): SettingManager {
        return SettingManager(sharedPreferences)
    }

    @Singleton
    @Provides
    internal fun provideCardManager(db: DiscordDb, yamlParser: CardYamlParser): CardManager {
        return CardManager(db, yamlParser)
    }

    @Singleton
    @Provides
    internal fun provideCardYamlParser(context: Context, objectMapper: ObjectMapper): CardYamlParser {
        return CardYamlParser(context, objectMapper)
    }

    @Singleton
    @Provides
    internal fun provideObjectMapper(yamlFactory: YAMLFactory): ObjectMapper {
        return ObjectMapper(yamlFactory)
    }

    @Singleton
    @Provides
    internal fun provideYAMLFactory(): YAMLFactory {
        return YAMLFactory()
    }
}
