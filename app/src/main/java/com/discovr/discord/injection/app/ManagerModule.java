package com.discovr.discord.injection.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.discovr.discord.data.db.DiscordDb;
import com.discovr.discord.data.manager.CardManager;
import com.discovr.discord.data.parser.CardYamlParser;
import com.discovr.discord.data.manager.SettingManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ManagerModule {

    @Singleton
    @Provides
    static SettingManager provideSettingManager(SharedPreferences sharedPreferences) {
        return new SettingManager(sharedPreferences);
    }

    @Singleton
    @Provides
    static CardManager provideCardManager(DiscordDb db, CardYamlParser yamlParser) {
        return new CardManager(db, yamlParser);
    }

    @Singleton
    @Provides
    static CardYamlParser provideCardYamlParser(Context context, ObjectMapper objectMapper) {
        return new CardYamlParser(context, objectMapper);
    }

    @Singleton
    @Provides
    static ObjectMapper provideObjectMapper(YAMLFactory yamlFactory) {
        return new ObjectMapper(yamlFactory);
    }

    @Singleton
    @Provides
    static YAMLFactory provideYAMLFactory() {
        return new YAMLFactory();
    }
}
