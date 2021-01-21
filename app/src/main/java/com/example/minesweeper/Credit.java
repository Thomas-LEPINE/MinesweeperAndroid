package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class Credit extends AppCompatActivity {

    /* COMPOSANTS */
    private Button btnRetourMenu;

    private ImageView _ivAnim1;
    private ImageView _ivAnim2;
    private ImageView _ivAnim3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        btnRetourMenu = (Button) findViewById(R.id.btnRetourMenu);
        //********************  Animation  **************************
        _ivAnim1=findViewById(R.id.ivAnim1);
        _ivAnim2=findViewById(R.id.ivAnim2);
        _ivAnim3=findViewById(R.id.ivAnim3);
        Animation animrotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        Animation animscale = AnimationUtils.loadAnimation(this, R.anim.scale);
        Animation animtranslate = AnimationUtils.loadAnimation(this, R.anim.translate);
        Animation animtranslatescaling = AnimationUtils.loadAnimation(this, R.anim.translatescaling);
        _ivAnim1.startAnimation(animrotate);
        _ivAnim2.startAnimation(animscale);
        _ivAnim3.startAnimation(animtranslatescaling);


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