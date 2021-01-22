package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Score extends AppCompatActivity {

    private Button btnBack=null;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private TableLayout tabledisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        btnBack=findViewById(R.id.btnReturnFScore);

        tabledisplay=findViewById(R.id.mytablelayout);
        prefs = getSharedPreferences("MY_PREFS_NAME",MODE_PRIVATE);
        editor = prefs.edit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Gson gson = new Gson();
        //Recuperation de la liste des scores
        String json = prefs.getString("ListScores","empty");
        if(json!="empty") {
            //On verifié qu'elle n'est pas vide
            List<Scores> list = gson.fromJson(json, new TypeToken<ArrayList<Scores>>() {
            }.getType());
            //Pour chaque valeur on ajoute une nouvelle ligne avec des textview
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).toString());
                TableRow row = new TableRow(Score.this);
                TextView tv1 = new TextView(Score.this);
                tv1.setText(list.get(i).getNom());
                TextView tv2 = new TextView(Score.this);
                tv1.setWidth(540);
                tv1.setTextSize(20);
                tv2.setTextSize(20);
                tv2.setGravity(Gravity.CENTER_HORIZONTAL);
                tv2.setText(String.valueOf(list.get(i).getTemps()));
                row.addView(tv1);
                row.addView(tv2);
                tabledisplay.addView(row);
            }
        }
        btnBack.setOnClickListener(new View.OnClickListener() { // Bouton retour au menu
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Score.this, MainActivity.class);  //Lancer l'activité DisplayVue
                startActivity(intent);    //Afficher la vue
            }
        });
    }
}



