package com.mattspissell.digdug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jessica on 12/13/2017.
 */

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing = false;
    Thread gameThread;
    SurfaceHolder surfaceHolder;
    private static final int FPS = 30;
    private static final int TBF = 1000 / FPS;
    private long previousTImeMilliseconds;
    private long currentTimeMilliseconds;
    public static float deltaTime; //seconds
    Point screenSize;
    private static int SCREEN_HEIGHT;
    private static int SCREEN_WIDTH;
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private Paint paint;
    int i;

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public GameView(Context context, Point point) {
        super(context);

        surfaceHolder = this.getHolder();
        screenSize = point;
        SCREEN_HEIGHT = point.y;
        SCREEN_WIDTH = point.x;
        gameObjects.add(new Starfield(100,30.0f));
    }

    @Override
    public void run() {
        previousTImeMilliseconds = System.currentTimeMillis();
        while(playing){
            currentTimeMilliseconds = System.currentTimeMillis();
            deltaTime = (currentTimeMilliseconds - previousTImeMilliseconds) / 1000.0f;
            update();
            draw();
            try{
                gameThread.sleep(TBF);
            }
            catch(InterruptedException e){}
            previousTImeMilliseconds = currentTimeMilliseconds;
        }
    }

    public void pause(){
        playing = false;
        try{
            gameThread.join();
        }
        catch(InterruptedException e){}
    }

    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    void update(){
        for(i = 0; i < gameObjects.size(); i++){
            gameObjects.get(i).onUpdate();
        }
    }

    void draw(){
        if(surfaceHolder.getSurface().isValid()){
            Canvas canvas = surfaceHolder.lockCanvas();
            //Draw
            canvas.drawARGB(255, 0, 0, 0);
            for(i = 0; i < gameObjects.size(); i++){
                gameObjects.get(i).onDraw(canvas);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
