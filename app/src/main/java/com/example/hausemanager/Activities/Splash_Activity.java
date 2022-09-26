package com.example.hausemanager.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.hausemanager.R;

public class Splash_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    Thread.sleep(5*1000);

                    startActivity(new Intent(Splash_Activity.this, Login_Activity.class));
                    finish();

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }
            }
        });

        thread.start();

    }
}