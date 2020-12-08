package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DebutDePartie extends AppCompatActivity {
    int countTimer =0;
    TextView tvTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debut_de_partie);
        tvTimer = findViewById(R.id.tvTimer);


        Thread t = new Thread() {

            @Override
            public void run() {

                while (!isInterrupted()) {

                    try {
                        Thread.sleep(1000);  //1000ms = 1 sec

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                countTimer++;
                                tvTimer.setText(String.valueOf(countTimer));
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        t.start();
    }
}