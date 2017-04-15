package com.discovr.discord.ui.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.discovr.discord.R;
import com.discovr.discord.model.Card;
import com.discovr.discord.model.Tag;
import com.discovr.discord.ui.main.MainContract;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwipeDeckAdapter extends BaseAdapter {
    private static final int NUMBER_OF_CARDS = Integer.MAX_VALUE;
    private final Context context;
    private Callback listener;

    @Inject
    public SwipeDeckAdapter(Context context) {
        this.context = context;
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() { return NUMBER_OF_CARDS; }

    @Override
    public Object getItem(int position) {
        return listener.getNextCard(position);
    }

    @Override
    public long getItemId(int position) {
        return listener.getNextCard(position).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SwipeDeckAdapter.ViewHolder holder;
        if (view != null) {
            holder = (SwipeDeckAdapter.ViewHolder) view.getTag(); // Get the saved view holder
        } else {
            // Create a new view holder and save it
            view = ((Activity) context).getLayoutInflater()
                    .inflate(R.layout.card, viewGroup, false);
            holder = new SwipeDeckAdapter.ViewHolder(view);
            view.setTag(holder);
        }

        Card card = listener.getNextCard(i);
        holder.bindData(card);

        return view;
    }

    static class ViewHolder {
        // Get the references to any field
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_description) TextView tvDescription;
        @BindView(R.id.tv_quote) TextView tvQuote;
        @BindView(R.id.iv_type) ImageView ivType;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void bindData(Card card) {
            // Set the fields to show
            tvTitle.setText(card.getTitle());
            tvDescription.setText(card.getDescription());
            tvQuote.setText(card.getQuote());

            if(card.getTags() != null)
                setImage(card);
        }

        private void setImage(Card card) {
            if(card.getTags().contains(Tag.DRINK))
                ivType.setImageResource(R.mipmap.ic_menu_drink);

            if(card.getTags().contains(Tag.HARDCORE))
                ivType.setImageResource(R.mipmap.ic_menu_hardcore);
        }
    }

    public interface Callback {
        Card getNextCard(int i);
    }
}