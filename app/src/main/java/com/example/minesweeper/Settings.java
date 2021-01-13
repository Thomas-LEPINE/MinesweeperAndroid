package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    private Button btnChangeUsername;
    private Button btnBackToMenu;
    private Dialog popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btnChangeUsername = (Button) findViewById(R.id.btnChangeUsername);
        btnBackToMenu = (Button) findViewById(R.id.btnSettingsBackMenu);
        popup=new Dialog(this);

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
                startActivity(new Intent(Settings.this, MainActivity.class));

            }
        });

    }
    private void ShowUsernamePopup()
    {
        final EditText etUsername;
        Button btnBackToSettings;
        Button btnValidateUsername;
        final SharedPreferences myPreference ;
        final SharedPreferences.Editor myEditor;

        popup.setContentView(R.layout.popup_change_username);
        btnBackToSettings = (Button) popup.findViewById(R.id.btnUsernameBackSettings);
        btnValidateUsername = (Button) popup.findViewById(R.id.btnValidateUsername);
        etUsername = (EditText) popup.findViewById(R.id.etChangeUsername);
        myPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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

                //Sauvegarde du nouveau username
                myEditor.putString("username", etUsername.getText().toString());
                myEditor.apply();
                popup.cancel();

            }
        });
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.show();//affichage de la popup

    }
}