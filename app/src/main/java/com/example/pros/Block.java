package com.example.pros;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Block extends GameObject {

    float xTarget;

    public Block(Bitmap bitmap, int xPos, int yPos) {
        super(bitmap, xPos, yPos);
        xTarget = xPos;
    }

    @Override
    public void move() {
        if (xPos < xTarget) {
            xPos += 20;
        }
        if (xPos > xTarget) {
            xPos -= 20;
        }
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

    public void goToTarget(float x, float y) {
        xTarget = x;
    }
}
