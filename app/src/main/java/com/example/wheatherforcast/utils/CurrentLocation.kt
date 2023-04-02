package com.example.wheatherforcast.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.*


const val My_LOCATION_PERMISSION_ID = 19

class CurrentLocation(
    val context: Context,
    val activity: Activity,
    //val currentLocationStatue: CurrentLocationStatue,
    val language: String
) {
    var fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    lateinit var gucoder: Geocoder
    lateinit var sharedPreference: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                requestNewLocationData()
            } else {

                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                activity.startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    fun checkPermission(): Boolean {
        val result = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
        return result
    }

    fun requestPermissions() {
        ActivityCompat.requestPermissions(
            activity, arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            My_LOCATION_PERMISSION_ID
        )
    }

    @SuppressLint("ServiceCast")
    fun isLocationEnabled(): Boolean {
        val locationManger: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManger.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManger.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    val mlocationCallBack: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            Log.i("TAG", "adress")
            val mLastLocation: Location? = p0.lastLocation
            gucoder = Geocoder(context)
            val adress =
                gucoder.getFromLocation(mLastLocation?.latitude!!, mLastLocation?.latitude!!, 1)
            if (adress?.size != 0) {
                if (adress != null) {
                    Log.i("Name", adress.get(0).latitude.toString())
                    sharedPreference =
                        context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                    editor = sharedPreference.edit()
                    editor.putString("countryName", adress.get(0).adminArea)
                    editor.putString("latitude", adress.get(0).latitude.toString())
                    editor.putString("longitude", adress.get(0).longitude.toString())
                    editor.commit()
                }
            } else {
                Log.i("TAGGG", "adress")
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val locationRequest = com.google.android.gms.location.LocationRequest()
        locationRequest.priority =
            com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.setNumUpdates(1)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationClient.requestLocationUpdates(
            locationRequest, mlocationCallBack,
            Looper.getMainLooper()
        )
    }
}