package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    /* COMPOSANTS */
    private Button btnGame;
    private Button btnChangeDifficulty;
    private Button btnSetings;
    private Button btnExit;
    private Button btnCredit;
    /* ##### */

    /* Attributs */
    private int difficulty;
    /* ###### */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGame = (Button) findViewById(R.id.btnNewGame);
        btnChangeDifficulty = (Button) findViewById(R.id.btnChangeDifficulty);
        btnSetings = (Button) findViewById(R.id.btnSetings);
        btnExit = (Button) findViewById(R.id.btnExit);
        btnCredit = (Button) findViewById(R.id.btnCredit);

        // La difficultée est mise à facile au début
        btnChangeDifficulty.setText(getString(R.string.difficulty) + " " + getString(R.string.difficulty_easy));
        difficulty = 0;
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
                startActivity(new Intent(MainActivity.this, Game.class));
            }
        });

    }


}