package com.discovr.discord.ui.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.SensorEvent;
import android.support.v4.app.DialogFragment;
import android.view.Menu;

import com.discovr.discord.model.Card;

import java.io.InputStream;
import java.util.List;

public interface MainContract {
    interface Activity {
        void showMessage(int errorMessageId);

        void showDiceVisibility(int visibility);

        void setDiceText(String face);

        void setDicePosition(int position, int left, int top, int right, int bottom);

        void setTimerPosition(int position, int left, int top, int right, int bottom);

        void showTimerVisibility(int visibility);

        void setTimerText(String timer);

        void setIconColorAndAlpha(Drawable icon, int colorAccent);

        List<Card> getExtraCards();

        String getStringById(int id);

        List<Card> getActiveCards();

        void shuffleDeck();

        void removeRulesCard();

        int getTopPositionId();

        Context getActivity();

        void showDialog(DialogFragment dialog, String tag);
    }

    interface ActivityPresenter {
        void actionSelected(String key, Drawable icon);

        void changeMenuIcon(Menu menu);

        List<Card> getActiveCards();

        void notifyTopChanged();

        void shuffleDeck();

        void loadCards();

        void removeRulesCard();

        Card getDiscordCard();

        void setDiscordCard();

        void showDiceVisibility(int visibility);

        void setDiceText(String face);

        void showTimerVisibility(int visibility);

        void setTimerText(String timer);

        // TODO remove this
        Context getActivity();

        void shakeDice(SensorEvent sensorEvent);
    }

    interface CardFragment {
    }
}