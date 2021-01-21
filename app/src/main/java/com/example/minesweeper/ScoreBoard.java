package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class ScoreBoard extends AppCompatActivity {

    /* COMPOSANTS */
    private Button btnGagner;
    private Dialog popup;
    private TextView tvUsernames;
    private TextView tvTimes;
    private Timer timer;
    private Boolean gameFinished=false;
    private Boolean isWin=true;
    private Integer timeToPlay=0;
    private SharedPreferences myPreference ;
    private SharedPreferences.Editor myEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        btnGagner = (Button) findViewById(R.id.btnGagner);
        tvUsernames = findViewById(R.id.tvUsername);
        tvTimes = findViewById(R.id.tvTime);
        popup=new Dialog(this);
        timer=new Timer();

        myPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        myEditor = myPreference.edit();
        myEditor.putString("username", "Paul");
        myEditor.apply();

        timer.execute(timeToPlay);


    }

    protected void onStart() {
        super.onStart();
        btnGagner.setOnClickListener(new View.OnClickListener() { // Bouton difficulté
            @Override
            public void onClick(View v) {
                gameFinished=true; //Fin de partie

            }
        });

    }
    public void ShowResultPopup(boolean win, int timeToPlay)
    {
        /* COMPOSANTS */
        TextView tvResult;
        TextView tvScore;
        ImageView ivResult;
        Button btnResultBackMenu;

        popup.setContentView(R.layout.popup_result);
        btnResultBackMenu = (Button) popup.findViewById(R.id.btnResultBackMenu);
        tvResult = (TextView) popup.findViewById(R.id.tvResult);
        tvScore = (TextView) popup.findViewById(R.id.tvScore);
        ivResult=(ImageView) popup.findViewById(R.id.ivResult);

        timer.cancel(true);//arrêt du timer

        if(win ==true) {//si le joueur à gagner la partie
            //Recherche des meilleur score et du pseudo du joueur qui l'a fait
            SharedPreferences sharedPreferences = getDefaultSharedPreferences(getApplicationContext());
            String bestUsername = sharedPreferences.getString("bestUsername", "Guest");
            tvUsernames.setText(bestUsername);
            Integer bestScore = sharedPreferences.getInt("bestScore",timeToPlay);

            tvResult.setText("GAGNE");
            if(bestScore<timeToPlay)//Comparaison du meilleur score avec le score du joueur
            {
                ivResult.setImageResource(R.drawable.fireworks);
                tvScore.setText("Le meilleur score de "+Integer.toString(bestScore)+" ms est détenu par "+bestUsername);

            }
            else
            {
                String username = sharedPreferences.getString("username", "Guest");
                myPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                myEditor = myPreference.edit();
                myEditor.putString("bestUsername", username);
                myEditor.putInt("bestScore", timeToPlay);
                myEditor.apply();
                ivResult.setImageResource(R.drawable.winner);
                tvScore.setText("Bravo "+ username+" avec "+Integer.toString(timeToPlay)+" ms tu as fait le meilleur score.");
            }
        }
        else
        {
            tvResult.setText("PERDU");
            tvScore.setText("Dommage... Essaie encore !!! ");
            ivResult.setImageResource(R.drawable.explosion);
        }
        btnResultBackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreBoard.this, MainActivity.class);  //Lancer l'activité MainActivity
                startActivity(intent);    //Afficher la vue

            }
        });
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.show();//affichage de la popup
    }

    //Timer
    private class Timer extends AsyncTask< Integer, Integer, Integer>{
        private int countTimer;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            countTimer=0;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            countTimer=integers[0];
            while(gameFinished==false) {
                try {
                    Thread.sleep(1000);// 1000ms = 1s
                    countTimer++;
                    publishProgress(countTimer);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
            }
            return countTimer;
        }

        protected void onProgressUpdate(Integer...values) {
            super.onProgressUpdate(values);
            tvTimes.setText(Integer.toString(values[0]));
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            ShowResultPopup(isWin, result);//Affichage de la popup

        }
    }
}







