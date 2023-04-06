package com.example.wheatherforcast.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheatherforcast.R
import com.example.wheatherforcast.databinding.FragmentHomeBinding
import com.example.wheatherforcast.db.homedb.HomeLocalSource
import com.example.wheatherforcast.db.homedb.RemoteSource
import com.example.wheatherforcast.home.model.Repository
import com.example.wheatherforcast.home.model.WeatherResponse
import com.example.wheatherforcast.home.viewmodel.HomeViewModel
import com.example.wheatherforcast.home.viewmodel.ViewModelFactory
import com.example.wheatherforcast.network.ApiStatus
import com.example.wheatherforcast.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*


class HomeFragment : Fragment() {

    //private val args by navArgs<HomeFragmentArgs>()

    lateinit var binding: FragmentHomeBinding
    lateinit var dayAdapter: DailyAdapter
    lateinit var hourAdapter: HourAdapter
    lateinit var factory: ViewModelFactory
    lateinit var viewModel: HomeViewModel
    lateinit var sharedPreference: SharedPreferences

    var city: String? = ""
    var lat: String? = ""
    var lon: String? = ""
    var units: String? = ""
    var currentLanguage: String? = ""
    var tempUnit = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreference =
            requireContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        LanguageConverter.checkLanguage(
            sharedPreference.getString(Constants.language, "")!!,
            requireContext()
        )
        getSharedPrefrencesValue()
    }

    fun getSharedPrefrencesValue() {

        lat = sharedPreference.getString(Constants.latitude, "33.44")
        lon = sharedPreference.getString(Constants.longitude, "-94")
        city = sharedPreference.getString(Constants.cityName, "")
        currentLanguage = sharedPreference.getString(Constants.language, "")
        units = sharedPreference.getString("units", "")
        Log.i("cityy", city.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LanguageConverter.checkLanguage(currentLanguage!!, requireContext())
        //Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment2_self)
        getSharedPrefrencesValue()
        setViewModel()
        getData()

        // setMenuItemAction(view)
    }

    fun setViewModel() {
        if (Constants.favModel == true) {
            lat = Constants.model.latitude
            lon = Constants.model.longitude
            city = Constants.model.cityName
            Log.i(" fav city", city.toString())
            Constants.favModel = false
        }

        factory = ViewModelFactory(
            Repository.getInstance(HomeLocalSource(requireContext()), RemoteSource()),
            this.requireContext(),
            lat,
            lon,
            currentLanguage,
            units
        )
        Log.i("Units", units.toString())
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
    }

    fun setMenuItemAction(v: View) {
        var drawerLayout: DrawerLayout = v.findViewById(R.id.drawer)
        binding.menuImg.setOnClickListener {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
        }
        //  bind.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }


    @SuppressLint("NewApi")
    fun setBackgroundVideo() {
        var uri: Uri =
            Uri.parse("android.resource://" + getActivity()?.getPackageName() + "/" + R.raw.cloud_video)
        binding.videoBg.setVideoURI(uri)
        binding.videoBg.setOnPreparedListener {
            it.isLooping = true
        }
        binding.videoBg.animate().alpha(1f)
        binding.videoBg.seekTo(0)
        binding.videoBg.start()
        //getData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getData() {
        lifecycleScope.launch {
            if (NetworkConnection.isOnline(requireContext()) == true) {
                viewModel.getWeatherResponse(lat, lon, currentLanguage, units)
            } else
                viewModel.getSavedWeather()

            viewModel.statList.collectLatest {
                when (it) {
                    is ApiStatus.Success -> {
                        binding.dailyStatusCard.visibility = View.VISIBLE
                        binding.loading.visibility = View.GONE
                        binding.videoBg.setZOrderOnTop(false)
                        setDailyRecycler(it.weatherResponse)
                        setHourlyRecycler(it.weatherResponse)
                        setHomeData(it.weatherResponse)
                        Constants.response = it.weatherResponse
                    }
                    is ApiStatus.Loading -> {
                        binding.loading.visibility = View.VISIBLE
                    }
                    else -> {
                        Toast.makeText(
                            requireContext(),
                            "There is a proplem in Api",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }


        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        setBackgroundVideo()
        LanguageConverter.checkLanguage(currentLanguage!!, requireContext())

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setHomeData(it: WeatherResponse) {
        LanguageConverter.checkLanguage(currentLanguage, requireContext())
        binding.degreeTv.text = ConvertUnits.convertTemp(it!!.daily!![0].temp.day, units!!)
        Icons.getSuitableIcon(it!!.daily!![0].weather[0].icon, binding.statusId)
        binding.currentStatus.text = it.daily!![0].weather[0].description
        //  ConvertUnits.convertTemp(it!!.daily!![0].temp.day, tempUnit = tempUnit)
        binding.pressureTv.text = it.current?.pressure.toString()
        binding.windTv.text = it.current?.wind_deg.toString()
        binding.temprTv.text = it.current?.temp.toString()
        binding.cloudTv.text = it.current?.clouds.toString()
        binding.ultraTv.text = it.current?.uvi.toString()
        Icons.getSuitableIcon(
            it.current!!.weather[0].icon,
            binding.statusId
        )
        binding.visibilityTv.text = it.current.visibility.toString()
        binding.countryName.text = CityName.getCityName(it.lat,it.lon,requireContext())
        binding.timeTv.text = TimeConverter.getCurrentTime(it!!.hourly!![0].dt, it!!.timezone)
        binding.todayId.text = DayFormatter.getDay(it!!.daily!![0].dt)

    }


    fun setDailyRecycler(it: WeatherResponse) {
        dayAdapter = DailyAdapter(it, units!!)
        binding.dailyList?.apply {
            adapter = dayAdapter
            layoutManager = LinearLayoutManager(this.context).apply {
                orientation = RecyclerView.VERTICAL

            }
        }
    }

    fun setHourlyRecycler(it: WeatherResponse) {
        hourAdapter = HourAdapter(it, units!!)
        binding.hourlyList?.apply {
            adapter = hourAdapter
            layoutManager = LinearLayoutManager(this.context).apply {
                orientation = RecyclerView.HORIZONTAL

            }
        }
    }


}