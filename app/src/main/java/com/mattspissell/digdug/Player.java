package com.mattspissell.digdug;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Matt on 12/14/2017.
 */

public class Player extends GameObject{
    private Bitmap spritesheet;
    private int score;
    private double dxa;
    private boolean up, down, left, right, horizontal, vertical;
    private boolean side;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    int dir;


    public void setx(boolean x){horizontal = x;}
    public void sety(boolean y){vertical = y;}

    public Player(Bitmap res, int w, int h, int numFrames){
        x = 100;
        y = GameView.SCREEN_HEIGHT/2;
        dy = 0;
        score = 0;
        height = h;
        width = w;
        int dir;





        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

            for (int i = 0; i < image.length; i++) {
                //right
                if(!horizontal && !vertical) {
                    image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
                    animation.setFrames(image);
                    animation.setDelay(300);
                    startTime = System.nanoTime();}
                //up
                    else if(!horizontal && vertical){
                            image[i] = Bitmap.createBitmap(spritesheet, (i+2) * width, 0, width, height);

                            animation.setFrames(image);
                            animation.setDelay(300);
                            startTime = System.nanoTime();

                        }
                        //left
                    else if(horizontal && !vertical) {
                        //for (int i = 0; i < image.length; i++) {
                            image[i] = Bitmap.createBitmap(spritesheet, (i+4) * width, 0, width, height);

                            animation.setFrames(image);
                            animation.setDelay(300);
                            startTime = System.nanoTime();

                        }
                    //down
                    else if(horizontal && vertical)//(direction = digdown)
                     {
                        //for (int i = 0; i < image.length; i++) {
                            image[i] = Bitmap.createBitmap(spritesheet, (i+6) * width, 0, width, height);

                            animation.setFrames(image);
                            animation.setDelay(300);
                            startTime = System.nanoTime();


                    }
                }


    }


    public void setUp(boolean b){up = b;}
    public void setDown(boolean c){down = c;}
    public void setLeft(boolean d){left = d;}
    public void setRight(boolean e){right = e;}


    public void update(){
        animation.update();

        // If statements based on user input that declare the dx and dy
        // If statements based on user input that declare the dx and dy
        if(up){
            dy = -5;
            dx = 0;
        } else if (down){
            dy = 5;
            dx = 0;
        } else if (left){
            dy=0;
            dx = -5;
        } else if (right) {
            dx = 5;
            dy=0;
        } /*else{
            dy = 0;
            dx = (int)(dxa =5);
            direction = 0;
        }*/

        //Capped speed
        if(dy>5)dy=5;
        if(dy<-5)dy = -5;
        y += dy*2;
        dy = 0;
        if(dx>5)dx=5;
        if(dx<-5)dx = -5;
        x += dx*2;
        dx = 0;

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    // If we were to update score within this class we would do this
    // public int getScore(){return score;}

    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetDY(){dy = 0;}
    public void resetDXA(){dxa = 0;}
    public void resetScore(){score = 0;}

}
