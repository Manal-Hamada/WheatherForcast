package com.example.wheatherforcast.setting

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.airbnb.lottie.utils.Utils
import com.example.wheatherforcast.R
import com.example.wheatherforcast.databinding.FragmentSettingBinding
import com.example.wheatherforcast.utils.Constants
import com.example.wheatherforcast.utils.NetworkConnection
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method


class SettingFragment : Fragment() {
    lateinit var binding: FragmentSettingBinding
    lateinit var sharedPrefrences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefrences =
            requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        editor = sharedPrefrences.edit()
       // LanguageConverter.checkLanguage(sharedPrefrences.getString(Constants.language,"")!!,requireContext())

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
        checkMapBtn()
        checkSpeedUnit()
        checkTempUnit()
        checkNotificationStatus()
    }

    fun checkLanguage() {
        binding.langRadBtn.setOnCheckedChangeListener { group, checkedId ->
            val language = if (binding.arabicBtn.id == checkedId) "ar" else "en"
            editor.putString(Constants.language, language)
            editor.putInt(Constants.checkedLang, binding.langRadBtn.checkedRadioButtonId)
            editor.commit()
          //  Navigation.findNavController(requireView()).navigate(R.id.action_settingFragment_self)
            requireActivity().finish()
            requireActivity().overridePendingTransition(0, 0)
            startActivity(requireActivity().intent)
            requireActivity().overridePendingTransition(0, 0)

        }

    }

  fun checkMapBtn(){
      binding.mapRadBtn.setOnClickListener {
          if(NetworkConnection.isOnline(requireContext())==true) {
              Navigation.findNavController(requireView()).navigate(R.id.action_settingFragment_to_mapFragment)
              Constants.mapChange=true
          }
          else
              Toast.makeText(requireContext(),"connect network ",Toast.LENGTH_SHORT).show()
      }
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
                binding.speedRadG.check(binding.mSRbtn.id)

            } else if (binding.fehrn.id == checkedId) {
                units = "imperial"
                binding.speedRadG.check(binding.hourBtn.id)

            } else {
                units = "standard"
                binding.speedRadG.check(binding.mSRbtn.id)
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
                Constants.checkedLang,
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
                Constants.checkedTempUnit,
                binding.tempRadG.checkedRadioButtonId
            )
        )
    }

    override fun onPause() {
        super.onPause()

    }

   /* @SuppressLint("RestrictedApi")
    private fun isNotificationEnabled(mContext: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val mAppOps = mContext.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val appInfo = mContext.applicationInfo
            val pkg = mContext.applicationContext.packageName
            val uid = appInfo.uid
            val appOpsClass: Class<*>
            try {
                appOpsClass = Class.forName(AppOpsManager::class.java.name)
                val checkOpNoThrowMethod: Method = appOpsClass.getMethod(
                    "checkOpNoThrow", Integer.TYPE, Integer.TYPE,
                    String::class.java
                )
                val opPostNotificationValue: Field =
                    appOpsClass.getDeclaredField("OP_POST_NOTIFICATION")
                val value = opPostNotificationValue.get(Int::class.java) as Int
                return checkOpNoThrowMethod.invoke(
                    mAppOps, value, uid,
                    pkg
                ) as Int == AppOpsManager.MODE_ALLOWED
            } catch (ex: Exception) {
                Utils.isNetworkException(ex)
            } catch (ex: NoSuchMethodException) {
                Utils.isNetworkException(ex)
            } catch (ex: NoSuchFieldException) {
                Utils.isNetworkException(ex)
            } catch (ex: InvocationTargetException) {
                Utils.isNetworkException(ex)
            } catch (ex: IllegalAccessException) {
                Utils.isNetworkException(ex)
            }
            false
        } else {
            false
        }
    }*/
}
