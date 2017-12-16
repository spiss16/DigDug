package com.mattspissell.digdug;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by benda on 12/16/2017.
 */

public class Monsters extends GameObject{

    private int score;
    private int speed;
    private Random rand = new Random();
    private Animation animation = new Animation();
    private Bitmap spritesheet;


    public Monsters(Bitmap res, int x, int y, int w, int h, int s, int numframes)
    {
        super.x = x;
        super.y = y;

        width = w;
        height = h;

        speed = 7 + (int) (rand.nextDouble()*score/30);

        //cap the monster speed
        if(speed>40)speed=40;

        Bitmap[] image = new Bitmap[numframes];

        spritesheet = res;

        for (int i = 0; i < image.length; i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet, i* width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(100-speed);

    }
    public void update()
    {
        x-=speed;
        animation.update();

    }
    public void draw(Canvas canvas)
    {
        try {
            canvas.drawBitmap(animation.getImage(), x, y, null);
        }catch(Exception e){}

    }
    @Override
    public int getWidth()
    {
        return width-10;
    }

}
