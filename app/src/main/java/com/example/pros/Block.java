package com.example.pros;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Block extends GameObject {

    float xTarget;

    public Block(Bitmap bitmap, int xPos, int yPos, int windowWidth, int windowHeight) {
        super(bitmap, xPos, yPos, windowWidth, windowHeight);
        xTarget = xPos;
    }

    @Override
    public void move() {
        if (xPos < xTarget) {
            for(int i = 0; i < 20; i++){//כדי שאם יש לחיצה ארוכה הוא יזוז בצעד צעד עד שהוא לא יכול יותר. אם זה זז 20 20 אז הוא לא יסכים לזוז אם הוא נגיד רחוק 18 מהמסגרת
                if(xPos + bitmap.getWidth() < windowWidth){
                    xPos += 1;
                }
            }
        }
        if (xPos > xTarget) {
            for(int i = 0; i < 20; i++){//כדי שאם יש לחיצה ארוכה הוא יזוז בצעד צעד עד שהוא לא יכול יותר. אם זה זז 20 20 אז הוא לא יסכים לזוז אם הוא נגיד רחוק 18 מהמסגרת
                if(xPos > 0){
                    xPos -= 1;
                }
            }
        }
    }

    public boolean checkCollision(GameObject other) {//לנסות להחזיר כאן מערך שמחזיר את האיקס וואי של מיקום הפגיעה ואז לעשות במהלך המשחק בדיקה אצל הבלוק אם הכדור פגע בצד הימני או השמאלי שלו ואז שהכדור יזוז בהתאם
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
