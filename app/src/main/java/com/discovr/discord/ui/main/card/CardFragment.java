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
import com.discovr.discord.data.manager.card.CardManager;
import com.discovr.discord.model.Card;
import com.discovr.discord.ui.main.MainContract;
import com.discovr.discord.ui.main.MainEvent;
import com.discovr.discord.ui.main.MainModel;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

// TODO create BaseFragment to inherit common methods
// This class has to be Java because swipePlaceHolder is not kotlin compatible
public class CardFragment extends Fragment implements MainContract.CardFragment {
    @Inject
    Subject<MainEvent> events;
    @Inject
    CardManager cardManager;
    @Inject
    MainContract.CardFragmentPresenter presenter;
    private Unbinder unbinder;

    @BindView(R.id.swipePlaceHolder)
    SwipePlaceHolderView swipePlaceHolder;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
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
        events.onNext(new MainEvent.FragmentStart());
    }

    @Override
    public void render(@NotNull MainModel model) {
        if (model instanceof MainModel.FragmentStart) {
            reloadCards(((MainModel.FragmentStart) model).getCards());
            return;
        }

        if (model instanceof MainModel.Error) {
            renderError((MainModel.Error) model);
            return;
        }

        throw new IllegalArgumentException("Don't know how to render model " + model);
    }


    private int currentCount = 0;
    private int cardsSize = 0;
    /*

    private fun swipe() {
        currentCount++
        if (cardsSize - currentCount < 3) {
            reloadCards()
        }
    }

    private fun reloadCards(List<Card> cards) {
        swipePlaceHolder.removeAllViews()
        currentCount = 0
        getCards()
    }

    private fun getCards() {
        cardManager.getCards()
                .doOnSubscribe(compositeDisposable::add)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addCardsToView, Timber::e)
    }

    private fun addCardsToView(List<Card> cards) {
        cardsSize = cards.size()
        for (Card card : cards) {
            swipePlaceHolder.addView(new CardSwipeView(card, events))
        }
    }
     */

    private void reloadCards(List<Card> cards) {
        swipePlaceHolder.removeAllViews();
        currentCount = 0;
        addCardsToView(cards);
    }

    private void addCardsToView(List<Card> cards) {
        cardsSize = cards.size();
        for (Card card : cards) {
            swipePlaceHolder.addView(new CardSwipeView(card, events));
        }
    }

    private void renderError(MainModel.Error model) {
        Timber.e(model.getError().getMessage());
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}