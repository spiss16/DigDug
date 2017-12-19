package com.mattspissell.digdug;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by benda on 12/17/2017.
 */

//Same as Monster class, but for the Goggles enemies

    public class Monsters2 extends GameObject{

        private int speed;
        private Player player;
        private Random rand = new Random();
        private Animation animation = new Animation();
        private Bitmap spritesheet2;
        private long startTime;


        public Monsters2(Bitmap res, int x, int y, int w, int h, int s, int numframes)
        {
            super.x = x;
            super.y = y;

            width = w;
            height = h;

            speed = 50;

            Bitmap[] image = new Bitmap[numframes];

            spritesheet2 = res;

            for (int i = 0; i < image.length; i++)
            {
                image[i] = Bitmap.createBitmap(spritesheet2, (i+5)* width, 0, width, height);
                startTime = System.nanoTime();
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

