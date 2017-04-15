package com.discovr.discord.ui.main.discord;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.discovr.discord.R;
import com.discovr.discord.model.Card;
import com.discovr.discord.ui.main.MainEvent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.subjects.Subject;

public class DiscordDialog extends DialogFragment {
    public static final String TAG = "DiscordDialog";
    public static final String CARD_KEY = "CARD_KEY";

    // TODO subscribe and composite disposable
    @Inject Subject<MainEvent> subject;
    private Card card;
    private Unbinder unbinder;

    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_description) TextView tvDescription;
    @BindView(R.id.tv_quote) TextView tvQuote;

    @OnClick(R.id.ll_discord)
    public void onDiscordCardClicked(View view) {
        // TODO subject onNext
    }

    public static DiscordDialog getDialog(Card discordCard) {
        DiscordDialog dialog = new DiscordDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DiscordDialog.CARD_KEY, discordCard);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_discord, container, false);
        unbinder = ButterKnife.bind(this, view);
        setCard();
        return view;
    }

    private void setCard() {
        if (getArguments() == null) {
            return;
        }

        Card card = getArguments().getParcelable(CARD_KEY);
        if (card != null) {
            setCardData(card.getTitle(), card.getDescription(), card.getQuote());
        }
    }

    private void setCardData(String title, String description, String quote) {
        tvTitle.setText(title);
        tvDescription.setText(description);
        tvQuote.setText(quote);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
