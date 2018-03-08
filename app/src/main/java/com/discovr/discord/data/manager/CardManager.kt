package com.discovr.discord.data.manager

import android.util.Log
import com.discovr.discord.data.db.CardDao
import com.discovr.discord.data.db.DiscordDb
import com.discovr.discord.data.parser.CardYaml
import com.discovr.discord.data.parser.CardYamlParser
import com.discovr.discord.data.parser.ParserException
import com.discovr.discord.model.Card
import com.discovr.discord.model.Tag
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class CardManager
@Inject
constructor(private val db: DiscordDb,
            private val settingManager: SettingManager,
            private val yamlParser: CardYamlParser) {
    private val cardDao: CardDao = db.cardDao()

    fun getCards(): Single<List<Card>> {
        val isDrinkSet = if (settingManager.getValue(Tag.DRINK)) 1 else 0
        val isHardcoreSet = if (settingManager.getValue(Tag.HARDCORE)) 1 else 0
        return cardDao.findAllWithFlags(isDrinkSet, isHardcoreSet)
    }

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
