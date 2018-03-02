package com.discovr.discord.data.parser

import android.content.Context
import android.content.res.Resources

import com.discovr.discord.model.Card
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

import java.io.IOException
import java.io.InputStream

import javax.inject.Inject

class CardYamlParser
@Inject
constructor(private val context: Context, private val objectMapper: ObjectMapper) {
    private val resources: Resources = context.resources

    private val cardsFromFile: List<CardYaml>
        @Throws(ParserException::class)
        get() {
            val resourceId = resources.getIdentifier(Card.FILE_NAME, Card.FILE_DIRECTORY, context.packageName)
            val stream = resources.openRawResource(resourceId)
            return parseFromYaml(stream)
        }

    @Throws(ParserException::class)
    fun loadCards(): List<CardYaml> {
        return cardsFromFile
    }

    @Throws(ParserException::class)
    private fun parseFromYaml(yamlInputStream: InputStream): List<CardYaml> {
        try {
            return objectMapper.readValue(yamlInputStream, object : TypeReference<List<CardYaml>>() {})
        } catch (exception: IOException) {
            throw ParserException("Error parsing cards")
        }
    }
}
