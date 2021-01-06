package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Game extends AppCompatActivity {

    private Button btnBackMenu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        btnBackMenu=findViewById(R.id.btnBackMenu);
        List<Hexa> setOfMines = new ArrayList<>();
        Coordinator  coor = new Coordinator(this,setOfMines);
        setOfMines.add(new Hexa(1,2));
        setOfMines.add(new Hexa(2,4));
        setOfMines.add(new Hexa(3,2));
        setOfMines.add(new Hexa(4,4));

        getSupportFragmentManager().beginTransaction()
                .add(R.id.clyHexa,setOfMines.get(2))
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.clyHexa,setOfMines.get(0))
                .commit();
        getSupportFragmentManager().beginTransaction()
               .add(R.id.temp,setOfMines.get(1))
          //      .replace();
             .commit();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        /*ConstraintSet set = new ConstraintSet();
        set.clone((ConstraintLayout)findViewById(R.id.clyHexa));
        set.connect(R.id.clyHexa,ConstraintSet.START,ConstraintSet.PARENT_ID,ConstraintSet.START,400);
        set.connect(R.id.clyHexa,ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP,200);
        set.applyTo((ConstraintLayout)findViewById(R.id.clyHexa));
*/
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




    }
}