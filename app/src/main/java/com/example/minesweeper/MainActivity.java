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
    /* COMPOSANTS (attributs) */
    private Button btnGame;
    private Button btnChangeDifficulty;
    private Button btnSettings;
    private Button btnExit;
    private Button btnCredit;
    private Button btnScore;
    private Music musicThread;
    protected Boolean musicIsOn = false;
    private SharedPreferences myPreference ;
    private SharedPreferences.Editor myEditor;

    /* ##### */

    /* Attributs de la classe */
    private int difficultyNbBombes;
    final static int NB_BOMBES_EASY = 10;
    final static int NB_BOMBES_MEDIUM = 18;
    final static int NB_BOMBES_HARD = 28;
    /* ###### */

    //Connection au service Music
    private ServiceConnection mServiceCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Music.MyServiceBinder myServiceBinder= (Music.MyServiceBinder) iBinder;
            musicThread = myServiceBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicThread = null;
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
        difficultyNbBombes = NB_BOMBES_EASY;

        //Verification que le service n'a pas été lancé
        if(Music.serviceIsRunning==false) {
            doBindService();// Etablir une connection avec le service
            Intent music = new Intent();
            music.setClass(this, Music.class);
            // Lancement du service Music :
            startService(music);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnChangeDifficulty.setOnClickListener(new View.OnClickListener() { // Bouton difficulté
            @Override
            public void onClick(View v) {
            switch(difficultyNbBombes){
                case NB_BOMBES_EASY:
                    // Facile -> normal
                    btnChangeDifficulty.setText(getString(R.string.difficulty) + " " + getString(R.string.difficulty_normal));
                    difficultyNbBombes = NB_BOMBES_MEDIUM;
                    break;
                case NB_BOMBES_MEDIUM:
                    // normal -> difficile
                    btnChangeDifficulty.setText(getString(R.string.difficulty) + " " + getString(R.string.difficulty_difficile));
                    difficultyNbBombes = NB_BOMBES_HARD;
                    break;
                case NB_BOMBES_HARD:
                    // difficile -> facile
                    btnChangeDifficulty.setText(getString(R.string.difficulty) + " " + getString(R.string.difficulty_easy));
                    difficultyNbBombes = NB_BOMBES_EASY;
                    break;
                default:
                    // ? (bug) -> facile
                    btnChangeDifficulty.setText(getString(R.string.difficulty) + " " + getString(R.string.difficulty_easy));
                    difficultyNbBombes = NB_BOMBES_EASY;
                    break;
            }
            }
        });

        btnCredit.setOnClickListener(new View.OnClickListener() { // Bouton crédit
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Credit.class);  // Lancer l'activité Credit
                startActivity(intent);    // Afficher la vue de l'activité
            }
        });

        btnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleGame = new Bundle();
                Intent intentGame = new Intent(MainActivity.this, Game.class);  // Game
                intentGame.putExtra("difficultyNbBombes", difficultyNbBombes);
                startActivity(intentGame);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Settings.class));
            }
        });
        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Score.class));
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Réinitialisation des variables concernant la musique :
                myPreference = getDefaultSharedPreferences(getApplicationContext());
                myEditor = myPreference.edit();
                myEditor.putBoolean("musicIsOn", true);
                myEditor.putString("btnMusicString", "Musique OFF");
                myEditor.apply();
                // Suppression de la connection au service :
                doUnbindService();
                finishAffinity();
                System.exit(0); // Fin de l'appli
            }
        });

    }
    public void onDestroy () {
        super.onDestroy();
        // Réinitialisation des variables concernant la musique :
        myEditor.putBoolean("musicIsOn", true);
        myEditor.putString("btnMusicString", "Musique OFF");
        myEditor.apply();
        // Suppression de la connection au service :
        doUnbindService();
    }
}