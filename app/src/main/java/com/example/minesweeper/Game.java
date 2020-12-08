package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
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
        getSupportFragmentManager().beginTransaction()
                .add(R.id.lyhHexa,setOfMines.get(0))
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.lyhHexa,setOfMines.get(1))
                .commit();

        RecyclerView hexaRcv;
        //hexaRcv = (RecyclerView)  findViewById(R.id.hexa_rcv);
        //GridLayoutManager manager = new GridLayoutManager(this, 3);
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