package com.example.pros;

import android.graphics.Bitmap;

public class Ball extends GameObject {

    private int lastPlayerTouched;
    private int xSpeed, ySpeed;
    public Ball(Bitmap bitmap, int xPos, int yPos, int windowWidth, int windowHeight) {
        super(bitmap, xPos, yPos, windowWidth, windowHeight);
        lastPlayerTouched = 0;
        xSpeed = 10;
        ySpeed = 10;
    }

    @Override
    public void move() {
        if(xPos > windowWidth - bitmap.getWidth() - xSpeed){
            xSpeed *= -1;
        }
        if (xPos + xSpeed < 0){
            xSpeed = 10;
        }
        xPos = xPos + xSpeed;

        if(yPos > windowHeigth - bitmap.getHeight() - ySpeed){
            ySpeed *= -1;
        }
        if (yPos + ySpeed < 0){
            ySpeed = 10;
        }
        yPos = yPos + ySpeed;
    }
}
