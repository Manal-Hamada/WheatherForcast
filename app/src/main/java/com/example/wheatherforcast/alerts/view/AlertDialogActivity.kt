package com.example.wheatherforcast.alerts.view

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.wheatherforcast.R
import com.example.wheatherforcast.databinding.ActivityAlertDialogBinding
import com.example.wheatherforcast.utils.Constants
import com.example.wheatherforcast.utils.ConvertUnits
import com.example.wheatherforcast.utils.Icons


class AlertDialogActivity : AppCompatActivity() {

    var mMediaPlayer: MediaPlayer? = null
    var tempUnit=Constants.Celsius
    var city=""

    lateinit var binding:ActivityAlertDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =ActivityAlertDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var  sharedPreference =getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

         city= sharedPreference.getString("cityName","").toString()

        getDataFromNetwork()
       // window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
      //  window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.addFlags(
            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        )
        setFinishOnTouchOutside(false)

        binding.btnCancle.setOnClickListener(){
            mMediaPlayer!!.stop()
            this.finish()
        }
        playSound()
    }

    private fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        }
    }


    fun getDataFromNetwork() {
        binding.tvAlarmCity.text=city
        binding.tvAlarmDescription.text=Constants.response?.current?.weather!!.get(0).description
        Icons.getSuitableIcon(Constants.response?.current!!.weather[0].icon, binding.imgAlarmIcon)
        binding.tvAlarmTemp.text= ConvertUnits.convertTemp(Constants.response!!.current!!.temp,tempUnit)
    }

}