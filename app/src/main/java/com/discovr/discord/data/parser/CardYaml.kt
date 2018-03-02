package com.discovr.discord.data.parser

import com.discovr.discord.model.Card

import java.util.ArrayList

class CardYaml {
    var id: Int? = null
    var quantity: Int? = null
    var title: String? = null
    var description: String? = null
    var quote: String? = null
    var timer: Int? = null
    var dices: List<String>? = null
    var tags: List<String>? = null

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