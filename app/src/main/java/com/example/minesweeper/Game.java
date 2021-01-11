package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Game extends AppCompatActivity {

    private Button btnBackMenu = null;
    TextView tvTimer;

    /* attributs */
    private int countTimer =0;
    private int difficulty;
    private int nbbomb = 75;
    private int maxlenrow=8;
    private int ncol=10;
    private List<Hexa> bomblist = new ArrayList<Hexa>();
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

        //Calcul des numero de ligne et colonne et des id
        int numcol=0;
        int numrow=0;
        int maxcurrow=8;
        for( int i =0; i<nbbomb;i++) {
            if (numrow == maxcurrow) {
                numrow = 0;
                numcol += 1;
                if (maxcurrow == 8) {
                    maxcurrow = 7;
                } else {
                    maxcurrow = 8;
                }
            }
            String idtemp = "frag" + String.valueOf(i);
            int intidtemp = getResources().getIdentifier(idtemp, "id", getPackageName());
            //System.out.println(String.valueOf(numrow)+"   "+String.valueOf(numcol));
            Hexa hextemp = (Hexa) getSupportFragmentManager().findFragmentById(intidtemp);
            hextemp.SetHexa(numcol, numrow, i);
            //Tous les fragments sont stockés dans cette liste
            bomblist.add(hextemp);
            bomblist.get(i).test();
            numrow += 1;
        }   
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