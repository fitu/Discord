package com.discovr.discord.ui.main.card;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daprlabs.cardstack.SwipeDeck;
import com.discovr.discord.R;
import com.discovr.discord.model.Card;
import com.discovr.discord.ui.main.MainActivity;
import com.discovr.discord.ui.main.MainContract;
import com.discovr.discord.ui.main.MainEvent;
import com.discovr.discord.ui.main.adapter.SwipeDeckAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.Subject;

import static com.discovr.discord.model.Card.RULES_CARD_ID;

public class CardFragment extends Fragment implements MainContract.CardFragment, SwipeDeck.SwipeEventCallback,
        SwipeDeckAdapter.Callback {
    @Inject SwipeDeckAdapter swipeDeckAdapter;
    @Inject Subject<MainEvent> subject;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MainContract.Activity activity;
    private Unbinder unbinder;
    
    @BindView(R.id.swipe_deck) SwipeDeck swipeDeck;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        activity = (MainActivity) context;
        subscribe();
    }

    private void subscribe() {
        subject.doOnSubscribe(compositeDisposable::add)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::actions);
    }

    private void actions(MainEvent event) {
        if (event instanceof MainEvent.SwipeLeft) {
            swipe(((MainEvent.SwipeLeft) event).getPosition());
        }

        if (event instanceof MainEvent.SwipeRight) {
            swipe(((MainEvent.SwipeRight) event).getPosition());
        }
    }

    private void swipe(int position) {
        if (position == RULES_CARD_ID) {
            activity.removeRulesCard();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_deck_card, container, false);
        unbinder = ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        swipeDeck.setAdapter(swipeDeckAdapter);
        swipeDeckAdapter.setListener(this);
        subject.onNext(new MainEvent.LoadCards());
    }

    @Override
    public Card getNextCard(int i) {
        List<Card> activeCards = activity.getActiveCards();
        int pos = i % activeCards.size();
        if (pos == activeCards.size() - 1)
            activity.shuffleDeck();
        return activeCards.get(pos);
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

    @Override
    public void cardSwipedLeft(int position) {
        subject.onNext(new MainEvent.SwipeLeft(position));
    }

    @Override
    public void cardSwipedRight(int position) {
        subject.onNext(new MainEvent.SwipeRight(position));
    }

    @Override
    public void cardsDepleted() {

    }

    @Override
    public void cardActionDown() {

    }

    @Override
    public void cardActionUp() {

    }
}