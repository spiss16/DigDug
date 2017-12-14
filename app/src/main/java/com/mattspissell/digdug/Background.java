package com.mattspissell.digdug;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Matt on 12/13/2017.
 */

public class Background {

    private Bitmap image;
    private int x,y;

    public Background(Bitmap res){
        image = res;
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
    }
}
