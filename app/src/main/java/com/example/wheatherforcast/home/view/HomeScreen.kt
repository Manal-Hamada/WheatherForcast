package com.example.wheatherforcast.home.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.wheatherforcast.R
import com.example.wheatherforcast.databinding.ActivityHomeScreenBinding
import com.example.wheatherforcast.utils.CurrentLocation
import com.google.android.gms.location.LocationServices


class HomeScreen : AppCompatActivity() {

    lateinit var binding: ActivityHomeScreenBinding
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    lateinit var navController: NavController
    lateinit var sharedPrefrences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefrences =
            this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        editor = sharedPrefrences.edit()

      //  LanguageConverter.checkLanguage(sharedPrefrences.getString("language", ""), this)
        sharedPrefrences = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)


        LocationServices.getFusedLocationProviderClient(this)
        // getLastLocation()
        CurrentLocation(applicationContext, this, "en").getLastLocation()

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.navView, navController)
        binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

        //  displayMenu()
        // onItemSelected()
    }

    fun displayMenu() {
        actionBarToggle = ActionBarDrawerToggle(this, binding.drawer, 0, 0)
        binding.drawer.addDrawerListener(actionBarToggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarToggle.syncState()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (!binding.drawer.isDrawerOpen(GravityCompat.START)) {
                binding.drawer.openDrawer(GravityCompat.START)
            } else {
                binding.drawer.closeDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}