package com.discovr.discord.ui.main.card

import com.discovr.discord.ui.main.MainContract
import com.discovr.discord.ui.main.MainEvent
import com.discovr.discord.ui.main.MainModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

// TODO create BasePresenter to inherit common methods
class CardPresenter
@Inject constructor(private val view: MainContract.CardFragment) : MainContract.CardFragmentPresenter {

    private val compositeDisposable = CompositeDisposable()

    /*
    events.doOnSubscribe(compositeDisposable::add)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::events);


     private void events(MainEvent event) {
        if (event instanceof MainEvent.SwipeLeft) {
            swipe();
            return;
        }

        if (event instanceof MainEvent.SwipeRight) {
            swipe();
            return;
        }

        if (event instanceof MainEvent.DrinkClick) {
            reloadCards();
            return;
        }

        if (event instanceof MainEvent.HardcoreClick) {
            reloadCards();
            return;
        }

        throw new IllegalArgumentException("Don't know how to render model" + model);
    }

     private void swipe() {
        currentCount++;
        if (cardsSize - currentCount < 3) {
            reloadCards();
        }
    }

   private void reloadCards(List<Card> cards) {
        swipePlaceHolder.removeAllViews();
        currentCount = 0;
        getCards();
    }

    private void getCards() {
        cardManager.getCards()
                .doOnSubscribe(compositeDisposable::add)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addCardsToView, Timber::e);
    }

    private void addCardsToView(List<Card> cards) {
        cardsSize = cards.size();
        for (Card card : cards) {
            swipePlaceHolder.addView(new CardSwipeView(card, events));
        }
    }

     */


    override fun subscribe(events: Observable<MainEvent>) {
        events.doOnSubscribe({ compositeDisposable.add(it) })
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter({ it is MainEvent.FragmentStart || it is MainEvent.DrinkClick ||
                        it is MainEvent.HardcoreClick })
                .flatMap<MainModel>({ this.handleEvent(it) })
                .subscribe({ view.render(it) })
    }

    private fun handleEvent(event: MainEvent): Observable<MainModel> {
        if (event is MainEvent.FragmentStart) {
            return startEvent(event)
        }

        if (event is MainEvent.DrinkClick) {
            return drinkEvent(event)
        }

        return hardcoreEvent(event as MainEvent.HardcoreClick)
    }

    private fun startEvent(event: MainEvent.FragmentStart) : Observable<MainModel> {
        return Observable.empty()
    }

    private fun drinkEvent(event: MainEvent.DrinkClick): Observable<MainModel> {
        return Observable.empty()
    }

    private fun hardcoreEvent(event: MainEvent.HardcoreClick): Observable<MainModel> {
        return Observable.empty()
    }

    override fun clear() {
        compositeDisposable.clear()
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }
}