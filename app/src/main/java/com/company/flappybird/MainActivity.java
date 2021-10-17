package com.company.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView bird, enemy1, enemy2, enemy3, coin, volume;
    private Button startGame;
    private Animation animation;
    private MediaPlayer mediaPlayer;
    private boolean soundOnOff = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bird = findViewById(R.id.blueBird);
        enemy1 = findViewById(R.id.beeEnemy);
        enemy2 = findViewById(R.id.blueHornedEnemy);
        enemy3 = findViewById(R.id.ufoEnemy);
        coin = findViewById(R.id.coin);
        volume = findViewById(R.id.volume);
        startGame = findViewById(R.id.startGame);

        animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_animation);
        bird.setAnimation(animation);
        enemy1.setAnimation(animation);
        enemy2.setAnimation(animation);
        enemy3.setAnimation(animation);
        coin.setAnimation(animation);



    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.bird_sounds);
        mediaPlayer.start();

        volume.setOnClickListener(v -> {
            if (soundOnOff) {
                mediaPlayer.setVolume(0,0);
                volume.setImageResource(R.drawable.ic_baseline_volume_off_24);
                soundOnOff = false;
            }
            else{
                mediaPlayer.setVolume(1,1);
                volume.setImageResource(R.drawable.ic_baseline_volume_up_24);
                soundOnOff = true;
            }
        });

        startGame.setOnClickListener(v -> {
            mediaPlayer.reset();
            volume.setImageResource(R.drawable.ic_baseline_volume_up_24);
            Intent i = new Intent(MainActivity.this, Game_Activity.class);
            startActivity(i);
        });

    }
}