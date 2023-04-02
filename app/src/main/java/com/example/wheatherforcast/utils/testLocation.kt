package com.example.wheatherforcast.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.wheatherforcast.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices


class testLocation : AppCompatActivity() {
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var gucoder:Geocoder
    private val My_LOCATION_PERMISSION_ID = 40
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_location)
        LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
    }


    fun getLastLocation(){
        if (checkPermission()){
            if (isLocationEnabled()){

                requestNewLocationData()
            }
            else{

                val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
        else{
            requestPermissions()
        }
    }
    fun checkPermission():Boolean{
        val result= ActivityCompat.checkSelfPermission(applicationContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )== PackageManager.PERMISSION_GRANTED||
                ActivityCompat.checkSelfPermission(applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )== PackageManager.PERMISSION_GRANTED
        return result
    }
    fun requestPermissions(){
        ActivityCompat.requestPermissions(this, arrayOf(  android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION ),
            My_LOCATION_PERMISSION_ID)
    }

    @SuppressLint("ServiceCast")
    fun isLocationEnabled():Boolean{
        val locationManger: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManger.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManger.
        isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    val mlocationCallBack: LocationCallback =object: LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            val mLastLocation: Location? =p0.lastLocation
            gucoder= Geocoder(applicationContext)
            val adress=gucoder.getFromLocation(mLastLocation?.latitude!!,mLastLocation?.latitude!!,1)
            if(adress?.size !=0){
                if (adress != null) {
                    Log.i("Name", adress.get(0).countryName)
                   // currentLocationStatue.success(adress)
                }
            }
            else{
                Log.i("TAGGG", "adress")
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData(){
        val locationRequest= com.google.android.gms.location.LocationRequest()
        locationRequest.priority=com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval=0
        fusedLocationClient= LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.requestLocationUpdates(locationRequest,mlocationCallBack,
            Looper.getMainLooper())
    }
}