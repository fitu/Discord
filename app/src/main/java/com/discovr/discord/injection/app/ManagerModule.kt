package com.discovr.discord.injection.app

import android.content.Context
import android.content.SharedPreferences
import com.discovr.discord.data.db.DiscordDb
import com.discovr.discord.data.manager.card.CardManager
import com.discovr.discord.data.manager.card.CardResult
import com.discovr.discord.data.manager.setting.SettingManager
import com.discovr.discord.data.manager.setting.SettingResult
import com.discovr.discord.data.parser.CardYamlParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Singleton

@Module
class ManagerModule {

    @Singleton
    @Provides
    internal fun provideSettingManager(results: Subject<SettingResult>,
                                       sharedPreferences: SharedPreferences): SettingManager {
        return SettingManager(results, sharedPreferences)
    }

    @Singleton
    @Provides
    internal fun provideSettingSubject(): Subject<SettingResult> {
        return BehaviorSubject.create()
    }

    @Singleton
    @Provides
    internal fun provideCardManager(results: Subject<CardResult>,
                                    db: DiscordDb,
                                    settingManager: SettingManager,
                                    yamlParser: CardYamlParser): CardManager {
        return CardManager(results, db, settingManager, yamlParser)
    }

    @Singleton
    @Provides
    internal fun provideCardSubject(): Subject<CardResult> {
        return BehaviorSubject.create()
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
