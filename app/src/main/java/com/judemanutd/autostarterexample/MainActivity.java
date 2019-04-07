package com.judemanutd.autostarterexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.judemanutd.autostarter.AutoStartPermissionHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> AutoStartPermissionHelper.getInstance().getAutoStartPermission(MainActivity.this));


    }
}
