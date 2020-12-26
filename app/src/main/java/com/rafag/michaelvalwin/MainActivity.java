package com.rafag.michaelvalwin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Context context = MainActivity.this;

        // En plan rápido, a capón todo..., lista de audios
        List<Integer> soundList = new ArrayList<>();
        soundList.add(R.raw.aurum);
        soundList.add(R.raw.fer);
        soundList.add(R.raw.jukotsu);
        soundList.add(R.raw.mario);
        soundList.add(R.raw.nao);
        soundList.add(R.raw.patt);
        Collections.shuffle(soundList);
        final int[] cuenta = {0};

        // Botón a pulsar para cambiar de audio
        final ImageButton bt = findViewById(R.id.imageButton);
        final DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        final MediaPlayer[] mp = {MediaPlayer.create(this, R.raw.aurum)};

        bt.setOnClickListener(v -> {
            if (mp[0].isPlaying()) {
                mp[0].stop();
            }
            // Sonidos aleatorios excepto el mío, que siempre será el último
            int sound ;
            if (cuenta[0] == soundList.size() ) {
                sound = R.raw.zilog;
            } else if (cuenta[0] > soundList.size() ) {
                Collections.shuffle(soundList);
                cuenta[0] = 0;
                sound = soundList.get(cuenta[0]);
            } else {
                sound = soundList.get(cuenta[0]);
            }
            cuenta[0]++;

            mp[0] = MediaPlayer.create(context, sound);
            mp[0].start();

            // Cambiamos de posición, teniendo en cuenta el tamaño de la imagen (creo)
            final float btWidth = bt.getWidth();
            final float btHeight = bt.getHeight();
            Random R = new Random();
            float dx = R.nextFloat() * displaymetrics.widthPixels ;
            if (dx > btWidth ) { dx = dx - btWidth;}
            float dy = R.nextFloat() * displaymetrics.heightPixels;
            if (dy > btHeight ) { dy = dy - btHeight;}
            bt.animate()
                    .x(dx)
                    .y(dy)
                    // Vamos a tardar en movernos tanto como el audio a reproducir :)
                    .setDuration(mp[0].getDuration())
                    .start();
        });

        // Botón invisible acerca De...
        Button acercaDe = findViewById(R.id.button);
        acercaDe.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Este tributo a Michael Valwin ha sido posible gracias a la colaboración de:" + "\n"
                    + "- Aurum" + "\n"
                    + "- FernandoEsra" + "\n"
                    + "- Jukotsu" + "\n"
                    + "- Knoss" + "\n"
                    + "- Naossus" + "\n"
                    + "- Luzerrante" )
                    .setCancelable(false)
                    .setPositiveButton("Michear", (dialog, id) -> {});
            AlertDialog alert = builder.create();
            alert.show();
        });
        // Recordatorio final: ¡Que basura de código!, eliminar el proyecto una vez publicado para
        // que no quede resto de que esta mierda la he hecho yo...
    }
}