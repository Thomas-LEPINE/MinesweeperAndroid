package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

public class ScoreBoard extends AppCompatActivity {
    private Button addBtn;
    private Button retour;
    private boolean Name=true;

    private File myfile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        addBtn=findViewById(R.id.btnAddVal);
        retour=findViewById(R.id.btnretour);
/*

        String[]names=new String[]{"Settings","ScrapedTags"};
        StringWriter sw=new StringWriter();
        JsonWriter writer=new JsonWriter(sw);
        writer.setIndent("\t");
        writer.beginObject();
        for(String name:names)
            processSharedFromName(writer,context,name);
        writer.endObject();
*/
    }

    @Override
    protected void onStart() {
        super.onStart();



        addBtn.setOnClickListener(new View.OnClickListener() { // Bouton retour au menu
            @Override
            public void onClick(View v) {
                Message mes =new Message("5","uigj");
                System.out.println(mes.toJsonMsg());

                //writeJSON();
            }
        });
        retour.setOnClickListener(new View.OnClickListener() { // Bouton retour au menu
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScoreBoard.this, MainActivity.class));
            }
        });
    }

    public void writeMessage(JsonWriter writer, Message message) throws IOException {
     /*   JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.beginObject();
        writer.name("id").value(message.getId());
        writer.name("text").value(message.getText());
        /*if (message.getGeo() != null) {
            writer.name("geo");
            writeDoublesArray(writer, message.getGeo());
        } else {
            writer.name("geo").nullValue();
        }
        //writer.name("user");
        //writeUser(writer, message.getUser());
        writer.endObject();
        writer.close();*/
    }


    public void writeJSON() {
        /*myfile=new File(this.getFilesDir(),"scores");
        FileReader fielreader = null;
        FileWriter fielwriter = null;

        BufferedReader buffread = null;
        BufferedWriter buffwrite = null;

        String response =null;

        if(!myfile.exists()) {
            try {
                myfile.createNewFile();

            }
        }

        /*JSONObject object = new JSONObject();
        try {
            object.put("name", "Jack Hack");
            object.put("score", new Integer(200));
            object.put("current", new Double(152.32));
            object.put("nickname", "Hacker");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(object);*/
    }



}

class Message{
    String _id;
    String _text;

    Message(String i, String t) {
        _id=i;
        _text=t;
    }

    public String getText() {
        return  _text;
    }
    public String getId() {
        return  _id;
    }
    public String toJsonMsg() {

        return "{ \"id\":"+String.valueOf(_id)+",\n"+"\"text\":" +String.valueOf(_text)+"}";

    }


}