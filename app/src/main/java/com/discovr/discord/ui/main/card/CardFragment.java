package com.discovr.discord.ui.main.card;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.discovr.discord.R;
import com.discovr.discord.data.manager.card.CardManager;
import com.discovr.discord.model.Card;
import com.discovr.discord.ui.main.MainContract;
import com.discovr.discord.ui.main.MainEvent;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

// This class has to be Java because swipePlaceHolder is not compatible
public class CardFragment extends Fragment implements MainContract.CardFragment {
    private static final String TAG = "CardFragment";

    @Inject Subject<MainEvent> events;
    @Inject CardManager cardManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int currentCount = 0;
    private int cardsSize = 0;
    private Unbinder unbinder;

    @BindView(R.id.swipePlaceHolder) SwipePlaceHolderView swipePlaceHolder;

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
        events.doOnSubscribe(compositeDisposable::add)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::events);
    }

    private void events(MainEvent event) {
        if (event instanceof MainEvent.SwipeLeft) {
            swipe();
        }

        if (event instanceof MainEvent.SwipeRight) {
            swipe();
        }

        if (event instanceof MainEvent.DrinkClick) {
            reloadCards();
        }

        if (event instanceof MainEvent.HardcoreClick) {
            reloadCards();
        }
    }

    private void swipe() {
        currentCount++;
        if (cardsSize - currentCount < 3) {
            reloadCards();
        }
    }

    private void reloadCards() {
        swipePlaceHolder.removeAllViews();
        getCards();
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
        swipePlaceHolder.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f));
    }

    @Override
    public void onStart() {
        super.onStart();
        getCards();
    }

    private void getCards() {
        cardManager.getCards()
                .doOnSubscribe(compositeDisposable::add)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addCardsToView, this::error);
    }

    private void addCardsToView(List<Card> cards) {
        cardsSize = cards.size();
        for (Card card : cards) {
            swipePlaceHolder.addView(new CardSwipeView(card, events));
        }
    }

    private void error(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
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