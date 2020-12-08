package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Coordinator {
    private final AppCompatActivity _activity;
    private final List<Hexa> _setOfMine;

    public Coordinator(AppCompatActivity act, List<Hexa> mines) {
        this._activity=act;
        this._setOfMine=mines;
        linkTogether();

    }

    private void linkTogether() {
        for(int i=0;i<_setOfMine.size();i++) {
            _setOfMine.get(i).linkTogether(this);
        }
    }
}
