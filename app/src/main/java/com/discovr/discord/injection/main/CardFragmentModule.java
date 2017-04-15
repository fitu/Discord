package com.discovr.discord.injection.main;

import android.support.v4.app.Fragment;

import com.discovr.discord.injection.util.scope.FragmentScope;
import com.discovr.discord.ui.main.MainActivity;
import com.discovr.discord.ui.main.adapter.SwipeDeckAdapter;
import com.discovr.discord.ui.main.card.CardFragment;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module
public abstract class CardFragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(CardFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindFragmentCardInjectorFactory(
            CardFragmentComponent.Builder builder);

    @Provides
    @FragmentScope
    static SwipeDeckAdapter provideSwipeDeckAdapter(MainActivity activity) {
        return new SwipeDeckAdapter(activity);
    }
}