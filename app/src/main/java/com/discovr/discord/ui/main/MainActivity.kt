package com.discovr.discord.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.discovr.discord.R
import com.discovr.discord.ui.main.card.CardFragment
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.general_toolbar.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
        MainContract.Activity, NavigationView.OnNavigationItemSelectedListener, SensorEventListener,
        HasSupportFragmentInjector {

    var injector: DispatchingAndroidInjector<Fragment>? = null @Inject set
    var presenter: MainContract.ActivityPresenter? = null @Inject set
    var cardFragment: CardFragment? = null @Inject set
    var actions: Subject<MainAction>? = null @Inject set
    var sensorManager: SensorManager? = null @Inject set
    var accelerometer: Sensor? = null @Inject set

    var sharedPreferences: SharedPreferences? = null

    private var drawerToggle: ActionBarDrawerToggle? = null
    private val compositeDisposable = CompositeDisposable()

    companion object {
        private const val TAG = "MainActivity"

        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    @OnClick(R.id.tvDice, R.id.fab)
    fun onClick(view: View) {
        when (view.id) {
            R.id.tvDice -> {
            } // TODO dice not here
            R.id.fab -> {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        seUpBarAndDrawer()
        subscribe()
        addFragment(cardFragment, R.id.frgContainer)
    }

    private fun seUpBarAndDrawer() {
        setSupportActionBar(toolbar)
        // TODO bind string
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close)
        drawerLayout!!.addDrawerListener(drawerToggle!!)
        navigationView!!.setNavigationItemSelectedListener(this)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }
    }

    private fun subscribe() {
        actions!!.doOnSubscribe({ compositeDisposable.add(it) })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ this.actions(it) })
    }

    private fun actions(action: MainAction) {
        when (action) {
            is MainAction.DrinkClick -> drinkClick(action.item)
            is MainAction.HardcoreClick -> hardcoreClick(action.item)
        }
    }

    private fun drinkClick(item: MenuItem) {
        item.setIcon(R.drawable.ic_menu_drink_tinted)
    }

    private fun hardcoreClick(item: MenuItem) {

    }

    override fun onStart() {
        super.onStart()
        sensorManager!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
        sensorManager!!.unregisterListener(this)
    }

    public override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Highlight the selected item has been done by NavigationView
        item.isChecked = true

        when (item.itemId) {
            R.id.menu_my_cards -> {
            }
            R.id.menu_rules -> {
            }
            R.id.menu_settings -> {
            }
        }

        drawerLayout!!.closeDrawers()
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle!!.onConfigurationChanged(newConfig)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionDrink -> {
                actions!!.onNext(MainAction.DrinkClick(item))
            }
            R.id.actionHardcore -> {
                actions!!.onNext(MainAction.HardcoreClick(item))
            }
        }
        return drawerToggle!!.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun addFragment(fragment: Fragment?, id: Int) {
        supportFragmentManager
                .beginTransaction()
                .replace(id, fragment)
                .commit()
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {}

    override fun onAccuracyChanged(sensor: Sensor, i: Int) {}

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment>? {
        return injector
    }
}