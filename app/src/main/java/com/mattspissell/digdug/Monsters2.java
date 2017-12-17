package com.mattspissell.digdug;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by benda on 12/17/2017.
 */

    public class Monsters2 extends GameObject{

        private int score;
        private int speed;
        private Random rand = new Random();
        private Animation animation = new Animation();
        private Bitmap spritesheet2;


        public Monsters2(Bitmap res, int x, int y, int w, int h, int s, int numframes)
        {
            super.x = x;
            super.y = y;

            width = w;
            height = h;

            speed = 50 + (int) (rand.nextDouble()*score/10);

            //cap the monster speed
            if(speed>150)speed=150;

            Bitmap[] image = new Bitmap[numframes];

            spritesheet2 = res;

            for (int i = 0; i < image.length; i++)
            {
                image[i] = Bitmap.createBitmap(spritesheet2, (i+5)* width, 0, width, height);
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

