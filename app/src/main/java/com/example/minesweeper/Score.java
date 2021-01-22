package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Score extends AppCompatActivity {

    private Button btnTest=null;
    private Button btnTest2=null;
    private Button btnBack=null;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        btnBack=findViewById(R.id.btnReturnFScore);
        btnTest=findViewById(R.id.btnTest);
        btnTest2=findViewById(R.id.btnTest2);

        prefs = getSharedPreferences("MY_PREFS_NAME",MODE_PRIVATE);
        editor = prefs.edit();



    }

    @Override
    protected void onStart() {
        super.onStart();
        btnBack.setOnClickListener(new View.OnClickListener() { // Bouton retour au menu
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Score.this, MainActivity.class);  //Lancer l'activité DisplayVue
                startActivity(intent);    //Afficher la vue
            }
        });
        btnTest.setOnClickListener(new View.OnClickListener() { // Bouton retour au menu
            @Override
            public void onClick(View v) {
                List temp=new ArrayList<Myscores>();

                for(int i=0;i<2;i++) {

                }

                Gson gson = new Gson();
                String json = gson.toJson(list); // list is an ArrayList
                editor.putString("LIST",json);
                editor.apply();
            }
        });
        btnTest2.setOnClickListener(new View.OnClickListener() { // Bouton retour au menu
            @Override
            public void onClick(View v) {

            }
        });
    }
    }
}

public class Myscores {

}