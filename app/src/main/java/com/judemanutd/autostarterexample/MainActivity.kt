package com.judemanutd.autostarterexample

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.judemanutd.autostarter.AutoStartPermissionHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logDeviceInfo()

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val autoStartAvailable = AutoStartPermissionHelper.getInstance().isAutoStartPermissionAvailable(this)
            val success = AutoStartPermissionHelper.getInstance().getAutoStartPermission(this@MainActivity)
            var message = "Failed"
            if (success) message = "Successful"

            Toast.makeText(this@MainActivity, "Supports AutoStart: $autoStartAvailable, Action $message", Toast.LENGTH_SHORT).show()
        }
    }

    private fun logDeviceInfo() {
        val tag = "DeviceInfo"
        Log.w(tag, "Board: ${Build.BOARD}")
        Log.w(tag, "Brand: ${Build.BRAND}")
        Log.w(tag, "Device: ${Build.DEVICE}")
        Log.w(tag, "Display: ${Build.DISPLAY}")
        Log.w(tag, "Hardware: ${Build.HARDWARE}")
        Log.w(tag, "Manufacturer: ${Build.MANUFACTURER}")
        Log.w(tag, "Product: ${Build.PRODUCT}")
        Log.w(tag, "Version.Release: ${Build.VERSION.RELEASE}")
        Log.w(tag, "Version.Codename: ${Build.VERSION.CODENAME}")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.w(tag, "Version.BaseOS: ${Build.VERSION.BASE_OS}")
            Log.w(tag, "Version.SecurityPatch: ${Build.VERSION.SECURITY_PATCH}")
        }
    }
}
