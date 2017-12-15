package com.mattspissell.digdug;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by benda on 12/15/2017.
 */

public class Tunnel extends GameObject {
    public int r;
    public int dx;
    public Tunnel(int x, int y)
    {
        r=75;
        super.x = x+80;
        super.y = y+75;
        dx = GameView.MOVESPEED;
    }
    public void update()
    {
        x += dx;
        if(x < -GameView.SCREEN_WIDTH){
            x = 0;
        }

    }
    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);//(Color.argb(255,244,164,96));
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(x, y, r, paint);

    }
}