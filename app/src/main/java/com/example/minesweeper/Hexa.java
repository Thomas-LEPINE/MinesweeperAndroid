package com.example.minesweeper;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Hexa extends Fragment implements View.OnClickListener {
    private int _row;
    private int _col;
    private int _state;
    private int _id;
    private int _nbbombes;
    private List<Integer> neighbours = new ArrayList<Integer>();
    private ImageView _ivMine;
    private Button _bpHexa;
    private View v;


    public Hexa() {
        // Required empty public constructor
    }

    public int get_id() {
        return _id;
    }

    public void SetHexa(int r, int c, int i) {
        this._row=r;
        this._col=c;
        this._id=i;
        computeNeighbour();
    }
    public void computeNeighbour() {
        int[] offset={-8,-7,-1,1,7,8};
        if(_row==0) {
            offset[0]=0;
            offset[1]=0;
        }
        if(_row==9) {
            offset[4]=0;
            offset[5]=0;
        }
        if(_col==0) {
            if(_row%2==0){
                offset[0]=0;
                offset[2]=0;
                offset[4]=0;
            } else {
                offset[2] = 0;
            }
        }
        if(_col==7) {
            offset[1]=0;
            offset[3]=0;
            offset[5]=0;
        }
        if(_col==6 & _row%2==1) {
            offset[3] = 0;
        }
        for(int i=0;i<6;i++) {
            if(offset[i]!=0){
                int rank=_id+offset[i];
                neighbours.add(rank);
            }
        }
    }

    public List<Integer> getNeighbours() {
        return neighbours;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_hexa,container, false);

        //Ajout d'un listener sur l'hexagone
        _bpHexa=(Button) v.findViewById(R.id.bpHexa);
        _bpHexa.setOnClickListener(this);
        return v;
    }


    //@Override
    public void onClick(View v) {
        //Ici le code quand on click sur chaque hexagone
        //System.out.println(this);
        this.displayneighbor();
    }

    public void displayneighbor(){
        String s=String.valueOf(_id)+" has ";
        for(int i=0;i<neighbours.size();i++){
            s+=" "+String.valueOf(neighbours.get(i))+" ";
        }
        System.out.println(s);
    }
    public void test() {
        System.out.println("Hexa : " + String.valueOf(_row)+"   "+String.valueOf(_col)+"    "+String.valueOf(_id));
    }
}