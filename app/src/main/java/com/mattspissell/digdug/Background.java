package com.mattspissell.digdug;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Matt on 12/13/2017.
 */

public class Background {

    private Bitmap image;
    private int x,y,dx;

    public Background(Bitmap res){
        image = res;
        dx = GameView.MOVESPEED;
    }

    //resets the background when cucled through once
    public void update(){
        x += dx;
        if(x < -GameView.SCREEN_WIDTH){
            x = 0;
        }
    }

    //draws background to create scrolling effect
    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
        if(x < 0){
            canvas.drawBitmap(image, x + GameView.SCREEN_WIDTH, y,null);
        }
    }

}
