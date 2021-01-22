package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class Game extends AppCompatActivity {

    private Button btnBackMenu = null;
    TextView tvTimer;

    /* attributs */
    private int countTimer =0;
    private int nbbomb = 75;
    private int lenrow=8;
    private int nbhexleft;
    private List<Hexa> bomblist = new ArrayList<Hexa>();
    private Dialog popup;
    private Game.Timer timer;
    private Boolean gameFinished=false;
    private Boolean isWin=true;
    private Integer timeToPlay=0;
    private SharedPreferences myPreference ;
    private SharedPreferences.Editor myEditor;
    private SharedPreferences prefscores;
    private SharedPreferences.Editor editorscore;


    private ImageView ivtest=null;

    /* ###### */

    private Switch swMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        btnBackMenu=findViewById(R.id.btnBackMenu);
        tvTimer = findViewById(R.id.tvTimer);
        swMode=findViewById(R.id.swMode);

        myPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        myEditor = myPreference.edit();


        popup=new Dialog(this);
        timer=new Game.Timer();
        timer.execute(timeToPlay);

        int difficultyNbBombes;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                difficultyNbBombes = 10; // Valeur par défaut
            } else {
                difficultyNbBombes = extras.getInt("difficultyNbBombes", 7 /* Valeur par défault*/);
            }
        } else {
            difficultyNbBombes = (int) savedInstanceState.getSerializable("difficultyNbBombes");
        }

        //Calcul des numero de ligne et colonne et des id
        int numcol=0;
        int numrow=0;
        for( int i =0; i<nbbomb;i++) {
            if (numrow == lenrow) {
                numrow = 0;
                numcol += 1;
                if (lenrow == 8) {
                    lenrow = 7;
                } else {
                    lenrow = 8;
                }
            }
            //Recuperation du nom du fragment ex : frag12
            String idtemp = "frag" + String.valueOf(i);
            int intidtemp = getResources().getIdentifier(idtemp, "id", getPackageName());
            Hexa hextemp = (Hexa) getSupportFragmentManager().findFragmentById(intidtemp);
            hextemp.SetHexa(numcol, numrow, i);
            //Tous les fragments sont stockés dans cette liste
            bomblist.add(hextemp);
            numrow += 1;
        }
        //nombre de case restante a découvrir
        nbhexleft=bomblist.size()-difficultyNbBombes-1;
        generatebombes(difficultyNbBombes);
        computeneighbourbomb();
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

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onDestroy()
    {
        timer.cancel(true);
        super.onDestroy();
    }

    //Recupere l'état du switch mode, soit en mode decouverte soit en mode drapeau
    public boolean getStateSwitch() {
       return swMode.isChecked();
    }

    //Generation aléatoire des bombes
    public void generatebombes(int n) {
        int generated=0;
        Random r = new Random();
        while(generated<n){
            int temp = r.nextInt(nbbomb);
            if(!bomblist.get(temp).isBomb()){
                generated+=1;
                bomblist.get(temp).setBombe();
            }
        }
    }

    //Calcul pour chaque fragment, le nombre de bombe avoisinante
    public void computeneighbourbomb() {
        List<Integer> listtemp;
        for(int i=0;i<nbbomb;i++) {
            if (!bomblist.get(i).isBomb()) {
                listtemp = bomblist.get(i).getNeighbours();
                int c = 0;
                for (int j = 0; j < listtemp.size(); j++) {
                    if (bomblist.get(listtemp.get(j)).isBomb()) {
                        c++;
                    }
                }
                bomblist.get(i).setNeigbour(c);
            }
        }
    }

    //Retourner l'ensemble des fragments n'ayant pas de voisin en bombes, ou les fragments ayant pour voisin un fragment n'ayant un fragment n'ayant pas de bombe en voisin
    //On commence sur le fragment clické, et on retourne ses voisins si il respect les conditions
    //Si le voisin est retourné, alors on fait la même chose a ses voisins (appel récursif)
    public void displayblank(int id) {
        List<Integer> listtemp;
        listtemp = bomblist.get(id).getNeighbours();
        for(int i=0;i<listtemp.size();i++) {
            if(bomblist.get(listtemp.get(i)).isRetournable() && bomblist.get(listtemp.get(i)).Retourner(false) && bomblist.get(listtemp.get(i)).get_value()==0 ) {
                displayblank(listtemp.get(i));
            }
        }
    }

    public void lost() {
        //On retourne tous les fragments
        for(int i=0;i<bomblist.size();i++) {
            if((bomblist.get(i).get_flag()!=0 && bomblist.get(i).get_value()!=-1) ) {
                //Si un drapeau est placé alors que ce n'est pas une bombe
                bomblist.get(i).setWrongFlag();
            } else {
                bomblist.get(i).Retourner(true);
            }
        }
        //Fin de partie
        isWin=false;
        gameFinished=true;
    }

    //A chasue case découverte,diminution du nombre de case restant a découvrire
    public void minusnbhexleft() {
        if(nbhexleft>=1) {
            nbhexleft--;
        } else {
            //Si plus de cases a découvrir, alors vistoire
            isWin=true;
            gameFinished=true;
        }
    }

    public void ShowResultPopup(boolean win, int timeToPlay)
    {
        /* COMPOSANTS */
        TextView tvResult;
        TextView tvScore;
        ImageView ivResult;
        Button btnResultBackMenu;

        popup.setContentView(R.layout.popup_result);
        btnResultBackMenu = (Button) popup.findViewById(R.id.btnResultBackMenu);
        tvResult = (TextView) popup.findViewById(R.id.tvResult);
        tvScore = (TextView) popup.findViewById(R.id.tvScore);
        ivResult=(ImageView) popup.findViewById(R.id.ivResult);

        timer.cancel(true);//arrêt du timer

        if(win ==true) {//si le joueur à gagner la partie
            //Recherche des meilleur score et du pseudo du joueur qui l'a fait;
            String bestUsername = myPreference.getString("bestUsername", "Guest");
            Integer bestScore = myPreference.getInt("bestScore",timeToPlay);
            //Recherche des meilleur score et du pseudo du joueur qui l'a fait
            SharedPreferences sharedPreferences = getDefaultSharedPreferences(getApplicationContext());

            tvResult.setText("GAGNE");
            if(bestScore<timeToPlay)//Comparaison du meilleur score avec le score du joueur
            {
                ivResult.setImageResource(R.drawable.fireworks);
                tvScore.setText("Le meilleur score de "+Integer.toString(bestScore)+" s est détenu par "+bestUsername);
            }
            else
            {
                String username = myPreference.getString("username", "Guest");
                myEditor = myPreference.edit();
                myEditor.putString("bestUsername", username);
                myEditor.putInt("bestScore", timeToPlay);
                myEditor.apply();
                ivResult.setImageResource(R.drawable.winner);
                tvScore.setText("Bravo "+ username+" avec "+Integer.toString(timeToPlay)+" s tu as fait le meilleur score.");
            }
            prefscores = getSharedPreferences("MY_PREFS_NAME",MODE_PRIVATE);
            editorscore = prefscores.edit();

            //Ajout du score dans 'historique
            Gson gson = new Gson();
            //Recuperation des scores précédents
            String json = prefscores.getString("ListScores","empty");
            List<Scores> list =null;
            //Si la liste est vide ou la récupère, sinon on la créer
            if(json!="empty") {
                 list = gson.fromJson(json, new TypeToken<ArrayList<Scores>>() {}.getType());

            } else {
                list = new ArrayList<Scores>();
            }
            //création du nouveau Scores
            Scores newscore = new Scores(myPreference.getString("username", "Guest"),timeToPlay);
            list.add(newscore);
            //Trie en fonction du temps
            Collections.sort(list, new ScoresTempsComparator());
            //Ajout dans les sharedpreferences
            String jsonwriter = gson.toJson(list);
            editorscore.putString("ListScores",jsonwriter);
            editorscore.apply();
        }
        else
        {
            tvResult.setText("PERDU");
            tvScore.setText("Dommage... Essaie encore !!! ");
            ivResult.setImageResource(R.drawable.explosion);
        }
        btnResultBackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Game.this, MainActivity.class);  //Lancer l'activité MainActivity
                startActivity(intent);    //Afficher la vue
            }
        });
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.show();//affichage de la popup
    }

    //Timer
    private class Timer extends AsyncTask< Integer, Integer, Integer> {
        private int countTimer;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            countTimer=0;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            countTimer=integers[0];
            while(gameFinished==false) {
                try {
                    Thread.sleep(1000);// 1000ms = 1s
                    countTimer++;
                    publishProgress(countTimer);
                    if (isCancelled()) {
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return countTimer;
        }

        protected void onProgressUpdate(Integer...values) {
            super.onProgressUpdate(values);
            tvTimer.setText(Integer.toString(values[0]));
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            ShowResultPopup(isWin, result);//Affichage de la popup
        }
    }
}

