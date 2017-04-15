package com.discovr.discord.data.manager;

import android.content.Context;

import com.discovr.discord.data.db.CardDao;
import com.discovr.discord.data.db.DiscordDb;
import com.discovr.discord.data.parser.CardYaml;
import com.discovr.discord.data.parser.CardYamlParser;
import com.discovr.discord.data.parser.ParserException;
import com.discovr.discord.model.Card;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CardManager {
    public static final String DEFAULT_CARDS_DIRECTORY = "raw";
    public static final String DEFAULT_CARDS_FILENAME = "cards";

    private final CardDao cardDao;
    private final CardYamlParser yamlParser;

    @Inject
    public CardManager(DiscordDb db, CardYamlParser yamlParser) {
        this.cardDao = db.cardDao();
        this.yamlParser = yamlParser;
    }

    public Observable<Boolean> loadCards() throws ParserException {
        List<Card> cards = CardYaml.toCards(yamlParser.loadCards());
        return saveCardsToDb(cards);
    }

    private Observable<Boolean> saveCardsToDb(List<Card> cards) {
        if (!cardDao.insertAll(cards).isEmpty()) {
            return Observable.just(true);
        }

        return Observable.just(false);
    }


    // TODO replace all this
    public static boolean isValid(Card card) {
        return true;
    }

    public static int getErrorMessage() {
        return 1;
    }

    public static List<Card> getExtraCards(Context context) {
        return new ArrayList<>();
    }

    public static boolean isValidTitle(String title) {
        return true;
    }

    public static boolean isValidQuote(String quote) {
        return true;
    }

    public static boolean isValidTimer(int timer) {
        return true;
    }

    public static boolean isValidDice(List<String> dices) {
        return true;
    }

    public static List<Card> parseFromYaml(InputStream cardsFile) {
        return new ArrayList<>();
    }
}
