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
    private boolean _visible;  //Si retourner ou non; true visible
    private int _id;
    private int _flag;
    private int _value;  //-1 est une bombe; entre 0 et 6 le nombre de bombes a cote
    private boolean _isbomb;
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
        this._visible=false;
        this._value=-4;
        this._flag=0;

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

    //Utilise pour ma decouverte multiple
    public boolean isRetournable() {
       if(!_visible && _flag==0 ) {
           return true;
       }
       return false;
    }

    public boolean Retourner(boolean finish) {

        switch (_value) {
            case 0:
                _ivMine.setImageResource(R.drawable.hex0);
                this._visible=true;   //ne pas mettre avant, le switch ne passe pas si c'est -1 (bombe)
                return true;
            case 1:
                _ivMine.setImageResource(R.drawable.hex1);
                this._visible=true;
                return true;
            case 2:
                _ivMine.setImageResource(R.drawable.hex2);
                this._visible=true;
                return true;
            case 3:
                _ivMine.setImageResource(R.drawable.hex3);
                this._visible=true;
                return true;
            case 4:
                _ivMine.setImageResource(R.drawable.hex4);
                this._visible=true;
                return true;
            case 5:
                _ivMine.setImageResource(R.drawable.hex5);
                this._visible=true;
                return true;
            case 6:
                _ivMine.setImageResource(R.drawable.hex6);
                this._visible=true;
                return true;
            case -1:
                if(finish) {
                    _ivMine.setImageResource(R.drawable.hexbombe);
                    this._visible = true;
                }
                return false;
            default:
                System.err.println("etmerde " + String.valueOf(_id));
        }
        return false;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_hexa,container, false);

        _ivMine=v.findViewById(R.id.ivMine);
        _ivMine.setImageResource(R.drawable.hexwater);
        //Ajout d'un listener sur l'hexagone
        _bpHexa=(Button) v.findViewById(R.id.bpHexa);
        _bpHexa.setOnClickListener(this);
        return v;
    }


    //@Override
    public void onClick(View v) {
        //Ici le code quand on click sur chaque hexagone
        //System.out.println(this);
        //this.displayneighbor();
        Game activity = (Game) getActivity();
        System.out.println(activity.getStateSwitch());
        if (!_visible) {
            //si carte non visible
            if (activity.getStateSwitch()) //si vrai = on met des drapeaux;   si faux = on découvre les bombes
            {                System.out.println(_flag);
                //Ajout drapeau
                switch (_flag) {
                    case 0:
                        _ivMine.setImageResource(R.drawable.hexflag);
                        _flag = 1;
                        break;
                    case 1:
                        _ivMine.setImageResource(R.drawable.hexquestion);
                        _flag = 2;
                        break;
                    case 2:
                        _ivMine.setImageResource(R.drawable.hexwater);
                        _flag = 0;
                        break;
                }
            } else {
                //on decouvre
                System.out.println("There");
                if (this._flag == 0) {  //on verifie quil n'y a pas de drapeau ou ?
                    this._visible = true;
                    //si pas retourner on la met visible
                    //System.out.println(_value);
                    System.out.println("value : "+ String.valueOf(_value));

                    switch (_value) {
                        case 0:
                            _ivMine.setImageResource(R.drawable.hex0);
                            activity.displayblank(_id);
                            break;
                        case 1:
                            _ivMine.setImageResource(R.drawable.hex1);
                            break;
                        case 2:
                            _ivMine.setImageResource(R.drawable.hex2);
                            break;
                        case 3:
                            _ivMine.setImageResource(R.drawable.hex3);
                            break;
                        case 4:
                            _ivMine.setImageResource(R.drawable.hex4);
                            break;
                        case 5:
                            _ivMine.setImageResource(R.drawable.hex5);
                            break;
                        case 6:
                            _ivMine.setImageResource(R.drawable.hex6);
                            break;
                        case -1:
                            System.out.println("Lost");
                            activity.lost();
                            _ivMine.setImageResource(R.drawable.hexexplose);
                    }
                }
            }
        }
    }


    public void displayneighbor(){
        String s=String.valueOf(_id)+" has ";
        for(int i=0;i<neighbours.size();i++){
            s+=" "+String.valueOf(neighbours.get(i))+" ";
        }
        // System.out.println(s);
    }
    public void test() {
        System.out.println("Hexa : " + String.valueOf(_row)+"   "+String.valueOf(_col)+"    "+String.valueOf(_id));
    }
    public void setBombe() {
        this._value=-1;
    }
    public boolean isBomb()
    {
        if(_value==-1){
            return true;
        } else {
            return false;
        }
    }
    public void printbombe() {
        System.out.println("Bomb n : " + String.valueOf(_id) + "  " + String.valueOf(_row) + "   " + String.valueOf(_col) + "  val " + String.valueOf(_value)+"  flag "+String.valueOf(_flag)+" visible "+String.valueOf(_visible));
    }
    public void setNeigbour(int n) {
        _value=n;
    }

    public int get_flag() {
        return _flag;
    }

    public int get_value() {
        return _value;
    }
    public void setWrongFlag() {
        _ivMine.setImageResource(R.drawable.hex6);
    }
}