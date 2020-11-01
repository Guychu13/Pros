package com.example.pros;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class GameObject {

    protected Bitmap bitmap;
    protected int xPos, yPos;

    public GameObject(Bitmap bitmap, int xPos, int yPos) {
        this.bitmap = bitmap;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, xPos, yPos, null);
    }

    public abstract void move();

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }
}
