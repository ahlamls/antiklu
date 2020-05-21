package com.antiklu.aplikasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import static com.antiklu.aplikasi.SpawnActivity.my_shared_preferences;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    Boolean session = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean("login", false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (session) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), SpawnActivity.class));
                    finish();
                }
            }
        }, 3000L); //3000 L = 3 detik
    }
}
