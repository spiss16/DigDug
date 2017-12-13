package com.mattspissell.digdug;

import android.graphics.Canvas;

/**
 * Created by Jessica on 12/13/2017.
 */

public abstract class GameObject {

    public abstract void init();
    public abstract void onUpdate();
    public abstract void onDraw(Canvas canvas);
}
