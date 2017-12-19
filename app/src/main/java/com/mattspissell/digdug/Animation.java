package com.mattspissell.digdug;

import android.graphics.Bitmap;

/**
 * Created by Matt on 12/14/2017.
 */

public class Animation {
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    //sets frames for all animation in game
    public void setFrames(Bitmap[] frames){
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }

    //delay for animation frames
    public void setDelay(long d){delay = d;}
    public void setFrame(int i){currentFrame = i;}

    public void update(){
        long elapsed = (System.nanoTime()-startTime)/1000000;

        //next frame of animation
        if(elapsed>delay){
            currentFrame++;
            startTime = System.nanoTime();
        }
        //reset frame
        if(currentFrame == frames.length){
            currentFrame = 0;
            playedOnce = true;
        }
    }
    public Bitmap getImage()
    {
        return frames[currentFrame];
    }
    public int getFrame(){return currentFrame;}
    public boolean playedOnce(){return playedOnce;}
}

