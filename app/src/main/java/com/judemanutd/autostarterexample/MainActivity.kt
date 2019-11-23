package com.judemanutd.autostarterexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.judemanutd.autostarter.AutoStartPermissionHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {

            val success = AutoStartPermissionHelper.getInstance().getAutoStartPermission(this@MainActivity)
            var message = "Failed"
            if (success) message = "Successfull"

            Toast.makeText(this@MainActivity, "Action $message", Toast.LENGTH_SHORT).show()

        }

    }
}
