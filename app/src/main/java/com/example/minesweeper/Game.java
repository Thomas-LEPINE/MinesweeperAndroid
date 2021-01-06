package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Game extends AppCompatActivity {

    private Button btnBackMenu = null;
    TextView tvTimer;

    /* attributs */
    private int countTimer =0;
    private int difficulty;
    /* ###### */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        btnBackMenu=findViewById(R.id.btnBackMenu);
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

    @Override
    protected void onStart() {
        super.onStart();
        btnBackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}