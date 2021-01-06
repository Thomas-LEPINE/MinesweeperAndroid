package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Credit extends AppCompatActivity {

    /* COMPOSANTS */
    private Button btnRetourMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        btnRetourMenu = (Button) findViewById(R.id.btnRetourMenu);
    }

    @Override
    protected void onStart() {
        super.onStart();

        btnRetourMenu.setOnClickListener(new View.OnClickListener() { // Bouton retour au menu
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Credit.this, MainActivity.class);  //Lancer l'activité DisplayVue
                startActivity(intent);    //Afficher la vue
            }
        });
    }

}