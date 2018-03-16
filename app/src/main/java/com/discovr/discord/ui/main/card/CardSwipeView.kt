package com.discovr.discord.ui.main.card

import android.util.Log
import android.widget.TextView
import com.discovr.discord.R
import com.discovr.discord.model.Card
import com.discovr.discord.ui.main.MainEvent
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.NonReusable
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.swipe.SwipeIn
import com.mindorks.placeholderview.annotations.swipe.SwipeOut
import io.reactivex.Observer

@Layout(R.layout.card)
@NonReusable
class CardSwipeView(private val card: Card, private val events: Observer<MainEvent>) {

    companion object {
        private const val TAG = "CardSwipeView"
    }

    @View(R.id.tvTitle) private val tvTitle: TextView? = null
    @View(R.id.tvDescription) private val tvDescription: TextView? = null
    @View(R.id.tvQuote) private val tvQuote: TextView? = null

    @Resolve
    private fun onResolved() {
        tvTitle!!.text = card.title
        tvDescription!!.text = card.description
        tvQuote!!.text = card.quote
    }

    @SwipeOut
    private fun swipeLeft() {
        Log.d(TAG, "Swipe left")
        events.onNext(MainEvent.SwipeLeft())
    }

    @SwipeIn
    private fun swipeRight() {
        Log.d(TAG, "Swipe right")
        events.onNext(MainEvent.SwipeRight())
    }
}