package com.discovr.discord.ui.main.card;

import android.util.Log;
import android.widget.TextView;

import com.discovr.discord.R;
import com.discovr.discord.model.Card;
import com.discovr.discord.ui.main.MainAction;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;

import io.reactivex.subjects.Subject;

@Layout(R.layout.card)
@NonReusable
public class CardSwipeView {
    private static final String TAG = "CardSwipeView";

    @View(R.id.tv_title) private TextView tvTitle;

    private final Card card;
    private final Subject<MainAction> actions;

    public CardSwipeView(Card card, Subject<MainAction> actions) {
        this.card = card;
        this.actions = actions;
    }

    @Resolve
    private void onResolved() {
        tvTitle.setText(card.getTitle());
    }

    @SwipeOut
    private void swipeLeft() {
        Log.d(TAG, "Swipe left");
        actions.onNext(new MainAction.SwipeLeft());
    }

    @SwipeIn
    private void swipeRight() {
        Log.d(TAG, "Swipe right");
        actions.onNext(new MainAction.SwipeRight());
    }
}
