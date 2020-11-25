package com.example.pros;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable{

    protected Thread gameTread;
    private Block myBlock;
    private Ball gameBall;
    private Background gameBackground;
    public GameView(Context context, int windowHeight, int windowWidth, int myPlayerSkinImageID) {
        super(context);

        gameTread = new Thread(this);
        Bitmap myBlockBitmap = BitmapFactory.decodeResource(getResources(), myPlayerSkinImageID);
        myBlockBitmap = Bitmap.createScaledBitmap(myBlockBitmap, 250, 50, false);

        Bitmap gameBallBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        gameBallBitmap = Bitmap.createScaledBitmap(gameBallBitmap, 70, 70, false);

        Bitmap backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.game_background_black);
        backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap, windowWidth, windowHeight, false);

        myBlock = new Block(myBlockBitmap, (int)(windowWidth * 0.5) - (myBlockBitmap.getWidth() / 2) , (int)(windowHeight * 0.9), windowWidth, windowHeight);
        gameBall = new Ball(gameBallBitmap, (int)(windowWidth * 0.5) - (myBlockBitmap.getWidth() / 2), (int)(windowHeight * 0.5) - (gameBallBitmap.getHeight() / 2), windowWidth, windowHeight);
        gameBackground = new Background(backgroundBitmap, 0, 0, windowWidth, windowHeight);

        gameTread.start();
    }

    @Override
    public void run() {
        while (true){
            drawSurface();
            move();
            if(gameBall.checkCollision(myBlock)){
                gameBall.setySpeed(gameBall.getySpeed() * -1);
                int[] collisionLocation = myBlock.getCollisionLocation(gameBall);
                int xCollision = collisionLocation[0];
                if(xCollision <= myBlock.getXPos() + myBlock.getBitmap().getWidth()
                        && xCollision > myBlock.getXPos() + myBlock.getBitmap().getWidth() - (myBlock.getXPos() + (myBlock.getBitmap().getWidth() / 10))
                ||
                        (xCollision > myBlock.getXPos() && xCollision < myBlock.getBitmap().getWidth() / 10)){
                    gameBall.setxSpeed(gameBall.getxSpeed() * -1);
                }
            }
        }
    }

    private void drawSurface() {
        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            if(canvas != null){
                gameBackground.draw(canvas);
                myBlock.draw(canvas);
                gameBall.draw(canvas);
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }


    private void move() {
        myBlock.move();
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
}
