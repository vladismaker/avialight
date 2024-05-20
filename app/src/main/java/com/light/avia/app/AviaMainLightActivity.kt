package com.light.avia.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AviaMainLightActivity : AppCompatActivity() {
    private var aviaCreaLight:Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.avia_activity_main_light)

        aviaCreaLight =12
    }
}