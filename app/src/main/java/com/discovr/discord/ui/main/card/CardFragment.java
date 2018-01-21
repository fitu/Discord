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
import com.discovr.discord.ui.main.MainAction;
import com.discovr.discord.ui.main.MainContract;

import javax.inject.Inject;

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
            swipe(((MainAction.SwipeLeft) action).getPosition());
        }

        if (action instanceof MainAction.SwipeRight) {
            swipe(((MainAction.SwipeRight) action).getPosition());
        }
    }

    private void swipe(int position) {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_deck_card, container, false);
        unbinder = ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}