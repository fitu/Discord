package com.discovr.discord.ui.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.SensorEvent;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Gravity;
import android.view.Menu;

import com.discovr.discord.R;
import com.discovr.discord.data.manager.SettingManager;
import com.discovr.discord.model.Card;
import com.discovr.discord.model.Tag;
import com.discovr.discord.ui.main.dice.DiceManager;
import com.discovr.discord.ui.main.discord.DiscordDialog;
import com.discovr.discord.ui.main.timer.TimerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import static com.discovr.discord.model.Card.RULES_CARD_ID;

public class MainPresenter implements MainContract.ActivityPresenter {
    private static final int ALPHA_TRANSLUCENT = 64;
    private static final int ALPHA_OPAQUE = 255;
    private static final int MARGIN_MINIMUM = 16;
    private static final int MARGIN_DEFAULT = 32;
    private static final int MARGIN_MAXIMUM = 250;
    private static final int MENU_KEY_DRINK = 0;
    private static final int MENU_KEY_HARDCORE = 1;

    private final MainContract.Activity activity;
    private final DiceManager diceManager;
    private final TimerManager timerManager;
    private final SettingManager settingManager;
    private final Random random;

    private List<Card> allCards;
    private List<Card> extraCards;
    private List<Card> activeCards;
    private List<Card> discordCards;
    private List<String> allowedTypes;
    private Card currentDiscordCard;

    @Inject
    public MainPresenter(MainContract.Activity activity, DiceManager diceManager,
                         TimerManager timerManager, SettingManager settingManager) {
        this.activity = activity;
        this.diceManager = diceManager;
        this.timerManager = timerManager;
        this.settingManager = settingManager;

        this.diceManager.setPresenter(this);
        this.timerManager.setPresenter(this);

        this.random = new Random(System.nanoTime());
        this.allCards = new ArrayList<>();
        this.activeCards = new ArrayList<>();
        this.extraCards = new ArrayList<>();
        this.discordCards = new ArrayList<>();
        this.allowedTypes = new ArrayList<>();
    }

    @Override
    public void changeMenuIcon(Menu menu) {
        setIconColorAndAlpha(menu.getItem(MENU_KEY_DRINK).getIcon(),
                settingManager.getBooleanValue(Tag.DRINK) ? ALPHA_OPAQUE : ALPHA_TRANSLUCENT,
                settingManager.getBooleanValue(Tag.DRINK) ? R.color.yellow : android.R.color.black);

        setIconColorAndAlpha(menu.getItem(MENU_KEY_HARDCORE).getIcon(),
                settingManager.getBooleanValue(Tag.HARDCORE) ? ALPHA_OPAQUE : ALPHA_TRANSLUCENT,
                settingManager.getBooleanValue(Tag.HARDCORE) ? android.R.color.holo_red_dark : android.R.color.black);
    }

    @Override
    public void actionSelected(String key, Drawable icon) {
        settingManager.saveBooleanValue(key, !settingManager.getBooleanValue(key));
        changeIconLook(key, icon);
        showMessage(key);
        reloadCards();
    }

    private void changeIconLook(String key, Drawable icon) {
        int color = android.R.color.black;
        int alpha = ALPHA_TRANSLUCENT;

        if (!settingManager.getBooleanValue(key)) {
            setIconColorAndAlpha(icon, alpha, color);
            return;
        }

        switch (key) {
            case Tag.DRINK:
                color = R.color.yellow;
                alpha = ALPHA_OPAQUE;
                break;
            case Tag.HARDCORE:
                color = android.R.color.holo_red_dark;
                alpha = ALPHA_OPAQUE;
                break;
        }

        setIconColorAndAlpha(icon, alpha, color);
    }

    private void setIconColorAndAlpha(Drawable icon, int alpha, int colorAccent) {
        icon.setAlpha(alpha);
        icon = DrawableCompat.wrap(icon);
        activity.setIconColorAndAlpha(icon, colorAccent);
    }

    private void showMessage(String key) {
        switch (key) {
            case Tag.DRINK:
                activity.showMessage(settingManager.getBooleanValue(key) ?
                        R.string.menu_action_drink_on :
                        R.string.menu_action_drink_off);
                break;
            case Tag.HARDCORE:
                activity.showMessage(settingManager.getBooleanValue(key) ?
                        R.string.menu_action_hardcore_on :
                        R.string.menu_action_hardcore_off);
                break;
        }
    }

    private void reloadCards() {
        activeCards.clear();
        loadCards();
        //     presenterFragmentCard.actionChanged();
    }

    @Override
    public void loadCards() {
        getCards();
        filterCards();
        Collections.shuffle(activeCards, random);
        if (settingManager.isFirstTime()) {
            addRulesCard();
        }
    }

    private void getCards() {
        allCards.clear();
 //       allCards = CardManager.parseFromYaml(getCardsFile(DEFAULT_CARDS_FILENAME));
        extraCards.clear();
        extraCards = activity.getExtraCards();
        allCards.addAll(extraCards);
        allCards.addAll(addRepeatedCards());
        setIds();
    }

    private List<Card> addRepeatedCards() {
        List<Card> repeatedCards = new ArrayList<>();
        for (Card card : allCards) {
            if (card.getQuantity() > 1) {
                for (int i = 1; i < card.getQuantity(); i++) {
                    repeatedCards.add(Card.copyCard(card));
                }
            }
        }
        return repeatedCards;
    }

