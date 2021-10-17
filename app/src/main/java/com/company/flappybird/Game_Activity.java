package com.company.flappybird;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Game_Activity extends AppCompatActivity {
    private TextView scoreCount, tapToPlay;
    private ImageView oneLife, twoLives, threeLives, bird, coin1, coin2, blueHorn, ufo, bee;
    private ConstraintLayout constraintLayout;

    private boolean touchControl = false;
    private boolean beginControl = false;

    private Runnable runnable, runnable2;
    private Handler handler, handler2;

    int birdX, coin1X, coin2X, beeX, blueHornX, ufoX;
    int birdY, coin1Y, coin2Y, beeY, blueHornY, ufoY;

    int centerCoin1X, centerCoin2X, centerBeeX, centerBlueHornX, centerUfoX;
    int centerCoin1Y, centerCoin2Y, centerBeeY, centerBlueHornY, centerUfoY;

    int screenWidth;
    int screenHeight;

    int score = 0;
    int target = 500;
    int lives = 3;
    boolean collision = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        constraintLayout = findViewById(R.id.constraintLayout);
        scoreCount = findViewById(R.id.coinCount);
        tapToPlay = findViewById(R.id.tapToPlay);
        oneLife = findViewById(R.id.oneLife);
        twoLives = findViewById(R.id.twoLives);
        threeLives = findViewById(R.id.threeLives);
        bird = findViewById(R.id.mainBird);
        coin1 = findViewById(R.id.firstLayer);
        coin2 = findViewById(R.id.secondLayer);
        blueHorn = findViewById(R.id.thirdLayer);
        ufo = findViewById(R.id.fourthLayer);
        bee = findViewById(R.id.fifthLayer);

        constraintLayout.setOnTouchListener((v, event) -> {
            tapToPlay.setVisibility(View.INVISIBLE);
            setTouchControl(event);
            if (!beginControl) {
                beginControl = true;

                screenWidth = (int) constraintLayout.getWidth();
                screenHeight = (int) constraintLayout.getHeight();
                makeVisible();

                handler = new Handler();
                runnable = () -> {
                    moveTheBird();
                    moveEnemies();
                    if (score < target) {
                        handler.postDelayed(runnable, 20);
                    }
                };
                handler.post(runnable);
            }
            else {
                setTouchControl(event);
            }
            return true;
        });
//end braces of onCreate
    }

    public void setTouchControl(MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            touchControl = true;
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
            touchControl = false;
        }
    }

    public void moveTheBird() {
        birdX = (int) bird.getX();
        birdY = (int) bird.getY();

        if(touchControl) {
            birdY = birdY - (screenHeight / 50);
        }
        else {
            birdY = birdY + (screenHeight / 50);
        }
        if(birdY <= 0) {
            birdY = 0;
        }
        if(birdY >= (screenHeight - bird.getHeight())) {
            birdY = screenHeight - bird.getHeight();
        }
        bird.setY(birdY);
    }

    public void moveEnemies() {
        getPositions();

        setSpeed(coin1, 120, coin1X);
        setSpeed(coin2, 110, coin2X);
        setSpeed(blueHorn, 150, blueHornX);
        setSpeed(ufo, 140, ufoX);
        setSpeed(bee, 130, beeX);

        checkEnemyCollision(centerBlueHornX, centerBlueHornY, blueHorn);
        checkEnemyCollision(centerBeeX, centerBeeY, bee);
        checkEnemyCollision(centerUfoX, centerUfoY, ufo);
        checkCoinCollision(centerCoin1X, centerCoin1Y, coin1);
        checkCoinCollision(centerCoin2X, centerCoin2Y, coin2);
    }

    public void makeVisible() {
        coin1.setVisibility(View.VISIBLE);
        coin2.setVisibility(View.VISIBLE);
        blueHorn.setVisibility(View.VISIBLE);
        ufo.setVisibility(View.VISIBLE);
        bee.setVisibility(View.VISIBLE);
    }

    public void makeInvisible() {
        coin1.setVisibility(View.INVISIBLE);
        coin2.setVisibility(View.INVISIBLE);
        blueHorn.setVisibility(View.INVISIBLE);
        ufo.setVisibility(View.INVISIBLE);
        bee.setVisibility(View.INVISIBLE);
    }

    public void setSpeed(ImageView icon, int velocity, int iconX) {
        iconX = iconX - (screenWidth / velocity);
        if (iconX < 0) {
            iconX = screenWidth + 200;
            setHeight(icon);
        }
        icon.setX((float) iconX);
    }

    public void setHeight(ImageView icon) {
        int iconY = (int) Math.floor(Math.random() * screenHeight);
        if(iconY <= 0) {
            iconY = 0;
        }
        if(iconY >= (screenHeight - icon.getHeight())) {
            iconY = screenHeight - icon.getHeight();
        }
        icon.setY((float) iconY);
    }

    public void getPositions() {
        coin1X = (int) coin1.getX();
        coin2X = (int) coin2.getX();
        blueHornX = (int) blueHorn.getX();
        ufoX = (int) ufo.getX();
        beeX = (int) bee.getX();

        coin1Y = (int) coin1.getY();
        coin2Y = (int) coin2.getY();
        blueHornY = (int) blueHorn.getY();
        ufoY = (int) ufo.getY();
        beeY = (int) bee.getY();

        centerCoin1X = coin1X + coin1.getWidth() / 2;
        centerCoin2X = coin2X + coin2.getWidth() / 2;
        centerBeeX = beeX + bee.getWidth() / 2;
        centerBlueHornX = blueHornX + blueHorn.getWidth() / 2;
        centerUfoX = ufoX + ufo.getWidth() / 2;

        centerCoin1Y = coin1Y + coin1.getHeight() / 2;
        centerCoin2Y = coin2Y + coin2.getHeight() / 2;
        centerBeeY = beeY + bee.getHeight() / 2;
        centerBlueHornY = blueHornY + blueHorn.getHeight() / 2;
        centerUfoY = ufoY + ufo.getHeight() / 2;
    }

    public void checkCoinCollision(int centerCoinX, int centerCoinY, ImageView coin) {
        if (centerCoinX >= birdX
                && centerCoinX <= (birdX + bird.getWidth())
                && centerCoinY >= birdY
                && centerCoinY <= (birdY + bird.getHeight())) {
            coin.setX(screenWidth + 200);
            score += 10;
            scoreCount.setText(String.valueOf(score));
            checkForWin();
        }
    }

    public void checkEnemyCollision(int centerEnemyX, int centerEnemyY, ImageView enemy) {
        if (!collision && centerEnemyX >= birdX
                && centerEnemyX <= (birdX + bird.getWidth())
                && centerEnemyY >= birdY
                && centerEnemyY <= (birdY + bird.getHeight())) {
            collision = true;
            enemy.setX(screenWidth + 200);
            reduceLives();
        }
    }

    public void reduceLives() {
        if(collision && lives == 3) {
            lives--;
            threeLives.setImageResource(R.drawable.empty_heart);
            collision = false;
        }
        else if(collision && lives == 2) {
            lives--;
            twoLives.setImageResource(R.drawable.empty_heart);
            collision = false;
        }
        else if(collision && lives == 1) {
            lives--;
            oneLife.setImageResource(R.drawable.empty_heart);
            collision = false;
        }
        else {
            handler.removeCallbacks(runnable);
            gameOver();
        }
    }

    public void checkForWin() {
        if (score >= target) {
            handler.removeCallbacks(runnable);
            constraintLayout.setEnabled(false);
            makeInvisible();
            tapToPlay.setText(R.string.winningPhrase);
            tapToPlay.setClickable(false);
            tapToPlay.setVisibility(View.VISIBLE);

            handler2 = new Handler();
            runnable2 = () -> {
                birdX = birdX + (screenWidth / 200);
                bird.setX(birdX);
                birdY = screenHeight / 2;
                bird.setY(birdY);
                if (birdX > screenWidth) {
                    handler2.removeCallbacks(runnable2);
                    gameOver();
                }
                else {
                    handler2.postDelayed(runnable2, 20);
                }
            };
            handler2.post(runnable2);
        }
    }

    public void gameOver() {
        Intent i = new Intent(Game_Activity.this, Game_Over.class);
        i.putExtra("score", score);
        i.putExtra("target", target);
        startActivity(i);
        finish();
    }

//end of Activity
}