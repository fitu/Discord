package com.discovr.discord.data.manager;

import com.discovr.discord.data.db.CardDao;
import com.discovr.discord.data.db.DiscordDb;
import com.discovr.discord.data.parser.CardYaml;
import com.discovr.discord.data.parser.CardYamlParser;
import com.discovr.discord.data.parser.ParserException;
import com.discovr.discord.model.Card;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class CardManager {
    private final CardDao cardDao;
    private final CardYamlParser yamlParser;

    @Inject
    public CardManager(DiscordDb db, CardYamlParser yamlParser) {
        this.cardDao = db.cardDao();
        this.yamlParser = yamlParser;
    }

    public Observable<Boolean> loadCards() throws ParserException {
        List<Card> cards = CardYaml.toCards(yamlParser.loadCards());
        List<Card> repeatedCards = getCardByQuantity(cards);
        return saveCardsToDb(repeatedCards);
    }

    private List<Card> getCardByQuantity(List<Card> cards) {
        // TODO is ok here? or should do it when retrieving cards
        List<Card> repeatedCards = new ArrayList<>();
        for (Card card : cards) {
            for (int quantity = 1; quantity <= card.getQuantity(); quantity++)
                repeatedCards.add(card);
        }
        return repeatedCards;
    }

    private Observable<Boolean> saveCardsToDb(List<Card> cards) {
        if (!cardDao.insertAll(cards).isEmpty()) {
            return Observable.just(true);
        }

        return Observable.just(false);
    }

    public Single<List<Card>> getCards() {
        return cardDao.getAll();
    }
}
