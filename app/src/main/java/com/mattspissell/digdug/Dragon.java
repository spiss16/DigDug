package com.mattspissell.digdug;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by benda on 12/14/2017.
 */

//WAS USED FOR TESTING ONLY

public class Dragon extends GameObject{
    private Bitmap dragonspritesheet;
    private int score;
    private double dya;
    private double dxa;
    private boolean up;
    private boolean side;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    int ddirection;

    public Dragon(Bitmap res, int w, int h, int numFrames){
        x = 1000;
        y = GameView.SCREEN_HEIGHT/3;
        dy = 0;
        score = 0;
        height = h;
        width = w;

        Bitmap[] image = new Bitmap[numFrames];
        dragonspritesheet = res;

        ddirection = 1;

        if(ddirection == 0) {
            for (int i = 0; i < image.length; i++) {
                image[i] = Bitmap.createBitmap(dragonspritesheet, i * width, 0, width, height);

                animation.setFrames(image);
                animation.setDelay(300);
                startTime = System.nanoTime();

            }}

        else if(ddirection == 1) {
            for (int i = 0; i < image.length; i++) {
                image[i] = Bitmap.createBitmap(dragonspritesheet, (i+8)* width, 0 * height, width, height);

                animation.setFrames(image);
                animation.setDelay(300);
                startTime = System.nanoTime();

            }
        }

    }


    public void setUp(boolean b){up = b;}

    public void update(){
        animation.update();

        // If statements based on user input that declare the dx and dy

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    // If we were to update score within this class we would do this
    // public int getScore(){return score;}

    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetDYA(){dya = 0;}
    public void resetScore(){score = 0;}

}
