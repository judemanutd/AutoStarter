package com.judemanutd.autostarterexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

import com.judemanutd.autostarter.AutoStartPermissionHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener { AutoStartPermissionHelper.getInstance().getAutoStartPermission(this@MainActivity) }


    }
}
