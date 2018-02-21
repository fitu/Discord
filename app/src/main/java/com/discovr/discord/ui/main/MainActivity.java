package com.discovr.discord.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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
import com.discovr.discord.ui.main.card.CardFragment;

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
    @Inject Subject<MainAction> actions;
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
                // TODO dice not here
                break;
            case R.id.fab:

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
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        seUpBarAndDrawer();
        subscribe();
        addFragment(cardFragment, R.id.frg_container);
    }

    private void seUpBarAndDrawer() {
        setSupportActionBar(toolbar);
        // TODO bind string
        drawerToggle =new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        navigationView.setNavigationItemSelectedListener(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void subscribe() {
        actions.doOnSubscribe(compositeDisposable::add)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::actions);
    }

    private void actions(MainAction action) {
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
                // TODO complete this
                break;
            case R.id.menu_rules:
                // TODO complete this
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_drink:
                break;
            case R.id.action_hardcore:
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
    public void onSensorChanged(SensorEvent sensorEvent) {
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return injector;
    }
}