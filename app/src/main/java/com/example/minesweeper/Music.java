package com.example.minesweeper;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class Music extends Service {
    /* COMPOSANTS */
    private MediaPlayer mp;
    private final IBinder mBinder=new MyServiceBinder();
    public static Boolean serviceIsRunning = false;

    //Methode permettant au client d'intéragir avec le service
    public class MyServiceBinder extends Binder{
        public Music getService()
        {return Music.this;}
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    @Override
    public void onCreate (){
        super.onCreate();
        //Préparation de la musique
        mp = MediaPlayer.create(this, R.raw.music1);
        if(mp!= null) {
            mp.setLooping(true);//musique jouée en boucle
            mp.setVolume(100, 100);
        }
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId)
    {
        serviceIsRunning=true;
        mp.start();
        return START_NOT_STICKY;//Ne pas relancer le service une fois kill
    }

    public void pauseMusic()
    {
        if(mp.isPlaying())
        {
            mp.pause();
        }
    }

    public void resumeMusic()
    {
        if(!mp.isPlaying())
        {
            mp.start();
        }
    }

    public Boolean isMusicPlaying()
    {
        return mp.isPlaying();
    }

    @Override
    public void onDestroy ()
    {
        super.onDestroy();
        if(mp != null)
        {
            try{
                serviceIsRunning=false;
                mp.stop();
                mp.release();
            }finally {
                mp = null;
            }
        }
    }
}
