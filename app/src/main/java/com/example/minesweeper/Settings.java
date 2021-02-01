package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class Settings extends AppCompatActivity {
    /* COMPOSANTS */
    private Button btnChangeUsername;
    private Button btnBackToMenu;
    private Button btnOnOffMusic;
    private Dialog popup;
    private String btnMusicString;
    private Music musicThread;
    protected Boolean musicIsOn;
    private SharedPreferences myPreference ;
    private SharedPreferences.Editor myEditor;

    private ServiceConnection mServiceCon=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            Music.MyServiceBinder myServiceBinder= (Music.MyServiceBinder) iBinder;
            musicThread = myServiceBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicThread=null;
        }
    };
    void doBindService(){
        bindService(new Intent(this,Music.class),
                mServiceCon, Context.BIND_AUTO_CREATE);
    }
    void doUnbindService()
    {
        if(musicIsOn)
        {
            unbindService(mServiceCon);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btnChangeUsername = (Button) findViewById(R.id.btnChangeUsername);
        btnBackToMenu = (Button) findViewById(R.id.btnSettingsBackMenu);
        btnOnOffMusic = (Button) findViewById(R.id.btnOnOffMusic);
        popup = new Dialog(this);
        myPreference = getDefaultSharedPreferences(getApplicationContext());
        myEditor = myPreference.edit();
        //Connection au service Music
        doBindService();
        //Récupération des variables permettant de définir l'état du bouton et si la musique est on ou off
        btnMusicString = myPreference.getString("btnMusicString", "Musique OFF");
        musicIsOn = myPreference.getBoolean("musicIsOn", true);
        btnOnOffMusic.setText(btnMusicString);
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnChangeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowUsernamePopup();
            }
        });
        btnBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sauvegarde de l'état du bouton et de la musique
                myEditor.putBoolean("musicIsOn", musicIsOn);
                myEditor.putString("btnMusicString", btnMusicString);
                myEditor.apply();
                startActivity(new Intent(Settings.this, MainActivity.class));
            }
        });
        btnOnOffMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicIsOn = musicThread.isMusicPlaying();
                //vérifie si la musique est éteinte ou allumé et changement d'état du bouton en fonction
                    if (musicIsOn==true) {
                        btnMusicString = "Musique ON";
                        musicThread.pauseMusic();
                        btnOnOffMusic.setText(btnMusicString);
                    } else {
                        btnMusicString = "Musique OFF";
                        musicThread.resumeMusic();
                        btnOnOffMusic.setText(btnMusicString);
                    }
            }
        });
    }

    private void ShowUsernamePopup()
    {
        /* COMPOSANTS */
        final EditText etUsername;
        Button btnBackToSettings;
        Button btnValidateUsername;
        final SharedPreferences myPreference ;
        final SharedPreferences.Editor myEditor;

        popup.setContentView(R.layout.popup_change_username);
        btnBackToSettings = (Button) popup.findViewById(R.id.btnUsernameBackSettings);
        btnValidateUsername = (Button) popup.findViewById(R.id.btnValidateUsername);
        etUsername = (EditText) popup.findViewById(R.id.etChangeUsername);
        myPreference = getDefaultSharedPreferences(getApplicationContext());
        myEditor = myPreference.edit();
        //Récupération de l'username et affichage dans l'editText
        String username = myPreference.getString("username", "Guest");
        etUsername.setText(username);

        btnBackToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.cancel();
            }
        });

        btnValidateUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        // Sauvegarde du nouveau username
        myEditor.putString("username", etUsername.getText().toString());
        myEditor.apply();
        popup.cancel();
            }
        });

        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.show();//affichage de la popup

    }
}