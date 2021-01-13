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
    private int difficultyNbBombes;
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
        difficultyNbBombes = 7;
    }


    @Override
    protected void onStart() {
        super.onStart();

        btnChangeDifficulty.setOnClickListener(new View.OnClickListener() { // Bouton difficulté
            @Override
            public void onClick(View v) {
                switch(difficultyNbBombes){
                    case 7:
                        // Facile -> normal
                        btnChangeDifficulty.setText(getString(R.string.difficulty) + " " + getString(R.string.difficulty_normal));
                        difficultyNbBombes = 15;
                        break;
                    case 15:
                        // normal -> difficile
                        btnChangeDifficulty.setText(getString(R.string.difficulty) + " " + getString(R.string.difficulty_difficile));
                        difficultyNbBombes = 22;
                        break;
                    case 22:
                        // difficile -> facile
                        btnChangeDifficulty.setText(getString(R.string.difficulty) + " " + getString(R.string.difficulty_easy));
                        difficultyNbBombes = 7;
                        break;
                    default:
                        // ? (bug) -> facile
                        btnChangeDifficulty.setText(getString(R.string.difficulty) + " " + getString(R.string.difficulty_easy));
                        difficultyNbBombes = 7;
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
                intentGame.putExtra("difficultyNbBombes", difficultyNbBombes);
                startActivity(intentGame);
            }
        });

        btnSetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Settings.class));
            }
        });

    }

}