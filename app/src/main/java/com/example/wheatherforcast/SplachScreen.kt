package com.example.wheatherforcast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.wheatherforcast.home.view.HomeScreen
import com.example.wheatherforcast.utils.Constants
import com.example.wheatherforcast.utils.LanguageConverter

class SplachScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Handler().postDelayed({
        Handler(Looper.getMainLooper()).postDelayed({
           // LanguageConverter.checkLanguage(Constants.language,applicationContext)
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3000 is the delayed time in milliseconds.
    }

    }
