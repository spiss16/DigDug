package com.mattspissell.digdug;

import android.graphics.Canvas;

/**
 * Created by benda on 12/15/2017.
 */

public class dirt extends GameView{
    public int r;
    public dirt(int x, int y)
    {
        r=5;
        super.x = x;
        super.y = y;
    }
    public void update()
    {
        x=-3;
    }
    public void draw(Canvas canvas)
    {


    }


}
