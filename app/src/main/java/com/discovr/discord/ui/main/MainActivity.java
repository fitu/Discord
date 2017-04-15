package com.discovr.discord.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.discovr.discord.R;
import com.discovr.discord.data.manager.CardManager;
import com.discovr.discord.model.Card;
import com.discovr.discord.model.Tag;
import com.discovr.discord.ui.main.card.CardFragment;
import com.discovr.discord.ui.main.dialogs.RulesDialog;
import com.discovr.discord.ui.main.discord.DiscordDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.Subject;

public class MainActivity extends AppCompatActivity implements
        MainContract.Activity, NavigationView.OnNavigationItemSelectedListener,
        SensorEventListener, HasSupportFragmentInjector {
    private static final String TAG = "MainActivity";

    @Inject DispatchingAndroidInjector<Fragment> injector;
    @Inject MainContract.ActivityPresenter presenter;
    @Inject CardFragment cardFragment;
    @Inject Subject<MainEvent> subject;
    @Inject SensorManager sensorManager;
    @Inject Sensor accelerometer;
    @Inject SharedPreferences sharedPreferences;
    private ActionBarDrawerToggle drawerToggle;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.cl_activity) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.tv_dice) TextView tvDice;
    @BindView(R.id.tv_timer) TextView tvTimer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view) NavigationView navigationView;

    @OnClick({R.id.tv_dice, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dice:
                subject.onNext(new MainEvent.RollDice());
                break;
            case R.id.fab:
                showDialog(DiscordDialog.getDialog(presenter.getDiscordCard()), DiscordDialog.TAG);
                break;
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);
        ButterKnife.bind(this);
        seUpBarAndDrawer();
        subscribe();
        addFragment(cardFragment, R.id.frg_container);
    }

    private void seUpBarAndDrawer() {
        setSupportActionBar(toolbar);
        drawerToggle =new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        navigationView.setNavigationItemSelectedListener(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void subscribe() {
        subject.doOnSubscribe(compositeDisposable::add)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::actions);
    }

    private void actions(MainEvent event) {
        if (event instanceof MainEvent.SwipeLeft) {
            presenter.notifyTopChanged();
        }

        if (event instanceof MainEvent.SwipeRight) {
            presenter.notifyTopChanged();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.menu_my_cards:

                break;
            case R.id.menu_rules:
                // TODO inject this?
                showDialog(new RulesDialog(), RulesDialog.TAG);
                break;
            case R.id.menu_settings:
                // TODO complete this
                break;
        }

        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        // TODO don't send a menu item
        presenter.changeMenuIcon(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_drink:
                presenter.actionSelected(Tag.DRINK, item.getIcon());
                break;
            case R.id.action_hardcore:
                presenter.actionSelected(Tag.HARDCORE, item.getIcon());
                break;
        }
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void addFragment(Fragment fragment, int id) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(id, fragment)
                .commit();
    }

    @Override
    public void showDialog(DialogFragment dialog, String tag) {
        dialog.show(getSupportFragmentManager(), tag);
    }

    @Override
    public void showMessage(int errorMessageId) {
        Snackbar.make(coordinatorLayout, errorMessageId, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setDiceText(String face) {
        tvDice.setText(face);
    }

    @Override
    public void showDiceVisibility(int visibility) {
        tvDice.setVisibility(visibility);
    }

    @Override
    public void setDicePosition(int position, int left, int top, int right, int bottom) {
        CoordinatorLayout.LayoutParams params =
                new CoordinatorLayout.LayoutParams(tvDice.getLayoutParams());
        params.gravity = position;
        params.setMargins(left, top, right, bottom);
        tvDice.setLayoutParams(params);
    }

    @Override
    public void setIconColorAndAlpha(Drawable icon, int colorAccent) {
        DrawableCompat.setTint(icon, ContextCompat.getColor(this, colorAccent));
    }

    @Override
    public List<Card> getExtraCards() {
        return CardManager.getExtraCards(this);
    }

    @Override
    public String getStringById(int id) {
        return getResources().getString(id);
    }

    @Override
    public void setTimerPosition(int position, int left, int top, int right, int bottom) {
        CoordinatorLayout.LayoutParams params =
                new CoordinatorLayout.LayoutParams(tvTimer.getLayoutParams());
        params.gravity = position;
        params.setMargins(left, top, right, bottom);
        tvTimer.setLayoutParams(params);
    }

    @Override
    public void showTimerVisibility(int visibility) {
        tvTimer.setVisibility(visibility);
    }

    @Override
    public void setTimerText(String timer) {
        tvTimer.setText(timer);
    }

    @Override
    public List<Card> getActiveCards() {
        return presenter.getActiveCards();
    }

    @Override
    public void shuffleDeck() {
        presenter.shuffleDeck();
    }

    @Override
    public void removeRulesCard() {
        presenter.removeRulesCard();
    }

    @Override
    public int getTopPositionId() {
        return 1;
    }

    @Override
    public Context getActivity() {
        return this;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        presenter.shakeDice(sensorEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return injector;
    }
}