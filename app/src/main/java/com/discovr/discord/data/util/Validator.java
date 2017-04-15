package com.discovr.discord.data.util;

import com.discovr.discord.R;
import com.discovr.discord.model.Card;

import java.util.List;

public class Validator {
    private static int errorMessage;

    public static boolean isValid(Card card) {
        return isDescriptionValid(card.getDescription()) && isTagValid(card.getTags()) &&
                isQuantityValid(card.getQuantity());
    }

    public static boolean isDescriptionValid(String description) {
        if (description != null && !description.isEmpty()) {
            return true;
        } else {
            errorMessage = R.string.error_message_card_description;
            return false;
        }
    }

    public static boolean isTagValid(List<String> tags) {
        if (tags != null && !tags.isEmpty()) {
            return true;
        } else {
            errorMessage = R.string.error_message_card_tag;
            return false;
        }
    }

    public static boolean isQuantityValid(int quantity) {
        if ((quantity > 0)) {
            return true;
        } else {
            errorMessage = R.string.error_message_card_quantity;
            return false;
        }
    }

    public static boolean isValidTitle(String title) {
        return title != null && !title.isEmpty();
    }

    public static boolean isValidQuote(String quote) {
        return quote != null && !quote.isEmpty();
    }

    public static boolean isValidTimer(int timer) {
        return (timer > 0);
    }

    public static boolean isValidDice(List<String> dices) {
        return dices != null && !dices.isEmpty();
    }

    public static int getErrorMessage() {
        return errorMessage;
    }
}
