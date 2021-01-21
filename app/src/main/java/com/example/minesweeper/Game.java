package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends AppCompatActivity {

    private Button btnBackMenu = null;
    private Button btnNewGame=null;
    TextView tvTimer;
    Thread t =null;

    /* attributs */
    private int countTimer =0;
    private int difficulty;
    private int nbbomb = 75;
    private int maxlenrow=8;
    private int ncol=10;
    private int nbbombleft;
    private List<Hexa> bomblist = new ArrayList<Hexa>();

    /* ###### */

    private Switch swMode;
    Bundle data = new Bundle();
    private Button btntest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        btnBackMenu=findViewById(R.id.btnBackMenu);
        tvTimer = findViewById(R.id.tvTimer);
        swMode=findViewById(R.id.swMode);

        btnNewGame=findViewById(R.id.btnNewGame);
        btnNewGame.setVisibility(View.INVISIBLE);
        btntest = findViewById(R.id.button);
        tvTimer = findViewById(R.id.tvTimer);
        t = new Thread() {
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
            public void onFinish() {

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
        //bomblist.get(10).setBombe();
        //bomblist.get(11).setBombe();
        //bomblist.get(12).setBombe();
        nbbombleft=10;
        generatebombes(10);
        computeneighbourbomb();
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
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testfunction();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    private void testfunction(){
        //System.out.println("Test fct");
        //System.out.println(swMode.isChecked());
        printallbombes();

    }

    public boolean getStateSwitch() {
       return swMode.isChecked();
    }

    public void generatebombes(int n) {
        System.out.println("generation des bombes");
        int generated=0;
        //int[] malist = new int[n];
        Random r = new Random();
        while(generated<n){
            int temp = r.nextInt(nbbomb);
            System.out.println(generated);
            if(!bomblist.get(temp).isBomb()){
                generated+=1;
                bomblist.get(temp).setBombe();
            } else {

            }
        }
    }
    public void printallbombes() {
        int c=0;
        for(int i=0; i<nbbomb;i++) {
           bomblist.get(i).printbombe();
            if(!bomblist.get(i).isBomb()){
                c+=1;
            }
        }
        System.out.println("Nombre de bombes :" + String.valueOf(c));
    }

    public void computeneighbourbomb() {
        List<Integer> listtemp;
        System.out.println("computing bombes");
        for(int i=0;i<nbbomb;i++) {
            if (!bomblist.get(i).isBomb()) {
                listtemp = bomblist.get(i).getNeighbours();
                int c = 0;
                for (int j = 0; j < listtemp.size(); j++) {
                    if (bomblist.get(listtemp.get(j)).isBomb()) {
                        System.out.println(String.valueOf(listtemp.get(j)) + " is a bombe");
                        c++;
                    }
                }
                bomblist.get(i).setNeigbour(c);
            }
        }
    }



    public void displayblank(int id) {
        List<Integer> listtemp;
        listtemp = bomblist.get(id).getNeighbours();
        for(int i=0;i<listtemp.size();i++) {
            if(bomblist.get(listtemp.get(i)).isRetournable()) {
                if(bomblist.get(listtemp.get(i)).Retourner(false)) {
                    if(bomblist.get(listtemp.get(i)).get_value()==0) {
                        displayblank(listtemp.get(i));
                    }
                }

            }
        }
    }

    public void lost() {
        System.out.println("Vraiment perdu");
        System.out.println(countTimer);
        for(int i=0;i<bomblist.size();i++) {
            if((bomblist.get(i).get_flag()!=0 && bomblist.get(i).get_value()==-1) || (bomblist.get(i).get_flag()!=0 && bomblist.get(i).get_value()!=-1)) {
                bomblist.get(i).setWrongFlag();
            } else {
                bomblist.get(i).Retourner(true);
            }
        }
        btnNewGame.setVisibility(View.INVISIBLE);
    }

    public void win(){
        System.out.println("GG MEC");
        btnNewGame.setVisibility(View.INVISIBLE);
    }

    public void minusnbbombleft() {
        if(nbbombleft>=1) {
            nbbombleft--;
        } else {

        }
    }
}