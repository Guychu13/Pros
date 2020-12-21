package com.example.pros;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class GameView extends SurfaceView implements Runnable{

    protected Thread gameTread;
    private MyBlock myBlock;
    private EnemyCpuBlock enemyCpuBlock;
    private Ball gameBall;
    private Background gameBackground;
    private GameScreenActivity.ScoreHandler scoreHandler;
    public GameView(Context context, int windowHeight, int windowWidth, int myPlayerSkinImageID, GameScreenActivity.ScoreHandler scoreHandler) {
        super(context);

        this.scoreHandler = scoreHandler;
        gameTread = new Thread(this);
        Bitmap myBlockBitmap = BitmapFactory.decodeResource(getResources(), myPlayerSkinImageID);
        myBlockBitmap = Bitmap.createScaledBitmap(myBlockBitmap, 250, 50, false);

        Bitmap enemyCpuBlockBitmap = BitmapFactory.decodeResource(getResources(), myPlayerSkinImageID);
        enemyCpuBlockBitmap = Bitmap.createScaledBitmap(enemyCpuBlockBitmap, 250, 50, false);

        Bitmap gameBallBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        gameBallBitmap = Bitmap.createScaledBitmap(gameBallBitmap, 70, 70, false);

        Bitmap backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.game_background_black);
        backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap, windowWidth, windowHeight, false);

        myBlock = new MyBlock(myBlockBitmap, (int)(windowWidth * 0.5) - (myBlockBitmap.getWidth() / 2) , (int)(windowHeight * 0.9), windowWidth, windowHeight);
        enemyCpuBlock = new EnemyCpuBlock(myBlockBitmap, (int)(windowWidth * 0.5) - (myBlockBitmap.getWidth() / 2) , (int)(windowHeight * 0.1), windowWidth, windowHeight);
        gameBall = new Ball(gameBallBitmap, (int)(windowWidth * 0.5) - (gameBallBitmap.getWidth() / 2), (int)(windowHeight * 0.5) - (gameBallBitmap.getHeight() / 2), windowWidth, windowHeight);
        gameBackground = new Background(backgroundBitmap, 0, 0, windowWidth, windowHeight);
        gameTread.start();

    }


    @Override
    public void run() {

        drawSurface();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                resetGame();
                giveBallInitialSpeed();
            }
        }, 2000);
//        resetGame();
//        giveBallInitialSpeed();
        while (true){
            if(gameBall.checkCollision(myBlock) || gameBall.checkCollision(enemyCpuBlock)){
                gameBall.setySpeed(gameBall.getySpeed() * -1);
//                int[] collisionLocation = myBlock.getCollisionLocation(gameBall);
//                int xCollision = collisionLocation[0];
//                gameBall.setxSpeed(gameBall.getxSpeed() * -1);
            }
            if(gameBall.whoScored() == 1 || gameBall.whoScored() == 2){
                Message goalMessage = scoreHandler.obtainMessage();
                if(gameBall.whoScored() == 1){
                    myBlock.setScore(myBlock.getScore() + 1);
                }
                if (gameBall.whoScored() == 2){
                    enemyCpuBlock.setScore(enemyCpuBlock.getScore() + 1);
                }
                resetGame();
                goalMessage.getData().putString("score_string", "" + myBlock.getScore() + "-" + enemyCpuBlock.getScore());
                scoreHandler.sendMessage(goalMessage);
                drawSurface();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            move();
            drawSurface();
        }
    }

    private void drawSurface() {
        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            if(canvas != null){
                gameBackground.draw(canvas);
                myBlock.draw(canvas);
                enemyCpuBlock.draw(canvas);
                gameBall.draw(canvas);
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }


    private void move() {
        myBlock.move();
        enemyCpuBlock.move(gameBall.getXPos());
        gameBall.move();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        myBlock.goToTarget(event.getX(), event.getY());
        switch (event.getAction() & MotionEvent.ACTION_MASK){

            case MotionEvent.ACTION_UP:
                myBlock.goToTarget(myBlock.xPos, myBlock.yPos);
                break;
        }
        return true;
    }

    public void resetGame(){
        gameBall.setXPos((int)(gameBall.getWindowWidth() * 0.5) - (gameBall.getBitmap().getWidth() / 2));
        gameBall.setYPos((int)(gameBall.getWindowHeight() * 0.5) - (gameBall.getBitmap().getHeight() / 2));
        myBlock.setXPos((int)(myBlock.getWindowWidth() * 0.5) - (myBlock.getBitmap().getWidth() / 2));
        enemyCpuBlock.setXPos((int)(enemyCpuBlock.getWindowWidth() * 0.5) - (enemyCpuBlock.getBitmap().getWidth() / 2));
        drawSurface();
    }

    public void giveBallInitialSpeed(){
        gameBall.setxSpeed(10);
        gameBall.setySpeed(10);
    }
}
