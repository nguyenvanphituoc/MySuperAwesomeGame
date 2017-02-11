package com.example.dell.mysuperawesomegame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer mediaPlayer ;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.create(this, R.raw.wedonttalkanymore);
        mediaPlayer.setLooping(true);

        Button btnTest = (Button) findViewById(R.id.btntest);
        Button btnMrNom2d = (Button) findViewById(R.id.btnMrNom2d);
        Button btnJumper2d = (Button) findViewById(R.id.btnJumper2d);
        btnTest.setOnClickListener(this);
        btnMrNom2d.setOnClickListener(this);
        btnJumper2d.setOnClickListener(this);
        PowerManager powerManager =
                (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");

        // soundPool.play()
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btntest)
            try {
                /*
                Class.forName("com.example.dell.mysuperawesomegame.MainActivity");*/
            Class clazz = Class
                    .forName("com.badlogic.androidgames.framework.LAYOUT.AndroidBasicsStarter");
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(view.getId() == R.id.btnMrNom2d)
            try {
                /*
                Class.forName("com.example.dell.mysuperawesomegame.MainActivity");*/
                Class clazz = Class
                        .forName("com.example.dell.mysuperawesomegame.mrnom2d.MrNomGame");
                Intent intent = new Intent(this, clazz);
                startActivity(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        if(view.getId() == R.id.btnJumper2d)
            try{
                Class clazz = Class
                        .forName("com.example.dell.mysuperawesomegame.jumper.SuperJumper");
                Intent intent = new Intent(this, clazz);
                startActivity(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }
}
