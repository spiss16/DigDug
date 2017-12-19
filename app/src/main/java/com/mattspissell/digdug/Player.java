package com.mattspissell.digdug;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Matt on 12/14/2017.
 */

//Animation for Dug

public class Player extends GameObject{
    private Bitmap spritesheet;
    private int score;
    private boolean up, down, left, right;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;

    public Player(Bitmap res, int w, int h, int numFrames){

        //initial settings for coordinates and such
        x = 100;
        y = GameView.SCREEN_HEIGHT/2;
        dy = 0;
        score = 0;
        height = h;
        width = w;

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

         for (int i = 0; i < image.length; i++) {
                //bitmap for Dug facing right
                    image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
                    animation.setFrames(image);
                    animation.setDelay(300);
                    startTime = System.nanoTime();
         }
    }
    //functions to determine Dug's acceleration
    public void setUp(boolean b){up = b;}
    public void setDown(boolean c){down = c;}
    public void setLeft(boolean d){left = d;}
    public void setRight(boolean e){right = e;}

    public void update(){
        animation.update();

        //update the score based on time
        long elapsed = (System.nanoTime()-startTime/1000000);
        if(elapsed>100)
        {
            score++;
            startTime = System.nanoTime();
        }

        //direction of acceleration returned, acceleration determined
        if(up){
            if(y <= ((GameView.SCREEN_HEIGHT)/5)){
                dy = 0;
                dx = 0;
            } else{
                dy = -15;
                dx = -1;
            }
        }
        else if (down){
            if(y > (GameView.SCREEN_HEIGHT-150)){
                dy = 0;
                dx = 0;
            } else{
                dy = 15;
                dx = -1;
            }
        }
        else if (left){
            if(x < 0){
                dy = 0;
                dx = 0;
            } else{
                dy = 0;
                dx = -15;
            }
        }
        else if (right){
            if(x > (GameView.SCREEN_WIDTH-160)){
                dy = 0;
                dx = 0;
            } else{
                dy = 0;
                dx = 15;
            }
        }

        //Capped speed if we decided to increment Dug's speed as the game progressed
        if(dy>15)dy=15;
        if(dy<-15)dy = -15;
        y += dy*2;
        dy = 0;
        if(dx>15)dx=15;
        if(dx<-15)dx = -15;
        x += dx*2;
        dx = 0;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }


    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetDY(){dy = 0;}
    public void resetDX(){dx = 0;}
    public void resetScore(){score = 0;}

}
