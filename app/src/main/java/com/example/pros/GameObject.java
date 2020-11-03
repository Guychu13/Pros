package com.example.pros;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class GameObject {

    protected Bitmap bitmap;
    protected int xPos, yPos;

    public GameObject(Bitmap bitmap, int xPos, int yPos) {
        this.bitmap = bitmap;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        if(canvas != null){
            canvas.drawBitmap(bitmap, xPos, yPos, paint);
        }
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
