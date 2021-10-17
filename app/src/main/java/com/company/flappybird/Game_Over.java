package com.company.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Game_Over extends AppCompatActivity {
    TextView resultText, finalScore, scoreValue;
    Button playAgain;
    Intent i;
    int score;
    int target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        resultText = findViewById(R.id.resultText);
        finalScore = findViewById(R.id.finalScore);
        scoreValue = findViewById(R.id.scoreValue);
        playAgain = findViewById(R.id.playAgain);

        i = getIntent();
        score = i.getIntExtra("score", 0);
        target = i.getIntExtra("target", 0);

        scoreValue.setText(String.valueOf(score));

        if (score >= target) {
            resultText.setText(R.string.winningPhrase);
        }
        else {
            resultText.setText(R.string.losingPhrase);
        }

        playAgain.setOnClickListener(v -> {
            Intent intent = new Intent(Game_Over.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}