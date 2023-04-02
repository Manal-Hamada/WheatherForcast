package com.example.wheatherforcast.map

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.wheatherforcast.R
import com.example.wheatherforcast.databinding.FragmentMapBinding
import com.example.wheatherforcast.favourites.model.FavModel
import com.example.wheatherforcast.favourites.model.FavouriteRepository
import com.example.wheatherforcast.favourites.viewmodel.FavouriteViewModelfactory
import com.example.wheatherforcast.favourites.viewmodel.FavouriteViewModel
import com.example.wheatherforcast.utils.AppDialogs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import java.util.*


class MapFragment() : Fragment() {

    lateinit var binding: FragmentMapBinding
    lateinit var sharedPreference: SharedPreferences
    lateinit var factory: FavouriteViewModelfactory
    lateinit var viewModel: FavouriteViewModel
    lateinit var dialog:Dialog
    var city:String?="here"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference =
            requireContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val supportMapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(object : OnMapReadyCallback {
            @RequiresApi(Build.VERSION_CODES.S)
            override fun onMapReady(googleMap: GoogleMap) {
                googleMap.setOnMapClickListener {
                    val marker = MarkerOptions()
                    marker.position(it)
                   getCityName(it.latitude,it.longitude)?.let { it1 -> Log.i("map position", it1) }
                   // addFav(it)
                    googleMap.clear()
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 5f))
                    marker.title(city)
                    googleMap.addMarker(marker)
                    setDialog(it,view)

                }
            }

        })

    }

    fun addFav(it: LatLng) {
        factory = FavouriteViewModelfactory(
            FavouriteRepository.getInstance(), this.requireContext()
        )
        viewModel = ViewModelProvider(this, factory).get(FavouriteViewModel::class.java)
        lifecycleScope.launch {
            viewModel.insertnewFavourite(
                FavModel(
                    it.latitude.toString(),
                    it.longitude.toString(),
                    getCityName(it.latitude, it.longitude),
                    it.latitude.toString() + it.longitude.toString()
                ),requireContext()
            )
        }
    }


    fun setDialog(latlng:LatLng, view:View){
        dialog=AppDialogs.setDialog("Save this location to your favourite:",requireContext(),R.layout.dialog_layout)
        val cancleBtn = dialog.findViewById(R.id.fav_di_cancle) as Button
        val saveBtn = dialog.findViewById(R.id.fav_di_save) as Button
        saveBtn.setOnClickListener {
            addFav(latlng)
            dialog.dismiss()
            Navigation.findNavController(view).navigate(R.id.action_mapFragment_to_favouriteScreen)
        }
        cancleBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }
    private fun getCityName(lat: Double,long: Double): String? {
        val cityName: String?
        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
        val Adress = geoCoder.getFromLocation(lat,long,3)

        cityName = Adress?.get(0)?.countryName
            city=cityName
        return cityName
    }

}











