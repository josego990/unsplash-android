package com.marckclarck.pruebatecnicaeltaier;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        GoToStart();

    }

    private void GoToStart(){
        final Intent intent = new Intent(SplashActivity.this,UnsplashStartActivity.class);


        final Handler handler_one = new Handler();
        handler_one.postDelayed(new Runnable() {
            @Override
            public void run() {

                final Handler handler_two = new Handler();
                handler_two.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(intent);
                        finish();
                    }
                }, 3000);
            }
        }, 2000);



    }

}
