package com.mattspissell.digdug;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by benda on 12/16/2017.
 */

//Animation for the Dragon, similar to Player animation

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

        speed = 50;

        //cap the monster speed in case we decided to accelerate as the time goes on
        if(speed>150)speed=150;

        Bitmap[] image = new Bitmap[numframes];

        spritesheet = res;

        //determine which frames are being used
        for (int i = 0; i < image.length; i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet, (i+8)* width, 0, width, height);
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
