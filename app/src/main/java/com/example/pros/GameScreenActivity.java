package com.example.pros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

public class GameScreenActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private int windowHeight, windowWidth, chosenSkinImageId;
    private GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        frameLayout = findViewById(R.id.frameLayout_gameScreen_gameFrameLayout);
        Intent fromMainScreenIntent = getIntent();
        chosenSkinImageId = fromMainScreenIntent.getIntExtra("chosenSkinImageId", 0);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        windowHeight = frameLayout.getHeight();
        windowWidth = frameLayout.getWidth();
        gameView = new GameView(this, windowHeight, windowWidth, chosenSkinImageId);
        frameLayout.addView(gameView);
    }

//    @Override
//    public void onPause(){
//        super.onPause();
//        gameView.gameTread.interrupt();
//        finish();
//    }

//    @Override
//    public void onBackPressed() {
//
//    }
}
