package com.discovr.discord.data.manager

import com.discovr.discord.data.db.CardDao
import com.discovr.discord.data.db.DiscordDb
import com.discovr.discord.data.parser.CardYaml
import com.discovr.discord.data.parser.CardYamlParser
import com.discovr.discord.data.parser.ParserException
import com.discovr.discord.model.Card

import java.util.ArrayList

import javax.inject.Inject

import io.reactivex.Observable
import io.reactivex.Single

class CardManager @Inject
constructor(db: DiscordDb, private val yamlParser: CardYamlParser) {
    private val cardDao: CardDao = db.cardDao()

    val cards: Single<List<Card>>
        get() = cardDao.findAll()

    @Throws(ParserException::class)
    fun loadCards(): Observable<Boolean> {
        val cards = CardYaml.toCards(yamlParser.loadCards())
        val repeatedCards = getCardByQuantity(cards)
        return saveCardsToDb(repeatedCards)
    }

    private fun getCardByQuantity(cards: List<Card>): List<Card> {
        // TODO is ok here? or should do it when retrieving cards
        val repeatedCards = ArrayList<Card>()
        for (card in cards) {
            for (quantity in 1..card.quantity!!)
                repeatedCards.add(card)
        }
        return repeatedCards
    }

    private fun saveCardsToDb(cards: List<Card>): Observable<Boolean> {
        return if (!cardDao.insertAll(cards).isEmpty()) {
            Observable.just(true)
        } else Observable.just(false)
    }
}
