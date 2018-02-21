package com.discovr.discord.data.parser

import com.discovr.discord.model.Card

class CardYaml(
        val id: Int,
        val quantity: Int,
        val title: String,
        val description: String,
        val quote: String,
        val timer: Int,
        val dices: List<String>,
        val tags: List<String>) {

    companion object {
        fun toCards(cardYamls: List<CardYaml>): List<Card> {
            return cardYamls.map { CardYaml.toCard(it) }
        }

        private fun toCard(cardYaml: CardYaml): Card {
            return Card.builder()
                    .id(cardYaml.id)
                    .quantity(cardYaml.quantity)
                    .title(cardYaml.title)
                    .description(cardYaml.description)
                    .quote(cardYaml.quote)
                    .timer(cardYaml.timer)
                    .dices(cardYaml.dices)
                    .tags(cardYaml.tags)
                    .build()
        }
    }
}