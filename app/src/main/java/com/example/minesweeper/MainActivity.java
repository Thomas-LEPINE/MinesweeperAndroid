package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.ServiceConnection;

import android.os.IBinder;

import android.view.View;
import android.widget.Button;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity {
    /* COMPOSANTS */
    private Button btnGame;
    private Button btnChangeDifficulty;
    private Button btnSettings;
    private Button btnExit;
    private Button btnCredit;

    private Button btnScore;

    private Music musicThread;
    protected Boolean musicIsOn=false;
    private SharedPreferences myPreference ;
    private SharedPreferences.Editor myEditor;

    /* ##### */

    /* Attributs */
    private int difficulty;
    /* ###### */

    //Connection au service Music
    private ServiceConnection mServiceCon=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            Music.MyServiceBinder myServiceBinder= (Music.MyServiceBinder) iBinder;
            musicThread = myServiceBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicThread=null;

        }
    };
    void doBindService(){
        bindService(new Intent(this,Music.class),
                mServiceCon, Context.BIND_AUTO_CREATE);
        musicIsOn = true;
    }

    void doUnbindService()
    {
        if(musicIsOn)
        {
            unbindService(mServiceCon);
            musicIsOn = false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGame = (Button) findViewById(R.id.btnNewGame);
        btnChangeDifficulty = (Button) findViewById(R.id.btnChangeDifficulty);
        btnSettings = (Button) findViewById(R.id.btnSetings);
        btnExit = (Button) findViewById(R.id.btnExit);
        btnCredit = (Button) findViewById(R.id.btnCredit);
        btnScore = (Button) findViewById(R.id.btnScore);

        // La difficultée est mise à facile au début
        btnChangeDifficulty.setText(getString(R.string.difficulty) + " " + getString(R.string.difficulty_easy));
        difficulty = 0;

        //Verification que le service n'a  pas été lancé
        if(Music.serviceIsRunning==false) {
            doBindService();// Etablir une connection avec le service
            Intent music = new Intent();
            music.setClass(this, Music.class);
            //Lancement du service Music
            startService(music);
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        btnChangeDifficulty.setOnClickListener(new View.OnClickListener() { // Bouton difficulté
            @Override
            public void onClick(View v) {
                switch(difficulty){
                    case 0:
                        // Facile -> normal
                        btnChangeDifficulty.setText(getString(R.string.difficulty) + " " + getString(R.string.difficulty_normal));
                        difficulty = 1;
                        break;
                    case 1:
                        // normal -> difficile
                        btnChangeDifficulty.setText(getString(R.string.difficulty) + " " + getString(R.string.difficulty_difficile));
                        difficulty = 2;
                        break;
                    case 2:
                        // difficile -> facile
                        btnChangeDifficulty.setText(getString(R.string.difficulty) + " " + getString(R.string.difficulty_easy));
                        difficulty = 0;
                        break;
                    default:
                        // ? (bug) -> facile
                        btnChangeDifficulty.setText(getString(R.string.difficulty) + " " + getString(R.string.difficulty_easy));
                        difficulty = 0;
                        break;
                }
            }
        });

        btnCredit.setOnClickListener(new View.OnClickListener() { // Bouton crédit
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Credit.class);  //Lancer l'activité DisplayVue
                startActivity(intent);    //Afficher la vue
            }
        });

        btnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleGame = new Bundle();
                Intent intentGame = new Intent(MainActivity.this, Game.class);  //Game
                //intentGame.putExtras(bundleGame.putString(difficulty, int));
                startActivity(intentGame);
            }
        });


        btnScore.setOnClickListener(new View.OnClickListener() { // Bouton crédit
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScoreBoard.class);  //Lancer l'activité DisplayVue
                startActivity(intent);    //Afficher la vue
            }
        });


        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Settings.class));
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Réinitialisation des variables concernant la musique
                myPreference = getDefaultSharedPreferences(getApplicationContext());
                myEditor = myPreference.edit();
                myEditor.putBoolean("musicIsOn", true);
                myEditor.putString("btnMusicString", "Musique OFF");
                myEditor.apply();
                //Supression de la connection au service
                doUnbindService();
                finishAffinity();
                System.exit(0);

            }
        });

    }
    public void onDestroy () {
        super.onDestroy();
        //Réinitialisation des variables concernant la musique
        myEditor.putBoolean("musicIsOn", true);
        myEditor.putString("btnMusicString", "Musique OFF");
        myEditor.apply();
        //Supression de la connection au service
        doUnbindService();


    }


}