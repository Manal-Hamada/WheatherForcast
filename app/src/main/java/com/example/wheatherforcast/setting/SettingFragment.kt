package com.example.wheatherforcast.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wheatherforcast.databinding.FragmentSettingBinding
import com.example.wheatherforcast.utils.Constants
import com.example.wheatherforcast.utils.LanguageConverter


class SettingFragment : Fragment() {
    lateinit var binding: FragmentSettingBinding
    lateinit var sharedPrefrences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefrences =
            requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        editor = sharedPrefrences.edit()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // saveButtonStates()
        getCheckedBtns()
        checkLanguage()
        checkLocationWay()
        checkSpeedUnit()
        checkTempUnit()
        checkNotificationStatus()
    }

    fun checkLanguage() {
        binding.langRadBtn.setOnCheckedChangeListener { group, checkedId ->
            val language = if (binding.arabicBtn.id == checkedId) "ar" else "en"
            Constants.language = language
           // LanguageConverter.checkLanguage(language,requireContext())
            editor.putString("language", language)
            editor.putInt("checkedLang", binding.langRadBtn.checkedRadioButtonId)
            editor.apply()
        }
        LanguageConverter.checkLanguage(Constants.language,requireContext())
    }


    fun checkLocationWay() {
        binding.locationRadG.setOnCheckedChangeListener { group, checkedId ->
            editor.putInt("checkedLocation", binding.locationRadG.checkedRadioButtonId)
            editor.commit()
        }
    }

    fun checkSpeedUnit() {
        var units = ""
        binding.speedRadG.setOnCheckedChangeListener { group, checkedId ->
            editor.putInt("checkedWindSpeed", binding.speedRadG.checkedRadioButtonId)
            if (binding.hourBtn.id == checkedId) {
                units = "imperial"
                binding.tempRadG.check(binding.fehrn.id)
            } else {
                units = "metric"
            }
            editor.putString("units", units)
            editor.commit()
        }

    }

    fun checkTempUnit() {
        var units = ""
        binding.tempRadG.setOnCheckedChangeListener { group, checkedId ->
            if (binding.celcsious.id == checkedId) {
                units = "metric"
                binding.speedRadG.check(binding.celcsious.id)

            } else if (binding.fehrn.id == checkedId) {
                units = "imperial"
                binding.speedRadG.check(binding.fehrn.id)

            } else {
                units = "standard"
                binding.speedRadG.check(binding.kelvin.id)
            }
            editor.putInt("checkedTempUnit", binding.tempRadG.checkedRadioButtonId)
            editor.putString("units", units)
            editor.commit()
        }

    }

    fun checkNotificationStatus() {
        binding.enableRadG.setOnCheckedChangeListener { group, checkedId ->
            editor.putInt("checkedNotifStatus", binding.enableRadG.checkedRadioButtonId)
            editor.commit()
        }
    }

    override fun onResume() {
        super.onResume()
        getCheckedBtns()
    }


    fun getCheckedBtns() {
        binding.langRadBtn.check(
            sharedPrefrences.getInt(
                "checkedLang",
                binding.langRadBtn.checkedRadioButtonId
            )
        )
        binding.locationRadG.check(
            sharedPrefrences.getInt(
                "checkedLocation",
                binding.locationRadG.checkedRadioButtonId
            )
        )
        binding.speedRadG.check(
            sharedPrefrences.getInt(
                "checkedWindSpeed",
                binding.speedRadG.checkedRadioButtonId
            )
        )
        binding.enableRadG.check(
            sharedPrefrences.getInt(
                "checkedNotifStatus",
                binding.enableRadG.checkedRadioButtonId
            )
        )
        binding.tempRadG.check(
            sharedPrefrences.getInt(
                "checkedTempUnit",
                binding.tempRadG.checkedRadioButtonId
            )
        )
    }
}
