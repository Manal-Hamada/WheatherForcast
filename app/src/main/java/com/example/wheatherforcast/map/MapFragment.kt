package com.example.wheatherforcast.map

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.wheatherforcast.R
import com.example.wheatherforcast.databinding.FragmentMapBinding
import com.example.wheatherforcast.db.favouritedb.FavLocalSource
import com.example.wheatherforcast.favourites.model.FavModel
import com.example.wheatherforcast.favourites.model.FavouriteRepository
import com.example.wheatherforcast.favourites.viewmodel.FavouriteViewModel
import com.example.wheatherforcast.favourites.viewmodel.FavouriteViewModelfactory
import com.example.wheatherforcast.utils.AppDialogs
import com.example.wheatherforcast.utils.CityName
import com.example.wheatherforcast.utils.Constants
import com.example.wheatherforcast.utils.LanguageConverter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*


class MapFragment() : Fragment() {

    lateinit var binding: FragmentMapBinding
    lateinit var sharedPreference: SharedPreferences
    lateinit var editor:SharedPreferences.Editor
    lateinit var factory: FavouriteViewModelfactory
    lateinit var viewModel: FavouriteViewModel
    lateinit var dialog:Dialog
    lateinit var search:EditText
    var city:String?="here"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference =
            requireContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        editor=sharedPreference.edit()
        LanguageConverter.checkLanguage(sharedPreference.getString(Constants.language,"")!!,requireContext())

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
            @SuppressLint("SuspiciousIndentation")
            @RequiresApi(Build.VERSION_CODES.S)
            override fun onMapReady(googleMap: GoogleMap) {
                googleMap.setOnMapClickListener {
                    val marker = MarkerOptions()
                    marker.position(it)
                  city=CityName.getCityName(it.latitude,it.longitude,requireContext())//?.let { it1 -> Log.i("map position", it1) }
                    googleMap.clear()
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 5f))
                    marker.title(city)
                    googleMap.addMarker(marker)
                    setDialog(it.latitude,it.longitude,view)

                }
            }

        })
        search()
    }

    fun saveLocation(lat:Double,lon:Double){
        editor.putString(Constants.longitude,lat.toString())
        editor.putString(Constants.longitude,lon.toString())
        editor.commit()
    }
    fun addFav(lat:Double,lon:Double) {
        factory = FavouriteViewModelfactory(
            FavouriteRepository.getInstance(FavLocalSource(requireContext())), this.requireContext()
        )
        viewModel = ViewModelProvider(this, factory).get(FavouriteViewModel::class.java)
        lifecycleScope.launch {
            viewModel.insertnewFavourite(
                FavModel(
                    lat.toString(),
                    lon.toString(),
                    CityName.getCityName(lat,lon,requireContext()),
                    lat.toString() + lon.toString()
                )
            )
        }
    }

    fun setDialog(lat:Double,lon:Double, view:View){
        var body=""
        if(Constants.fromfav==true)
               body=getString(R.string.fav_dialoge_body)
        else
            body=getString(R.string.save_location)

        dialog=AppDialogs.setDialog(body,requireContext(),R.layout.dialog_layout)
        val cancleBtn = dialog.findViewById(R.id.fav_di_cancle) as Button
        val saveBtn = dialog.findViewById(R.id.fav_di_save) as Button
        saveBtn.setOnClickListener {
            if(Constants.fromfav==true) {
                addFav(lat, lon)
                dialog.dismiss()
                Constants.fromfav=false
                Navigation.findNavController(view)
                    .navigate(R.id.action_mapFragment_to_favouriteScreen)
            }
            else{
                saveLocation(lat,lon)
                dialog.dismiss()
                Toast.makeText(requireContext(),"current location selected",Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view)
                    .navigate(R.id.action_mapFragment_to_settingFragment)
            }
        }
        getActivity()?.getFragmentManager()?.popBackStack();
        cancleBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }
    fun search(){

        binding.search.setOnEditorActionListener(OnEditorActionListener { textView, actionId, keyEvent ->
            if ((actionId == EditorInfo.IME_ACTION_SEARCH)
                || (actionId == EditorInfo.IME_ACTION_DONE) ||
                (keyEvent.action === KeyEvent.ACTION_DOWN)
                || (keyEvent.action === KeyEvent.KEYCODE_ENTER)
            ) {

                geoLocate()
            }
            false
        })
    }
    private fun geoLocate() {
        val searchString: String = binding.search.getText().toString()
        val geocoder = Geocoder(requireContext())
        var list: List<Address>? = ArrayList()
        try {
            list = geocoder.getFromLocationName(searchString, 1)
            setDialog(list?.get(0)!!.latitude,list?.get(0)!!.longitude,requireView())

        } catch (e: IOException) {
            Toast.makeText(requireContext(),"No results",Toast.LENGTH_SHORT).show()
            Log.i("search error", "geoLocate: IOException: "+e.message.toString())
        }
        if (list!!.size > 0) {
            val address: Address = list[0]
            Log.i("searched adress", "geoLocate: found a location: " + address.toString())
            setDialog(address!!.latitude,address.longitude,requireView())

        }
        else
            Toast.makeText(requireContext(),"No results",Toast.LENGTH_SHORT).show()
    }
}











