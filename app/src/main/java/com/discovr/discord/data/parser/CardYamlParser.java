package com.discovr.discord.data.parser;

import android.content.Context;
import android.content.res.Resources;

import com.discovr.discord.model.Card;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

public class CardYamlParser {
    private final Context context;
    private final ObjectMapper objectMapper;
    private final Resources resources;

    @Inject
    public CardYamlParser(Context context, ObjectMapper objectMapper) {
        this.context = context;
        this.objectMapper = objectMapper;
        this.resources = context.getResources();
    }

    public List<CardYaml> loadCards() throws ParserException {
        return getCardsFromFile();
    }

    private List<CardYaml> getCardsFromFile() throws ParserException {
        int resourceId = resources.getIdentifier(Card.FILE_NAME, Card.FILE_DIRECTORY, context.getPackageName());
        InputStream stream = resources.openRawResource(resourceId);
        return parseFromYaml(stream);
    }

    private List<CardYaml> parseFromYaml(InputStream yamlInputStream) throws ParserException {
        try {
            return objectMapper.readValue(yamlInputStream, new TypeReference<List<CardYaml>>() {});
        } catch (IOException exception) {
            throw new ParserException("Error parsing cards");
        }
    }
}
