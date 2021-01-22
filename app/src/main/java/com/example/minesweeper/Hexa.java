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
    private int _flag;   //0 normal, 1 drapeau, 2 point d'interogation
    private int _value;  //-1 est une bombe; entre 0 et 6 le nombre de bombes a cote
    private boolean _isbomb;
    private List<Integer> neighbours = new ArrayList<Integer>();
    private ImageView _ivMine;  //Image affiche
    private Button _bpHexa;  //Bouton du fragment
    private View v;


    public Hexa() {
        // Required empty public constructor
    }

    public void SetHexa(int r, int c, int i) {
        this._row=r;
        this._col=c;
        this._id=i;
        this._visible=false;
        this._value=-4;
        this._flag=0;
        //Calcul des id des voisins
        computeNeighbour();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_hexa,container, false);

        _ivMine=v.findViewById(R.id.ivMine);
        _ivMine.setImageResource(R.drawable.hexwater);

        _bpHexa=(Button) v.findViewById(R.id.bpHexa);
        _bpHexa.setOnClickListener(this);
        return v;
    }

    //@Override
    public void onClick(View v) {
        //Recupere l'activity game
        Game activity = (Game) getActivity();
        //si carte non visible/retourner
        if (!_visible) {
            if (activity.getStateSwitch()) //si vrai = on met des drapeaux;   si faux = on découvre les bombes
            {
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
                if (this._flag == 0) {  //on verifie quil n'y a pas de drapeau ou ?
                    //si pas retourner on la met visible
                    this._visible = true;
                    //Dminue le nombre de cases restantes à découvrir
                    activity.minusnbhexleft();
                    //En fonction du nombre de bombe à coté, on met l'image en correspondance
                    switch (_value) {
                        case 0:
                            _ivMine.setImageResource(R.drawable.hex0);
                            //Si pas de bombe voisine, on lance la decouverte des autres cases vides
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
                            //Si _value=-1 alors c'est une bombe, on déclanche le défaite
                            activity.lost();
                            _ivMine.setImageResource(R.drawable.hexexplose);
                    }
                }
            }
        }
    }

    //Retourne si c'est une bombe
    public boolean isBomb()
    {
        if(_value==-1){
            return true;
        } else {
            return false;
        }
    }

    //Utilise pour ma decouverte multiple
    public boolean isRetournable() {
        //Si non visble et que c'est une case vide
        if(!_visible && _flag==0 ) {
            return true;
        }
        return false;
    }

    //Retourne la liste des ID des voisins
    public List<Integer> getNeighbours() {
        return neighbours;
    }

    public int get_flag() {
        return _flag;
    }

    public int get_value() {
        return _value;
    }

    public void setWrongFlag() {
        _ivMine.setImageResource(R.drawable.hexwrongflag);
    }

    public void setBombe() {
        this._value=-1;
    }

    public void setNeigbour(int n) {
        _value=n;
    }

    //Calcul des id des voisins
    public void computeNeighbour() {
        //offset est le tableau des voisins possible, un fragment a 6 voisins possile. En fonction de chaque id de chaque fragment, il faut additioner l'id avec l'offset our avoir les voisins
        //ensuite on verifie chaque conditions pour que l'id calculé soit valides
        int[] offset={-8,-7,-1,1,7,8};
        //Si fragment sur ligne du haut
        if(_row==0) {
            offset[0]=0;
            offset[1]=0;
        }
        //Si fragment sur ligne du bas
        if(_row==9) {
            offset[4]=0;
            offset[5]=0;
        }
        //Si fragment sur le bords gauche
        if(_col==0) {
            //Si fragment sur le bords gauche "interieur"
            if(_row%2==0){
                offset[0]=0;
                offset[2]=0;
                offset[4]=0;
            } else { //Si fragment sur le bords gauche "extérieur"
                offset[2] = 0;
            }
        }
        //Si fragment sur le bords droit "interieur"
        if(_col==7) {
            offset[1]=0;
            offset[3]=0;
            offset[5]=0;
        }
        //Si fragment sur le bords doit "exterieur" et sur une ligne ou il y a 7 bombe, pas 8
        if(_col==6 & _row%2==1) {
            offset[3] = 0;
        }
        //On parcours toutes la listes des offset possible
        for(int i=0;i<6;i++) {
            //Si l'offset n'as pas ete mis a 0
            if(offset[i]!=0){
                //On calcul l'id et l'ajoute en voisin
                int rank=_id+offset[i];
                neighbours.add(rank);
            }
        }
    }

    //Dans le cas des découvertes de multiples case
    //Si finish, alors on retourne tous
    //Sinon retourne les autres sasn declancher la decouverte multiple
    //La fonction return false si il ne faut pas reexecuter la fonction "displaybalnk"
    public boolean Retourner(boolean finish) {
        Game activity = (Game) getActivity();
        switch (_value) {
            case 0:
                _ivMine.setImageResource(R.drawable.hex0);
                activity.minusnbhexleft();
                this._visible=true;   //ne pas mettre avant, le switch ne passe pas si c'est -1 (bombe)
                return true;
            case 1:
                _ivMine.setImageResource(R.drawable.hex1);
                activity.minusnbhexleft();
                this._visible=true;
                return true;
            case 2:
                _ivMine.setImageResource(R.drawable.hex2);
                activity.minusnbhexleft();
                this._visible=true;
                return true;
            case 3:
                _ivMine.setImageResource(R.drawable.hex3);
                activity.minusnbhexleft();
                this._visible=true;
                return true;
            case 4:
                _ivMine.setImageResource(R.drawable.hex4);
                activity.minusnbhexleft();
                this._visible=true;
                return true;
            case 5:
                _ivMine.setImageResource(R.drawable.hex5);
                activity.minusnbhexleft();
                this._visible=true;
                return true;
            case 6:
                _ivMine.setImageResource(R.drawable.hex6);
                activity.minusnbhexleft();
                this._visible=true;
                return true;
            case -1:
                if(finish) {
                    _ivMine.setImageResource(R.drawable.hexbombe);
                    this._visible = true;
                }
                return false;
            default:
                break;
        }
        return false;
    }
}