    private void setIds() {
        for (int i = 0; i < allCards.size(); i++) {
     //       allCards.get(i).setId(i + 1);
        }
    }

    private void filterCards() {
        getAllowedTypes();
        activeCards.clear();
        discordCards.clear();

        for (Card card : allCards) {
            if (card.getTags() != null && allowedTypes.containsAll(card.getTags())) {
                if (isDiscord(card)) {
                    discordCards.add(card);
                } else {
                    activeCards.add(card);
                }
            }
        }
    }

    private boolean isDiscord(Card card) {
        return card.getTags().contains(Tag.DISCORD);
    }

    private List<String> getAllowedTypes() {
        allowedTypes.clear();
        allowedTypes.add(Tag.NORMAL);
        allowedTypes.add(Tag.DISCORD);

        if (settingManager.getBooleanValue(Tag.DRINK)) {
            allowedTypes.add(Tag.DRINK);
        }
        if (settingManager.getBooleanValue(Tag.HARDCORE)) {
            allowedTypes.add(Tag.HARDCORE);
        }
        return allowedTypes;
    }

    private void addRulesCard() {
        activeCards.add(RULES_CARD_ID, Card.builder()
                .id(RULES_CARD_ID)
                .title(activity.getStringById(R.string.rules_card_rules_title))
                .description(activity.getStringById(R.string.rules_card_rules_description))
                .build());
    }

    @Override
    public void removeRulesCard() {
        activeCards.remove(RULES_CARD_ID);
    }

    @Override
    public void notifyTopChanged() {
        diceManager.removeDices();
        timerManager.removeTimer();

        Card topCard = getTopCard();
        showDicesAndTimer(topCard);

        if (hasTimer(topCard)) {
            timerManager.startTimer(topCard.getTimer());
        }
    }

    private void showDicesAndTimer(Card topCard) {
        if (hasDices(topCard) && hasTimer(topCard)) {
            activity.setDicePosition(
                    Gravity.BOTTOM | Gravity.START,
                    MARGIN_MAXIMUM, // Left
                    MARGIN_MINIMUM, // Top
                    MARGIN_MINIMUM, // Right
                    MARGIN_DEFAULT); // Bottom
            diceManager.showDices(topCard.getDices());

            activity.setTimerPosition(
                    Gravity.BOTTOM | Gravity.END,
                    MARGIN_MINIMUM, // Left
                    MARGIN_MINIMUM, // Top
                    MARGIN_MAXIMUM, // Right
                    MARGIN_DEFAULT); // Bottom
            timerManager.showTimer(topCard.getTimer());
            return;
        }

        if (hasDices(topCard)) {
            activity.setDicePosition(
                    Gravity.BOTTOM | Gravity.CENTER,
                    MARGIN_MINIMUM, // Left
                    MARGIN_MINIMUM, // Top
                    MARGIN_MINIMUM, // Right
                    MARGIN_DEFAULT); // Bottom
            diceManager.showDices(topCard.getDices());
            return;
        }

        if (hasTimer(topCard)) {
            activity.setTimerPosition(
                    Gravity.BOTTOM | Gravity.CENTER,
                    MARGIN_MINIMUM, // Left
                    MARGIN_MINIMUM, // Top
                    MARGIN_MINIMUM, // Right
                    MARGIN_DEFAULT); // Bottom
            timerManager.showTimer(topCard.getTimer());
        }
    }

    private boolean hasDices(Card topCard) {
        return topCard.getDices() != null && !topCard.getDices().isEmpty();
    }

    private boolean hasTimer(Card topCard) {
        return topCard.getTimer() > 0;
    }

    private Card getTopCard() {
        int topId = activity.getTopPositionId();
        return (settingManager.isFirstTime() || topId <= -1) ? activeCards.get(0) : findCardById(topId);
    }

    private Card findCardById(int topId) {
        for (Card card : allCards) {
            if (card.getId() == topId) {
                return card;
            }
        }
        return null;
    }

    @Override
    public void shuffleDeck() {
        loadCards();
    }

    @Override
    public void shakeDice(SensorEvent sensorEvent) {
        diceManager.shakeDice(sensorEvent);
    }

    @Override
    public void showDiceVisibility(int visibility) {
        activity.showDiceVisibility(visibility);
    }

    @Override
    public void setDiceText(String face) {
        activity.setDiceText(face);
    }

    /**
     * Timer
     */
    @Override
    public void showTimerVisibility(int visibility) {
        activity.showTimerVisibility(visibility);
    }

    @Override
    public void setTimerText(String timer) {
        activity.setTimerText(timer);
    }

    /**
     * Dialog
     */
    @Override
    public Card getDiscordCard() {
        if (currentDiscordCard == null) {
            setDiscordCard();
        }

        return currentDiscordCard;
    }

    @Override
    public void setDiscordCard() {
        currentDiscordCard = discordCards.get(random.nextInt(discordCards.size()));
        activity.showDialog(DiscordDialog.getDialog(currentDiscordCard), DiscordDialog.TAG);
    }

    @Override
    public List<Card> getActiveCards() {
        return activeCards;
    }

    @Override
    public Context getActivity() {
        return activity.getActivity();
    }
}
