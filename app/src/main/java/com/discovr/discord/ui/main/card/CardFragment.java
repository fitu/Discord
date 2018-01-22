package com.discovr.discord.ui.main.card;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.discovr.discord.R;
import com.discovr.discord.model.Card;
import com.discovr.discord.ui.main.MainAction;
import com.discovr.discord.ui.main.MainContract;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.Subject;

public class CardFragment extends Fragment implements MainContract.CardFragment {
    @Inject Subject<MainAction> actions;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Unbinder unbinder;

    @BindView(R.id.swipe_place_holder_view) SwipePlaceHolderView swipePlaceHolderView;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribe();
    }

    private void subscribe() {
        actions.doOnSubscribe(compositeDisposable::add)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::actions);
    }

    private void actions(MainAction action) {
        if (action instanceof MainAction.SwipeLeft) {
            addCard("Left");
        }

        if (action instanceof MainAction.SwipeRight) {
            addCard("Right");
        }
    }

    private void addCard(String direction) {
        swipePlaceHolderView.addView(new CardSwipeView(Card.builder().title(direction).build(), actions));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_deck_card, container, false);
        unbinder = ButterKnife.bind(this, layout);
        setUpSwipeView();
        return layout;
    }

    private void setUpSwipeView() {
        swipePlaceHolderView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f));
    }

    @Override
    public void onStart() {
        super.onStart();
        addCardsToView(getCards());
    }

    private List<Card> getCards() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            cards.add(Card.builder().title("" + i).build());
        }
        return cards;
    }

    private void addCardsToView(List<Card> cards) {
        for (Card card : cards) {
            swipePlaceHolderView.addView(new CardSwipeView(card, actions));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}