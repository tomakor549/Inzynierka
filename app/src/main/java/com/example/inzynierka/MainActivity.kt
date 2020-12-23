package com.example.inzynierka

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.navigateUp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

//start activity
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = fragments_toolbar as Toolbar
        setSupportActionBar(toolbar)//przycis dziedziczony przez view (own view in android)

        val drawerLayout: DrawerLayout = drawer_layout
        val navView: NavigationView = nav_view
        val navController = findNavController(R.id.nav_host_fragment)

        /*
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_article, R.id.nav_share_data, R.id.nav_trip_plans, R.id.nav_profile), drawerLayout)
        */
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_article, R.id.nav_trip_plans, R.id.nav_profile), drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        Log.d("m_act", "koniec onCreate w MainActivity")
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // dodanie menu
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id==R.id.action_emergency){
            Toast.makeText(this, "Wciśnięto przycisk na toolbarze. Hurra!", Toast.LENGTH_SHORT).show()
            return true
        }

        return false
    }
}