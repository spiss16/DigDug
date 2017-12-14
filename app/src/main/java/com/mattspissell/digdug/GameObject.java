package com.mattspissell.digdug;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Jessica on 12/13/2017.
 */

public abstract class GameObject {

    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected int width;
    protected int height;

    // public abstract void init();
    // public abstract void onUpdate();
    // public abstract void onDraw(Canvas canvas);


    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public Rect getRectangle(){
        return new Rect(x,y, x+width, y+height);
    }
}
