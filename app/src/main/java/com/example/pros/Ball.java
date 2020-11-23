package com.example.pros;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Ball extends GameObject {

    private int lastPlayerTouched;
    private int xSpeed, ySpeed;
    public Ball(Bitmap bitmap, int xPos, int yPos, int windowWidth, int windowHeight) {
        super(bitmap, xPos, yPos, windowWidth, windowHeight);
        lastPlayerTouched = 0;
        xSpeed = 10;
        ySpeed = 10;
    }

    public int getLastPlayerTouched() {
        return lastPlayerTouched;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setLastPlayerTouched(int lastPlayerTouched) {
        this.lastPlayerTouched = lastPlayerTouched;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    @Override
    public void move() {
//        if(xPos > windowWidth - bitmap.getWidth() - xSpeed){
//            xSpeed *= -1;
//        }
        if (xPos + xSpeed < 0){
            xSpeed = 10;
        }
        xPos = xPos + xSpeed;

//        if(yPos > windowHeight - bitmap.getHeight() - ySpeed){
//            ySpeed *= -1;
//        }
        if (yPos + ySpeed < 0){
            ySpeed = 10;
        }
        yPos = yPos + ySpeed;

        ballIfCollisionSideBoarders(windowWidth, windowHeight);
    }

    public boolean checkCollision(GameObject other) {
        int left = Math.max(xPos, other.xPos);
        int right = Math.min(xPos + bitmap.getWidth(), other.xPos + other.bitmap.getWidth());
        int top = Math.max(yPos, other.yPos);
        int bottom = Math.min(yPos + bitmap.getHeight(), other.yPos + other.bitmap.getHeight());
        for (int row = left; row < right; row++) {
            for (int col = top; col < bottom; col++) {
                if (bitmap.getPixel(row - xPos, col - yPos) != Color.TRANSPARENT &&
                        other.bitmap.getPixel(row - other.xPos, col - other.yPos) != Color.TRANSPARENT) {
                    return true;
                }
            }
        }
        return false;
    }

    public void ballIfCollisionSideBoarders(int boardWidth, int boardHeight) {

        if(xPos + bitmap.getWidth() >= boardWidth || xPos <= 0){
            xSpeed *= -1;
        }
        if(yPos + bitmap.getHeight() >= boardHeight || yPos <= 0){
            ySpeed *= -1;
        }
    }
}
