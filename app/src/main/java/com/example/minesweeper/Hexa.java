package com.example.minesweeper;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Hexa extends Fragment {
    private int _row;
    private int _col;
    private int _state;
    private int _nbbombes;
    private ImageButton _ivMine;
    private Coordinator _coor;
    private View v;


    public Hexa() {
        // Required empty public constructor
    }

    public Hexa(int r, int c) {
        this._row=r;
        this._col=c;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_hexa,container, false);
        _ivMine = (ImageButton) v.findViewById(R.id.imageButton);

        _ivMine.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You Clicked the button!"+ String.valueOf(_col), Toast.LENGTH_LONG).show();

            }
        });



        return v;
    }

    public void linkTogether(Coordinator c) {
        _coor=c;
    }


    public void test() {
        System.out.println(String.valueOf(_row)+"   "+String.valueOf(_col));
    }
}