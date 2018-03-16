package com.discovr.discord.ui.main

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.DragAndDropPermissions
import android.view.Menu
import android.view.MenuItem
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.discovr.discord.R
import com.discovr.discord.model.Tag
import com.discovr.discord.ui.main.card.CardFragment
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.general_toolbar.*
import org.jetbrains.anko.itemsSequence
import javax.inject.Inject

// TODO create BaseActivity to inherit common methods
class MainActivity : AppCompatActivity(),
        MainContract.Activity, NavigationView.OnNavigationItemSelectedListener, SensorEventListener,
        HasSupportFragmentInjector {

    var presenter: MainContract.ActivityPresenter? = null @Inject set
    var cardFragment: CardFragment? = null @Inject set
    var events: Subject<MainEvent>? = null @Inject set

    var sensorManager: SensorManager? = null @Inject set
    var accelerometer: Sensor? = null @Inject set
    private var drawerToggle: ActionBarDrawerToggle? = null
    var injector: DispatchingAndroidInjector<Fragment>? = null @Inject set

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
        setUpBar()
        setUpDrawer()
        addFragment(cardFragment, R.id.frgContainer)
    }

    private fun setUpBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setUpDrawer() {
        // TODO bind string
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close)
        drawerLayout!!.addDrawerListener(drawerToggle!!)
        navigationView!!.setNavigationItemSelectedListener(this)
    }

    override fun onStart() {
        super.onStart()
        presenter!!.subscribe(events as Observable<MainEvent>)
        sensorManager!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun render(model: MainModel) {
        if (model is MainModel.Menu) {
            model.menuItem.icon = model.newIcon
            return
        }

        if (model is MainModel.DrinkClick) {
            model.menuItem.icon = model.newIcon
            return
        }

        if (model is MainModel.HardcoreClick) {
            model.menuItem.icon = model.newIcon
            return
        }

        if (model is MainModel.Error) {
            renderError(model)
            return
        }

        throw IllegalArgumentException("Don't know how to render model $model")
    }

    private fun renderError(model: MainModel.Error) {
        Log.e(TAG, model.error.message)
        finish()
    }

    override fun onStop() {
        super.onStop()
        presenter!!.clear()
        sensorManager!!.unregisterListener(this)
    }

    public override fun onDestroy() {
        super.onDestroy()
        presenter!!.dispose()
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
        for (item in menu.itemsSequence()) {
            when (item.itemId) {
                R.id.actionDrink -> events!!.onNext(MainEvent.MenuEvent(
                        item,
                        presenter!!.getValue(Tag.DRINK),
                        android.R.color.holo_green_light))
                R.id.actionHardcore -> events!!.onNext(MainEvent.MenuEvent(
                        item,
                        presenter!!.getValue(Tag.HARDCORE),
                        android.R.color.holo_red_light))
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionDrink -> events!!.onNext(MainEvent.DrinkClick(
                    item,
                    presenter!!.getValue(Tag.DRINK),
                    android.R.color.holo_green_light,
                    Tag.DRINK))
            R.id.actionHardcore -> events!!.onNext(MainEvent.HardcoreClick(
                    item, presenter!!.getValue(Tag.HARDCORE),
                    android.R.color.holo_red_light,
                    Tag.HARDCORE))
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