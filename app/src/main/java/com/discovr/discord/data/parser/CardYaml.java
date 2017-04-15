package com.discovr.discord.data.parser;

import com.discovr.discord.model.Card;

import java.util.ArrayList;
import java.util.List;

public class CardYaml {
    private Integer id;
    private Integer quantity;
    private String title;
    private String description;
    private String quote;
    private Integer timer;
    private List<String> dices;
    private List<String> tags;

    public CardYaml() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getQuote() {
        return quote;
    }

    public List<String> getDices() {
        return dices;
    }

    public Integer getTimer() {
        return timer;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public void setTimer(Integer timer) {
        this.timer = timer;
    }

    public void setDices(List<String> dices) {
        this.dices = dices;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public static List<Card> toCards(List<CardYaml> cardYamls) {
        List<Card> cards = new ArrayList<>();
        for (CardYaml cardYaml : cardYamls) {
            cards.add(CardYaml.toCard(cardYaml));
        }
        return cards;
    }

    private static Card toCard(CardYaml cardYaml) {
        return Card.builder()
                .id(cardYaml.id)
                .quantity(cardYaml.quantity)
                .title(cardYaml.title)
                .description(cardYaml.description)
                .quote(cardYaml.quote)
                .timer(cardYaml.timer)
                .dices(cardYaml.dices)
                .tags(cardYaml.tags)
                .build();
    }
}