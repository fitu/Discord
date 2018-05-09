package com.discovr.discord.ui.main.card

import com.discovr.discord.data.manager.card.CardManager
import com.discovr.discord.data.manager.card.CardResult
import com.discovr.discord.data.manager.setting.SettingManager
import com.discovr.discord.data.manager.setting.SettingResult
import com.discovr.discord.ui.main.MainContract
import com.discovr.discord.ui.main.MainEvent
import com.discovr.discord.ui.main.MainModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

// TODO create BasePresenter to inherit common methods
class CardPresenter
@Inject constructor(private val view: MainContract.CardFragment,
                    events: Observable<MainEvent>,
                    private val settingManager: SettingManager,
                    private val cardManager: CardManager) : MainContract.CardFragmentPresenter {

    companion object {
        // TODO check what's happen when obfuscate it
        val TAG: String = CardPresenter::class.java.simpleName
    }

    private val compositeDisposable: CompositeDisposable?

    init {
        compositeDisposable = CompositeDisposable()

        events.doOnSubscribe { compositeDisposable.add(it) }
                .observeOn(Schedulers.io())
                .flatMap<MainModel> { this.handleEvents(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.render(it) }

        settingManager.resultsObs.doOnSubscribe { compositeDisposable.add(it) }
                .observeOn(Schedulers.io())
                .filter { it.id != TAG }
                .filter { false }
                .flatMap<MainModel> { this.handlePrefResults(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.render(it) }

        cardManager.resultsObs.doOnSubscribe { compositeDisposable.add(it) }
                .observeOn(Schedulers.io())
                .filter { it.id != TAG }
                .filter { false }
                .flatMap<MainModel> { this.handleCardResults(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.render(it) }
    }

    private fun handleEvents(event: MainEvent): Observable<MainModel> {
        return startEvent(event as MainEvent.FragmentStart)
    }

    private fun startEvent(event: MainEvent.FragmentStart) : Observable<MainModel> {
        return Observable.empty()
    }

    private fun handlePrefResults(result: SettingResult): Observable<MainModel> {
        return Observable.empty()
    }

    private fun handleCardResults(result: CardResult): Observable<MainModel> {
        return Observable.empty()
    }

    override fun clear() {
        compositeDisposable!!.clear()
    }

    override fun dispose() {
        compositeDisposable!!.dispose()
    }
}